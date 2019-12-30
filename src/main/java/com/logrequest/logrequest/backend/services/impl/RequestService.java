package com.logrequest.logrequest.backend.services.impl;

import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.repositories.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll(int offset, int limit) {
        List<Request> items = requestRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        return items;
    }

    public Integer count() {
        return Math.toIntExact(requestRepository.count());
    }
}
