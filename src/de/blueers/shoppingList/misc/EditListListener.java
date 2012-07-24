package de.blueers.shoppingList.misc;

import java.util.ArrayList;

import de.blueers.shoppingList.models.ShoppingList;


public interface EditListListener {
	public void transactionCancel();
	public void transactionComplete(ArrayList<ShoppingList> list);
}
