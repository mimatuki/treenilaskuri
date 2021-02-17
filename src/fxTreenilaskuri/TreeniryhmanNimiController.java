package fxTreenilaskuri;

import fi.jyu.mit.fxgui.ModalController;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * @author Milla Tukiainen
 * @version 15.2.2019
 * 
 * Kysyt‰‰n treeniryhm‰n nimi ja luodaan siihen dialogi
 */
public class TreeniryhmanNimiController implements ModalControllerInterface<String> {
	   
    @FXML private TextField textVastaus;
    private String vastaus = null;
   
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
   
    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }
    @Override
    public String getResult() {
        return vastaus;
    }
   
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }

    /*
     * Tehd‰‰n, kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
    
    /*
     * Luodaan nimenkysymisdialogi ja palautetaan kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ nime‰ k‰ytet‰‰n oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                TreeniryhmanNimiController.class.getResource("TreeniryhmanNimiView.fxml"),
                "Treeniryhma",
                modalityStage, oletus);
    }
}