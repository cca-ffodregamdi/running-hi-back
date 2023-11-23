package com.runninghi.adminpost.command.domain.mongo;

import com.runninghi.adminpost.command.domain.aggregate.document.AdminCourse;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminCourseCommandRepository extends MongoRepository<AdminCourse, String> {

    Optional<AdminCourse> findByAdminPostNo(Long adminPostNo);
}
