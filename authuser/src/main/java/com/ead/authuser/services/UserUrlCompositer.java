package com.ead.authuser.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserUrlCompositer {
     String userUrl(UUID id, Pageable pageable);
}
