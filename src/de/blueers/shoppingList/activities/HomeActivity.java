package de.blueers.shoppingList.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.misc.MyTabContentFactory;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.ShoppingItemsDataSource;
import de.blueers.shoppingList.persistence.ShoppingListsDataSource;


public class HomeActivity extends SherlockActivity {
	ListView shoppingLists;
	ListsAdapter listsAdapter;
	ShoppingListsDataSource listsDataSource;
	ShoppingItemsDataSource itemsDataSource;
	private HashMap<String, Long> tabs;

    TabHost mTabHost;

	private static final String TAG = "HomeActivity";
    
    ProgressDialog progresBar;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tab);

        listsDataSource = new ShoppingListsDataSource(this);
        listsDataSource.open();
        itemsDataSource = new ShoppingItemsDataSource(this);
        itemsDataSource.open();
        ArrayList<ShoppingList> lists= listsDataSource.getAllLists();
        
        tabs = new HashMap<String, Long>();
        for(ShoppingList l : lists){
        	tabs.put(l.getName(), l.getId());
        }
        
        MyTabContentFactory tabContentfactory = new MyTabContentFactory(this,tabs, itemsDataSource);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        
        int i = 0;
        for(ShoppingList list:lists){
            TabHost.TabSpec listTab = mTabHost.newTabSpec(list.getName());
            listTab.setIndicator(list.getName());
            listTab.setContent(tabContentfactory);
            mTabHost.addTab(listTab);
            i++;
            if(i>=4){break;}
        }
        mTabHost.setCurrentTab(0);       
    }
    public void onPause(){
    	super.onPause();
    	itemsDataSource.close();
    }
    public void onStop() {
    	super.onStop();
     	
    }
    public void onRestart(){
    	super.onRestart();
    	itemsDataSource.open();
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
    private void addList(String listName){
    	//ShoppingList sl = new ShoppingList(listName);
    	ShoppingList sl = listsDataSource.createList(listName);
    	this.listsAdapter.add(sl);

    	
    }
    private void addItem(String itemName){
    	long listId = tabs.get(mTabHost.getCurrentTabTag());
    	Log.d(TAG, "Add " + itemName);
    	ShoppingItem item = itemsDataSource.createItem(itemName, listId);
    	ListView itemlist;
       	Log.d(TAG, "created " + itemName);
           	
    	itemlist= (ListView)mTabHost.getCurrentView().findViewById(R.id.list_view_items);
     	Log.d(TAG, "itemlist " + itemlist.toString());
     	ItemsAdapter ia = ((ItemsAdapter)itemlist.getAdapter());
     	Log.d(TAG, "ia " + ia.toString());
    	ia.add(item);
   	
    }



    
}
