package com.jpapps.firebaseapplication.itest.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpapps.firebaseapplication.R;
import com.jpapps.firebaseapplication.itest.model.MItem;
import com.jpapps.firebaseapplication.itest.utiliity.LogUtilsutility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 2/3/17.
 */

public class AdapterRcvItem extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

   // RcvItemAdapter // AdapterRcvItem
    private final int VIEW_TYPE_LOADING = -1;
    public ArrayList<MItem> resultlist;
    public int totalcount=0;
    private Context context;
    private LayoutInflater layoutInflater;


    public  AdapterRcvItem (Context context, ArrayList<MItem> resultlist, int totalcount ) {
        this.context = context;
        this.resultlist = resultlist;
        this.totalcount = totalcount;
        this.layoutInflater = LayoutInflater.from(context);


    }
       public   AdapterRcvItem (Context context , ArrayList<MItem> resultlist) {
        this.context = context;
        this.resultlist = resultlist;
        this.totalcount = isValid(resultlist)?resultlist.size():0;
        this.layoutInflater = LayoutInflater.from(context);


    }
    public   AdapterRcvItem (Context context ) {
        this.context = context;
        this.resultlist = new ArrayList<MItem>();
        this.totalcount = 0;
        this.layoutInflater = LayoutInflater.from(context);


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rcv=null;
   /*     View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_item_feed, parent, false);
        if (viewType==VIEW_TYPE_LOADING) {
            rootView = LayoutInflater.from(context).inflate(R.layout.layout_loading_item, parent, false);
            rcv = new AdapterAuction.LoadingViewHolder(rootView);
        }
        else if (viewType==0) {
            rootView = LayoutInflater.from(context).inflate(R.layout.recycler_item_auction, parent, false);
            rcv = new AdapterAuction.ViewHolder(rootView);
        }*/
        View rootView = LayoutInflater.from(context).inflate(R.layout.rcv_item, parent, false);
        rcv = new  ViewHolder(rootView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder topholder, int position) {
        try {
            if(topholder!=null) {
          /*      if (topholder instanceof  AdapterRcvItem.LoadingViewHolder) {
                    AdapterRcvItem.LoadingViewHolder loadingViewHolder = ( AdapterRcvItem.LoadingViewHolder) topholder;
                    loadingViewHolder.progressBar.setIndeterminate(true);
                }else*/ if (topholder instanceof  ViewHolder) {
                    ViewHolder holder = ( ViewHolder) topholder;
                    setViewholder(holder,position);
                }
                else
                {
                }
            }
        } catch (Exception e) {
            LogUtilsutility.LOGD("exception", "AdapterRcvItem onBindViewHolder " + e.getMessage());
        }
    }
    private void setViewholder(ViewHolder holder, int position) {
        try {
            final MItem results =  getItem(position);
            if(isValid(results)) {

                 if(isValid(results.getImageUrl())) {
                     Picasso.get().load(results.getImageUrl())
                             // .placeholder(R.drawable.)
                             //.error(R.drawable.user_placeholder_error)
                             .into(holder.imv);
                 }
                 defSetText(holder.txv,results.getTitle());


            }
            } catch (Exception e) {
            LogUtilsutility.LOGD("exception", "AdapterRcvItem setViewholder " +position+" e  "+ e.getMessage());
            }
        }

    public MItem getItem (int position) {
        MItem results = null;
        if (isValid(resultlist, position))
            results = resultlist.get(position);
        return results;
    }
    @Override
    public int getItemCount() {
        return isValid(resultlist)?resultlist.size():0;
    }
    @Override
    public int getItemViewType(int position) {
        //  int pos=0;
    /*    //   pos=result90list.size()
        if(position==0)
            pos=position;
        else
        {

            pos=getItem(position) == null ? VIEW_TYPE_LOADING : position;
        }*/
        //  return position;

        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              //  BusProvider.getInstance().post(event);
                // bus.post(event);
            }
        });

    }

    public Boolean isValid(List list)
    {
        if(list!=null)
            if(list.size()>0)
                return  true;
        return  false;

    }

    public Boolean isValid(List list, int position)
    {
        if(isValid(list))
            if(list.size()>=position)
                return  true;
        return  false;

    }

    public Boolean isValid(Object object)
    {
        if(object!=null)
            return  true;
        return  false;

    }

    public Boolean isValid(String text) {
        if(text != null) if(!text.trim().equalsIgnoreCase("")) return true;
        return false;

    }

    public String defString(EditText text, String def) {
        if(text != null) if(isValid(text.getText() + "")) return text.getText() + "";
        return def;

    }

    public String defString(EditText text) {
        if(text != null) if(isValid(text.getText() + "")) return text.getText() + "";
        return "";

    }

    public String defString(String text, String def) {
        if(text != null) return text;
        return def;

    }

    public String defString(String text) {
        if(text != null) return text;
        return "";

    }

    public void defSetText(TextView textv, String text, String def) {
        if(isValid(textv))
            textv.setText(defString(text,def));

    }

    public void defSetText(TextView textv,String text) {
        if(isValid(textv))
            textv.setText(defString(text));

    }

    public void defSetText(EditText textv,String text, String def) {
        if(isValid(textv))
            textv.setText(defString(text,def));

    }

    public void defSetText(EditText textv,String text) {
        if(isValid(textv))
            textv.setText(defString(text));

    }

 /*   static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }*/


    public class ViewHolder  extends RecyclerView.ViewHolder{
        private ImageView imv;
    private TextView txv;

        public ViewHolder(View view) {
            super(view);
            imv = (ImageView) view.findViewById(R.id.imv);
            txv = (TextView) view.findViewById(R.id.txv);
        }
    }
}
