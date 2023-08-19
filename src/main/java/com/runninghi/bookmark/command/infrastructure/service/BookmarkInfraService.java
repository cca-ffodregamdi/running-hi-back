package com.runninghi.bookmark.command.infrastructure.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.annotation.InfraService;

import java.lang.annotation.Annotation;

@InfraService
public class BookmarkInfraService implements DomainService {
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
