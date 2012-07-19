package de.blueers.shoppingList.models;

public class ShoppingList {

	private String name;
	private Long id;
	public ShoppingList() {
	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
