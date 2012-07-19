package de.blueers.shoppingList.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.blueers.shoppingList.R;
import de.blueers.shoppingList.models.ShoppingItem;

public class ItemsAdapter extends ArrayAdapter<ShoppingItem> {
	private final Context context;
	private final ArrayList<ShoppingItem>  values;

	public ItemsAdapter(Context context, ArrayList<ShoppingItem> values) {
		super(context, R.layout.listitem_list, values);
		this.context = context;
		this.values = values;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView;
		if (convertView != null) {
			rowView = convertView;
		}
		else {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listitem_list, parent, false);			
		}
		TextView tvItemName = (TextView) rowView.findViewById(R.id.list_item_list_name);
		TextView tvNumberOfItems = (TextView) rowView.findViewById(R.id.list_item_number_of_items);
		tvItemName.setText(values.get(position).getName());  
		tvNumberOfItems.setText("0");
		return rowView;
	}
} 
