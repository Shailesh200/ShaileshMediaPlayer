/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaileshmediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Shailesh Kumar
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    private String filePath;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file you want to open (*.mp4)",
                "*.mp4");
                fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        
        if(filePath!=null)
        {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
                
                slider.setValue(mediaPlayer.getVolume()*100);
                slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                mediaPlayer.setVolume((slider.getValue()/100));
                }});
                
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                seekSlider.setValue(newValue.toSeconds());
                }});
                
                seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }});
            
            
            mediaPlayer.play();
        }
    }
    
    @FXML
    private void increaseVolume(ActionEvent event){
        double volume = mediaPlayer.getVolume()*20;
        mediaPlayer.setVolume(volume);
    }
    
    @FXML
    private void pauseVedio(ActionEvent event){
        mediaPlayer.pause();
    }
    
    @FXML
    private void playVedio(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    @FXML
    private void stopVedio(ActionEvent event){
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastVedio(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void fasterVedio(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    
    @FXML
    private void slowVedio(ActionEvent event){
        mediaPlayer.setRate(.75);
    }
    
    @FXML
    private void slowerVedio(ActionEvent event){
        mediaPlayer.setRate(.5);
    }
    
    @FXML
    private void exitVedio(ActionEvent event){
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
