/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;
import org.blinkenlights.jid3.v1.ID3V1_1Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.MovieModel;

/**
 * FXML Controller class
 *
 * @author Asvør
 */
public class FXMLDocumentController implements Initializable
{

//    @FXML
//    private AnchorPane rootPane2;
    @FXML
    private TableView<Movie> tbViewMovie;
    @FXML
    private TableColumn<Movie, String> colImdbRating;
    @FXML
    private TableColumn<Movie, String> colPersonalRating;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableView<Category> tbViewCategory;
    @FXML
    private TableColumn<Category, String> colCat;
    @FXML
    private TextField txtSearch;

    MovieModel mModel;
    
    @FXML
    private Button btnAddMovie;
    
    private Boolean searchDone;
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Button btnSearch;
    

    public FXMLDocumentController()
    {
        searchDone = false;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            mModel = new MovieModel();
            tbViewMovie.setItems(mModel.getAllMovies());
            tbViewCategory.setItems(mModel.getAllCategories());
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        colCat.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<> ("title"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory<> ("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<> ("personalRating"));
        

    }    


    @FXML
    public void handleSearch(ActionEvent event) throws SQLException
    {
        if (searchDone == false)
        {
            searchDone = true;
            String input = txtSearch.getText();
            tbViewMovie.setItems(mModel.searchMovies(input));
            btnSearch.setText("Clear");
        } else if (searchDone == true)
        {
            searchDone = false;
            btnSearch.setText("Search");
            tbViewMovie.setItems(mModel.getAllMovies());
            txtSearch.clear();
        }
    }

    @FXML
    public void addCategory(ActionEvent event)
    {
        
        String catName = JOptionPane.showInputDialog(null, "Category name", "add new category", JOptionPane.OK_OPTION);
        Category newCat = new Category(0, catName);
        if (catName == null || catName.equals(""))
        {
            return;
        }
        mModel.addCategory(newCat);
    }

    @FXML
    public void addMovie(ActionEvent event) throws IOException
    {
        Stage primeStage = (Stage) btnAddMovie.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/GUI/View/addMovieWindow.fxml"));
        Parent root = loader.load();

        AddMovieWindowController addMovieController = loader.getController();
        addMovieController.setModel(mModel);

        Stage stageAddMovie = new Stage();
        stageAddMovie.setScene(new Scene(root));

        stageAddMovie.initModality(Modality.WINDOW_MODAL);
        stageAddMovie.initOwner(primeStage);
        stageAddMovie.show();
    }
    
    @FXML
    public void deleteMovie () throws SQLException
    {
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this movie?", "Delete", JOptionPane.YES_NO_OPTION);
        if (p == 0)
        {
            mModel.deleteMovie(tbViewMovie.getSelectionModel().getSelectedItem());
 
        }
    }
    
    @FXML
    public void deleteCategory(ActionEvent event)
    {
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this category?", "Delete", JOptionPane.YES_NO_OPTION);
        
        if (p == 0)
        {
            mModel.deleteCategory(tbViewCategory.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void addPersonalRating(ActionEvent event)
    {
        double p = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter a rating between 1 - 10", "Enter", JOptionPane.OK_CANCEL_OPTION));
       
      if( p >= 1 && p <= 10)
      {
            mModel.addPersonalRating(tbViewMovie.getSelectionModel().getSelectedItem(), p);
      } 
      else
          JOptionPane.showMessageDialog(null, "You have to enter a number between 1 and 10", "Incorrect number", JOptionPane.ERROR_MESSAGE);
    }
    
  
}
