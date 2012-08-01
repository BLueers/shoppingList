package de.blueers.shoppingList.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.misc.EditListListener;
import de.blueers.shoppingList.models.ShoppingList;

public class EditListsDialogFragment extends SherlockDialogFragment {
	
	private ListsAdapter mListsAdapter;
	private ListView mListView;
	private EditListListener mEditListListener;
	private static final String TAG = "EditListsDialogFragment";	
	public EditListsDialogFragment(ListsAdapter listsAdapter, EditListListener editListListener){
		super();
		mListsAdapter = listsAdapter;
		mEditListListener= editListListener;
		
	}
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
        Log.d(TAG, "onCreate");
        View fragmentView = inflater.inflate(R.layout.fragment_edit_lists, container, false);
        mListView= (ListView)fragmentView.findViewById(R.id.list_view_shopping_lists);
        mListView.setAdapter(mListsAdapter); 
        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE);
        
        Log.d(TAG, "onCreate2");
       
        MyListener listener = new MyListener();       
        
    	Button deleteButton= (Button) fragmentView.findViewById( R.id.button_delete);
    	deleteButton.setOnClickListener(listener);
        Log.d(TAG, "onCreate3");
    	
    	Button addButton= (Button) fragmentView.findViewById( R.id.button_add);
    	addButton.setOnClickListener(listener);
        Log.d(TAG, "onCreate4");

		EditText nameEditText = (EditText) fragmentView.findViewById(R.id.edit_text_add_list);
		nameEditText.setOnKeyListener(listener);
        Log.d(TAG, "onCreate5");
		
        return fragmentView;
        
    }
    private void addList(String name){
    	
		ShoppingList sl;
    	sl= mEditListListener.addList(name);
    	mListsAdapter.add(sl);

    }
    private void deleteLists(){
		
   // 	long[] ids = mListView.getCheckedItemIds();
    	ArrayList<ShoppingList> lists;
    	SparseBooleanArray checkedItems;
    	lists = new ArrayList<ShoppingList> ();
    	checkedItems = mListView.getCheckedItemPositions();
    	
    	for (int i = 0; i <= checkedItems.size(); i++){
    		if(checkedItems.get(i)){
        		lists.add(mListsAdapter.getItem(i));
    		}
    	}
		mEditListListener.transactionComplete(lists);

    }
    private class MyListener implements View.OnClickListener, OnKeyListener{
	    public void onClick(View v)
	    {
        	switch (v.getId()){
        	case R.id.button_add:
        		EditText t = (EditText) getView().findViewById(R.id.edit_text_add_list);
        		addList(t.getText().toString());
        		t.setText("");
        		break;
        	case R.id.button_delete:
        		deleteLists();
        		break;
        	}
	    } 
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
              // Perform action on key press
            	addList(((EditText)v).getText().toString());
            	((EditText)v).setText("");
            	return true;
            }
            return false;
        }

    }
}

