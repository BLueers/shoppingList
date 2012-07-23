package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.fragments.ItemAddFragment;
import de.blueers.shoppingList.fragments.ItemsFragment;
import de.blueers.shoppingList.misc.ItemsChangeListener;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.persistence.ShoppingItemsDataSource;



public class ItemsActivity extends SherlockFragmentActivity {
	String listName;
	long listId;
	ShoppingItemsDataSource dataSource;
	ItemsFragment itemsFragment;
	ItemAddFragment itemAddFragment;
	
	private static final String TAG = "ItemsActivity";
     
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listName = getIntent().getExtras().getString("list_name");
        listId = getIntent().getExtras().getLong("list_id",0);
        dataSource = new ShoppingItemsDataSource(this);
        dataSource.open();

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(listName);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "start Activity");

        setContentView(R.layout.activity_items);
        
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout

        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            ArrayList<ShoppingItem> items = dataSource.getAllItems(listId);
            ItemsAdapter itemsAdapter= new ItemsAdapter(this,items);

            // Create an instance of ExampleFragment
            itemsFragment = new ItemsFragment(itemsAdapter, listId);
            Log.d(TAG, "create Fragment");
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, itemsFragment).commit();
        }
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
        		showAddItemFragment();
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

    private void showAddItemFragment(){
    	if (itemAddFragment == null){
    		itemAddFragment = new ItemAddFragment(
    				new ItemsChangeListener() {
    					public void onItemAdded(String itemName) {
    						addItem(itemName);
                         }
                });
    	}
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    	// Replace whatever is in the fragment_container view with this fragment,
    	// and add the transaction to the back stack so the user can navigate back
    	transaction.replace(R.id.fragment_container, itemAddFragment);
    	transaction.addToBackStack(null);

    	// Commit the transaction
    	transaction.commit();
    	
	
    }
    private void addItem(String itemName){
    	ShoppingItem item = dataSource.createItem(itemName, listId);
    	itemsFragment.addItem(item);   	
    }

}
