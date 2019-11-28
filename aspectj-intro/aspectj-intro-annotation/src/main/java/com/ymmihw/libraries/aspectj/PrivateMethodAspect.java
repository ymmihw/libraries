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
  public void callAt2() {}

  @Pointcut("execution(* com.ymmihw.libraries.aspectj.SecuredMethod.getString(..))")
  public void callAt1() {}

  @Around("callAt2() || callAt1()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    logger.info("before {}", pjp.getSignature().getName());
    return pjp.proceed();
  }
}
