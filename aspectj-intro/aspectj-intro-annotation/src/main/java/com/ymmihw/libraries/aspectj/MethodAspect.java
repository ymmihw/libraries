package com.ymmihw.libraries.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MethodAspect {
  private static final Logger logger = LoggerFactory.getLogger(MethodAspect.class);

  @Pointcut("execution(* com.ymmihw.libraries.aspectj.MyClass.privateMethod(..))")
  public void callAtPrivateMethod() {}

  @Pointcut("execution(* com.ymmihw.libraries.aspectj.MyClass.getString(..))")
  public void callAtLombokGetter() {}

  @Pointcut("execution(* org.apache.commons.lang3.StringUtils.isEmpty(..))")
  public void callInJar() {}

  @Around("callAtPrivateMethod() || callAtLombokGetter() || callInJar()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    logger.info("before {}", pjp.getSignature().getName());
    return pjp.proceed();
  }
}
