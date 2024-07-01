package com.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 应用上下文管理
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return applicationContext != null ? applicationContext.getBean(beanName) : null;
	}
}
