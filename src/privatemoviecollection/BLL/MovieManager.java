/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.Database.CategoryDbDAO;
import privatemoviecollection.DAL.Database.MovieDbDAO;

/**
 *
 * @author kokus
 */
public class MovieManager
{
    private MovieDbDAO mDbDAO;
    private CategoryDbDAO cDbDAO;
    
    public MovieManager() throws IOException
    {
        this.mDbDAO = new MovieDbDAO();
        this.cDbDAO = new CategoryDbDAO();
    }
    
    public void addMovie (Movie movieToAdd) throws SQLException
    {
        mDbDAO.addMovie(movieToAdd);
    }
    
    public void deleteMovie (Movie movieToDelete) throws SQLException
    {
        mDbDAO.deleteMovie(movieToDelete);
    }
    
    public List<Movie> getAllMovies () throws SQLException
    {
        return mDbDAO.getAllMovies();
    }
    
    public Movie getMovie (Movie movie)
    {
        return mDbDAO.getMovie(movie.getId());
    }
    
    public List<Movie> searchMovies (String input)
    {
        return mDbDAO.searchMovies(input);
    }
    
    public void addCategory(Category catToAdd)
    {
        cDbDAO.addCategory(catToAdd);
    }
    
    public void deleteCategory(Category catToDelete)
    {
        cDbDAO.deleteCategory(catToDelete);
    }
    
    public void addToCategory (Movie movieToAdd, Category chosenCat)
    {
        cDbDAO.addToCategory(movieToAdd, chosenCat);
    }
    
    public void deleteFromCategory(Movie movieToDelete)
    {
        cDbDAO.deleteFromCategory(movieToDelete);
    }
    
    public List<Category> getAllCategories ()
    {
        return cDbDAO.getAllCategories();
    }
    
    public void addPersonalRating (Movie movieToRate, double personalRating)
    {
        mDbDAO.addPersonalRating(movieToRate, personalRating);
    }
    
    public void lastview (Movie movieToEdit)
    {
        mDbDAO.lastview(movieToEdit);
    }
}

