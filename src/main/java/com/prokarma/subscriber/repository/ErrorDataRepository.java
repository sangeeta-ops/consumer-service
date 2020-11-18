package com.prokarma.subscriber.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.subscriber.entity.ErrorEntity;

public interface ErrorDataRepository extends CrudRepository<ErrorEntity, String> {

}
