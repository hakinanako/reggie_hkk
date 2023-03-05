package org.com.hkk.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

//全局的异常处理  ——> AOP
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){

        boolean duplicate_entry = exception.getMessage().contains("Duplicate entry");
        if(duplicate_entry) {
            String[] s = exception.getMessage().split(" ");
            String msg = s[2]+"已存在";
            log.error("失败：{}", exception.getMessage());
            return R.error(msg);
        }
       return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception){

        return R.error(exception.getMessage());
    }
}
