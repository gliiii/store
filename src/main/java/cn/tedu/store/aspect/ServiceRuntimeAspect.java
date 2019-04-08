package cn.tedu.store.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceRuntimeAspect {
	
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object a(ProceedingJoinPoint pjp) throws Throwable {
		//记录开始时间
		long start=System.currentTimeMillis();
		
		//执行切面所应用的方法
		Object result=pjp.proceed();
		
		//记录结束时间
		long end=System.currentTimeMillis();
		System.err.println("时间："+(end-start));
		
		//返回
		return result;
	}
}
