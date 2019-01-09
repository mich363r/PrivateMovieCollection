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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;
import org.blinkenlights.jid3.v1.ID3V1_1Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;
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
    @FXML
    private AnchorPane rootPane2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }

    public AddMovieWindowController()
    {
    }
    
    
    @FXML
    private void openFile(ActionEvent event) throws SQLException
    {
           FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Open Music File");
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        File mediafile = fileChooser.showOpenDialog(stage);
        String title = null;
        String rating = null;
        String location = null;

        MP3File mp3 = new MP3File(mediafile);
        try
        {
            for (ID3Tag tag : mp3.getTags())
            {
                if (tag instanceof ID3V1_0Tag || tag instanceof ID3V1_1Tag)
                {
                    ID3V1Tag id3Tag = (ID3V1Tag) tag;
                    title = id3Tag.getTitle();
                    location = mediafile.getPath();
                } else if (tag instanceof ID3V2_3_0Tag)
                {
                    ID3V2_3_0Tag id3Tag = (ID3V2_3_0Tag) tag;
                    title = id3Tag.getTitle();
                    location = mediafile.getPath();

                }
            }
            this.txtTitle.setText(title);
            this.txtRating.setText(rating);
            this.txtFile.setText(location);
            
           
        } catch (ID3Exception e)
        {
            e.printStackTrace();
        }  
    }

    public void setModel (MovieModel mModel)
    {
        this.mModel = mModel;
    }

    @FXML
    private void saveMovie(ActionEvent event)
    {
    }

    @FXML
    private void cancelMovie(ActionEvent event)
    {
    }
    
    }
    

