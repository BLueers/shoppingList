package de.blueers.shoppingList.persistence;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.models.ShoppingList;

public class MyDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allItemsColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LIST_REFERENCE, 
			MySQLiteHelper.COLUMN_ITEM_NAME};

	private String[] allListColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LIST_NAME };

	
	public MyDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	
	public ShoppingItem createItem(String item, long listId) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ITEM_NAME, item);
		values.put(MySQLiteHelper.COLUMN_LIST_REFERENCE, listId);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_SHOPPING_ITEM, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_ITEM,
				allItemsColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ShoppingItem newShoppingItem = cursorToShoppingItem(cursor);
		cursor.close();
		return newShoppingItem;
	}
	public ShoppingList createList(String shoppingList) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_LIST_NAME, shoppingList);
		long insertId = database.insert(MySQLiteHelper.TABLE_SHOPPING_LISTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_LISTS,
				allListColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ShoppingList newshoppingList = cursorToShoppingList(cursor);
		cursor.close();
		return newshoppingList;
	}

	public void deleteItem(ShoppingItem shoppingItem) {
		long id = shoppingItem.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_SHOPPING_ITEM, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}
	public void deleteList(ShoppingList shoppingList) {
		long id = shoppingList.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.COLUMN_LIST_NAME, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}


	public ArrayList<ShoppingItem> getAllItems(long listId) { 
		ArrayList<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_ITEM
				, allItemsColumns
				, MySQLiteHelper.COLUMN_LIST_REFERENCE+ " = " + listId 
				, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ShoppingItem shoppingItem = cursorToShoppingItem(cursor);
			shoppingItems.add(shoppingItem);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return shoppingItems;
	}
	public ArrayList<ShoppingList> getAllLists() { 
		ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_LISTS,
				allListColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ShoppingList shoppingList = cursorToShoppingList(cursor);
			shoppingLists.add(shoppingList);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return shoppingLists;
	}

	private ShoppingItem cursorToShoppingItem(Cursor cursor) {
		ShoppingItem shoppingItem = new ShoppingItem();
		shoppingItem.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
		shoppingItem.setName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ITEM_NAME)));
		shoppingItem.setListId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LIST_REFERENCE)));
		return shoppingItem;
	}
	private ShoppingList cursorToShoppingList(Cursor cursor) {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID )));
		shoppingList.setName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LIST_NAME )));
		return shoppingList;
	}
} 