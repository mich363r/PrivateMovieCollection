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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.Exception.DALException;
import privatemoviecollection.GUI.Model.MovieModel;

/**
 * FXML Controller class
 *
 * @author Asvør
 */
public class AddMovieWindowController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtFile;
    @FXML
    private Button btnOpenFile;
    @FXML
    private TextField txtRating;
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Button btnCancelMovie;
    @FXML
    private Button btnSaveMovie;
    @FXML
//    private Button btnSearchImdb;
    
        MovieModel mModel;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /*
    sets the model 
    
    @param mModel
    */
    public void setModel(MovieModel mModel) {
        this.mModel = mModel;
    }
    
    /*
    takes the input from the textfields and adds a movie based on said input
    
    @param event
    @throws SQLException
    @throws DALException
    */
    @FXML
    public void saveMovie(ActionEvent event) throws SQLException, DALException {
        Stage primeStage = (Stage) btnSaveMovie.getScene().getWindow();

        if (txtTitle.getText().length() == 0 || txtRating.getText().length() == 0 || txtFile.getText().length() == 0)
        {
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION, "You have to fill out all fields!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        String title = this.txtTitle.getText();
        String rating = this.txtRating.getText();
        double doubleRating = Double.parseDouble(rating);
        String filelink = this.txtFile.getText();
        Date lastview = null;
      
        Movie newMovie = new Movie(0, title, doubleRating, 0, filelink, lastview);
        mModel.addMovie(newMovie);

        primeStage.close();
        
        
    }

    /*
      Closes the add movie window.
      *param event
     */
    @FXML
    public void cancelMovie(ActionEvent event) {
        Stage primeStage = (Stage) btnCancelMovie.getScene().getWindow();
        primeStage.close();
    }
    
    /*
    chooses which mp4 or mpeg4 file to add
    
    @param event
    */
    @FXML
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filterMp4 = new FileChooser.ExtensionFilter("Select a file (mp4)", "*.mp4");
        FileChooser.ExtensionFilter filterMpeg4 = new FileChooser.ExtensionFilter("Select a mpeg4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(filterMp4);
        fileChooser.getExtensionFilters().add(filterMpeg4);
        fileChooser.setTitle("Open movie");
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        File mediafile = fileChooser.showOpenDialog(stage);
       if(mediafile != null)
       {
        String location = mediafile.getPath();
        this.txtFile.setText(location);
       }
        

    }
    /*
    Searches imdb based on the title input field
    
    @param event
    @throws IOException
    */
    @FXML
    private void searchImdbTitle(ActionEvent event) throws IOException
    {
        if (txtTitle.getLength() == 0 || txtTitle.getText().equals(" "))
        {
            Alert alert = new Alert (AlertType.ERROR, "You have to write a title first", ButtonType.OK);
            alert.showAndWait();
            return;
        }
            String url = "https://www.imdb.com/find?ref_=nv_sr_fn&q=" + txtTitle.getText().replace(" ", "+");
            Desktop.getDesktop().browse(URI.create(url));
    
    }
    

}
