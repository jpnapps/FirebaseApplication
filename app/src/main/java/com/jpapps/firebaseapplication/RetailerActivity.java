package com.jpapps.firebaseapplication;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jpapps.firebaseapplication.model.Data;
import com.jpapps.firebaseapplication.network.BusProvider;
import com.jpapps.firebaseapplication.network.NetworkService;
import com.jpapps.firebaseapplication.network.NotifyEvent;
import com.jpapps.firebaseapplication.network.PrefManager;
import com.jpapps.firebaseapplication.network.ToastHandler;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.DeviceFitImageView;
import com.jpndev.utilitylibrary.FlexLayout;
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import static com.jpapps.firebaseapplication.network.NetworkService.mretailerroot;
import static com.jpapps.firebaseapplication.network.NetworkService.mretailerrootpaged;

/**
 * Created by jithi on 25-05-2018.
 */

public class RetailerActivity extends BaseAppCompactActivity {



    AdapterRetilerItem adapter;

    private RecyclerView list_rcv;


    private int lastVisibleItem, totalItemCount;
    private boolean isLoading=false;
    private boolean isFullLoaded=false;
    private int visibleThreshold = 2;
    int flag=0;
    ArrayList<Data> joblist=new ArrayList<Data>();

    private SwipeRefreshLayout swipeLay,search_swipe_lay;

    private FlexLayout emptyFlay;
    private DeviceFitImageView im;
    private CustomFontTextView welcomeDtxv,emptyDtxv;
    private  String job_type="";
    private  String search_job_type="";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        job_type="";
        PrefManager.getInstance(this).putSharedBoolean("islogged",true);
        if(!isValid(mretailerroot))
            NetworkService.startActionAll(RetailerActivity.this,0);
        setContentView(R.layout.activity_retailer);
/*
        try {*/
            list_rcv = (RecyclerView) findViewById(R.id.list_rcv);
            list_rcv.setLayoutManager(new LinearLayoutManager(RetailerActivity.this));
            im = (DeviceFitImageView)findViewById(R.id.im);
            emptyDtxv = (CustomFontTextView)findViewById(R.id.empty_dtxv);
            emptyFlay = (FlexLayout)findViewById(R.id.empty_flay);
            emptyFlay.setVisibility(View.VISIBLE);
            swipeLay = (SwipeRefreshLayout)findViewById(R.id.swipe_lay);
            swipeLay.setVisibility(View.VISIBLE);

