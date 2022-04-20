package com.ead.authuser.services.impl;

import com.ead.authuser.services.UserUrlCompositer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserUrlCompositerImpl implements UserUrlCompositer {
    @Override
    public String userUrl(UUID id, Pageable pageable) {
        String url = "/courses?userId=" + id + "&page=" + pageable.getPageNumber() +
                "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        return url;
    }
}
