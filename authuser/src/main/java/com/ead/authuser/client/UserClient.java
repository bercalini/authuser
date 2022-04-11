package com.ead.authuser.client;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.ResponsePageDTO;
import com.ead.authuser.services.UserUrlCompositer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UserUrlCompositer userUrlCompositer;
    
    public Page<CourseDTO> findCourseById(UUID userId, Pageable pageable) {
        List<CourseDTO> coursesDTOS = null;
        String url = userUrlCompositer.userUrl(userId, pageable);
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};
            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            coursesDTOS = result.getBody().getContent();
            log.debug("Response Number of Elements: {}", coursesDTOS.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId" , userId);
        return new PageImpl<>(coursesDTOS);
    }

}
