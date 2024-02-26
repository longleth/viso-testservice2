package com.example.testoutboundservice2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class OutboundController {

    private final String requestUri = "http://localhost:8902/test";
    private final String clientRegistrationId = "outbound-oauth2-client";
    private WebClient webClient;

    @GetMapping(value = "/outbound")
    public ResponseEntity<Object> hello(
            @RegisteredOAuth2AuthorizedClient(clientRegistrationId)
            OAuth2AuthorizedClient authorizedClient) {

        return webClient.get()
                .uri(requestUri)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
