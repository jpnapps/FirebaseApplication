package com.jpapps.firebaseapplication.itest.model;

public class MItem
{
    private String title;

    private String imageUrl;

    private String date;

    private String descrription;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getDescrription ()
    {
        return descrription;
    }

    public void setDescrription (String descrription)
    {
        this.descrription = descrription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo";
        //return "ClassPojo [title = "+title+", imageUrl = "+imageUrl+", date = "+date+", descrription = "+descrription+"]";
    }
}