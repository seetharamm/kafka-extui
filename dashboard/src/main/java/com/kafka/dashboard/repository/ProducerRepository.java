package com.kafka.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafka.dashboard.entity.ProducerEntity;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, Long>{

}
