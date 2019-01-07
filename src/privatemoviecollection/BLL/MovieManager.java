/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.Database.MovieDbDAO;

/**
 *
 * @author kokus
 */
public class MovieManager
{
    MovieDbDAO mDbDAO;
    
    public MovieManager() throws IOException
    {
        this.mDbDAO = new MovieDbDAO();
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
}

