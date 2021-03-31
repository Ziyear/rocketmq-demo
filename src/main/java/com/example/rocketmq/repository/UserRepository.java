package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-29 22:14
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
