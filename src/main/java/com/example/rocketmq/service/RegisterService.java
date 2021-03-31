package com.example.rocketmq.service;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.common.BizTypeEnum;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.model.entity.User;
import com.example.rocketmq.mq.RocketProducerFactory;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-29 22:10
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    public void register(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        userRepository.save(user);
        // 发送mq消息 给新用户发送优惠卷
        SendObjectDTO sendData = new SendObjectDTO();
        sendData.setBizId(user.getId());
        sendData.setBizType(BizTypeEnum.COUPONS_FOR_NEWCOMERS.getCode());
        RocketProducerFactory.getProduct(MQTypeEnum.DEFAULT).send(sendData);
    }
}
