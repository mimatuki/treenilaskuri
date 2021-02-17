package fxTreenilaskuri;

import treenilaskuri.TreeniKerta;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class TreeniKerratGUIController implements ModalControllerInterface<TreeniKerta> {

    @FXML private TextField textPvm;
      
    @FXML private MenuButton chooserTreeniKerta;
    
    TreeniKerta muokattava;
    
    @FXML
    void handleDefaultCancel(ActionEvent event) {
        ModalController.closeStage(textPvm);
    }

    @Override
    public TreeniKerta getResult() {
        return muokattava;
     }

    @Override
    public void handleShown() {
        //        
    }

    @Override
    public void setDefault(TreeniKerta kr) {
        textPvm.setText(kr.getPvm());
        
        switch(kr.getTreeniNro())
        {
            case 1:
            	chooserTreeniKerta.setText("Lenkki");
                break;
            case 2:
            	chooserTreeniKerta.setText("Sali");
                break;
            case 3:
            	chooserTreeniKerta.setText("Palloilu");
                break;
            case 4:
            	chooserTreeniKerta.setText("Hiihto");
                break;
            case 5:
            	chooserTreeniKerta.setText("Tanssi");
                break;
           default:
        	   chooserTreeniKerta.setText("VIRHE");
               break;
        }        
       muokattava = kr;
    }
    @FXML
    void handleOK() {
        
       muokattava.setPvm(textPvm.getText());
       
       System.out.println("Tallennetaan treenikerta: ");
       muokattava.tulosta(System.out);
       
       ModalController.closeStage(textPvm);
    }
   
     @FXML
     void chooseLenkki() {
        muokattava.setLajiNro(1);
        chooserTreeniKerta.setText("Lenkki");
    }
   
   @FXML
    void chooseSali() {
        muokattava.setLajiNro(2);
        chooserTreeniKerta.setText("Sali");        
    }
   
    @FXML
    void choosePalloilu() {
        muokattava.setLajiNro(3);
        chooserTreeniKerta.setText("Palloilu");
    }
    
    @FXML
    void chooseHiihto() {
        muokattava.setLajiNro(4);
        chooserTreeniKerta.setText("Hiihto");
    }
    
    @FXML
    void chooseTanssi() {
        muokattava.setLajiNro(5);
        chooserTreeniKerta.setText("Tanssi");
    }
    
    
}
