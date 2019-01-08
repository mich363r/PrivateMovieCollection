/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.MovieModel;

/**
 * FXML Controller class
 *
 * @author Asvør
 */
public class AddMovieWindowController implements Initializable
{

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtFile;
    @FXML
    private Button btnOpenFile;
    @FXML
    private TextField txtRating;

    MovieModel mModel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            mModel = new MovieModel();
        } catch (IOException ex)
        {
            Logger.getLogger(AddMovieWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(AddMovieWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void openFile(ActionEvent event) throws SQLException
    {
        
        Stage primeStage = (Stage) btnOpenFile.getScene().getWindow();
        
        String title = this.txtTitle.getText();
        String location = this.txtFile.getText();
        
        Movie newMovie = new Movie(0, title, location, 0);
        mModel.addMovie(newMovie);
        
        primeStage.close();
    }
    
//    Stage primeStage = (Stage) btnSaveSong.getScene().getWindow();
//
//        String title = this.txtTitle.getText();
//        String artist = this.txtArtist.getText();
//        String location = this.txtFile.getText();
//
//        Song newSong = new Song(title, artist, location, 0);
//        tModel.addSong(newSong);
//
//        primeStage.close();
    
    
    public void setModel (MovieModel mModel)
    {
        this.mModel = mModel;
    }
    
    }
    

