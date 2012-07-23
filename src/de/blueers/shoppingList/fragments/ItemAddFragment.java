package de.blueers.shoppingList.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.misc.ItemsChangeListener;

public class ItemAddFragment extends SherlockFragment {
	ItemsChangeListener listener; 
    
	
	public ItemAddFragment(ItemsChangeListener listener){
		super();
		this.listener = listener;
		
	}
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_item_add, container, false);
    }
}