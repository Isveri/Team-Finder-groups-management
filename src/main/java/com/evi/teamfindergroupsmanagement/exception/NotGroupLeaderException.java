package com.evi.teamfindergroupsmanagement.exception;

import lombok.Getter;

@Getter
public class NotGroupLeaderException extends RuntimeException{

    private final String code = "3";

    public NotGroupLeaderException(String message){super(message);}
}
