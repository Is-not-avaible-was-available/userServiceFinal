package com.learning.UserServiceFinal;

import com.learning.UserServiceFinal.Models.User;
import com.learning.UserServiceFinal.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class AddingUserDetailsTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Commit
    public void testAddingUserDetails(){
//        User user = new User();
//        user.setName("rajat");
//        user.setEmail("rajat@gmail.com");
//        user.setPassword(bCryptPasswordEncoder.encode("rajat"));
//        userRepository.save(user);
    }
}
