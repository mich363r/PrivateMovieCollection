/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import java.util.Date;
import java.util.List;

/**
 *
 * @author kokus
 */
public class Movie
{
    private String title;
    private String filelink;
    private int id;
    private double imdbRating;
    private double personalRating;
    private Date lastview;

    public Movie(int id, String title, double imdbRating, double personalRating, String filelink)
    {
        this.id = id;
        this.title = title;
        this.filelink = filelink;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        
    }
    public Movie(int id, String title, double imdbRating, double personalRating, String filelink, Date lastview)
    {
        this.id = id;
        this.title = title;
        this.filelink = filelink;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
    }

    public String getTitle()
    {
        return title;
    }

    public double getImdbRating()
    {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating)
    {
        this.imdbRating = imdbRating;
    }

    public double getPersonalRating()
    {
        return personalRating;
    }

    public void setPersonalRating(double personalRating)
    {
        this.personalRating = personalRating;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getFilelink()
    {
        return filelink;
    }

    public void setFilelink(String filelink)
    {
        this.filelink = filelink;
    }

    public int getId()
    {
        return id;
    }

    public Date getLastview()
    {
        return lastview;
    }

    public void setLastview(Date lastview)
    {
        this.lastview = lastview;
    }
    
    
    
}
