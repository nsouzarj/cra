package br.adv.cra.entity;

public class OutraParteJSON {
	private String id;
	private String name;

	public OutraParteJSON() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "OutraParteJSON [id=" + id + ", name=" + name + "]";
	}
  
}
