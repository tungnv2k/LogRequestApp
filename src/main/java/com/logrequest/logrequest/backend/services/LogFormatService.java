package com.logrequest.logrequest.backend.services;

import com.logrequest.logrequest.backend.models.LogFormat;

import java.util.List;

public interface LogFormatService {
    
    List<LogFormat> fetch(int offset, int limit);
    
    Integer count();
}
