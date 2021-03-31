package com.example.rocketmq;

import com.example.rocketmq.model.entity.User;
import com.example.rocketmq.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class RocketmqDemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest() throws Exception {
        User user = new User();
        user.setName("test");
        user.setPassword("12345");
        User result = userRepository.save(user);
        Assert.notNull(result.getId());
    }

}
