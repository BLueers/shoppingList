package de.blueers.shoppingList.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView; 
import de.blueers.shoppingList.R;

public class ItemsAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String>  values;

	public ItemsAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.listitem_list, values);
		this.context = context;
		this.values = values;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listitem_list, parent, false);
		TextView tvListName = (TextView) rowView.findViewById(R.id.list_item_list_name);
		TextView tvNumberOfItems = (TextView) rowView.findViewById(R.id.list_item_number_of_items);
		tvListName.setText("Name " + values.get(position));  
		tvNumberOfItems.setText("0");
		return rowView;
	}
} 
