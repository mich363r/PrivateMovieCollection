/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author michaellemmiche
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TableView<?> tbViewMovie;
    @FXML
    private TableView<?> tbViewCategory;
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    private void chooseFile(ActionEvent event) throws ID3Exception, IOException
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Open Music File");
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
                    artist = id3Tag.getArtist();
                    location = mediafile.getPath();
                } else if (tag instanceof ID3V2_3_0Tag)
                {
                    ID3V2_3_0Tag id3Tag = (ID3V2_3_0Tag) tag;
                    title = id3Tag.getTitle();
                    artist = id3Tag.getArtist();
                    location = mediafile.getPath();

                }
            }
            this.txtTitle.setText(title);
            this.txtArtist.setText(artist);
            this.txtFile.setText(location);
        } catch (ID3Exception e)
        {
            e.printStackTrace();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
