package de.blueers.shoppingList.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import de.blueers.shoppingList.R;
import de.blueers.shoppingList.models.ShoppingList;

public class ListsAdapter extends ArrayAdapter<ShoppingList> {
	private final Context context;
	private ArrayList<ShoppingList> mSelectedLists;
	public ListsAdapter(Context context, ArrayList<ShoppingList> values) {
		super(context, R.layout.listitem_list, values);
		this.context = context;
		mSelectedLists = new ArrayList<ShoppingList>();
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;
		if (convertView != null) {
			rowView = convertView;
		}
		else {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listitem_list, parent, false);
		}

		TextView tvListName = (TextView) rowView.findViewById(R.id.list_item_list_name);
		TextView tvNumberOfItems = (TextView) rowView.findViewById(R.id.list_item_number_of_items);
        CheckBox selectedCheckBox = (CheckBox) rowView.findViewById( R.id.list_item_check_box );

		tvListName.setText(this.getItem(position).getName());  
		tvNumberOfItems.setText("0");
		selectedCheckBox.setOnCheckedChangeListener(new ListItemListener(this.getItem(position)));

		
		return rowView;
	}
	public boolean hasStableIds(){
		return true;
	}
	public long getItemId(int position) {
		   return getItem(position).getId();
		}
	public ArrayList<ShoppingList> getSelectedLists(){
		return mSelectedLists;
	}
    private class ListItemListener implements OnCheckedChangeListener{
    	ShoppingList mList;
    	public ListItemListener(ShoppingList list){
    		mList= list;
    	}
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    {
	        if ( isChecked )
	        {
        		mSelectedLists.add(mList);
	        } else {
	        	mSelectedLists.remove(mList);	     //remove iterates through the list, might be time consuming   	
	        }
	        
	    }
    }
} 
