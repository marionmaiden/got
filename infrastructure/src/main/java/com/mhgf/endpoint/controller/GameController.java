package com.mhgf.endpoint.controller;

import com.mhgf.domain.aggregate.TurnRequest;
import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.repository.GameMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * @author Mario Freitas
 */
@Controller
public class GameController {

    @Autowired
    GameMap gameMap;

    @MessageMapping("/turn/{id}")
    @SendTo("/response/turn/{id}")
    public TurnResponse play(@DestinationVariable UUID id, TurnRequest turnRequest) throws Exception {
        TurnResponse response = gameMap.getGame(id).play(turnRequest);
        return response;
    }
}
