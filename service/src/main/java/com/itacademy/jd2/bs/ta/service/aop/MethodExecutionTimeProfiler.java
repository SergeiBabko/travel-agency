package com.itacademy.jd2.bs.ta.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MethodExecutionTimeProfiler {

	private static final Logger LOGGER = LoggerFactory.getLogger("MethodExecutionTimeProfiler");

	@Pointcut("execution(* com.itacademy.jd2.bs.ta.service.impl.*.*(..))")
	public void serviceMethods() {
	}

	@Around("serviceMethods()")
	public Object profile(final ProceedingJoinPoint pjp) throws Throwable {
		long start = System.nanoTime();
		Object output = pjp.proceed();
		long executionTime = System.nanoTime() - start;
		LOGGER.info("Method execution: [{}: method \"{}\" = {} microseconds]", pjp.getTarget().getClass().getSimpleName(),
				pjp.getSignature().getName(), executionTime / 1000);
		return output;
	}

}