package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;

public class ItemsActivity extends Activity {
	ListView itemlist;
	ListsAdapter listAdapter;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        itemlist= (ListView)findViewById(R.id.list_view_items);
       // String[] items={"Hallo1","Hallo2","Hallo3","Hallo4"};
        ArrayList<String> items;
        items= new ArrayList<String>();
        items.add("Milch");
        items.add("Käse");
        items.add("totes Tier im eigenen Darm geräuchert");
        listAdapter = new ListsAdapter(this,items);
        itemlist.setAdapter(listAdapter); 
        listAdapter.add("ende");
        itemlist.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
 //       		((ListsAdapter)parent.getAdapter()).add("klick");
        		
        	}
        }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.activity_home, menu);
        
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(getApplicationContext(),
    			"Hallo", Toast.LENGTH_LONG)
    			.show();
    	
    	switch (item.getItemId()) {
            case R.id.menu_item_add:
            	showAddItemDialog();
                return true;
            case R.id.menu_item_refresh:
                return true;
            case R.id.menu_item_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAddItemDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);

        mAlertDialog.setTitle("Add shopping List");
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
    	this.listAdapter.add(itemName);
    	
    }

}
