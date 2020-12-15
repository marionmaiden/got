package com.mhgf.endpoint.controller;

import com.mhgf.domain.repository.PlayersPoll;
import com.mhgf.domain.aggregate.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mario Freitas
 */
@RestController
public class PlayerController {

    @Autowired
    PlayersPoll playersPoll;

    @GetMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlayerResponse registerPlayer() {
        return playersPoll.addPlayer();
    }

}
