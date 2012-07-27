package de.blueers.shoppingList.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.misc.EditListListener;
import de.blueers.shoppingList.models.ShoppingList;

public class EditListsDialogFragment extends SherlockDialogFragment {
	
	private ListsAdapter mListsAdapter;
	private ListView mlistView;
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
        mlistView= (ListView)fragmentView.findViewById(R.id.list_view_shopping_lists);
        mlistView.setAdapter(mListsAdapter); 
        
        Log.d(TAG, "onCreate2");
       
        MyListener listener = new MyListener();       
        
//      CheckBox selectAllCheckBox = (CheckBox) fragmentView.findViewById( R.id.lists_check_all );
//    	selectAllCheckBox.setOnCheckedChangeListener(listener);
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
		Toast.makeText(getActivity().getApplicationContext(),
    			"Delete ", Toast.LENGTH_LONG)
    			.show();
		
		mEditListListener.transactionComplete(mListsAdapter.getSelectedLists());

    }
    private class MyListener implements View.OnClickListener, OnCheckedChangeListener, OnKeyListener{
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    {
	    	
	    	//the List should be checkable to make this part easier, also the Adapter should take care of this
//	    	int countLists = mListsAdapter.getCount();
//	    	for (int i= 0; i< countLists; i++){
//	    		mListsAdapter.getVie
//	    		View v = (View) mlistView.getItemAtPosition(i);
//	    		Log.d(TAG, "found position "+ i);
//	    		CheckBox cb=(CheckBox) v.findViewById(R.id.list_item_check_box);
//	    		Log.d(TAG, "found cb "+ i);
//	    		cb.setChecked(isChecked);
//	    		Log.d(TAG, "checked cb "+ i);
//	    	}
	    }
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

