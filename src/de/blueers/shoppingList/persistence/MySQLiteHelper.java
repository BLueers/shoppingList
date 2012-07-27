package de.blueers.shoppingList.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "shopping.db";
	private static final int DATABASE_VERSION = 4;
	public static final String COLUMN_ID = "_id";
	
	public static final String TABLE_SHOPPING_LISTS = "shopping_lists";
	public static final String COLUMN_LIST_NAME = "list_name";
	private static final String CREATE_LISTS_TABLE = "create table "
			+ TABLE_SHOPPING_LISTS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_LIST_NAME
			+ " text not null);";

	
	public static final String TABLE_SHOPPING_ITEM = "shopping_items";
	public static final String COLUMN_ITEM_NAME = "item_name";
	public static final String COLUMN_LIST_REFERENCE = "list_id_reference";
	private static final String CREATE_ITEMS_TABLE = "create table "
			+ TABLE_SHOPPING_ITEM + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_LIST_REFERENCE	+ " integer, "
			+ COLUMN_ITEM_NAME
			+ " text not null);";
	
	
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_LISTS_TABLE);
		database.execSQL(CREATE_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_ITEM);
		onCreate(db);
	}

} 
