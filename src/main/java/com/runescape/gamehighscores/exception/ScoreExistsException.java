package com.runescape.gamehighscores.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Player score")
public class ScoreExistsException extends RuntimeException {

    public ScoreExistsException(Integer playerId, String categoryName, boolean present) {
        super("Player with id: " + playerId + (present ? " already has" : " does not have")+
                " score for category: " + categoryName);
    }

}