            swipeLay.setColorSchemeResources(R.color.green, R.color.colorPrimary, R.color.red, R.color.md_amber_300);
            swipeLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(isNetworkAvailable()) {
                        swipeLay.setRefreshing(true);
                        isBackendError = false;
                        NetworkService.startActionAll(RetailerActivity.this, 0);
                    }
                    else
                    {
                        swipeLay.setRefreshing(false);
                        ToastHandler.newInstance(getApplicationContext()).mustShowToast("Please check Network connection");
                    }

                }
            });
            onSetAdapter();
       /* }catch (Exception e)
        {
            LogUtils.makeLogTag(""+e.getMessage());
        }*/
    }







    private SwipeRefreshLayout searchSwipeLay;
    private RecyclerView searchRcv;

    public void setSwipeLayVisibiity(boolean searchVisibiity) {

        try{
          //  searchSwipeLay.setVisibility((!searchVisibiity)?View.VISIBLE:View.GONE);
            swipeLay.setVisibility((searchVisibiity)?View.VISIBLE:View.GONE);
        }catch (Exception e)
        {
            LogUtils.LOGD("exception", "CJF setSearchVisibiity exce " + e.getMessage());
        }
    }
	/*@Override
	public void onSetAdapter() {
		try{
			if(isValid(NetworkService.mJobsTotalList))
			{
				adapter=new AdapterRetilerItem(RetailerActivity.this,NetworkService.mJobsTotalList);
			list_rcv.setAdapter(adapter);
			}
		}catch (Exception e)
		{
			LogUtils.makeLogTag(""+e.getMessage());
		}

	}*/

    public void  showLoading() {
        try {
            joblist.add(null);
            adapter.notifyItemInserted(joblist.size() - 1);
            isLoading = true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void  hideLoading() {
        try {
            joblist.remove(null);
            isLoading = false;
            //  loading_rlay.setVisibility(View.GONE);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onSetAdapter() {
        try {
            LogUtils.LOGD("jithish", "FF  setAdapter " );
            im.setVisibility(View.GONE);
            emptyFlay.setVisibility(View.VISIBLE);
            emptyDtxv.setText("Loading ..");
            if(!isValid(mretailerroot))
            {
               mretailerroot= PrefManager.getInstance(RetailerActivity.this).getMretailer();
            }
            if(isValid(mretailerroot)) {
                joblist=new ArrayList<Data>();
                joblist.addAll(		mretailerroot.getDatalist());
                if(isValid(joblist)) {
                    emptyFlay.setVisibility(View.GONE);
                    adapter=new AdapterRetilerItem(RetailerActivity.this,joblist);
                    list_rcv.setItemAnimator(new DefaultItemAnimator());
                    list_rcv.setAdapter(adapter);
                    final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) list_rcv.getLayoutManager();
                    list_rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) ) {
                                if(isValid(mretailerroot.getNext_page_url())) {
                                    if (!isBackendError) {
                                        showLoading();
                                        isLoading = true;
                                        //int page = joblist.size() / 50;
                                        NetworkService.startActionAll(RetailerActivity.this, mretailerroot.getNext_page_url());
                                    } else {
                                        isLoading = false;
                                        hideLoading();
                                    }
                                } else {
                                    isLoading = false;
                                    hideLoading();
                                }

                            }
                        }
                    });
                }
                else
                {
                    joblist=new ArrayList<Data>();
                    adapter =new AdapterRetilerItem(RetailerActivity.this,joblist);
                    //adapter =new AdapterRetilerItem(RetailerActivity.this);
                    //	list_rcv.setItemAnimator(new DefaultItemAnimator());
                    list_rcv.setAdapter(adapter);
                    emptyFlay.setVisibility(View.VISIBLE);

                    if(flag==0)
                        emptyDtxv.setText("Loading ..");
                    else {
                        im.setVisibility(View.VISIBLE);
                        defSetText(emptyDtxv, " jobs not found ");
                    }

                }
            }
            else
            {
                joblist=new ArrayList<Data>();
                adapter =new AdapterRetilerItem(RetailerActivity.this,joblist);
                //	adapter =new AdapterRetilerItem(RetailerActivity.this);
                //list_rcv.setItemAnimator(new DefaultItemAnimator());
                list_rcv.setAdapter(adapter);
                emptyFlay.setVisibility(View.VISIBLE);

                if(flag==0)
                    emptyDtxv.setText("Loading ..");
                else {
                    im.setVisibility(View.VISIBLE);
                    defSetText(emptyDtxv, " jobs not found ");
                }
            }
            flag++;

        }
        catch (Exception e)
        {
            LogUtils.LOGD("exception", "FF  setAdapter " + e.getMessage());

        }
    }










    boolean isBackendError=false;

    @Subscribe
    public void onNotifyEvent(NotifyEvent event) {
        try{
            if(event.isSuccess)
            {
                if (isValid(swipeLay)) {
                    if (swipeLay.isRefreshing()) {
                        swipeLay.setRefreshing(false);

                    }
                }
         /*   if (isValid(search_swipe_lay)) {
                if (search_swipe_lay.isRefreshing()) {
                    search_swipe_lay.setRefreshing(false);

                }
            }*/
     /*       if(event.isFeedSearch)
                setSearchNotifyEvent(event);
            else {
                hideSearchFeed();*/
                LogUtils.LOGD("jithish", "FF onNotifyEvent");
                if (event.isPaged) {
                    if(isValid(mretailerrootpaged))
                        if(isValid(mretailerroot))
                    if (isValidSize(mretailerrootpaged.getDatalist())) {
                        if (isValidSize(mretailerroot.getDatalist()))
                            if (isValidSize(joblist))
                                if (isValid(list_rcv)) {
                                    hideLoading();
                                    isLoading = false;
                                    joblist.addAll(mretailerrootpaged.getDatalist());
                                    //	adapter.notifyItemRangeChanged(joblist.size()-mretailerrootpaged.size(), joblist.size());
                                    adapter.notifyDataSetChanged();
                                    LogUtils.LOGD("layerutils", "MF setAdapter list " + joblist.size());
                                    mretailerroot.addDatalist(mretailerrootpaged.getDatalist());
                                    mretailerroot.setvalues(mretailerrootpaged);
                                    PrefManager.getInstance(getApplicationContext()).saveMretailer(mretailerroot);
                                    mretailerrootpaged=null;

                                }
                    }
                    else
                    {
                        hideLoading();
                        isBackendError=true;
                        isLoading = false;
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    isBackendError=false;
                    isLoading = false;
                    onSetAdapter();
                }
            }
            else if(isValid(event.status))
            {
                isLoading = false;
                emptyFlay.setVisibility(View.VISIBLE);
                emptyDtxv.setText("Network Interuppted, Please try later");
                swipeLay.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e)
        {
            isLoading = false;
            LogUtils.makeLogTag(""+e.getMessage());
        }

    }



}
