package com.example.EagleActivities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ActivityNotFoundAdvice {
@ResponseBody
    @ExceptionHandler(ActivityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String activityNotFoundHandler(ActivityNotFoundException exception){
    return  exception.getMessage();
}
}
