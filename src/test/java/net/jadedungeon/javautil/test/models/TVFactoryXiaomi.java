package net.jadedungeon.javautil.test.models;

public class TVFactoryXiaomi implements TVCompany {
	@Override
	public TV produceTV() {
		System.out.println("TV factory produce TV...");
		return new TV("小米电视机", "合肥", true);
	}

    @Override
    public TV repair(TV tv) {
    	tv.setFunctional(true);
        System.out.println("tv is repair finished...");
        return tv;
    }
}
