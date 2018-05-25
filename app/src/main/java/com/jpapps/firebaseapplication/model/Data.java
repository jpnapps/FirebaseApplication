package com.jpapps.firebaseapplication.model;

public class Data
{
    private String redirect_url;

    private String logo;

    private String share_url;

    private String description;

    private String bee_points;

    private String name;

    private String cashback;

    private String slug;

    private String rating;

    public String getRedirect_url ()
    {
        return redirect_url;
    }

    public void setRedirect_url (String redirect_url)
    {
        this.redirect_url = redirect_url;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public String getShare_url ()
    {
        return share_url;
    }
 /*   public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }*/
    public void setShare_url (String share_url)
    {
        this.share_url = share_url;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getBee_points ()
    {
        return bee_points;
    }

    public void setBee_points (String bee_points)
    {
        this.bee_points = bee_points;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCashback ()
    {
        return cashback;
    }

    public void setCashback (String cashback)
    {
        this.cashback = cashback;
    }

    public String getSlug ()
    {
        return slug;
    }

    public void setSlug (String slug)
    {
        this.slug = slug;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString()
    {
        return "";
      //  return "ClassPojo [redirect_url = "+redirect_url+", logo = "+logo+", share_url = "+share_url+", description = "+description+", bee_points = "+bee_points+", name = "+name+", cashback = "+cashback+", slug = "+slug+", rating = "+rating+"]";
    }
}