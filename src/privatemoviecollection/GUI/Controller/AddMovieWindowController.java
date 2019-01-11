/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import privatemoviecollection.BE.Movie;
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
    
    MovieModel mModel;
    FXMLDocumentController dController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public AddMovieWindowController() 
    {
        
    }

    public void setModel(MovieModel mModel) {
        this.mModel = mModel;
    }
    
    public void setController (FXMLDocumentController dController)
    {
        this.dController = dController;
    }

    @FXML
    private void saveMovie(ActionEvent event) throws SQLException {
        Stage primeStage = (Stage) btnSaveMovie.getScene().getWindow();

        String title = this.txtTitle.getText();
        String rating = this.txtRating.getText();
        double doubleRating = Double.parseDouble(rating);
        String filelink = this.txtFile.getText();
        String lastview = null;

        Movie newMovie = new Movie(0, title, doubleRating, 0, filelink, lastview);
        mModel.addMovie(newMovie);

//        dController.refreshTableview();
        primeStage.close();
    }

    /**
     * Cancels add movie window.
     */
    @FXML
    private void cancelMovie(ActionEvent event) {
        Stage primeStage = (Stage) btnCancelMovie.getScene().getWindow();
        primeStage.close();
    }

    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filterMp4 = new FileChooser.ExtensionFilter("Select a file (mp4)", "*.mp4");
        FileChooser.ExtensionFilter filterMpeg4 = new FileChooser.ExtensionFilter("Select a mpeg4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(filterMp4);
        fileChooser.getExtensionFilters().add(filterMpeg4);
        fileChooser.setTitle("Open movie");
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        File mediafile = fileChooser.showOpenDialog(stage);
        String location = mediafile.getPath();

        this.txtFile.setText(location);

    }

    

}
