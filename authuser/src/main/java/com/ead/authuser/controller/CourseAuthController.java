package com.ead.authuser.controller;

import com.ead.authuser.client.CourseClient;
import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.input.CourseUserInput;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseAuthController {

    @Autowired
    private CourseClient userClient;

    @Autowired
    private UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public Page<CourseDTO> findCourseById(@PathVariable(value = "userId")UUID userId, @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable) {
        return userClient.findCourseById(userId, pageable);
    }

    @PostMapping("/users/{userId}/courses/save")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCourseModel saveUserInCourse(@PathVariable(value = "userId")UUID userId, @RequestBody @Valid CourseUserInput courseUserInput) {
        return userCourseService.save(courseUserInput, userId);
    }

}
