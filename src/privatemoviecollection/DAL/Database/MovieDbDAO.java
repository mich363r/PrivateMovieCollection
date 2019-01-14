/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL.Database;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.BE.Movie;

/**
 *
 * @author Asvør
 */
public class MovieDbDAO
{
    DbConnectionProvider ds;
    
    /*
    establishes a connection to the database
    */
    public MovieDbDAO() throws IOException
    {
        ds = new DbConnectionProvider();
    }
    
    /*
    adds a movie to the database
    @param movieToAdd
    @throws SQLServerException
    @throws SQLException
    */
public void addMovie(Movie movieToAdd) throws SQLServerException, SQLException
{
    String title = movieToAdd.getTitle();
    String filelink = movieToAdd.getFilelink();
    double imdbRating = movieToAdd.getImdbRating();
    double personalRating = movieToAdd.getPersonalRating();
    Date lastview = null;
    
    try (Connection con = ds.getConnection())
    {
        String sql1 = "DELETE FROM Movie WHERE title = (?)";
        PreparedStatement pstmt1 = con.prepareStatement(sql1);
        pstmt1.setString(1, title);
        pstmt1.execute();
        
        String sql = "INSERT INTO Movie VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, title);
        pstmt.setDouble(2, imdbRating);
        pstmt.setDouble(3, personalRating);
        pstmt.setString(4, filelink);
        pstmt.setDate(5, lastview);
        pstmt.execute();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}

    /*
    deletes a movie from the database
    @param movieToDelete
    @throws SQLServerException
    @throws SQLException
    */
public void deleteMovie(Movie movieToDelete) throws SQLServerException, SQLException   
{
    int id = movieToDelete.getId();
    
    try (Connection con = ds.getConnection())
    {
        PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM CatMovie WHERE movieId = (?)");
        pstmt1.setInt(1, id);
        pstmt1.execute();
        
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Movie WHERE id = (?)");
        pstmt.setInt(1, id);
        pstmt.execute();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}

/*
gets all the movies from the database and returns them as a list of movies
@throws SQLServerException
@throws SQLException
*/
public List<Movie> getAllMovies () throws SQLServerException, SQLException
{
    List<Movie> movieList = new ArrayList<>();
    
    try (Connection con = ds.getConnection())
    {
        
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Movie");
        while (rs.next())
        {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String filelink = rs.getString("filelink");
            double imdbRating = rs.getDouble("imdbRating");
            double personalRating = rs.getDouble("personalRating");
            Date lastview = rs.getDate("lastview");
            
            movieList.add(new Movie (id, title, imdbRating, personalRating, filelink, lastview));
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return movieList;
}

/*
 gets a movie from the database by matching the id
@param id the id of the movie to get
*/
public Movie getMovie(int id)
{
    Movie wantedMovie = null;
    
    try (Connection con = ds.getConnection())
    {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Movie WHERE id = (?)");
        pstmt.setInt(1, id);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next())
        {
            id = rs.getInt("id");
            String title = rs.getString("title");
            String filelink = rs.getString("filelink");
            double imdbRating = rs.getDouble("imdbRating");
            double personalRating = rs.getDouble("personalRating");
            Date lastview = rs.getDate("lastview");
            wantedMovie = new Movie(id, title, imdbRating, personalRating, filelink, lastview);
            
        }
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return wantedMovie;
}
/*
searches the database based on the input and returns the result as alist of movies
@param input the searched query
*/
public List<Movie> searchMovies (String input)
{
    List <Movie> movieList = new ArrayList <>();
    try (Connection con = ds.getConnection())
    {

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Movie WHERE title like ?");
        pstmt.setString(1, "%" + input + "%");
        
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            double imdbRating = rs.getDouble("imdbRating");
            double personalRating = rs.getDouble("personalRating");
            String filelink = rs.getString("filelink");
            Date lastview = rs.getDate("lastview");


            movieList.add(new Movie (id, title, imdbRating, personalRating, filelink, lastview));
        
        }
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return movieList;
}





/*
searches for movies based on IMDB rating
@param highImdb
@param  lowImdb
@return List<Movie>
*/
public List<Movie> searchImdbRating (double lowImdb, double highImdb)
{
    List <Movie> movieToSearch = new ArrayList <>();
    double lowRating = lowImdb;
    double highRating = highImdb;
    
    try ( Connection con = ds.getConnection())
    {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Movie WHERE imdbRating BETWEEN (?) AND (?)");
        pstmt.setDouble(1, lowRating);
        pstmt.setDouble(2, highRating);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next())
        {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            double imdbRating = rs.getDouble("imdbRating");
            double personalRating = rs.getDouble("personalRating");
            String filelink = rs.getString("filelink");
            Date lastview = rs.getDate("lastview");
            
            movieToSearch.add(new Movie (id, title, imdbRating, personalRating, filelink, lastview));
        }
        
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return movieToSearch;
}

    /*
    adds a personal rating to a movie
    @param  movieToRate
    @param personalRating the input rating
    */
    public void addPersonalRating (Movie movieToRate, double personalRating)
    {
       
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Movie SET personalRating = (?) WHERE id = (?)");
            pstmt.setDouble(1, personalRating);
            pstmt.setInt(2, movieToRate.getId());
            pstmt.execute();
                       
            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /*
    converts the date to sql and updates the movie's lastview in the database
    @param movieToEdit
    */
    public void setLastviewed (Movie movieToEdit)
    {
        java.util.Date dateToConvert = movieToEdit.getLastview();
        java.sql.Date dateConverter = new java.sql.Date(dateToConvert.getTime());
        Date lastSeen = dateConverter;
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Movie SET Lastview = (?) WHERE id = (?)");
            pstmt.setDate(1, lastSeen);
            pstmt.setInt(2, movieToEdit.getId());
            pstmt.execute();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
