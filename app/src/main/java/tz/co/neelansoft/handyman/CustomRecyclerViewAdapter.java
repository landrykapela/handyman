package tz.co.neelansoft.handyman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by landre on 02/07/2018.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<HandymanEntry> myList;
    private Context context;


    //public constructor
    public CustomRecyclerViewAdapter(Context context, List<HandymanEntry> list){
        this.context = context;
        this.myList = list;
    }

    @NonNull
    @Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position){

        }

    }

}
