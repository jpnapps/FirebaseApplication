package com.jpapps.firebaseapplication.itest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.jpapps.firebaseapplication.R;
import com.jpapps.firebaseapplication.itest.adapter.AdapterRcvItem;
import com.jpapps.firebaseapplication.itest.aquery_network.NetworkApi;
import com.jpapps.firebaseapplication.itest.model.MItem;
import com.jpapps.firebaseapplication.itest.network.GsonUtils;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Mani-Ceino on 4/22/2018.
 */

public class HomeActivity  extends AppCompatActivity{

	private RecyclerView listRcv;
	AdapterRcvItem adapterRcvItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home2);

		listRcv = (RecyclerView) findViewById(R.id.list_rcv);
		LinearLayoutManager mLayoutManager;
		mLayoutManager = new LinearLayoutManager(HomeActivity.this);
		listRcv.setLayoutManager(mLayoutManager);
		AjaxCallback<JSONArray> cb = new AjaxCallback<JSONArray>(){
			@Override
			public void callback(String url, JSONArray json, AjaxStatus status) {
				try {
					ArrayList<MItem> itemlist= GsonUtils.getInstance().gsonToMItems(json);
					adapterRcvItem = new AdapterRcvItem(HomeActivity.this,itemlist );
					listRcv.setAdapter(adapterRcvItem);
					status.getMessage();
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		NetworkApi.getInstance().getBaseArrayWeb(HomeActivity.this,"https://s3.amazonaws.com/ceinodemo/data.json",cb);
	}
}
