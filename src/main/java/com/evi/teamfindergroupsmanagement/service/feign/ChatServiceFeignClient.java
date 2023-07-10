package com.evi.teamfindergroupsmanagement.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "chat-service")
public interface ChatServiceFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/chat/{groupId}")
    ResponseEntity<Long> createChat(@PathVariable Long groupId);

}
