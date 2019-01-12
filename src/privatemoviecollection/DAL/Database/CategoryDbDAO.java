/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;

/**
 *
 * @author Asvør
 */
public class CategoryDbDAO
{

    DbConnectionProvider ds;

    public CategoryDbDAO() throws IOException
    {
        ds = new DbConnectionProvider();
    }

    public void addCategory(Category catToAdd)
    {
        String name = catToAdd.getName();
        try (Connection con = ds.getConnection())
        {
            String sql = "INSERT INTO Category VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.execute();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteCategory(Category catToDelete)
    {
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Category WHERE id = (?)");
            pstmt.setInt(1, catToDelete.getId());
            pstmt.execute();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addToCategory(Movie movieToAdd, Category chosenCat)
    {

        try (Connection con = ds.getConnection())
        {
            String sql = "INSERT INTO CatMovie VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, chosenCat.getId());
            pstmt.setInt(2, movieToAdd.getId());
            pstmt.execute();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteFromCategory(Movie movieToDelete)
    {
        int id = movieToDelete.getId();
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM CatMovie WHERE movieId = (?)");
            pstmt.setInt(1, id);
            pstmt.execute();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories()
    {
        List<Category> catList = new ArrayList<>();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Category");

            while (rs.next())
            {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                catList.add(new Category(id, name));

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return catList;
    }

    public List<Movie> getCategorySongs(Category chosenCat)
    {
        List<Movie> catMovieList = new ArrayList<>();
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Movie INNER JOIN CatMovie ON Movie.id = CatMovie.movieId WHERE categoryId = (?)");
            pstmt.setInt(1, chosenCat.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                double imdbRating = rs.getDouble("imdbRating");
                double personalRating = rs.getDouble("personalRating");
                String filelink = rs.getString("filelink");
                Date lastview = rs.getDate("lastview");

                catMovieList.add(new Movie(id, title, imdbRating, personalRating,filelink,lastview));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return catMovieList;
    }
}
