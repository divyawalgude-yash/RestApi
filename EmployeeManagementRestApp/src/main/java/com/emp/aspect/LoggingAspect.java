package com.emp.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	Logger logger = Logger.getLogger(LoggingAspect.class.getName());

	@Pointcut(value = "execution(* com.emp.controller.EmployeeController.get*(..)) || execution(* com.emp.service.EmployeeServiceImpl.get*(..))")
	public void applicationPoint() {

	}

	@Before("applicationPoint()")
	public void beforeLog(JoinPoint joinPoint) {

		logger.info("LoggingAspect.beforeLog()");
		logger.info("******" + joinPoint.getArgs());

		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
	}

	@AfterThrowing("applicationPoint()")
	public void afterThrowing(JoinPoint joinPoint) {

		logger.info("LoggingAspect.afterThrowing()");
		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
	}

	@After("applicationPoint()")
	public void afterLog(JoinPoint joinPoint) {

		logger.info("LoggingAspect.afterLog()");
		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
		logger.info("******" + joinPoint.getClass().getName());

	}
}
