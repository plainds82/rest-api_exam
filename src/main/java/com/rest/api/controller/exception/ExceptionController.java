package com.rest.api.controller.exception;

import com.rest.api.advice.exception.CAuthenticationEntrypointException;
import com.rest.api.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntrypointException();
    }

    @GetMapping(value = "/accessDenied")
    public CommonResult AccessDeniedException() {
        System.out.println("ExceptionController.AccessDeniedException");
        throw new AccessDeniedException("");
    }
}
