package fxTreenilaskuri;

import treenilaskuri.Jasen;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Muokataan järjestelmässä olevaa jäsentä
 * @author Milla Tukiainen
 * @version 3.5.2019
 */
public class JasenMuokkausGUIController implements ModalControllerInterface<Jasen>{

    @FXML private TextField textNimi;
    @FXML private TextField textIka;
    @FXML private TextField textPaino;
    @FXML private TextField textPuhelin;
    
    private Jasen muokattava;
    
    @FXML
    void handleOK(ActionEvent event) {
       System.out.println("Tallennetaan jäsen: " + muokattava.getNimi());
        
        muokattava.setNimi(textNimi.getText());
        muokattava.setIka(textIka.getText());
        muokattava.setPaino(textPaino.getText());
        muokattava.setPuhelin(textPuhelin.getText());
        
        ModalController.closeStage(textNimi);
    }
    
    @FXML
    void handleDefaultCancel(ActionEvent event) {
        ModalController.closeStage(textNimi);
    }
    
    @Override
    public void handleShown() {
        //
    }

    @Override
    public Jasen getResult() {
        return null;
    }

    @Override
    public void setDefault(Jasen j) {
           textNimi.setText(j.getNimi());
	       textIka.setText(j.getIka());
	       textPaino.setText(j.getPaino());
	       textPuhelin.setText(j.getPuhelin());
	       
	       muokattava = j;
	}
}
