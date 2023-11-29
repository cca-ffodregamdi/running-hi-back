package com.runninghi.adminpost.query.infrastructure.mongo;

import com.runninghi.adminpost.command.domain.aggregate.document.AdminCourse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminCourseQueryRepository extends MongoRepository<AdminCourse, String> {
}
