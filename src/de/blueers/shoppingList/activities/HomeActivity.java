package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;


public class HomeActivity extends SherlockActivity {
	ListView shoppingLists;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shoppingLists= (ListView)findViewById(R.id.list_view_shopping_lists);
       // String[] items={"Hallo1","Hallo2","Hallo3","Hallo4"};
        ArrayList<String> items;
        items= new ArrayList<String>();
        items.add("Netto");
        items.add("Plus");
        items.add("Herbert");
        ListsAdapter adapter = new ListsAdapter(this,items);
        shoppingLists.setAdapter(adapter); 
        adapter.add("ende"); 
        shoppingLists.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Toast.makeText(getApplicationContext(),
        			"Click ListItem Number " + position, Toast.LENGTH_LONG)
        			.show();
 //       		((ListsAdapter)parent.getAdapter()).add("klick");
        		Intent intent = new Intent(view.getContext(), ItemsActivity.class);
        	 //   String message = editText.getText().toString();
        	 //   intent.putExtra(EXTRA_MESSAGE, message);
        	    startActivity(intent);
        		
        	}
        }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
 	   inflater.inflate(R.menu.activity_home, (com.actionbarsherlock.view.Menu) menu);
 	   return super.onCreateOptionsMenu(menu);

    }
}
