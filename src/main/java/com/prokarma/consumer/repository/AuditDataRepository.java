package com.prokarma.consumer.repository;

import org.springframework.data.repository.CrudRepository;

import com.prokarma.consumer.entity.AuditEntity;

public interface AuditDataRepository extends CrudRepository<AuditEntity, String> {

}
