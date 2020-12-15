package com.mhgf.endpoint.controller;

import com.mhgf.domain.aggregate.TurnRequest;
import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.repository.GameMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Mario Freitas
 */
@Controller
public class GameController {

    @Autowired
    GameMap gameMap;

    @MessageMapping("/turn")
    @SendTo("/response/turn")
    public TurnResponse play(TurnRequest turnRequest) throws Exception {
        TurnResponse response = gameMap.getGame(turnRequest.getGameId()).play(turnRequest);
        return response;
    }
}
