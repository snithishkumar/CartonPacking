package com.ordered.report.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ordered.report.R;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.services.ServiceUtl;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.fragment.HistoryFragment;
import com.ordered.report.view.fragment.OrderViewCartonListFragment;
import com.ordered.report.view.fragment.OrderViewDeliveryListFragment;
import com.ordered.report.view.fragment.OrderViewOrderItemsFragment;
import com.ordered.report.view.fragment.OrderedFragment;
import com.ordered.report.view.fragment.PackingFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OrderViewActivity extends AppCompatActivity {
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private int tabPosition = 0;
    private OrderedService orderedService;
    private int homeActivityTabPos = 4;
    private OrderEntity orderEntity;
    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("currentTabPosition",homeActivityTabPos);
        startActivity(intent);

        super.onBackPressed();

    }


    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtl.requestSync(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        orderedService = new OrderedService(this);
        homeActivityTabPos = getIntent().getIntExtra("currentTabPosition",4);
        tabPosition = getIntent().getIntExtra("tabPosition",0);
        String orderGuid = getIntent().getStringExtra("orderGuid");
        orderEntity = orderedService.getOrderEntityByGuid(orderGuid);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        viewPager =  findViewById(R.id.order_view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.order_view_tabs);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.setCurrentItem(tabPosition);

        Toolbar toolbar = findViewById(R.id.order_view_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        orderedService = new OrderedService(this);

    }

    @Override
    public void onPause() {
        AppBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        AppBus.getInstance().register(this);
        super.onResume();
    }

    @Subscribe
    public void packingResponse(ResponseData responseData){
        if(responseData.getData() instanceof CartonDetailsJson){
            CartonDetailsJson cartonDetailsJson = (CartonDetailsJson)responseData.getData();
            String cartonGuid = cartonDetailsJson.getCartonGuid();
            showOrderViewDetailsActivity(cartonGuid);
        }else{
            DeliveryDetailsEntity deliveryDetailsEntity = (DeliveryDetailsEntity)responseData.getData();
            showDeliveryOrderViewActivity(deliveryDetailsEntity.getDeliveryUUID());
        }


    }

    private void showOrderViewDetailsActivity(String cartonGuid){
        Intent intent = new Intent(this, OrderViewDetailsActivity.class);
        intent.putExtra("orderGuid",orderEntity.getOrderGuid());
        intent.putExtra("cartonGuid",cartonGuid);
        intent.putExtra("currentTabPosition",homeActivityTabPos);
        intent.putExtra("tabPosition",1);
        startActivity(intent);
        finish();
    }


    private void showDeliveryOrderViewActivity(String deliveryGuid){
        Intent intent = new Intent(this, OrderViewDetailsActivity.class);
        intent.putExtra("orderGuid",orderEntity.getOrderGuid());
        intent.putExtra("deliveryGuid",deliveryGuid);
        intent.putExtra("currentTabPosition",homeActivityTabPos);
        intent.putExtra("tabPosition",2);
        startActivity(intent);
        finish();
    }


    private void setupViewPager(ViewPager viewPager) {
        OrderViewActivity.ViewPagerAdapter adapter = new OrderViewActivity.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new OrderViewOrderItemsFragment(), "Order Details");
        adapter.addFragment(new OrderViewCartonListFragment(), "Carton List");
        adapter.addFragment(new OrderViewDeliveryListFragment(), "Delivery List");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }
}
