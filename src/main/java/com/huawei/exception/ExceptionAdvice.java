package com.huawei.exception;

import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
@ResponseBody
@ControllerAdvice(annotations = {Controller.class})
public class ExceptionAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Map<String, Object> handleParameterVerificationException(Exception e) {
        Map<String, Object> errorMsg = new HashMap<>(4);
        errorMsg.put("code", 403);
        errorMsg.put("success", false);
        errorMsg.put("msg", "参数校验未通过");
        Map<String, String> detail = new HashMap<>();
        /// BindException
        BindingResult bindingResult=null;
        if (e instanceof BindException) {
           bindingResult = ((BindException) e).getBindingResult();
            // MethodArgumentNotValidException
        } else if (e instanceof MethodArgumentNotValidException) {
             bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            //ValidationException 的子类异常ConstraintViolationException
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintViolations) {
                PathImpl path = (PathImpl) violation.getPropertyPath();
                NodeImpl leafNode = path.getLeafNode();
                String paramName = leafNode.getName();
                String message = violation.getMessage();
                detail.put(paramName,message);
            }
        } else {
            logger.error("处理参数时异常" + e.getMessage());
            errorMsg.put("msg", e.getMessage());
        }
        if (bindingResult!=null){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            // 获取所有的错误信息
            for (int i = 0; i < fieldErrors.size(); i++) {
                detail.put(fieldErrors.get(i).getField(), allErrors.get(i).getDefaultMessage());
            }
        }
        if (detail.size() > 0) {
            errorMsg.put("detail", detail);
        }
        System.out.println(errorMsg);
        return errorMsg;
    }


}
