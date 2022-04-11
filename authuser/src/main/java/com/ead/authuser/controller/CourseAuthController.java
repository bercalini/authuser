package com.ead.authuser.controller;

import com.ead.authuser.client.UserClient;
import com.ead.authuser.dto.CourseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseAuthController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/users/{userId}/courses")
    public Page<CourseDTO> findCourseById(@PathVariable(value = "userId")UUID userId, @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable) {
        return userClient.findCourseById(userId, pageable);
    }

}
