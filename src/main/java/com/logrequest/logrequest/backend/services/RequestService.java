package com.logrequest.logrequest.backend.services;

import com.logrequest.logrequest.backend.models.Request;

import java.util.List;

public interface RequestService {

    List<Request> fetch(int offset, int limit);

    Integer count();
}
