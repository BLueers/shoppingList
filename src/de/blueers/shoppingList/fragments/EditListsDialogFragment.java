package de.blueers.shoppingList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.misc.EditListListener;

public class EditListsDialogFragment extends SherlockDialogFragment {
	
	private ListsAdapter mListsAdapter;
	private ListView mlistView;
	private EditListListener mEditListListener;
	public EditListsDialogFragment(ListsAdapter listsAdapter, EditListListener editListListener){
		super();
		mListsAdapter = listsAdapter;
		mEditListListener= editListListener;
		
	}
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
       
        View fragmentView = inflater.inflate(R.layout.fragment_edit_lists, container, false);
        mlistView= (ListView)fragmentView.findViewById(R.id.list_view_shopping_lists);
        mlistView.setAdapter(mListsAdapter); 
        mlistView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getActivity().getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
        	} 
        }); 
        MyListener listener = new MyListener();
        
        CheckBox selectAllCheckBox = (CheckBox) fragmentView.findViewById( R.id.lists_check_all );
    	selectAllCheckBox.setOnCheckedChangeListener(listener);
    	Button deleteButton= (Button) fragmentView.findViewById( R.id.button_delete);
    	deleteButton.setOnClickListener(listener);

        return fragmentView;
        
    }
    private void deleteLists(){
		Toast.makeText(getActivity().getApplicationContext(),
    			"Delete ", Toast.LENGTH_LONG)
    			.show();
		
		mEditListListener.transactionComplete(mListsAdapter.getSelectedLists());

    }
    private class MyListener implements View.OnClickListener, OnCheckedChangeListener{
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    {
	        if ( isChecked )
	        {
        		Toast.makeText(getActivity().getApplicationContext(),
            			"Checked ", Toast.LENGTH_LONG)
            			.show();
	        }
	        
	    }
	    public void onClick(View v)
	    {
        		Toast.makeText(getActivity().getApplicationContext(),
            			"Delete ", Toast.LENGTH_LONG)
            			.show();
        		deleteLists();
	    } 	
    }
}

