package com.runninghi.user.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserCommandControllerTest {
    @Autowired
    private UserCommandController userCommandController;
}
