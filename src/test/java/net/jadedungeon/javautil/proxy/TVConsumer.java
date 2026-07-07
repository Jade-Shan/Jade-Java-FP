package net.jadedungeon.javautil.proxy;

import net.jadedungeon.javautil.Proxy.JadeProxyCglib;
import net.jadedungeon.javautil.test.models.TV;
import net.jadedungeon.javautil.test.models.TVCompany;
import net.jadedungeon.javautil.test.models.TVFactoryHuawei;
import net.jadedungeon.javautil.test.models.TVFactoryXiaomi;

public class TVConsumer {

	public static void main(String[] args) {
		JadeProxyCglib tvProxy = new JadeProxyCglib();

		// ====================================

		TVCompany tvCompany = (TVCompany) //
		tvProxy.getProxyInstance(TVFactoryXiaomi.class);
		TV xiaomiTv = tvCompany.produceTV();
		tvCompany.repair(xiaomiTv);

		System.out.println("==============================");

		TVFactoryHuawei tvFactoryB = (TVFactoryHuawei) //
		tvProxy.getProxyInstance(TVFactoryHuawei.class);
		TV huaweiTV = tvFactoryB.produceHuaweiTV();
		tvFactoryB.repairHuaweiTV(huaweiTV);
	}

}
