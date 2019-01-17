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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.Exception.DALException;
import privatemoviecollection.GUI.Model.MovieModel;

/**
 * FXML Controller class
 *
 * @author Asvør
 */
public class MainMovieViewController implements Initializable
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
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Movie> tbViewCatMovies;
    @FXML
    private TableColumn<Movie, String> colCatTitle;
    @FXML
    private TableColumn<Movie, String> colCatImdb;
    @FXML
    private TableColumn<Movie, String> colCatPersonal;
    @FXML
    private TableColumn<Movie, Date> colLastview;
    @FXML
    private TextField txtSearchRatings;

    private MovieModel mModel;
    private Category currentCategory;
    private Boolean searchDone;

    public MainMovieViewController()
    {
        searchDone = false;
    }

    /*
    Creates a new object of the MovieModel class and populates the tableviews
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        alertMessage();

        try
        {
            mModel = new MovieModel();
            tbViewMovie.setItems(mModel.getAllMovies());
            tbViewCategory.setItems(mModel.getAllCategories());
        } 
        catch (IOException | SQLException | DALException ex)
        {
            Alert alert = new Alert(AlertType.ERROR, "Something went wrong either inside the database or with your connection to it");
            alert.showAndWait();
        }

        //populates category table
        colCat.setCellValueFactory(new PropertyValueFactory<>("name"));

        //populates movie table
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        colLastview.setCellValueFactory(new PropertyValueFactory<>("lastview"));

        //populates catmovie table
        colCatTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCatImdb.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        colCatPersonal.setCellValueFactory(new PropertyValueFactory<>("personalRating"));

    }

    public void alertMessage()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("Remember to delete movies you have not watched in 2 years with a personal rating under 6");

        alert.showAndWait();
    }

    /*
    sends a search input to the model and inserts the responce into the tableview
    the second click will clear the input field.
     */
    @FXML
    public void handleSearch(ActionEvent event) throws SQLException, DALException
    {

        if (searchDone == false)
        {
            if (txtSearch.getText().length() == 0 && txtSearchRatings.getText().length() != 0)
            {
                double highImdb = 10;
                double lowImdb = Double.parseDouble(txtSearchRatings.getText());

                if (lowImdb >= 0 && lowImdb <= highImdb && lowImdb <= highImdb)
                {
                    mModel.searchImdbRating(lowImdb, highImdb);
                }
            }
            
            if (txtSearchRatings.getText().length() == 0)
            {
                String input = txtSearch.getText();
                tbViewMovie.setItems(mModel.searchMovies(input));

                if (!tbViewCategory.getSelectionModel().isEmpty())
                {
                    tbViewCatMovies.setItems(mModel.searchMoviesInCat(input, currentCategory));
                }
            }

            searchDone = true;
            btnSearch.setText("Clear");

        } 
        else if (searchDone == true)
        {
            if (!tbViewCategory.getSelectionModel().isEmpty())
            {
                tbViewCatMovies.setItems(mModel.getAllMoviesInCategory(currentCategory));
            }
            searchDone = false;
            btnSearch.setText("Search");
            tbViewMovie.setItems(mModel.getAllMovies());
            txtSearch.clear();
            txtSearchRatings.clear();
        }
    }

    /*
    creates a new category if the input is valid
     */
    @FXML
    public void addCategory(ActionEvent event) throws DALException
    {
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add new category");
        dialog.setContentText("Enter category name");

        Optional<String> result = dialog.showAndWait();

        String catName = "";

        if (result.isPresent())
        {
            List<Category> nameList = mModel.getAllCategories();
            catName = result.get();
            
            if (catName.equals("") || catName.equals(" "))
            {
                return;
            }
            for (Category DuplicateName : nameList)
            {
                if (DuplicateName.getName().toLowerCase().equals(catName.toLowerCase()))
                {
                    Alert alert = new Alert(AlertType.ERROR, "Duplicate found, aborting");                  
                    alert.showAndWait();
                    return;
                }
            }
            
            Category newCat = new Category(0, catName);
            mModel.addCategory(newCat);
            tbViewCategory.setItems(mModel.getAllCategories());
        }
         

    }

    /*
    opens a new window which is then used to add a new movie
    @throws IOException
     */
    @FXML
    public void addMovie(ActionEvent event) throws IOException
    {
        Stage primeStage = (Stage) btnAddMovie.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/GUI/View/AddMovieWindow.fxml"));
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
    public void deleteMovie() throws SQLException, DALException
    {
        if (tbViewMovie.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "You have to select a movie to delete", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete selected movie?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.YES)
        {
            mModel.deleteMovie(tbViewMovie.getSelectionModel().getSelectedItem());
        }
    }

    /*
    Deletes the selected category
     */
    @FXML
    public void deleteCategory(ActionEvent event) throws DALException
    {
        if (tbViewCategory.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "You have to select a category to delete", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete selected category?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES)
        { 
            mModel.deleteCategory(tbViewCategory.getSelectionModel().getSelectedItem());
        }
    }

    /*
    adds a personal rating to the selected movie between 1 and 10
     */
    @FXML
    public void addPersonalRating(ActionEvent event) throws SQLException, DALException
    {
        if (tbViewMovie.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "You have to select a movie to rate", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (!tbViewMovie.getSelectionModel().isEmpty())
        {
            textInputRating();
        }
    }
    
    public void textInputRating () throws SQLException, DALException
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Personal rating");
        dialog.setContentText("Enter a rating between 1 - 10");
        
        Optional <String> s = dialog.showAndWait();
        
        
        if (s.isPresent())
        {
            double p = Double.parseDouble(s.get());
            
            if (p >= 1 && p <= 10)
            {
                mModel.addPersonalRating(tbViewMovie.getSelectionModel().getSelectedItem(), p);
            }
            
            if (p > 10)
            {
                Alert alert = new Alert(AlertType.ERROR, "You have to enter a number between 1 and 10", ButtonType.OK);
                alert.showAndWait();
        
            }
        } 
    }

    /*
    plays the tableview movie when you click it twice
     */
    @FXML
    public void clickPlayMovie(MouseEvent event) throws IOException, DALException, SQLException
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
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    selects which category's items are shown
     */
    @FXML
    public void selectCat(MouseEvent event) throws SQLException, DALException
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
    public void addToCategory(ActionEvent event) throws DALException
    {
        if (tbViewMovie.getSelectionModel().getSelectedItem() == null || tbViewCategory.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.ERROR, "You have to select both a movie to add, and a category to add it to.", ButtonType.OK);
            alert.showAndWait();
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
    public void deleteFromCategory(ActionEvent event) throws DALException
    {
        if (tbViewCatMovies.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "You have to select a movie from the list of category movies to delete", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete this movie from this category", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
            
        if (result.get() == ButtonType.YES)
        {
            mModel.deleteFromCategory(tbViewCatMovies.getSelectionModel().getSelectedItem(), tbViewCategory.getSelectionModel().getSelectedItem());
            tbViewCatMovies.getItems().clear();
            tbViewCatMovies.setItems(mModel.getAllMoviesInCategory(currentCategory)); // gets the new list of songs minus the deleted ones.
        }
    }

    public void searchRatings(ActionEvent event) throws DALException
    {
        double highImdb = 10;
        double lowImdb = Double.parseDouble(txtSearch.getText());

        if (lowImdb >= 0 && lowImdb <= highImdb && lowImdb <= highImdb)
        {
            mModel.searchImdbRating(lowImdb, highImdb);
        }
    }

    @FXML
    private void playMovieInCategory(MouseEvent event) throws IOException, DALException, SQLException
    {
        if (!tbViewCatMovies.getSelectionModel().isEmpty())
        {
            String moviePath = tbViewCatMovies.getSelectionModel().getSelectedItem().getFilelink();
            File movieFile = new File(moviePath);
            Movie movieToPlay = tbViewCatMovies.getSelectionModel().getSelectedItem();

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
}