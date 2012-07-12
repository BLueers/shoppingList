package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;

public class ItemsActivity extends Activity {
	ListView itemlist;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        itemlist= (ListView)findViewById(R.id.list_view_items);
       // String[] items={"Hallo1","Hallo2","Hallo3","Hallo4"};
        ArrayList<String> items;
        items= new ArrayList<String>();
        items.add("item0");
        items.add("hallo1");
        items.add("hallo2");
        ListsAdapter adapter = new ListsAdapter(this,items);
        itemlist.setAdapter(adapter); 
        adapter.add("ende");
        itemlist.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
 //       		((ListsAdapter)parent.getAdapter()).add("klick");
        		
        	}
        }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        
        return true;
    }
}
