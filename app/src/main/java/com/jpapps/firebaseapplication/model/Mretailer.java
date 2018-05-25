package com.jpapps.firebaseapplication.model;

import java.util.ArrayList;

public class Mretailer
{
    private String to;

    private String next_page_url;

    private String last_page;

    private String total;

    private String per_page;

   // private Data[] data;
    public ArrayList<Data> data;
    private String from;

    private String filter;

    private String prev_page_url;

    private String current_page;

    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getNext_page_url ()
    {
        return next_page_url;
    }

    public void setNext_page_url (String next_page_url)
    {
        this.next_page_url = next_page_url;
    }

    public String getLast_page ()
    {
        return last_page;
    }

    public void setLast_page (String last_page)
    {
        this.last_page = last_page;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getPer_page ()
    {
        return per_page;
    }

    public void setPer_page (String per_page)
    {
        this.per_page = per_page;
    }
    public ArrayList<Data> getDatalist ()
    {
        return data;
    }
    public void addDatalist (ArrayList<Data>data)
    {
        this.data.addAll(data);
    }
    public void setData (ArrayList<Data>data)
    {
        this.data = data;
    }
 /*   public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }*/

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
    }

    public String getFilter ()
    {
        return filter;
    }

    public void setFilter (String filter)
    {
        this.filter = filter;
    }

    public String getPrev_page_url ()
    {
        return prev_page_url;
    }

    public void setPrev_page_url (String prev_page_url)
    {
        this.prev_page_url = prev_page_url;
    }

    public String getCurrent_page ()
    {
        return current_page;
    }

    public void setCurrent_page (String current_page)
    {
        this.current_page = current_page;
    }

    @Override
    public String toString()
    {
        return "";
       // return "ClassPojo [to = "+to+", next_page_url = "+next_page_url+", last_page = "+last_page+", total = "+total+", per_page = "+per_page+", data = "+data+", from = "+from+", filter = "+filter+", prev_page_url = "+prev_page_url+", current_page = "+current_page+"]";
    }

    public void setvalues(Mretailer values) {
        this.next_page_url=values.next_page_url;
        this.prev_page_url=values.prev_page_url;
    }
}