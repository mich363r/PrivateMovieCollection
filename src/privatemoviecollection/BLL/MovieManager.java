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
   
    /*
    this constructor initializes the DbDAO fields
    */
    public MovieManager() throws IOException
    {
        this.mDbDAO = new MovieDbDAO();
        this.cDbDAO = new CategoryDbDAO();
    }
    
    /*
    tells the movie data access object to add a new movie to the database
    @param movieToAdd the movie we want added
    */
    public void addMovie (Movie movieToAdd) throws SQLException
    {
        mDbDAO.addMovie(movieToAdd);
    }
    
    /*
    tells the movie data access object to delete a movie from the database.
    @param movieToDelete the movie we want to delete
    */
    public void deleteMovie (Movie movieToDelete) throws SQLException
    {
        mDbDAO.deleteMovie(movieToDelete);
    }
    
    /*
    tells the movie data access object to retrieve all the movies in the database
    and then returns this information as a List
    */
    public List<Movie> getAllMovies () throws SQLException
    {
        return mDbDAO.getAllMovies();
    }
    
    /*
    tells the movie data access object to retrieve a specific movie in the database
    @param movie the wanted movie
    */
    public Movie getMovie (Movie movie)
    {
        return mDbDAO.getMovie(movie.getId());
    }
    
    /*
    tells the MovieDbDAO class to run the search method and then returns the result
    @param input the search query
    */
    public List<Movie> searchMovies (String input)
    {
        return mDbDAO.searchMovies(input);
    }
    
    /*
    tells the CategoryDbDAO class to add a category to the database
    @param catToAdd the category we are adding to the database
    */
    public void addCategory(Category catToAdd)
    {
        cDbDAO.addCategory(catToAdd);
    }
    
    /*
    tells the CategoryDbDAO class to remove a category from the database
    @param catToDelete the category we want deleted
    */
    public void deleteCategory(Category catToDelete)
    {
        cDbDAO.deleteCategory(catToDelete);
    }
    
    /*
    tells the CategoryDbDAO class to add a movie to the chosen category
    @param movieToAdd the movie want we want added
    @param chosenCat the category we want to add to
    */
    public void addToCategory (Movie movieToAdd, Category chosenCat)
    {
        cDbDAO.addToCategory(movieToAdd, chosenCat);
    }
    
    /*
    tells the CategoryDbDAO class to delete a movie from a category
    @param movieToDelete the movie we want removed
    */
    public void deleteFromCategory(Movie movieToDelete, Category chosenCategory)
    {
        cDbDAO.deleteFromCategory(movieToDelete, chosenCategory);
    }
    
    /*
    tells the CategoryDbDAO class to retrieve all categories and then returns the list
    */
    public List<Category> getAllCategories ()
    {
        return cDbDAO.getAllCategories();
    }
    
    /*
    tells the MovieDbDAO class to add a personal rating to the chosen movie
    @param movieToRate the movie we want rated
    @param personalRating the input rating to add
    */
    public void addPersonalRating (Movie movieToRate, double personalRating)
    {
        mDbDAO.addPersonalRating(movieToRate, personalRating);
    }
    
    /*
    tells the MovieDbDAO class to set the last view date of the chosen movie
    @param movieToEdit
    */
    public void setLastviewed (Movie movieToEdit)
    {
        mDbDAO.setLastviewed(movieToEdit);
    }
    
    /*
    tells the MovieDbDAO class to get all the movies within the chosen category
    */
    public List<Movie> getAllMoviesInCategory(Category chosenCat)
    {
       return cDbDAO.getCategoryMovies(chosenCat);
    }
    
    public List<Movie> searchImdbRating (double lowImdb, double highImdb)
    {
        return mDbDAO.searchImdbRating(lowImdb, highImdb);
    }
    
    public List<Movie> searchMoviesInCat (String input, Category chosenCat)
    {
        return cDbDAO.searchMoviesInCat(input, chosenCat);
    }
    
}

