package com.logrequest.logrequest.backend.repositories;

import com.logrequest.logrequest.backend.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {

}
