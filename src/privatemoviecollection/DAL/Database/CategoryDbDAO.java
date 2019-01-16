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
import privatemoviecollection.DAL.Exception.DALException;

/**
 *
 * @author Asvør
 */
public class CategoryDbDAO
{

    DbConnectionProvider ds;
    
    /*
    this constructor establishes the connection to our database
    */
    public CategoryDbDAO() throws IOException
    {
        ds = new DbConnectionProvider();
    }
    
    /*
    adds a category to our database
    @param catToAdd
    */
    public void addCategory(Category catToAdd) throws DALException
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
           throw new DALException("Could not access the database",e);
        }
    }
    
    /*
    deletes a category from our database
    @param catToDelete
    */
    public void deleteCategory(Category catToDelete) throws DALException
    {
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM CatMovie WHERE categoryId = (?)");
            pstmt1.setInt(1, catToDelete.getId());
            pstmt1.execute();
                    
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Category WHERE id = (?)");
            pstmt.setInt(1, catToDelete.getId());
            pstmt.execute();

        } catch (Exception e)
        {
            throw new DALException("Could not access the database",e);
        }
    }

    /*
    adds a movie to our chosen category in the database.
    @param movieToAdd
    @param chosenCat
    */
    public void addToCategory(Movie movieToAdd, Category chosenCat) throws DALException
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
           throw new DALException("Could not access the database",e);
        }
    }
    
    /*
    deletes a movie from our category
    @param movieToDelete
    */
    public void deleteFromCategory(Movie movieToDelete, Category chosenCategory) throws DALException
    {
        int id = movieToDelete.getId();
        int catId = chosenCategory.getId();
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM CatMovie WHERE movieId = (?) AND categoryId = (?)");
            pstmt.setInt(1, id);
            pstmt.setInt(2, catId);
            pstmt.execute();

        } catch (Exception e)
        {
           throw new DALException("Could not access the database",e);
        }
    }
    
    /*
    retrieves all the categories from the database
    and returns them as a list of categories
    */
    public List<Category> getAllCategories() throws DALException
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
           throw new DALException("Could not access the database",e);
        }
        return catList;
    }
    
    /*
    retrieves from the database a list of movies within the chosen category
    @param chosenCat the category from which we retrieve the movies
    */
    public List<Movie> getCategoryMovies(Category chosenCat) throws DALException
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
            throw new DALException("Could not access the database",e);
        }
        return catMovieList;
    }
    
    /**
 *  Search for movies in categories
 */
    public List<Movie> searchMoviesInCat (String input, Category chosenCat) throws DALException
    {
        List<Movie> searchCatMovie = new ArrayList<>();
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Movie INNER JOIN CatMovie ON Movie.id = CatMovie.movieId Where Movie.title LIKE (?) AND CatMovie.categoryId = (?)");
            pstmt.setString(1, "%" + input + "%");
            pstmt.setInt(2, chosenCat.getId());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                double imdbRating = rs.getDouble("imdbRating");
                double personalRating = rs.getDouble("personalRating");
                String filelink = rs.getString("filelink");
                Date lastview = rs.getDate("lastview");

                searchCatMovie.add(new Movie(id, title, imdbRating, personalRating,filelink,lastview));
            }
        }
        catch (Exception e)
        {
            throw new DALException("Could not access the database",e);
        }
        return searchCatMovie;
    }
}

