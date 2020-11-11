package com.prokarma.consumer.repository;

import org.springframework.data.repository.CrudRepository;

import com.prokarma.consumer.entity.CustomerEntity;

public interface KafkaDataRepository extends CrudRepository<CustomerEntity, Integer> {

}
