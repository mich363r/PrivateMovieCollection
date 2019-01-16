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
import privatemoviecollection.DAL.Exception.DALException;

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
    @throws DALException
    */
    public void addMovie (Movie movieToAdd) throws SQLException, DALException
    {
        mDbDAO.addMovie(movieToAdd);
    }
    
    /*
    tells the movie data access object to delete a movie from the database.
    @param movieToDelete the movie we want to delete
    @throws DALException
    @throws SQLException
    */
    public void deleteMovie (Movie movieToDelete) throws SQLException, DALException
    {
        mDbDAO.deleteMovie(movieToDelete);
    }
    
    /*
    tells the movie data access object to retrieve all the movies in the database
    and then returns this information as a List
    @return
    @throws DALException
    @throws SQLException
    */
    public List<Movie> getAllMovies () throws SQLException, DALException
    {
        return mDbDAO.getAllMovies();
    }
    
    /*
    tells the movie data access object to retrieve a specific movie in the database
    @param movie the wanted movie
    @return
    @throws DALException
    */
    public Movie getMovie (Movie movie) throws DALException
    {
        return mDbDAO.getMovie(movie.getId());
    }
    
    /*
    tells the MovieDbDAO class to run the search method and then returns the result
    @param input the search query
    @return
    @throws DALException
    */
    public List<Movie> searchMovies (String input) throws DALException
    {
        return mDbDAO.searchMovies(input);
    }
    
    /*
    tells the CategoryDbDAO class to add a category to the database
    @param catToAdd the category we are adding to the database
    @throws DALException
    */
    public void addCategory(Category catToAdd) throws DALException
    {
        cDbDAO.addCategory(catToAdd);
    }
    
    /*
    tells the CategoryDbDAO class to remove a category from the database
    @param catToDelete the category we want deleted
    @throws DALException
    */
    public void deleteCategory(Category catToDelete) throws DALException
    {
        cDbDAO.deleteCategory(catToDelete);
    }
    
    /*
    tells the CategoryDbDAO class to add a movie to the chosen category
    @param movieToAdd the movie want we want added
    @param chosenCat the category we want to add to
    @throws DALException
    */
    public void addToCategory (Movie movieToAdd, Category chosenCat) throws DALException
    {
        cDbDAO.addToCategory(movieToAdd, chosenCat);
    }
    
    /*
    tells the CategoryDbDAO class to delete a movie from a category
    @param movieToDelete the movie we want removed
    @throws DALException
    */
    public void deleteFromCategory(Movie movieToDelete, Category chosenCategory) throws DALException
    {
        cDbDAO.deleteFromCategory(movieToDelete, chosenCategory);
    }
    
    /*
    tells the CategoryDbDAO class to retrieve all categories and then returns the list
    
    @return
    @throws DALException
    */
    public List<Category> getAllCategories () throws DALException
    {
        return cDbDAO.getAllCategories();
    }
    
    /*
    tells the MovieDbDAO class to add a personal rating to the chosen movie
    @param movieToRate the movie we want rated
    @param personalRating the input rating to add
    @throws DALException
    */
    public void addPersonalRating (Movie movieToRate, double personalRating) throws DALException
    {
        mDbDAO.addPersonalRating(movieToRate, personalRating);
    }
    
    /*
    tells the MovieDbDAO class to set the last view date of the chosen movie
    @param movieToEdit
    @throws DALException
    */
    public void setLastviewed (Movie movieToEdit) throws DALException
    {
        mDbDAO.setLastviewed(movieToEdit);
    }
    
    /*
    tells the MovieDbDAO class to get all the movies within the chosen category
    
    @param chosenCat
    @return
    @throws DALException
    */
    public List<Movie> getAllMoviesInCategory(Category chosenCat) throws DALException
    {
       return cDbDAO.getCategoryMovies(chosenCat);
    }
    
    /*
    searches movies based on IMDb rating and returns it as a list
    
    @param lowImdb
    @param highImdb
    @return
    @throws DALException
    */
    public List<Movie> searchImdbRating (double lowImdb, double highImdb) throws DALException
    {
        return mDbDAO.searchImdbRating(lowImdb, highImdb);
    }
    
    /*
    Searches movies within the chosen category based on input
    
    @param input
    @param chosenCat
    @return
    @throws DALException
    */
    public List<Movie> searchMoviesInCat (String input, Category chosenCat) throws DALException
    {
        return cDbDAO.searchMoviesInCat(input, chosenCat);
    }
    
}

