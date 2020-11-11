package com.prokarma.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.consumer.entity.ErrorEntity;

public interface ErrorDataRepository extends CrudRepository<ErrorEntity, String> {

}
