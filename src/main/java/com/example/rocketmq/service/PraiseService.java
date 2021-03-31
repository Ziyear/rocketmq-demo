package com.example.rocketmq.service;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.model.dto.PraiseRecordDTO;
import com.example.rocketmq.model.entity.PraiseRecord;
import com.example.rocketmq.mq.RocketProducerFactory;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.repository.PraiseRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-31 21:26
 */
@Service
public class PraiseService {
    @Autowired
    private PraiseRecordRepository praiseRecordRepository;

    public void praise(PraiseRecordDTO recordDTO) {
        PraiseRecord praiseRecord = new PraiseRecord();
        praiseRecord.setUserId(recordDTO.getUserId());
        praiseRecord.setCreateTime(new Date());
        praiseRecord.setLiveName(recordDTO.getLiveName());
        praiseRecordRepository.save(praiseRecord);
    }

    public void push(Integer userId, String liveName) {
        PraiseRecordDTO recordDTO = new PraiseRecordDTO();
        recordDTO.setUserId(userId);
        recordDTO.setLiveName(liveName);
        SendObjectDTO sendData = new SendObjectDTO();
        sendData.setBody(JSON.toJSONString(recordDTO));
        RocketProducerFactory.getProduct(MQTypeEnum.PRAISE).send(sendData);
    }
}
