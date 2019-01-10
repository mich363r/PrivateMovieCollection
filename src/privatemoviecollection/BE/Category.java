/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import java.util.List;

/**
 *
 * @author Asvør
 */
public class Category
{

    private int id;
    private String name;
    private List<Movie> movieList;

    public Category(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public List<Movie> getMovieList() 
    {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList)
    {
        this.movieList = movieList;
    }

}
