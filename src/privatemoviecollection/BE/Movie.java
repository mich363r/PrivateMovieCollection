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

    public Movie(String title, String location, int id)
    {
        this.title = title;
        this.location = location;
        this.id = id;
    }

    public String getTitle()
    {
        return title;
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
