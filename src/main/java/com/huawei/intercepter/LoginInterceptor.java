package com.huawei.intercepter;


import com.huawei.annotation.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // HandlerMethod.class 是不是 handler.getClass() 的子类或者子接口
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 方法上是否有授权注解
            Authorization annotation = handlerMethod.getMethodAnnotation(Authorization.class);
            if (annotation==null){
                // 方法所属类上是否有授权注解
                annotation= handlerMethod.getMethod().getDeclaringClass().getAnnotation(Authorization.class);
                if (annotation==null) {
                    //访问的方法不需要授权 放行
                    return true;
                }
            }
            String[] value = annotation.value();
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
            //需要授权  验证权限
             logger.info("需要授权,验证权限是否足够");
            //权限符合 放行
        }
        return true;
    }
}
