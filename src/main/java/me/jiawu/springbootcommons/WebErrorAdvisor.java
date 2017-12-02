package me.jiawu.springbootcommons;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebErrorAdvisor extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebErrorAdvisor.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        logger.error("have exception here");
        return new ResponseEntity(ex.getMessage(), HttpStatus.valueOf(200));
    }
}