package com.evi.teamfindergroupsmanagement.exception;

import lombok.Getter;

@Getter
public class AlreadyInGroupException extends RuntimeException{
    private final String code = "5";

    public AlreadyInGroupException(String message){super(message);}

}
