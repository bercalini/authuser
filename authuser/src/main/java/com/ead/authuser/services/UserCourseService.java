package com.ead.authuser.services;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.input.CourseUserInput;
import com.ead.authuser.models.UserCourseModel;

import java.util.UUID;

public interface UserCourseService {
    UserCourseModel save(CourseUserInput courseUserInput, UUID userId);
}
