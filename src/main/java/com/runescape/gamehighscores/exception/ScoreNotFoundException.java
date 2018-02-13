package com.runescape.gamehighscores.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such score")
public class ScoreNotFoundException extends RuntimeException{

    public ScoreNotFoundException(Integer id) {
        super("Score with id : " + id + " not found");
    }

}
