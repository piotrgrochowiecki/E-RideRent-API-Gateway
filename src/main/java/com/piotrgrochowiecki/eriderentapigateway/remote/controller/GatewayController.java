package com.piotrgrochowiecki.eriderentapigateway.remote.controller;

import com.piotrgrochowiecki.eriderentapigateway.remote.service.ForwardingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GatewayController {

    private final ForwardingService forwardingService;

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<?> handleGetRequest(HttpServletRequest request) {
        return forwardingService.forward(request);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public ResponseEntity<?> handlePostRequest(HttpServletRequest request,
                                              @RequestBody String body) {
        return forwardingService.forward(request, body);
    }
}
