package com.tsunglin.tsunglin00.config;

import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.util.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局異常處理
 */
@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(BindException.class)
    public Object bindException(BindException e) {
        Result result = new Result();
        result.setResultCode(510);
        BindingResult bindingResult = e.getBindingResult();
        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e) {
        Result result = new Result();
        result.setResultCode(510);
        BindingResult bindingResult = e.getBindingResult();
        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(java.lang.Exception.class)
    public Object handleException(java.lang.Exception e, HttpServletRequest req) {
        Result result = new Result();
        result.setResultCode(500);
        //區分是否為自定義異常
        if (e instanceof Exception) {
            result.setMessage(e.getMessage());
            if (e.getMessage().equals(ServiceResultEnum.NOT_LOGIN_ERROR.getResult()) || e.getMessage().equals(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult())) {
                result.setResultCode(416);
            } else if (e.getMessage().equals(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult()) || e.getMessage().equals(ServiceResultEnum.ADMIN_TOKEN_EXPIRE_ERROR.getResult())) {
                result.setResultCode(419);
            }
        } else {
            e.printStackTrace();
            result.setMessage("未知異常，請查看控制台日誌並檢查配置文件。");
        }
        return result;

    }
}
