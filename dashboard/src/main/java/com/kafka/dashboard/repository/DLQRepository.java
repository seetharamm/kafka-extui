package com.kafka.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafka.dashboard.entity.DLQEntitty;

@Repository
public interface DLQRepository extends JpaRepository<DLQEntitty, Long> {

}
