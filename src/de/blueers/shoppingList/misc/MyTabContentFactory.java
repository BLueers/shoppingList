package de.blueers.shoppingList.misc;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;
import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ItemsAdapter;
import de.blueers.shoppingList.models.ShoppingItem;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.ShoppingItemsDataSource;

public class MyTabContentFactory implements TabContentFactory {

	ListView itemlist;
	private static final String TAG = "ItemsFragment";
	ShoppingItemsDataSource mDataSource;
	private Context mContext;
	private LayoutInflater mInflater;
	private HashMap<String, Long> tabs;
	
	public MyTabContentFactory(Context context, HashMap<String, Long> tabs, ShoppingItemsDataSource dataSource){

		
		mDataSource= dataSource;
		mContext = context;
        mInflater = (LayoutInflater)
        		mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        this.tabs = tabs;
        
}
	
	public View createTabContent(String tag) {
		View fragmentView = mInflater.inflate(R.layout.fragment_items, null);

		ArrayList<ShoppingItem> items = mDataSource.getAllItems(tabs.get(tag));
        ItemsAdapter itemsAdapter= new ItemsAdapter(mContext,items);

        // Create an instance of ExampleFragment
        itemlist= (ListView)fragmentView.findViewById(R.id.list_view_items);
        itemlist.setAdapter(itemsAdapter); 
        itemlist.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(mContext,
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
        	} 
        }); 
        return fragmentView;
		
	}
}
