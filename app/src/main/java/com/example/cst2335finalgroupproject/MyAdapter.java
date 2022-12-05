package com.example.cst2335finalgroupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cst2335finalgroupproject.DataModel;

import java.util.ArrayList;

/*
this class handle the recyclerView
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    // the list items of the search result
    private ArrayList<DataModel> dataModels;
    // contex has all required resources of the application
    private Context mContext;
    private ItemListener itemListener;
    // use it to see if user come from the mainActivity or saveItem activity
    private Boolean isSavedItems;
    private LayoutInflater mInflater;

    /*
    class constructor
     */
    public MyAdapter(Context context, ArrayList<DataModel> dataModels, ItemListener itemListener, Boolean isSavedItems) {
        this.mInflater = LayoutInflater.from(context);
        this.dataModels = dataModels;
        this.mContext = context;
        this.itemListener = itemListener;
        this.isSavedItems = isSavedItems;
    }

    /*
    inflates the viewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate listview item
        View view = mInflater.inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(view);
    }

    /*
    bind the viewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // put current position items to dataModel
        DataModel dataModel = dataModels.get(holder.getAdapterPosition());
        holder.myTextView.setText(dataModel.getPhotographer());
        // assign the image link to the ImageView
        // if statement: check if user is visiting the MainActivity or SavedItemsActivity
        if (dataModels.get(holder.getAdapterPosition()).getSmallImageUrl() == null) {
            // giving the image link to Glide to show it on the screen
            // .load() load the image link on the screen
            //.getImageUrl() get the image Url from DataModel
            Glide.with(mContext).load(dataModels.get(holder.getAdapterPosition()).getImageUrl()).placeholder(R.drawable.ic_android_black_24dp).into(holder.imageView);
        } else {
            Glide.with(mContext).load(dataModels.get(holder.getAdapterPosition()).getSmallImageUrl()).placeholder(R.drawable.ic_android_black_24dp).into(holder.imageView);
        }

        // decide if show the button in each row or not
        // we need button for savedItemActivity rows
        //if the item has been saved it will appear SavedItemsActivity page
        //if the user is in the SavedActivity page, want to show the button etc
        if (isSavedItems) {
            // find the delete button
            holder.deleteButton.setVisibility(View.VISIBLE);
            // make the button visible
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // call the item clicked listener and pass it the dataModel clicked
                    // items is a array of dataModels
                    // because we need clicked the delete button, row dataModel to delete it from database
                    itemListener.buttonClicked(dataModels.get(holder.getAdapterPosition()));
                }
            });
        }

    }

    /*
    removes the item from the recyclerView
     */
    public void removeItem(DataModel dataModel) {
        //itemListener is a interface that we defined to notify the SavedItemActivity
        dataModels.remove(dataModel);
        // call this method to refresh the listview
        notifyDataSetChanged();
    }

    /*
     returning the number of the rows for both main and saved items
     */
    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    /*
       stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView imageView;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            // initialize myTextView
            myTextView = itemView.findViewById(R.id.tv);
            // initialize imageView
            imageView = itemView.findViewById(R.id.iv);
            // initialize deleteButton
            deleteButton = itemView.findViewById(R.id.delete_btn);
            // set click listener on the view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) itemListener.rowClicked(dataModels.get(getAdapterPosition()));
        }
    }

}

/*
use it when user click a row of the recyclerView OR click delete button
 */
interface ItemListener {
    /*
    calls when a row of the recyclerView clicked
     */
    void rowClicked(DataModel dataModel);
    /*
    calls when the delete button of the recyclerView clicked
     */
    void buttonClicked(DataModel dataModel);
}