/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import java.util.List;

/**
 *
 * @author kokus
 */
public class Movie
{
    private String title;
    private String location;
//    private List <Category> category;
    private int id;
    private double imdbRating;
    private double personalRating;

    public Movie(int id, String title, String location, double imdbRating)
    {
        this.id = id;
        this.title = title;
        this.location = location;
        this.imdbRating = imdbRating;
        
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getId()
    {
        return id;
    }
}
