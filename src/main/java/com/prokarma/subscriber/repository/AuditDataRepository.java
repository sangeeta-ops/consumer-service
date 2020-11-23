package com.prokarma.subscriber.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.subscriber.entity.Audit;

public interface AuditDataRepository extends CrudRepository<Audit, String> {

}
