package com.evi.teamfindergroupsmanagement.exception.handler;

import com.evi.teamfindergroupsmanagement.exception.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorCodeMsg> userNotFound(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorCodeMsg> groupNotFound(GroupNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler(NotGroupLeaderException.class)
    public ResponseEntity<ErrorCodeMsg> notGroupLeader(NotGroupLeaderException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler({CodeDoesntExistException.class})
    public ResponseEntity<ErrorCodeMsg> codeDoesntExist(CodeDoesntExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler({AlreadyInGroupException.class})
    public ResponseEntity<ErrorCodeMsg> alreadyInGroup(AlreadyInGroupException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorCodeMsg> usernameNotFound(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code("6").build());
    }

}
