package com.ymmihw.libraries.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class PrivateMethodAspect {
  private static final Logger logger = LoggerFactory.getLogger(PrivateMethodAspect.class);

  @Pointcut("execution(* com.ymmihw.libraries.aspectj.SecuredMethod.privateMethod(..))")
  public void callAt() {}

  @Around("callAt()")
  public void around(ProceedingJoinPoint pjp) throws Throwable {
    logger.info("before {}", pjp.getSignature().getName());
  }
}
