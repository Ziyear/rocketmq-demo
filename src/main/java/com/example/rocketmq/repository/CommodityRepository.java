package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
}
