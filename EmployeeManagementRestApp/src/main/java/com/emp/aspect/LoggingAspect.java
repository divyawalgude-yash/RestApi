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

	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	Logger logger = Logger.getLogger(LoggingAspect.class.getName());

	@Pointcut(value = "execution(* com.emp.controller.EmployeeController.get*(..))")
	public void applicationControllerPoint() {

	}
	
	@Pointcut(value = "execution(* com.emp.service.EmployeeServiceImpl.get*(..))")
	public void applicationServicePoint() {

	}


	@Before("applicationControllerPoint() or applicationServicePoint()")
	public void beforeLog(JoinPoint joinPoint) {
//		System.out.println("LoggingAspect.beforeLog()");
//		System.out.println("******" + joinPoint.getSignature().getName());
//		System.out.println("******" + joinPoint.getSourceLocation());
		logger.info("LoggingAspect.beforeLog()");
		logger.info("******" + joinPoint.getArgs());

		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
	}

	@AfterThrowing("applicationControllerPoint() or applicationServicePoint()")
	public void afterThrowing(JoinPoint joinPoint) {
//		System.out.println("LoggingAspect.afterThrowing()");
//		System.out.println("******" + joinPoint.getSignature().getName());
//		System.out.println("******" + joinPoint.getSourceLocation());
		logger.info("LoggingAspect.afterThrowing()");
		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
	}

	@After("applicationControllerPoint()")
	public void afterLog(JoinPoint joinPoint) {
//		System.out.println("LoggingAspect.afterLog()");
//		System.out.println("******" + joinPoint.getSignature().getName());
//		System.out.println("******" + joinPoint.getSourceLocation());
		logger.info("LoggingAspect.afterLog()");
		logger.info("******" + joinPoint.getSignature());
		logger.info("******" + joinPoint.getSourceLocation());
		logger.info("******" + joinPoint.getClass().getName());

	}
}
