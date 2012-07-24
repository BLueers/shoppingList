package de.blueers.shoppingList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.blueers.shoppingList.R;

public class EditListsDialogFragment extends SherlockDialogFragment {
	
	private ListAdapter mListAdapter;
	private ListView mlistView;
	public EditListsDialogFragment(ListAdapter listAdapter){
		super();
		mListAdapter = listAdapter;
		
	}
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
//    	View v = inflater.inflate(R.layout.fragment_add_item, container, false);
//        View tv = v.findViewById(R.id.item_name);
//        ((TextView)tv).setText("This is an instance of MyDialogFragment");
//        return v;
        

        
        View fragmentView = inflater.inflate(R.layout.fragment_edit_lists, container, false);
        mlistView= (ListView)fragmentView.findViewById(R.id.list_view_shopping_lists);
        mlistView.setAdapter(mListAdapter); 
        mlistView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getActivity().getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
        	} 
        }); 
        return fragmentView;

        
        
        
    }
}

