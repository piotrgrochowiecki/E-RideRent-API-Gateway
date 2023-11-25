package com.piotrgrochowiecki.eriderentapigateway.helper;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityHelper {

    public ResponseEntity<?> transformResponseEntity(ResponseEntity<?> responseEntityFromService) {
        if (responseEntityFromService != null) {
            return ResponseEntity.status(responseEntityFromService.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseEntityFromService.getBody());
        } else {
            throw new RuntimeException("Did not receive response from server");
        }
    }

}
