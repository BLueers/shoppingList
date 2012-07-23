package de.blueers.shoppingList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.models.ShoppingItem;

public class ItemsFragment extends SherlockFragment {
	ListView itemlist;
	ItemsAdapter listAdapter;
	long listId;
	private static final String TAG = "ItemsFragment";
	
	public ItemsFragment(ItemsAdapter listAdapter, long listId){
		super();
		this.listAdapter = listAdapter;
		this.listId = listId;
		
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_items, container, false);
        itemlist= (ListView)fragmentView.findViewById(R.id.list_view_items);
        itemlist.setAdapter(listAdapter); 
        itemlist.setOnItemClickListener(new OnItemClickListener() {
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
    public void addItem(ShoppingItem item){

    	this.listAdapter.add(item);
    	
    }

}

