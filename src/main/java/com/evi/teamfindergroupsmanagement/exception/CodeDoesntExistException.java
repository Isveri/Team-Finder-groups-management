package com.evi.teamfindergroupsmanagement.exception;


import lombok.Getter;

@Getter
public class CodeDoesntExistException extends RuntimeException{
    private final String code = "4";

    public CodeDoesntExistException(String message) {
        super(message);
    }

}
