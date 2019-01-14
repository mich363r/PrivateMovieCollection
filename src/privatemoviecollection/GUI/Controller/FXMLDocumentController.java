/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
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
    @FXML
    private Button btnAddMovie;

    private Boolean searchDone;
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Button btnSearch;

    private MovieModel mModel;
    private Category currentCategory;
    @FXML
    private TableView<Movie> tbViewCatMovies;
    @FXML
    private Button deleteFromCategory;
    @FXML
    private TableColumn<Movie, String> colCatTitle;
    @FXML
    private TableColumn<Movie, String> colCatImdb;
    @FXML
    private TableColumn<Movie, String> colCatPersonal;

    public FXMLDocumentController()
    {
        searchDone = false;
    }

    /*
    Creates a new object of the MovieModel class and populates the tableviews
    */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            mModel = new MovieModel();
            tbViewMovie.setItems(mModel.getAllMovies());
            tbViewCategory.setItems(mModel.getAllCategories());
        } catch (IOException | SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        colCat.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        colCatTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCatImdb.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colCatPersonal.setCellValueFactory(new PropertyValueFactory<>("personalRating"));

    }

    /*
    sends a search input to the model and inserts the responce into the tableview
    the second click will clear the input field.
    */
    @FXML
    public void handleSearch(ActionEvent event) throws SQLException
    {
        double highImdb = 10;
        double lowImdb = Double.parseDouble(txtSearch.getText());
        
//        if (lowImdb >= 0 && lowImdb <= highImdb && lowImdb <= highImdb)
//        {
            mModel.searchImdbRating(lowImdb, highImdb);
//        }
        
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
    /*
    creates a new category if the input is valid
    */
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
    /*
    opens a new window which is then used to add a new movie
    @throws IOException
    */
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
    
    /*
    Deletes the selected movie
    */
    @FXML
    public void deleteMovie() throws SQLException
    {
        if (tbViewMovie.getSelectionModel().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "You have to select a movie to delete", "Invalid selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this movie?", "Delete", JOptionPane.YES_NO_OPTION);
        if (p == 0)
        {
            mModel.deleteMovie(tbViewMovie.getSelectionModel().getSelectedItem());
        }
    }
    
    /*
    Deletes the selected category
    */
    @FXML
    public void deleteCategory(ActionEvent event)
    {
        if (tbViewCategory.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null, "You have to select a category to delete");
            return;
        }
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this category?", "Delete", JOptionPane.YES_NO_OPTION);

        if (p == 0)
        {
            mModel.deleteCategory(tbViewCategory.getSelectionModel().getSelectedItem());
        }
    }
    /*
    adds a personal rating to the selected movie between 1 and 10
    */
    @FXML
    public void addPersonalRating(ActionEvent event)
    {
        if (tbViewMovie.getSelectionModel().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "you have to select a movie", "invalid selection", JOptionPane.ERROR_MESSAGE);
           return;
        }
        double p = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter a rating between 1 - 10", "Enter", JOptionPane.OK_CANCEL_OPTION));

        if (p >= 1 && p <= 10)
        {
            mModel.addPersonalRating(tbViewMovie.getSelectionModel().getSelectedItem(), p);
        } else
        {
            JOptionPane.showMessageDialog(null, "You have to enter a number between 1 and 10", "Incorrect number", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
    plays the tableview movie when you click it twice
    */
    @FXML
    public void clickPlayMovie(MouseEvent event) throws IOException
    {
        if (!tbViewMovie.getSelectionModel().isEmpty())
        {
            String moviePath = tbViewMovie.getSelectionModel().getSelectedItem().getFilelink();
            File movieFile = new File(moviePath);
            Movie movieToPlay = tbViewMovie.getSelectionModel().getSelectedItem();

            Date lastview = new Date();
            if (event.getButton().equals(MouseButton.PRIMARY))
            {
                if (event.getClickCount() == 2)
                {
                    Desktop.getDesktop().open(movieFile);
                    movieToPlay.setLastview(lastview);
                    mModel.setLastviewed(movieToPlay);
                }
            }
        }
    }
    
    /*
    opens imdb's homepage
    */
    @FXML
    public void openImdb(ActionEvent event)
    {
        try
        {
            String url = "https://www.imdb.com/";
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    selects which category's items are shown
    */
    @FXML
    public void selectCat(MouseEvent event) throws SQLException
    {
        if (!tbViewCategory.getSelectionModel().isEmpty())
        {
            currentCategory = tbViewCategory.getSelectionModel().getSelectedItem();
            tbViewCatMovies.setItems(mModel.getAllMoviesInCategory(currentCategory));
            if (event.getButton().equals(MouseButton.PRIMARY))
            {
                if (event.getClickCount() == 2)
                {

                    tbViewCategory.getSelectionModel().clearSelection();
                    currentCategory = null;
                }
            }
        }
    }
    
    /*
    adds selected movie to the selected category
    */
    @FXML
    public void addToCategory(ActionEvent event)
    {
        if (tbViewMovie.getSelectionModel().getSelectedItem() == null || tbViewCategory.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null, "You have to select both a movie to add, and a category to add it to.", "Illegal selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Movie selectedMovie = tbViewMovie.getSelectionModel().getSelectedItem();
        Category selectedCat = tbViewCategory.getSelectionModel().getSelectedItem();
        mModel.addToCategory(selectedMovie, selectedCat);
    }

    /*
    removes the selected movie from the selected category
    */
    @FXML
    public void deleteFromCategory(ActionEvent event)
    {
        if (tbViewCatMovies.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You have to select a movie from the list of category movies to delete", "Illegal selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this movie from this category", "Delete", JOptionPane.YES_NO_OPTION);

        if (p == 0)
        {
            mModel.deleteFromCategory(tbViewCatMovies.getSelectionModel().getSelectedItem(), tbViewCategory.getSelectionModel().getSelectedItem());
            tbViewCatMovies.getItems().clear();
            tbViewCatMovies.setItems(mModel.getAllMoviesInCategory(currentCategory)); // gets the new list of songs minus the deleted ones
        }
    }
}
