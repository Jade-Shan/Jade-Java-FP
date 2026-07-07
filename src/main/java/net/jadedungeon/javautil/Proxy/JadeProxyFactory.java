package net.jadedungeon.javautil.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JadeProxyFactory {

	private Object target;

	public JadeProxyFactory(Object o) {
		this.target = o;
	}

	public Object getProxy() {
		ClassLoader loader = this.getClass().getClassLoader();
		Class<?>[] interfaces = target.getClass().getInterfaces();
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) 
					throws Throwable 
			{
				System.out.println("TV proxy find factory for tv.... ");
				Object invoke = method.invoke(target, args);
				return invoke;
			}
		};

		return Proxy.newProxyInstance(loader, interfaces, handler);
	}

}
