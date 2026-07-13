package net.jadedungeon.javautil.Proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 基于 CGLib 的代理工厂。
 * <p>
 * 通过 CGLib 字节码增强库创建基于类继承的动态代理对象。
 * 与 {@link JadeProxyFactory}（JDK 动态代理）不同，CGLib 代理不要求
 * 目标对象实现接口，可以直接代理基于类的继承关系。
 * </p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * SomeClass proxy = (SomeClass) new JadeProxyCglib().getProxyInstance(SomeClass.class);
 * proxy.someMethod();  // 调用会被拦截
 * }</pre>
 *
 * <p>实现了 {@link MethodInterceptor} 接口，
 * {@link #intercept(Object, Method, Object[], MethodProxy)} 方法会在
 * 代理对象调用时被触发。</p>
 *
 * @see JadeProxyFactory
 */
public class JadeProxyCglib implements MethodInterceptor {

	/**
	 * 为目标类创建 CGLib 代理对象。
	 * <p>
	 * 过程：
	 * </p>
	 * <ol>
	 *   <li>创建 {@link Enhancer} 工具类实例</li>
	 *   <li>设置父类（被代理的目标类）</li>
	 *   <li>设置回调函数（this，即当前 MethodInterceptor）</li>
	 *   <li>创建子类代理对象并返回</li>
	 * </ol>
	 *
	 * @param clazz 被代理的目标类
	 * @return 代理对象（目标类的子类实例）
	 */
	public Object getProxyInstance(//
			@SuppressWarnings("rawtypes") Class clazz) //
	{
		Enhancer enhancer = new Enhancer(); // 1.工具类
		enhancer.setSuperclass(clazz);      // 2.设置父类
		enhancer.setCallback(this);         // 3.设置回调函数
		return enhancer.create();           // 4.创建子类（代理对象）
	}

	/**
	 * 拦截代理对象上的方法调用。
	 * <p>
	 * 在目标对象的方法执行前/后可以插入额外的处理逻辑。
	 * 当前实现简单地委托给父类方法（{@code methodProxy.invokeSuper}）。
	 * </p>
	 *
	 * @param o           代理对象
	 * @param method      被调用的原始方法
	 * @param objects     方法参数
	 * @param methodProxy CGLib 方法代理（用于调用父类实现）
	 * @return 方法调用的返回值
	 * @throws Throwable 如果方法执行过程中发生异常
	 */
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
