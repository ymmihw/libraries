package com.ymmihw.libraries.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoadingTimeAspect {
  private static final Logger logger = LoggerFactory.getLogger(LoadingTimeAspect.class);

  @Pointcut("execution(* org.apache.commons.lang3.StringUtils.trimToNull(..))")
  public void callTrimToNull() {}

  @Around("callTrimToNull()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    logger.info("before {}", pjp.getSignature().getName());
    return pjp.proceed();
  }
}
