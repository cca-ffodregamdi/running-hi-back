package com.runninghi.common.annotation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Primary
public @interface DomainService {
}
