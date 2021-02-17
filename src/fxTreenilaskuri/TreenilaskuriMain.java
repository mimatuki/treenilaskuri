package fxTreenilaskuri;

import java.io.File;


import fi.jyu.mit.fxgui.Dialogs;
import javafx.application.Application;

import javafx.stage.Stage;
import treenilaskuri.Treenilaskuri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/*
 * @author Milla Tukiainen
 * @version 15.2.2019
 * 
 * Pääohjelma Treenilaskuri-ohjelman käynnistämiseksi
 */
public class TreenilaskuriMain extends Application {
	
	String TreeniryhmanNimi = TreeniryhmanNimiController.kysyNimi(null, "FcPaPa");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TreenilaskuriGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final TreenilaskuriGUIController treenilaskuriCtrl = (TreenilaskuriGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("treenilaskuri.css").toExternalForm());
            primaryStage.setScene(scene);
                      
            Treenilaskuri laskuri = new Treenilaskuri();
            File jasentiedot = new File("nimet.dat");
            jasentiedot.createNewFile();
            File treenikertatiedot = new File("treenikerrat.dat");
            treenikertatiedot.createNewFile();

            treenilaskuriCtrl.setTreeniryhma(laskuri); 
            
            primaryStage.show(); 
        }
        catch(Exception ex) {
            Dialogs.showMessageDialog(
	                  "Tiedostoa ei vielä ole! " + ex.getMessage());     
	    }
	    } 
	
	/*
	 * Käynnistetään käyttöliittymä
	 * @param args komentorivin parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
