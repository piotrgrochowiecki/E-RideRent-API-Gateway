package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserManagementClientServiceImpl implements UserManagementClientService {

    private final WebClient userManagementClient;

    @Value("${url.userManagement}")
    private String USER_MANAGEMENT_URL;

    @Value("${url.userManagement.user}")
    private String USER_MANAGEMENT_USER_CONTROLLER;

    @Value("${url.userManagement.user.getByUuid}")
    private String USER_MANAGEMENT_USER_GET_BY_UUID;

    @Value("${url.userManagement.user.getByEmail}")
    private String USER_MANAGEMENT_USER_GET_BY_EMAIL;

    @Value("${url.userManagement.user.create}")
    private String USER_MANAGEMENT_USER_CREATE;

    private final String USER_CONTROLLER_URL = USER_MANAGEMENT_URL + USER_MANAGEMENT_USER_CONTROLLER;

    public UserManagementClientServiceImpl(WebClient userManagementClient) {
        this.userManagementClient = userManagementClient;
    }

    @Override
    public ResponseEntity<?> getByUuid(String uuid) {
        String endpoint = USER_CONTROLLER_URL + USER_MANAGEMENT_USER_GET_BY_UUID + uuid;

        return userManagementClient.get()
                .uri(endpoint + uuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(UserResponseDto.class)
                .block();
    }

}
