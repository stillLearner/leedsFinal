package com.novo.zealot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.novo.zealot.Bean.Food;
import com.novo.zealot.R;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<Food> {
    public int resourceId; // Resource ID for the layout
    public FoodAdapter(Context context, int textViewResourceId, List<Food> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = getItem(position);  // Get the Food object at the current position

        // Inflate the layout for each item in the list
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        // Find views within the layout
        ImageView foodImage = view.findViewById(R.id.food_image);
        TextView foodName = view.findViewById(R.id.food_name);
        TextView foodInclusion = view.findViewById(R.id.food_inclusion);
        TextView foodInclusionContent = view.findViewById(R.id.food_inclusion_content);
        TextView foodUnit = view.findViewById(R.id.food_inclusion_unit);

        // Set data to the views from the Food object
        foodImage.setImageResource(food.getImageId());
        foodName.setText(food.getName());
        foodInclusion.setText(food.getInclusion());
        foodInclusionContent.setText(food.getInclusion_content());
        foodUnit.setText(food.getUnit());

        return view;  // Return the populated view for the current item
    }
}
