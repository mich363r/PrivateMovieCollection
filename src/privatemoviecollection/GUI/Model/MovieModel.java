/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.MovieManager;

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

    public MovieModel() throws IOException, SQLException
    {
        mManager = new MovieManager();
        categoryList = FXCollections.observableArrayList();
        categoryList.addAll(mManager.getAllCategories());
        movieList = FXCollections.observableArrayList();
        movieList.addAll(mManager.getAllMovies());
    }

    public void addMovie(Movie movieToAdd) throws SQLException
    {
        mManager.addMovie(movieToAdd);
        movieList.add(movieToAdd);
    }

    public void deleteMovie(Movie movieToDelete) throws SQLException
    {
        mManager.deleteMovie(movieToDelete);
        this.movieList.remove(movieToDelete);
    }

    public ObservableList<Movie> getAllMovies() throws SQLException
    {
        List<Movie> tempMovieList = mManager.getAllMovies();
        movieList.clear();
        movieList.addAll(tempMovieList);
        return movieList;
    }

    public Movie getMovie(Movie movie)
    {
        return mManager.getMovie(movie);
    }

    public ObservableList<Movie> searchMovies(String input)
    {
        return FXCollections.observableArrayList(mManager.searchMovies(input));
    }

    public void addCategory(Category catToAdd)
    {
        mManager.addCategory(catToAdd);
        categoryList.add(catToAdd);
    }

    public void deleteCategory(Category catToDelete)
    {
        mManager.deleteCategory(catToDelete);
        this.categoryList.remove(catToDelete);
    }

    public void addToCategory(Movie movieToAdd, Category chosenCat)
    {
        mManager.addToCategory(movieToAdd, chosenCat);
        catMovie.add(movieToAdd);
    }

    public void deleteFromCategory(Movie movieToDelete)
    {
        mManager.deleteFromCategory(movieToDelete);
        catMovie.remove(movieToDelete);
    }

    public ObservableList<Category> getAllCategories()
    {
        categoryList = FXCollections.observableArrayList(mManager.getAllCategories());
        return categoryList;
    }
    
    public void addPersonalRating (Movie movieToRate, double personalRating)
    {
        mManager.addPersonalRating(movieToRate, personalRating);
        movieToRate.setPersonalRating(personalRating);   
    }
    
    public void lastview (Movie movieToEdit)
    {
        mManager.lastview(movieToEdit);
    }
}
