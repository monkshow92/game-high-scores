package com.runescape.gamehighscores.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Cannot delete 'Overall' Category")
public class OverallCategoryCannotBeDeletedException extends RuntimeException {

    public OverallCategoryCannotBeDeletedException() {
        super("Cannot delete 'Overall' Category");
    }

}
