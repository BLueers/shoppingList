package de.blueers.shoppingList.models;

public class ShoppingItem {

	private String name;
	private Long id;
	private Long listId;

	public ShoppingItem() {
		
	}

	public Long getListId() {
		return listId;
	}
	public void setListId(Long listId) {
		this.listId = listId;
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
