package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.persistence.ShoppingItemsDataSource;



public class ItemsActivity extends SherlockActivity {
	ListView itemlist;
	ItemsAdapter listAdapter;
	ShoppingItemsDataSource dataSource;
	String listName;
	long listId;
 
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        dataSource = new ShoppingItemsDataSource(this);
        dataSource.open();

        listName = getIntent().getExtras().getString("list_name");
        listId = getIntent().getExtras().getLong("list_id",0);
        ArrayList<ShoppingItem> items = dataSource.getAllItems(listId);
               
        itemlist= (ListView)findViewById(R.id.list_view_items);

        listAdapter = new ItemsAdapter(this,items);
        itemlist.setAdapter(listAdapter); 
        itemlist.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
        	} 
        }); 
            ActionBar mActionBar = getSupportActionBar();
            mActionBar.setTitle(listName);
            mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	   inflater.inflate(R.menu.activity_home, (com.actionbarsherlock.view.Menu) menu);
	   return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent mIntent = new Intent(this, HomeActivity.class);
	            startActivity(mIntent);
	            return true;
        	case R.id.menu_item_add:
            	showAddItemDialog();
                return true;
            case R.id.menu_item_refresh:
        		for (int i= 0; i<=300; i++){
        			addItem("generiert " + i);
        		}
                return true;
            case R.id.menu_item_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAddItemDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);

        mAlertDialog.setTitle("Add item");
        mAlertDialog.setMessage("Input name");

        final EditText mInput = new EditText(this);
        mInput.setText("");
        mAlertDialog.setView(mInput);

        mAlertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        String itemName = mInput.getText().toString();
                        addItem(itemName);
                }
        });

        mAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
        });

        mAlertDialog.show();
    }
    private void addItem(String itemName){

    	ShoppingItem item = dataSource.createItem(itemName, listId);
    	this.listAdapter.add(item);
    	
    }

}
