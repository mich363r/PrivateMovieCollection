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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class FXMLDocumentController implements Initializable
{

    @FXML
    private AnchorPane rootPane2;
    @FXML
    private TableView<Movie> tbViewMovie;
//    @FXML
//    private TableView<Category> tbViewCategory;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableColumn<Movie, String> colImdbRating;
    @FXML
    private TableColumn<Movie, String> colPersonalRating;
    @FXML
    private TableColumn<Movie, String> colTitle;

    MovieModel mModel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            mModel = new MovieModel();
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        colTitle.setCellFactory(new PropertyValueFactory<> ("Title"));
//        colImdbRating.setCellFactory(new PropertyValueFactory<> ("Imdb Rating"));
//        colPersonalRating.setCellFactory(new PropertyValueFactory<> ("Personal Rating"));
        
//        TableColumn<Movie, String> f = new TableColumn("Title");
//        f.setCellFactory(new PropertyValueFactory<> ("Title"));

    }    


    @FXML
    private void chooseFile(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Open Music File");
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        File mediafile = fileChooser.showOpenDialog(stage);
        String title = null;
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
           
        } catch (ID3Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event)
    {
    }
    
}
