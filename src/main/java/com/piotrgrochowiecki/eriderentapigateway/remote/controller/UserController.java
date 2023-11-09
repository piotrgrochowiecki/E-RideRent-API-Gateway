package com.piotrgrochowiecki.eriderentapigateway.remote.controller;

import com.piotrgrochowiecki.eriderentapigateway.remote.client.UserManagementClientService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserManagementClientService userManagementClientService;

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<?> getByUuid(@PathVariable @NotBlank String uuid) {
        return userManagementClientService.getByUuid(uuid);
    }

}
