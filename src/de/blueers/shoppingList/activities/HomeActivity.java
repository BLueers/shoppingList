package de.blueers.shoppingList.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.ShoppingListsDataSource;


public class HomeActivity extends SherlockActivity {
	ListView shoppingLists;
	ListsAdapter listsAdapter;
	ShoppingListsDataSource dataSource;
    private static final String TAG = "HomeActivity";
    
    ProgressDialog progresBar;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shoppingLists= (ListView)findViewById(R.id.list_view_shopping_lists);
        dataSource = new ShoppingListsDataSource(this);
        dataSource.open();
        listsAdapter = new ListsAdapter(this, dataSource.getAllLists());
        shoppingLists.setAdapter(listsAdapter); 
        shoppingLists.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Intent intent = new Intent(view.getContext(), ItemsActivity.class);
        		intent.putExtra("list_id", listsAdapter.getItem(position).getId());
        		intent.putExtra("list_name", listsAdapter.getItem(position).getName());
         	    startActivity(intent);
        	}
        });        
    }
    public void onPause(){
   		Toast.makeText(getApplicationContext(),
    			"onPause", Toast.LENGTH_SHORT)
    			.show();
    	super.onPause();
    	dataSource.close();
    }
    public void onStop() {
  		Toast.makeText(getApplicationContext(),
    			"onStop", Toast.LENGTH_SHORT)
    			.show();
    	super.onStop();
     	
    }
    public void onRestart(){
    	super.onRestart();
    	dataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
 	   inflater.inflate(R.menu.activity_home, (com.actionbarsherlock.view.Menu) menu);
 	   return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
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
    private void addItem(String listName){
    	//ShoppingList sl = new ShoppingList(listName);
    	ShoppingList sl = dataSource.createList(listName);
    	this.listsAdapter.add(sl);

    	
    }
    

    
}
