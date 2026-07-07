package net.jadedungeon.javautil.Proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JadeProxyCglib implements MethodInterceptor {

	// 给目标对象创建一个代理对象
	public Object getProxyInstance(//
			@SuppressWarnings("rawtypes") Class clazz) //
	{
		Enhancer enhancer = new Enhancer(); // 1.工具类
		enhancer.setSuperclass(clazz);      // 2.设置父类
		enhancer.setCallback(this);         // 3.设置回调函数
		return enhancer.create();           // 4.创建子类（代理对象）
	}

	@Override
	public Object intercept(//
			Object o, Method method, Object[] objects, MethodProxy methodProxy//
		) throws Throwable //
	{
		System.out.println("TVProxyFactory enhancement.....");
		Object object = methodProxy.invokeSuper(o, objects);
		return object;
	}

}
