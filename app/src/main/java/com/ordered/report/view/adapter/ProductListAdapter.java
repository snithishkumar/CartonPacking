package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ramse on 25-03-2016.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.HomeImageViewHolder> {
    private Context context = null;
    private List<ProductDetailsEntity> productEntities = new ArrayList<>();
    private HomeActivity homeActivity;
    public ProductListAdapter(Context context, List<ProductDetailsEntity> productEntities) {
        this.productEntities = productEntities;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public HomeImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.category_list_item, parent, false);

        return new HomeImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeImageViewHolder holder, int position) {
        final ProductDetailsEntity productDetailsEntity = productEntities.get(position);
        holder.productTitle.setText(productDetailsEntity.getProductName());
    }


    @Override
    public int getItemCount() {
        return productEntities.size();
    }

    public  class HomeImageViewHolder extends RecyclerView.ViewHolder {
        protected TextView addmore,subItemsCount,productTitle;
        public HomeImageViewHolder(View v) {
            super(v);
            productTitle=(TextView) v.findViewById(R.id.product_title);
            addmore = (TextView) v.findViewById(R.id.add_more);
            subItemsCount = (TextView) v.findViewById(R.id.sub_items_count);

            addmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeActivity.showAddProductList(null,0);
                }
            });

            subItemsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeActivity.showSubProductList();
                }
            });
        }

    }
    public void refresh(List<ProductDetailsEntity> productEntities){
        this.productEntities=productEntities;
        notifyDataSetChanged();
    }
}
