package de.blueers.shoppingList.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.fragments.EditListsDialogFragment;
import de.blueers.shoppingList.fragments.ItemsFragment;
import de.blueers.shoppingList.misc.EditListListener;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.MyDataSource;

public class HomeActivity extends SherlockFragmentActivity {

	private MyDataSource mDataSource;
	private long mActiveListId;
	private ItemsFragment mActiveItemsFragment;
	private static final String TAG = "HomeActivity";
	private EditListsDialogFragment mEditListsDialogFragment;
	private HashMap<Long, Tab> mListIdToTab;
    private Menu mMenu;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDataSource = new MyDataSource(this);
        mDataSource.open();
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
       
        ArrayList<ShoppingList> lists= mDataSource.getAllLists();
        
        mListIdToTab = new HashMap<Long, Tab>();
        int i=1;
        for(ShoppingList list:lists){
            addTab(list);
        	i++;
            if(i>=8){break;}
        }
    	Log.d(TAG, "Tabs added" + i);
    }
	private void addTab(ShoppingList list){
        ActionBar actionBar =  getSupportActionBar();
        Tab tab = actionBar.newTab()
                .setText(list.getName())
                .setTabListener(new TabListener(
                        this, list.getName(), list.getId()));
        actionBar.addTab(tab);
        mListIdToTab.put(list.getId(), tab);
	}

	public void onPause() {
		super.onPause();
		mDataSource.close();
	}

	public void onStop() {
		super.onStop();
	}

	public void onRestart() {
		super.onRestart();
		mDataSource.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		mMenu = menu;
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		
		inflater.inflate(R.menu.activity_home,
				(com.actionbarsherlock.view.Menu) menu);
		
		MenuItem menuItemAdd=menu.findItem(R.id.menu_action_item_add);
		menuItemAdd.setOnActionExpandListener(new ActionExpandListener());
		
		EditText editTextAdd = (EditText) menuItemAdd.getActionView();
		editTextAdd.setOnKeyListener(new actionItemListener());
		
		 String[] jdh = {"Hamburt", "Peter","zah"} ;
	       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, jdh );
	        AutoCompleteTextView textView = (AutoCompleteTextView) menuItemAdd.getActionView();
	        textView.setAdapter(adapter);
	        textView.setThreshold(0);
	       textView.showDropDown();
			

		
		return super.onCreateOptionsMenu(menu);
	}

	private void addItemfromActionBar(){
		EditText editTextAdd= (EditText)mMenu.findItem(R.id.menu_action_item_add).getActionView();
		if(!editTextAdd.getText().toString().equals("")){
			addItem(editTextAdd.getText().toString());     
		}
		editTextAdd.setText("");
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow( editTextAdd.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	mMenu.findItem(R.id.menu_action_item_add).collapseActionView();	
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_button_item_add:
        	addItemfromActionBar();
		case R.id.menu_item_refresh:
			return true;
		case R.id.menu_item_edit_lists:
			showEditListDialog();
			return true;
		case R.id.menu_item_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showEditListDialog() {
		Log.d(TAG, "edit Lists selected");
		ArrayList<ShoppingList> lists = mDataSource.getAllLists();
		Log.d(TAG, "Listarray initialized");

		EditListListener listener = new EditListListener() {
			public void transactionCancel() {
				mEditListsDialogFragment.dismiss();
			}

			public void transactionComplete(ArrayList<ShoppingList> lists) {
				HomeActivity.this.deleteLists(lists);
				mEditListsDialogFragment.dismiss();
			}
			public ShoppingList addList(String name){
				return HomeActivity.this.addList(name);
			}
		};

		ListsAdapter listsAdapter = new ListsAdapter(this, lists);
		mEditListsDialogFragment = new EditListsDialogFragment(listsAdapter,
				listener);
		Log.d(TAG, "Fragment instanciated");
		mEditListsDialogFragment.show(getSupportFragmentManager(), "dialog");
	}

	private ShoppingList addList(String listName) {

		ShoppingList sl = mDataSource.createList(listName);
		addTab(sl);
		return sl;
	}
	private void deleteLists(ArrayList<ShoppingList> lists) {
        
		ActionBar actionBar =  getSupportActionBar();
        for(ShoppingList list:lists){
        	Tab tab = mListIdToTab.get(list.getId());
        	actionBar.removeTab(tab);
        	mDataSource.deleteList(list);
        }
	}

	private void addItem(String itemName) {
		ShoppingItem item = mDataSource.createItem(itemName, mActiveListId);
		Log.d(TAG, "created " + itemName);
		mActiveItemsFragment.addItem(item);

	}
	private class ActionExpandListener implements OnActionExpandListener {
        public boolean onMenuItemActionCollapse(MenuItem item) {
        	mMenu.findItem(R.id.menu_button_item_add).setVisible(false);
        	
            return true; 
        }
        public boolean onMenuItemActionExpand(MenuItem item) {
        	mMenu.findItem(R.id.menu_button_item_add).setVisible(true);

        	final AutoCompleteTextView editTextAdd= (AutoCompleteTextView) item.getActionView();     	
        	       	
        	editTextAdd.post(new Runnable() {
                public void run() {
                	editTextAdd.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editTextAdd, InputMethodManager.SHOW_IMPLICIT);
                    editTextAdd.showDropDown();
                }
            });
            return true;  
        }
    }
	private class actionItemListener implements OnKeyListener{
	    public boolean onKey(View v, int keyCode, KeyEvent event) {
	        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
	            (keyCode == KeyEvent.KEYCODE_ENTER)) {
	        	addItemfromActionBar();
	        	return true;
	        }
	        return false;
	    }
	
	}
	private class TabListener implements
			com.actionbarsherlock.app.ActionBar.TabListener {
		private ItemsFragment itemsFragment;
		private final Context mContext;
		private final String mTag;
		private final long mlistId;

		public TabListener(Context context, String tag, long listId) {
			mContext = context;
			mTag = tag;
			mlistId = listId;
		}

		/* The following are each of the ActionBar.TabListener callbacks */

		public void onTabSelected(Tab tab,
				FragmentTransaction fragmentTransaction) {
			// Check if the fragment is already initialized

			Log.d(TAG, "selected " + mTag);
			if (itemsFragment == null) {

				Log.d(TAG, "selected " + mTag);

				ArrayList<ShoppingItem> items = mDataSource
						.getAllItems(mlistId);
				ItemsAdapter itemsAdapter = new ItemsAdapter(mContext, items);

				itemsFragment = new ItemsFragment(itemsAdapter, mlistId);

				fragmentTransaction.add(android.R.id.content, itemsFragment,
						mTag);
			} else {
				// If it exists, simply attach it in order to show it
				fragmentTransaction.attach(itemsFragment);
			}
			mActiveItemsFragment = itemsFragment;
			mActiveListId = mlistId;
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
