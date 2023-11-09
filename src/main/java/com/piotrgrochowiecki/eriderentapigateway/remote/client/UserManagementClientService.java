package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import org.springframework.http.ResponseEntity;

public interface UserManagementClientService {

    ResponseEntity<?> getByUuid(String uuid);

}
