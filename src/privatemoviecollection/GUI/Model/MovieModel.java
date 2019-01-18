/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.MovieManager;
import privatemoviecollection.DAL.Exception.DALException;

/**
 *
 * @author kokus
 */
public class MovieModel
{
    private ObservableList<Movie> movieList;
    private ObservableList<Category> categoryList;
    private ObservableList<Movie> catMovie;
    private MovieManager mManager;

    /*
    Initiates the lists and creates a new instance of the manager class.
    */
    public MovieModel() throws IOException, SQLException, DALException
    {
        mManager = new MovieManager();
        catMovie = FXCollections.observableArrayList();
        categoryList = FXCollections.observableArrayList();
        categoryList.addAll(mManager.getAllCategories());
        movieList = FXCollections.observableArrayList();
        movieList.addAll(mManager.getAllMovies());
    }
    
    /*
    adds a movie to our database and then clears our observableList and sets it again, thus refreshing the list
    
    @param movieToAdd
    */
    public void addMovie(Movie movieToAdd) throws SQLException, DALException
    {
        mManager.addMovie(movieToAdd);
        movieList.add(movieToAdd); 
    }
    
    /*
    deletes a movie and removes it from our observableList
    
    @param movieToDelete
    */
    public void deleteMovie(Movie movieToDelete) throws SQLException, DALException
    {
        mManager.deleteMovie(movieToDelete);
        this.movieList.remove(movieToDelete);
    }
    
    /*
    retrieves all our movies and stores them in a temporary list.
    Furthermore, it clears our observableList, sets it anew.
    
    @return movieList
    */
    public ObservableList<Movie> getAllMovies() throws SQLException, DALException
    {
        List<Movie> tempMovieList = mManager.getAllMovies();
        movieList.clear();
        movieList.addAll(tempMovieList);
        return movieList;
    }
    
    /*
    calls on the manager to retrieve a specific movie
    
    @param movie
    @return
    */
    public Movie getMovie(Movie movie) throws DALException
    {
        return mManager.getMovie(movie);
    }

    /*
    retrieves a list from the manager and then converts it into an observableList.
    
    @param input
    @return 
    */
    public ObservableList<Movie> searchMovies(String input) throws DALException
    {
        return FXCollections.observableArrayList(mManager.searchMovies(input));
    }

    /*
    adds a category to our database as well as our observableList
    
    @param catToAdd
    */
    public void addCategory(Category catToAdd) throws DALException
    {
        mManager.addCategory(catToAdd);
        categoryList.add(catToAdd);
    }
    
    /*
    removes a category from our database as well as our observableList.
    
    @param catToDelete
    */
    public void deleteCategory(Category catToDelete) throws DALException
    {
        mManager.deleteCategory(catToDelete);
        this.categoryList.remove(catToDelete);
    }
    
    /*
    adds a movie to our database of movies within a category.
    It also adds the movie to our observableList of movies within a category.
    
    @param movieToAdd
    @param chosenCat
    */
    public void addToCategory(Movie movieToAdd, Category chosenCat) throws DALException
    {
        mManager.addToCategory(movieToAdd, chosenCat);
        catMovie.add(movieToAdd);
    }
    
    /*
    removes a movie from our database of movies within a category.
    It also from the movie from our observableList of movies within a category.
    
    @param movieToDelete
    @param chosenCategory
    */
    public void deleteFromCategory(Movie movieToDelete, Category chosenCategory) throws DALException
    {
        mManager.deleteFromCategory(movieToDelete, chosenCategory);
        catMovie.remove(movieToDelete);
    }
    
    /*
    Retrieves and converts the list of categories from the manager and returns it
    
    @return categoryList an observableList with all the categories
    */
    public ObservableList<Category> getAllCategories() throws DALException
    {
        return categoryList;
    }
    
    /*
    adds a personal rating to the chosen movie
    
    @param movieToRate
    @param personalRating
    */
    public void addPersonalRating (Movie movieToRate, double personalRating) throws DALException, SQLException
    {
        mManager.addPersonalRating(movieToRate, personalRating);
        movieToRate.setPersonalRating(personalRating);   
        movieList.clear();
        movieList.addAll(mManager.getAllMovies());
    }
    
    /*
    sets the date of the last time the chosen movie was watched
    
    @param movieToEdit
    */
    public void setLastviewed (Movie movieToEdit) throws DALException, SQLException
    {
        Date lastview = new Date();
        movieToEdit.setLastview(lastview);
        mManager.setLastviewed(movieToEdit);
        movieList.setAll(mManager.getAllMovies());
    }
    
    /*
    makes a temporary list of movies, refreshes the observableList and returns it.
    
    @param chosenCat
    @return catMovie a list containing all the movies linked to the chosen category
    */
    public ObservableList<Movie> getAllMoviesInCategory(Category chosenCat) throws DALException
    {
          List<Movie> tempMovies = mManager.getAllMoviesInCategory(chosenCat);
        catMovie.clear();
        catMovie.addAll(tempMovies);
        return catMovie;
    }
    
    /*
    searches for movies based on imdb rating and then refreshes the list based on the result
    @param lowImdb the input from the user
    @param highImdb the roof for the search, in this case 10, based on imdb's rating system
    @return movieList
    */
    public ObservableList<Movie> searchImdbRating (double lowImdb, double highImdb) throws DALException
    {
        movieList.setAll((mManager.searchImdbRating(lowImdb, highImdb)));
        return movieList;
    }
    
    /*
    searches on movies based on the current chosen category
    
    @param input
    @param chosenCat the chosen category
    @return catMovie the list containing the result of the search query
    */
    public ObservableList<Movie> searchMoviesInCat (String input, Category chosenCat) throws DALException
    {
        catMovie.setAll((mManager.searchMoviesInCat(input, chosenCat)));
        return catMovie;
        
    }
}
