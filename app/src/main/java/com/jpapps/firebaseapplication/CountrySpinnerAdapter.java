package com.jpapps.firebaseapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.jpapps.firebaseapplication.model.ItemData;

import java.util.ArrayList;

/**
 * Created by ceino on 11/1/18.
 */

public class CountrySpinnerAdapter extends ArrayAdapter<ItemData> {

    public

    int groupid;
    Activity context;
    ArrayList<ItemData> list;
    LayoutInflater inflater;
    public CountrySpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData>
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(final int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        final ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        imageView.setImageResource(list.get(position).getImageId());
        //TextView textView=(TextView)itemView.findViewById(R.id.txt);
       // textView.setText(list.get(position).getText());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}
