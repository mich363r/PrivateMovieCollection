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
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.MovieManager;

/**
 *
 * @author kokus
 */
public class MovieModel
{
   private ObservableList <Movie> movieList;
   private MovieManager mManager;
   
   public MovieModel () throws IOException, SQLException
   {
       MovieManager mManager = new MovieManager();
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
    
//    public ObservableList<Movie≥ searchMovies(String input)
//    {
//        return mManager.searchMovies(input);
//    }
}
