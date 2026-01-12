package com.bookshelf.adapters.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${services.user-service.url:http://localhost:8082}")
public interface UserServiceFeignClient {

    @GetMapping("/auth/search-user/{id}")
    String getUserById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorization);
}
