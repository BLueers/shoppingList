package de.blueers.shoppingList.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.fragments.ItemsFragment;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.ShoppingItemsDataSource;
import de.blueers.shoppingList.persistence.ShoppingListsDataSource;


public class HomeActivity extends SherlockFragmentActivity{
	ListView shoppingLists;
	ListsAdapter listsAdapter;
	ShoppingListsDataSource listsDataSource;
	ShoppingItemsDataSource itemsDataSource;
	private long mActiveListId;
	private ItemsFragment mActiveItemsFragment;

    TabHost mTabHost;

	private static final String TAG = "HomeActivity";
    
    ProgressDialog progresBar;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tab);
    	Log.d(TAG, "construktor ");

        listsDataSource = new ShoppingListsDataSource(this);
        listsDataSource.open();
        itemsDataSource = new ShoppingItemsDataSource(this);
        itemsDataSource.open();
        ArrayList<ShoppingList> lists= listsDataSource.getAllLists();
    	Log.d(TAG, "add Tab ");
        
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        int i=1;
        for(ShoppingList list:lists){
        	Log.d(TAG, "add Tab " + i);
            Tab tab = actionBar.newTab()
                    .setText(list.getName())
                    .setTabListener(new TabListener<ItemsFragment>(
                            this, list.getName(), list.getId()));
          Log.d(TAG, "add it ");
          actionBar.addTab(tab);
            i++;
            if(i>=8){break;}
        }
    	Log.d(TAG, "Tabs added" + i);
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
    	
    	ShoppingItem item = itemsDataSource.createItem(itemName, mActiveListId);
       	Log.d(TAG, "created " + itemName);
          	
       	mActiveItemsFragment.addItem(item);
   	
    }

    public class TabListener<T extends SherlockFragment> implements com.actionbarsherlock.app.ActionBar.TabListener {
        private ItemsFragment itemsFragment;
        private final SherlockFragmentActivity mActivity;
        private final String mTag;
        private final long mlistId;
       
        public TabListener(SherlockFragmentActivity activity, String tag, long listId) {
            mActivity = activity;
            mTag = tag;
            mlistId = listId;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
            // Check if the fragment is already initialized
     		
        	Log.d(TAG, "selected " + mTag );
         	if (itemsFragment == null) {
         		
            	Log.d(TAG, "selected " + mTag );
         		
                ArrayList<ShoppingItem> items = itemsDataSource.getAllItems(mlistId);
                ItemsAdapter itemsAdapter= new ItemsAdapter(mActivity,items);

                itemsFragment = new ItemsFragment(itemsAdapter, mlistId);
                
                fragmentTransaction.add(android.R.id.content, itemsFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
            	fragmentTransaction.attach(itemsFragment);
            }
            mActiveItemsFragment = itemsFragment;
            mActiveListId= mlistId;
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (itemsFragment != null) {
                // Detach the fragment, because another one is being attached
                ft.detach(itemsFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }

    
}
