/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL.Database;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
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

    public MovieDbDAO() throws IOException
    {
        ds = new DbConnectionProvider();
    }
    
public void addMovie(Movie movieToAdd) throws SQLServerException, SQLException
{
    String title = movieToAdd.getTitle();
    String filelink = movieToAdd.getFilelink();
    double imdbRating = movieToAdd.getImdbRating();
    double personalRating = movieToAdd.getPersonalRating();
    String lastview = movieToAdd.getLastview();
    
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
        pstmt.setString(5, lastview);
        pstmt.execute();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}
    
public void deleteMovie(Movie movieToDelete) throws SQLServerException, SQLException   
{
    int id = movieToDelete.getId();
    
    try (Connection con = ds.getConnection())
    {
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Movie WHERE id = (?)");
        pstmt.setInt(1, id);
        pstmt.execute();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}

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
            String lastview = rs.getString("lastview");
            
            movieList.add(new Movie (id, title, imdbRating, personalRating, filelink, lastview));
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return movieList;
}

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
            String lastview = rs.getString("lastview");
            wantedMovie = new Movie(id, title, imdbRating, personalRating, filelink, lastview);
            
        }
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return wantedMovie;
}

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
            String filelink = rs.getString("filelink");
            double imdbRating = rs.getDouble("imdbRating");
            double personalRating = rs.getDouble("personalRating");
            String lastview = rs.getString("lastview");
//            String category = rs.getString("category");

            movieList.add(new Movie (id, title, imdbRating, personalRating, filelink, lastview));
        
        }
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return movieList;
}

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
    
    public void lastview (Movie movieToEdit)
    {
        String lastSeen = movieToEdit.getLastview();
        
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Movie SET Lastview = (?) WHERE id = (?)");
            pstmt.setString(1, lastSeen);
            pstmt.setInt(2, movieToEdit.getId());
            pstmt.execute();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Movie checkDuplicate (Movie movieToCheck)
    {
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("SELECT title FROM Movie WHERE title = (?)");
            pstmt.setString(1, movieToCheck.getTitle());
            pstmt.execute();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
            return movieToCheck;
    }
}
