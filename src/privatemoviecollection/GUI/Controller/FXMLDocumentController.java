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
import java.text.SimpleDateFormat;
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
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        colCatTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCatImdb.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colCatPersonal.setCellValueFactory(new PropertyValueFactory<>("personalRating"));

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
    public void deleteMovie() throws SQLException
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
        if(tbViewCategory.getSelectionModel().getSelectedItem() == null)
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

    @FXML
    public void addPersonalRating(ActionEvent event)
    {
        double p = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter a rating between 1 - 10", "Enter", JOptionPane.OK_CANCEL_OPTION));

        if (p >= 1 && p <= 10)
        {
            mModel.addPersonalRating(tbViewMovie.getSelectionModel().getSelectedItem(), p);
        } else
        {
            JOptionPane.showMessageDialog(null, "You have to enter a number between 1 and 10", "Incorrect number", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void playMovie(Movie movieToPlay) throws IOException
    {
        String moviePath = tbViewMovie.getSelectionModel().getSelectedItem().getFilelink();
        File movieFile = new File(moviePath);
        Desktop.getDesktop().open(movieFile);
    }

    @FXML
    private void clickPlayMovie(MouseEvent event) throws IOException
    {
        String moviePath = tbViewMovie.getSelectionModel().getSelectedItem().getFilelink();
        File movieFile = new File(moviePath);
        Movie movieToPlay = tbViewMovie.getSelectionModel().getSelectedItem();
        String lastview = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (event.getButton().equals(MouseButton.PRIMARY))
        {
            if (event.getClickCount() == 2)
            {
                Desktop.getDesktop().open(movieFile);
                movieToPlay.setLastview(lastview);
                mModel.lastview(movieToPlay);
            }
        }

    }

    @FXML
    private void openImdb(ActionEvent event)
    {
        try
        {
            String url = "https://www.imdb.com/";
            Desktop.getDesktop().browse(URI.create(url));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectCat(MouseEvent event) throws SQLException
    {
        if(!tbViewCategory.getSelectionModel().isEmpty())
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

    @FXML
    private void addToCategory(ActionEvent event)
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

    @FXML
    private void deleteFromCategory(ActionEvent event)
    {
        int p = JOptionPane.showConfirmDialog(null, "Do you want to delete this movie from this category", "Delete", JOptionPane.YES_NO_OPTION);
        
        if (p == 0)
        {
            mModel.deleteFromCategory(tbViewCatMovies.getSelectionModel().getSelectedItem());
            tbViewCatMovies.getItems().clear();
            tbViewCatMovies.setItems(mModel.getAllMoviesInCategory(currentCategory)); // gets the new list of songs minus the deleted ones
        }
    }
}
