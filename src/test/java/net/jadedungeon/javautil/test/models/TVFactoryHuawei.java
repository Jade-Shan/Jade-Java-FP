package net.jadedungeon.javautil.test.models;

public class TVFactoryHuawei {

	public TV produceHuaweiTV() {
		System.out.println("华为TV factory produce TV...");
		return new TV("华为电视机", "合肥", true);
	}

    public TV repairHuaweiTV(TV tv) {
    	tv.setFunctional(true);
        System.out.println("华为电视 is repair finished...");
        return tv;
    }

}
