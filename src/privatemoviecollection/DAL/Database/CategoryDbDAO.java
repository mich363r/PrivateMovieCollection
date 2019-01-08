/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL.Database;

import java.io.IOException;
import java.sql.Connection;
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
    
    public void addCategory(String name)
    {
        try (Connection con = ds.getConnection())
        {
           String sql = "INSERT INTO Category VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.execute();
            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void deleteCategory(int id)
    {
        try (Connection con = ds.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Category WHERE id = (?)");
            pstmt.setInt(1, id);
            pstmt.execute();
            
        } 
        catch (Exception e)
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
            pstmt.setInt(1, movieToAdd.getId());
            pstmt.setInt(2, chosenCat.getId());
            pstmt.execute();
            
        } 
        catch (Exception e)
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
            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public List<Category> getAllCategorys ()
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
                catList.add(new Category (id, name));
                
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return catList;
    }
}
