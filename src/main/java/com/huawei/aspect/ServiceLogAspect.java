package com.huawei.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ServiceLogAspect {
     Logger logger = LoggerFactory.getLogger(this.getClass());

     @Pointcut("execution(* com.huawei.controller.*.*(..))")
     public void webLog(){}

     @After("webLog()")
     public void logRecords(JoinPoint joinPoint){
       ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request = attributes.getRequest();
         String ip=request .getRemoteHost();
         Signature signature = joinPoint.getSignature();
         String typeName = signature.getDeclaringTypeName();
         String methodName = signature.getName();
         String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
         MethodSignature methodSignature = (MethodSignature) signature;
         String[] strings = methodSignature.getParameterNames();
         Object[] args = joinPoint.getArgs();
         Map<String,String> map = new HashMap<>();
         for (int i = 0; i < strings.length; i++) {
              Object o= args[i];
              if (ObjectUtils.isEmpty(o)){
                  map.put(strings[i],"null");
              }else {
                  map.put(strings[i], JSON.toJSONString(args[i]));
              }
         }
         String info = String.format("用户[%s] %s 访问了[%s.%s]\n携带的参数%s",
                 ip, dateStr , typeName, methodName,map);
         logger.info(info);
     }
}
