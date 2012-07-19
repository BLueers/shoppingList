package de.blueers.shoppingList.persistence;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import de.blueers.shoppingList.models.ShoppingList;

public class ShoppingListsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LIST_NAME };

	public ShoppingListsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ShoppingList createList(String shoppingList) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_LIST_NAME, shoppingList);
		long insertId = database.insert(MySQLiteHelper.TABLE_SHOPPING_LISTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_LISTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ShoppingList newshoppingList = cursorToShoppingList(cursor);
		cursor.close();
		return newshoppingList;
	}

	public void deleteList(ShoppingList shoppingList) {
		long id = shoppingList.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.COLUMN_LIST_NAME, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public ArrayList<ShoppingList> getAllLists() { 
		ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOPPING_LISTS,
				allColumns, null, null, null, null, null);

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

	private ShoppingList cursorToShoppingList(Cursor cursor) {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID )));
		shoppingList.setName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LIST_NAME )));
		return shoppingList;
	}
} 