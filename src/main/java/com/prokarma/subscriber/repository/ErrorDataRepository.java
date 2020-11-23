package com.prokarma.subscriber.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.subscriber.entity.Error;

public interface ErrorDataRepository extends CrudRepository<Error, String> {

}
