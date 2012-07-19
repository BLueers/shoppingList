package de.blueers.shoppingList.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.blueers.shoppingList.R;
import de.blueers.shoppingList.adapters.ListsAdapter;
import de.blueers.shoppingList.models.ShoppingList;
import de.blueers.shoppingList.persistence.ShoppingListsDataSource;


public class HomeActivity extends SherlockActivity {
	ListView shoppingLists;
	ListsAdapter listAdapter;
	ShoppingListsDataSource dataSource;
    private static final String TAG = "HomeActivity";
    AsyncUpdateList asyncUpdateList;
    ProgressDialog progresBar;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shoppingLists= (ListView)findViewById(R.id.list_view_shopping_lists);
        dataSource = new ShoppingListsDataSource(this);
        dataSource.open();
        ArrayList<ShoppingList> items = dataSource.getAllLists();
        listAdapter = new ListsAdapter(this,items);
        shoppingLists.setAdapter(listAdapter); 
        shoppingLists.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        		int position, long id) {
        		Intent intent = new Intent(view.getContext(), ItemsActivity.class);
        		intent.putExtra("list_id", listAdapter.getItem(position).getId());
        		intent.putExtra("list_name", listAdapter.getItem(position).getName());
         	    startActivity(intent);
        		
        	}
        }); 
       // new AsyncUpdateList().execute();
    }
    public void onPause(){
    	super.onPause();
    	dataSource.close();
    }
    public void onStop() {
    	super.onStop();
    	//dataSource.close();
    	
    }
    public void onRestart(){
    	super.onRestart();
    	dataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
 	   inflater.inflate(R.menu.activity_home, (com.actionbarsherlock.view.Menu) menu);
 	   return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case R.id.menu_item_add:
            	showAddItemDialog();
                return true;
            case R.id.menu_item_refresh:
        		for (int i= 0; i<=300; i++){
        			addItem("generiert " + i);
        		}

            	return true;
            case R.id.menu_item_settings:
            	progresBar = new ProgressDialog(this);
                progresBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progresBar.setMessage("Loading...");
                progresBar.setCancelable(false);
                progresBar.setProgress(0);
                progresBar.setMax(3);
                progresBar.show();
             	Log.d(TAG, "Setting los");
            	asyncUpdateList=new AsyncUpdateList(this);
            	asyncUpdateList.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAddItemDialog() {
    	
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);

        mAlertDialog.setTitle("Add shopping List");
        mAlertDialog.setMessage("Input name");

        final EditText mInput = new EditText(this);
        mInput.setText("");
        mAlertDialog.setView(mInput);

        mAlertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        String itemName = mInput.getText().toString();
                        addItem(itemName);
                }
        });

        mAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
        });

        mAlertDialog.show();
    }
    private void addItem(String listName){
    	//ShoppingList sl = new ShoppingList(listName);
    	ShoppingList sl = dataSource.createList(listName);
    	this.listAdapter.add(sl);
    	
    }
    private class AsyncUpdateList extends AsyncTask<Void, Void, Void> {
    	HomeActivity homeActivity;
    	
    	AsyncUpdateList(HomeActivity homeActivity){
    		this.homeActivity= homeActivity;
    	}
    	
    	protected Void doInBackground(Void... params){
         	Log.d(TAG, "start");
        	try {
				Thread.sleep(1000);
				homeActivity.progresBar.setProgress(1);
				Thread.sleep(1000);
				homeActivity.progresBar.setProgress(2);
				Thread.sleep(1000);
				homeActivity.progresBar.setProgress(3);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         	Log.d(TAG, "fertig");
        	return null;
        }

        protected void onProgressUpdate(Void... progress) {
           // setProgressPercent(progress[0]);
        	Log.d(TAG, "progress");
        }

        protected void onPostExecute(Void result) {
        	Log.d(TAG, "end Task");
        	homeActivity.progresBar.dismiss();
        	homeActivity.showAddItemDialog();
         }
    }

}
