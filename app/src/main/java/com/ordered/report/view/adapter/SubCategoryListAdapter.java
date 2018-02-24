package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ramse on 25-03-2016.
 */
public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.HomeImageViewHolder> {
    private Context context = null;
    private List<String> list = new ArrayList<>();
    private HomeActivity homeActivity;
    public SubCategoryListAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public HomeImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.sub_category_list_item, parent, false);

        return new HomeImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeImageViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class HomeImageViewHolder extends RecyclerView.ViewHolder {
        protected TextView addmore,subItemsCount;
        public HomeImageViewHolder(View v) {
            super(v);
        }

    }
}
