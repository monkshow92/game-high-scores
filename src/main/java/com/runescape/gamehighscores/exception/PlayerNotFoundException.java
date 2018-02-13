package com.runescape.gamehighscores.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such player")
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(Integer id) {
        super("Player with id : " + id + " not found");
    }

}
