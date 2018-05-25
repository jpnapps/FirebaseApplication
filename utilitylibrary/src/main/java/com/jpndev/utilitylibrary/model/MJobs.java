package com.jpndev.utilitylibrary.model;

public class MJobs
{
    private String id;

    private String title;

    private String company_logo;

    private String location;

    private String description;

    private String company;

    private String how_to_apply;

    private String created_at;

    private String type;

    private String url;

    private String company_url;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCompany_logo ()
    {
        return company_logo;
    }

    public void setCompany_logo (String company_logo)
    {
        this.company_logo = company_logo;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getHow_to_apply ()
    {
        return how_to_apply;
    }

    public void setHow_to_apply (String how_to_apply)
    {
        this.how_to_apply = how_to_apply;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getCompany_url ()
    {
        return company_url;
    }

    public void setCompany_url (String company_url)
    {
        this.company_url = company_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", title = "+title+", company_logo = "+company_logo+", location = "+location+", description = "+description+", company = "+company+", how_to_apply = "+how_to_apply+", created_at = "+created_at+", type = "+type+", url = "+url+", company_url = "+company_url+"]";
    }
}
