/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author michaellemmiche
 */
public class PrivateMovieCollection extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/View/MainMovieView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
//        wait(10);
        warningMessage();
        
        
    }
    public void warningMessage() 
    {
        JOptionPane.showMessageDialog(null, "Remember to delete movies you have not watched in 2 years with a personal rating under 6");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        launch(args);
        
        
    }
    
}
