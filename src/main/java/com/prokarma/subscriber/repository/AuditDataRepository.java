package com.prokarma.subscriber.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.subscriber.entity.AuditEntity;

public interface AuditDataRepository extends CrudRepository<AuditEntity, String> {

}
