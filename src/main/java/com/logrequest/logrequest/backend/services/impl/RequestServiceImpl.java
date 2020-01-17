package com.logrequest.logrequest.backend.services.impl;

import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.repositories.RequestRepository;
import com.logrequest.logrequest.backend.services.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> fetch(int offset, int limit) {
        return requestRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Integer count() {
        return Math.toIntExact(requestRepository.count());
    }
}
