package com.logrequest.logrequest.backend.services.impl;

import com.logrequest.logrequest.backend.models.LogFormat;
import com.logrequest.logrequest.backend.repositories.LogFormatRepository;
import com.logrequest.logrequest.backend.services.LogFormatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogFormatServiceImpl implements LogFormatService {

    private final LogFormatRepository logFormatRepository;

    public LogFormatServiceImpl(LogFormatRepository logFormatRepository) {
        this.logFormatRepository = logFormatRepository;
    }

    @Override
    public List<LogFormat> fetch(int offset, int limit) {
        return logFormatRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Integer count() {
        return Math.toIntExact(logFormatRepository.count());
    }
}
