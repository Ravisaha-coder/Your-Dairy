package com.example.yourdairy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private ArrayList<String> fileNames;
    DBHelper db;
    /**
     * Provide a reference to the type of views that we are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView fileName;

        public ViewHolder(View view){
            super(view);
            fileName = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getFileName(){
            return fileName;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_items,parent,false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getFileName().setText(fileNames.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DBHelper(v.getContext());
                Intent intent = new Intent(v.getContext(),FileActivity.class);
                String fileCreatets = (String) fileNames.get(position);
                FileObj fileObj = db.getFileByCreatets(fileCreatets);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fileObj", fileObj);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return fileNames.size();
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public FilesAdapter(ArrayList dataSet){
        this.fileNames = dataSet;
    }
}
