package net.jadedungeon.javautil.test.models;

public class TV {

	private String name;// 名称

	private String address;// 生产地

	private Boolean functional; // 能否正常工作

	public TV(String name, String address, Boolean functional) {
		super();
		this.name = name;
		this.address = address;
		this.functional = functional;
	}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public Boolean getFunctional() { return functional; }

	public void setFunctional(Boolean functional) { this.functional = functional; }

	@Override
	public String toString() {
		return "TV [name=" + name + ", address=" + address + 
				", functional=" + functional + "]";
	}

}