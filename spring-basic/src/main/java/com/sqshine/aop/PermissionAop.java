package com.sqshine.aop;

import com.sqshine.annotation.Permission;
import com.sqshine.exception.MyException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAop {
    @Pointcut(value = "@annotation(com.sqshine.annotation.Permission)")
    private void cutPermission() {

    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            String[] permissions = permission.value();
            //检查角色内容
            if (permissions.length == 0) {
                throw new MyException("permissions not allowed");
            }
        }
        return point.proceed();

    }
}