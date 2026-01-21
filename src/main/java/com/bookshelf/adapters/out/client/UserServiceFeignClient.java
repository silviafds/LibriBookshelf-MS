package com.bookshelf.adapters.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Feign client responsible for communicating with the User Service.
 *
 * This interface provides a declarative HTTP client used to retrieve
 * user information from the User Service.
 *
 * It forwards the Authorization header to maintain security context
 * across microservices.
 */
@FeignClient(name = "user-service", url = "${services.user-service.url:http://localhost:8082}")
public interface UserServiceFeignClient {

    /**
     * Retrieves user information by user ID.
     *
     * @param id user identifier
     * @param authorization JWT authorization header
     * @return user information
     */
    @GetMapping("/auth/search-user/{id}")
    String getUserById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorization);
}
