package com.learning.UserServiceFinal.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.UserServiceFinal.Clients.KafkaProducerClient;
import com.learning.UserServiceFinal.DTOs.SendEmailDTO;
import com.learning.UserServiceFinal.DTOs.UserDTO;
import com.learning.UserServiceFinal.Exceptions.AlreadyExistsException;
import com.learning.UserServiceFinal.Exceptions.BadCredentialsException;
import com.learning.UserServiceFinal.Exceptions.InvalidSessionException;
import com.learning.UserServiceFinal.Exceptions.NotFoundException;
import com.learning.UserServiceFinal.Models.Role;
import com.learning.UserServiceFinal.Models.Session;
import com.learning.UserServiceFinal.Models.SessionStatus;
import com.learning.UserServiceFinal.Models.User;
import com.learning.UserServiceFinal.Repositories.SessionRepository;
import com.learning.UserServiceFinal.Repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final KafkaProducerClient kafkaProducerClient;
    private final ObjectMapper objectMapper;

    public AuthService(UserRepository userRepository,
                       SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder
                 ,ObjectMapper objectMapper, KafkaProducerClient kafkaProducerClient
    ) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = Jwts.SIG.HS256.key().build();
        this.objectMapper = objectMapper;
        this.kafkaProducerClient = kafkaProducerClient;
    }

    public UserDTO signUp(String email, String name, String password) throws AlreadyExistsException {
        Optional<User> optionalUser= userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            throw new AlreadyExistsException("Email already registered!");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User saved = userRepository.save(user);

        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setFrom("yadraj1803@gmail.com");
        sendEmailDTO.setTo(user.getEmail());
        sendEmailDTO.setSubject("Welcome");
        sendEmailDTO.setMessage("Welcome to my app, good to see you here!");

        try {
            kafkaProducerClient.sendMessage("sendEmailToUser",
                    objectMapper.writeValueAsString(sendEmailDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UserDTO.from(saved);
    }

    public ResponseEntity<UserDTO> login(String email, String password) throws NotFoundException, BadCredentialsException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found!");
        }

        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Username/password doesn't match!");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("name", user.getName());
        claims.put("expiryAt", new Date(LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.ofHours(18))));
//        claims.put("roles", user.getRoles());
        String token = Jwts
                .builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();

//        String token = RandomStringUtils.randomAlphanumeric(30);
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);
        UserDTO userDTO = UserDTO.from(user);
        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token: " + token);
        return new ResponseEntity<UserDTO>(userDTO, headers, HttpStatus.OK);
    }

    public SessionStatus validate(String token, Long userId){
        Optional<Session> optionalSession = sessionRepository.findSessionByTokenAndUser_Id(token, userId);
        if(optionalSession.isEmpty()){
            return SessionStatus.ENDED;
        }
        Session session = optionalSession.get();
        if(session.getSessionStatus().equals(SessionStatus.ENDED)){
            return SessionStatus.ENDED;
        }
        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        String email = (String) claimsJws.getPayload().get("email");
        Date expiry = claimsJws.getPayload().getExpiration();

        System.out.println(email);
        System.out.println(expiry);
        System.out.println("debug");

        return SessionStatus.ACTIVE;
    }

    public void logout(String token, Long userId) {
        Optional<Session> optionalSession = sessionRepository
                .findSessionByTokenAndUser_Id(token, userId);
        if(optionalSession.isEmpty()){
            return;
        }
        Session session = optionalSession.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }
}
