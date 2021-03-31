package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.OrderItem;
import com.example.rocketmq.model.entity.PraiseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-31 21:26
 */
public interface PraiseRecordRepository extends JpaRepository<PraiseRecord, Integer> {
}
