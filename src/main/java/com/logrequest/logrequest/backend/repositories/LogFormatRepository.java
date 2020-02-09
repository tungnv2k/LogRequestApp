package com.logrequest.logrequest.backend.repositories;

import com.logrequest.logrequest.backend.models.LogFormat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogFormatRepository extends MongoRepository<LogFormat, String> {

    LogFormat findByName(String name);

    List<LogFormat> findAllByName(String name);
}
