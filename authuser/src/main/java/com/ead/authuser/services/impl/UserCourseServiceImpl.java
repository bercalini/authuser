package com.ead.authuser.services.impl;

import com.ead.authuser.exception.UserExistsInCourseException;
import com.ead.authuser.exception.UserNotFoundException;
import com.ead.authuser.input.CourseUserInput;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private UserService userService;

    @Override
    public UserCourseModel save(CourseUserInput courseUserInput, UUID userId) {
        UserModel userModel = userService.findById(userId).orElseThrow(() -> new UserNotFoundException("User com id  not found" + userId));
        if (userCourseRepository.existsByUserAndCourseId(userModel, courseUserInput.getCourseId())) {
            throw new UserExistsInCourseException("User Exists " + userId + " in Course " + courseUserInput.getCourseId());
        }
        UserCourseModel userCourseModel = UserCourseModel.builder().user(userModel).courseId(courseUserInput.getCourseId()).build();
        return userCourseRepository.save(userCourseModel);
    }
}
