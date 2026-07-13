package net.jadedungeon.javautil.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于 JDK 动态代理的代理工厂。
 * <p>
 * 使用 {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}
 * 创建基于接口的动态代理对象。目标对象必须实现至少一个接口，
 * 代理会在方法调用前后插入额外的处理逻辑。
 * </p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * SomeInterface target = new SomeInterfaceImpl();
 * SomeInterface proxy = (SomeInterface) new JadeProxyFactory(target).getProxy();
 * proxy.someMethod();  // 调用会被拦截并打印日志
 * }</pre>
 *
 * <p>限制：只能代理基于接口的方法调用，不能代理非接口的类方法。
 * 如需基于类继承的代理，请使用 {@link JadeProxyCglib}。</p>
 */
public class JadeProxyFactory {

	/** 被代理的目标对象 */
	private Object target;

	/**
	 * 构造代理工厂。
	 *
	 * @param o 被代理的目标对象（必须至少实现一个接口）
	 */
	public JadeProxyFactory(Object o) {
		this.target = o;
	}

	/**
	 * 创建代理对象。
	 * <p>
	 * 生成的代理对象实现了目标对象的所有接口，
	 * 方法调用会通过内部的 {@link InvocationHandler} 拦截处理。
	 * </p>
	 *
	 * @return 代理对象（可强制转型为目标对象实现的接口类型）
	 */
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
