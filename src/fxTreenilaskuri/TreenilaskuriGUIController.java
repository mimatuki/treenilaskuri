package fxTreenilaskuri;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import treenilaskuri.Jasen;
import treenilaskuri.Treenilaskuri;
import treenilaskuri.Treeni;
import treenilaskuri.SailoException;
import treenilaskuri.TreeniKerta;

/*
 * @author Milla Tukiainen
 * @version 15.2.2019
 * @version 22.3.2019 lis‰tty j‰senten ja treenien k‰sittely
 * @version 5.4.2019 
 * Luokka treenilaskurin k‰yttˆliittym‰n tapahtumien tekemiseksi
 */
public class TreenilaskuriGUIController implements Initializable{
	
	@FXML    private TextField Kayttajatunnus;
    @FXML    private Label labelVirhe;
    @FXML    private BorderPane Jasenentiedot;
    @FXML    private ScrollPane panelJasen;
    @FXML    private ListChooser<Jasen> chooserJasenet;
    @FXML    private TextField hakuehto;
    @FXML    private StringGrid<TreeniKerta> tableTreeniKerrat;
    @FXML    private TextField maara;
    @FXML    private TextField panelNimi;
    @FXML    private TextField panelIka;
    @FXML    private TextField panelPaino;
    @FXML    private TextField panelPuhelin;
   
    private Treenilaskuri treeniryhma;
    private Jasen jasenKohdalla;
    private TreeniKerta kertaKohdalla;
	
	private String treeniryhmannimi = "FC PaPa";
	  
    
    @FXML private void handleAvaa() {
    	avaa();
    }
    
    @FXML private void handleLopeta() {
    	tallenna();
    	Platform.exit();
    }
    
    @FXML private void handleUusiJasen(ActionEvent event) {
        uusiJasen();
        tallenna();
    }
   
    @FXML private void handlePoistaJasen() {
        poistaJasen();
        tallenna();
    }
    
    @FXML private void handlePoistaTreeni() {
    	poistaTreeniKerta();
    	tallenna();
    }
    
    @FXML private void handleUusiTreeni(ActionEvent event) {
        uusiTreeniKerta();
        tallenna();
    }
    
    @FXML
    void handleMuokkaaTreenia(ActionEvent event) {
        kertaKohdalla = tableTreeniKerrat.getObject();
        
        ModalController.showModal(
                TreenilaskuriGUIController.class.getResource("TreeniKerratView.fxml"),
                "Muokkaa treenikertaa", null, kertaKohdalla);
        
        hae(jasenKohdalla.getTunnusNro());
        tallenna();
    }
    
    @FXML private void handleApua() {
        avustus();
    }
    
    @FXML private void handleTietoja(ActionEvent event) {
    	ModalController.showModal(TreenilaskuriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }   
    
    @FXML void handleMuokkaa(ActionEvent event) {
    	ModalController.showModal(TreenilaskuriGUIController.class.getResource("JasenMuokkausView.fxml"), "Muokkaa j‰senen tietoja", null, jasenKohdalla);
    	hae(jasenKohdalla.getTunnusNro());
    	tallenna();
    }
    
    @FXML private void handleHakuehto() {
        if ( jasenKohdalla != null )
            hae(jasenKohdalla.getTunnusNro()); 
    }
    
    
    /**
     * N‰ytet‰‰n ohjelman suunnistelma erillisess‰ selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/mimatuki");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    /**
     * Tallennetaan tiedot
     * @return null jos onnistuu, muuten virhe
     */
	private String tallenna() {
        try {
        	treeniryhma.talleta();
        	tulostaJasenet();
        	return null;
        } catch (SailoException ex) {
        	Dialogs.showMessageDialog("Tallennuksessa ongelmia" + ex.getMessage());
        	return ex.getMessage();
        }
    }
	
    /**
     * Tarkistetaan, onko tallennus tehty
     * @return true, jos voi sulkea ja false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * Kysyt‰‰n tiedoston nimi ja luetaan se
     * @return true, jos onnistui ja false jos ei
     */
    public boolean avaa() {
        String uusinimi = TreeniryhmanNimiController.kysyNimi(null, treeniryhmannimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    /**
     * Alustaa treeniryhm‰n lukemalla sen valitusta tiedostosta 
     * @param nimi tiedosto, josta treeniryhm‰n tiedot luetaan 
     */
    protected String lueTiedosto(String nimi) {
        treeniryhmannimi = nimi;
        setTitle("Treeniryhm‰ - " + treeniryhmannimi);
        try {
        	treeniryhma.lueTiedostosta(nimi);
        	hae(0);
        	return null;
        }
        catch (SailoException e) {
        	hae(0);
        	String virhe = e.getMessage();
        	if(virhe != null) Dialogs.showMessageDialog(virhe);
        	return virhe;
        }
    }

    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
  
    /**
     * Tulostaa valitun k‰ytt‰j‰n tiedot j‰rjestelm‰‰n
     * Alustetaan myˆs j‰senlistan kuuntelija 
     */
    private void alusta() {
        chooserJasenet.clear();
        chooserJasenet.addSelectionListener(e -> naytaJasen());
        tulostaJasenet();
    }
  
    /**
     * N‰ytt‰‰ listasta valitun j‰senen tiedot
     */
    private void naytaJasen() {
        jasenKohdalla = chooserJasenet.getSelectedObject();
        if (jasenKohdalla == null) {           
            return;
        }        
        panelNimi.setText(jasenKohdalla.getNimi());
        panelIka.setText(jasenKohdalla.getIka());
        panelPaino.setText(jasenKohdalla.getPaino());
        panelPuhelin.setText(jasenKohdalla.getPuhelin());
        
        naytaTreeniKerrat(jasenKohdalla);
    }

	/**
     * N‰ytt‰‰ valitun j‰senen treenikerrat
     */
    private void naytaTreeniKerrat(Jasen jasen) {
        tableTreeniKerrat.clear();
        if (jasen == null)
            return;

        try {
            List<TreeniKerta> kerrat = treeniryhma
                    .annaTreeniKerrat(jasen);
            if (kerrat.size() == 0) 
                return;
            for (TreeniKerta kr : kerrat)
                naytaTreeniKerta(kr);
        } catch (SailoException e) {
            naytaVirhe(e.getMessage());
        }
    }

    /**
     * N‰ytt‰‰ treenikerran tiedot
     */
    private void naytaTreeniKerta(TreeniKerta kr) {
        int kenttia = kr.getKenttia();
        String[] rivi = new String[kenttia];
        for (int i = 0, k = kr.ekaKentta(); k <= kenttia+1; i++, k++)
            rivi[i] = kr.anna(k);
        
        int lajiId = Integer.parseInt(rivi[0]);
        rivi[0] = treeniryhma.annaTreeni(lajiId);
       
        tableTreeniKerrat.add(kr, rivi);
    }
    
    /**
     * Poistetaan treenikertataulukosta valitulla kohdalla oleva treenikerta. 
     */
    private void poistaTreeniKerta() {
        int rivi = tableTreeniKerrat.getRowNr();
        if (rivi < 0)
            return;
        TreeniKerta kerta = tableTreeniKerrat.getObject();
        if (kerta == null)
            return;
        treeniryhma.poistaTreeniKerta(kerta);
        naytaTreeniKerrat(jasenKohdalla);
        int kertoja = tableTreeniKerrat.getItems().size();
        if (rivi >= kertoja)
            rivi = kertoja - 1;
        tableTreeniKerrat.getFocusModel().focus(rivi);
        tableTreeniKerrat.getSelectionModel().select(rivi);
    }
    /**
     * Hakee j‰senten tiedot listaan
     * @param jnro j‰senen numero, joka aktivoidaan haun j‰lkeen
     */
    private void hae(int jnro) {
    	String ehto = hakuehto.getText();
    	
    	chooserJasenet.clear();

        int index = 0;
        Collection<Jasen> jasenet;
        try {
        	jasenet = treeniryhma.etsi(ehto, 0);
        	int i = 0;
        	for (Jasen jasen:jasenet) {
        		if (jasen.getTunnusNro() == jnro) index = i;
        		chooserJasenet.add(jasen.getNimi(), jasen);
        		i++;
        	}
        	} catch(SailoException ex) {
        		Dialogs.showMessageDialog("J‰senen hakemisessa ongelmia" + ex.getMessage());
        	}
    	chooserJasenet.setSelectedIndex(index);
    }

    /**
     * Luo uuden j‰senen jota aletaan editoimaan 
     */
    private void uusiJasen() {
        Jasen uusi = new Jasen();
        uusi.rekisteroi();
    	//tallenna();
        uusi.taytaPekkaAllo();
        try {
            treeniryhma.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    } 
    
    /**
     * Poistetaan listalta valittu j‰sen
     */
    private void poistaJasen() {
        Jasen jasen = jasenKohdalla;
        if (jasen == null)
            return;
        if (!Dialogs.showQuestionDialog("Poisto",
                "Poistetaanko j‰sen: " + jasen.getNimi(), "Kyll‰", "Ei"))
            return;
        treeniryhma.poista(jasen);
        int index = chooserJasenet.getSelectedIndex();
        hae(0);
        chooserJasenet.setSelectedIndex(index);
    }
    /** 
     * Tekee uuden tyhj‰n treenilajin editointia varten 
     */ 
    public void uusiTreenilaji() { 
        if ( jasenKohdalla == null ) return;  
        Treeni tre = new Treeni();  
        tre.rekisteroi();  
        tre.taytaTreeni(jasenKohdalla.getTunnusNro());  
        try {
			treeniryhma.lisaa(tre);
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! " + e.getMessage());
		}  
        hae(jasenKohdalla.getTunnusNro());          
    } 
    
    /**
     * Tekee uuden treenikerran
     */
    public void uusiTreeniKerta() {
    	if ( jasenKohdalla == null ) return;
        TreeniKerta kerta = new TreeniKerta();  
        kerta.rekisteroi();  
        kerta.taytaTreeniKerta(jasenKohdalla.getTunnusNro(), 2);  
        try {
            treeniryhma.lisaa(kerta);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! " + e.getMessage());
        }   
        hae(jasenKohdalla.getTunnusNro());
        }

	/**
     * @param treeniryhma Treeniryhm‰, jota k‰ytet‰‰n k‰yttˆliittym‰ss‰
     */
    public void setTreeniryhma(Treenilaskuri treeniryhma) {
        this.treeniryhma = treeniryhma;
        try {
        	treeniryhma.lueTiedostosta("");
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lukemisessa! ");
        }
        hae(0);
    }
    
    /**
     * Tulostaa j‰senen tiedot
     * @param os tietovirta johon tulostetaan
     * @param jasen tulostettava j‰sen
     */
    public void tulosta(PrintStream os, final Jasen jasen) {
    	Treeni treeni = new Treeni();
        os.println("----------------------------------------------");
        jasen.tulosta(os);
        os.println("----------------------------------------------");  
    	try {
        List<TreeniKerta> treenikerrat = treeniryhma.annaTreeniKerrat(jasen);
		for (TreeniKerta kr:treenikerrat) {
			kr.tulosta(os);
			treeni.tulosta(os);		
		}
    	} catch (SailoException ex) {
    		Dialogs.showMessageDialog("Harrastusten hakemisessa ongelmia! " + ex.getMessage());
    	}
    }
    
    
    /**
     * Tulostaa listassa olevat j‰senet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
    	try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki j‰senet");
            Collection<Jasen> jasenet = treeniryhma.etsi("", -1); 
            for (Jasen jasen:jasenet) { 
                tulosta(os, jasen);
                os.println("\n\n");
            }
        } catch (SailoException ex) { 
            Dialogs.showMessageDialog("J‰senen hakemisessa ongelmia! " + ex.getMessage()); 
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alusta();
		
	}
   
    /**
     * Laskee nimet.dat tiedostosta rivien m‰‰r‰n, eli j‰senten m‰‰r‰n j‰rjestelm‰ss‰
     * @param tiedostonimi nimet.dat
     * @return j‰senm‰‰r‰, kun nimet.dat tiedostoon on lis‰tty j‰seni‰.
     */
    public static int laskeJasenet(String tiedostonimi) {
        int jasenmaara = 0;

        FileInputStream syotevirta;
        InputStreamReader virranlukija;
        BufferedReader puskuroituLukija = null;

        try {
            syotevirta = new FileInputStream(tiedostonimi);
            virranlukija = new InputStreamReader(syotevirta);
            puskuroituLukija = new BufferedReader(virranlukija);
            while (puskuroituLukija.readLine() != null) {
                jasenmaara++;
            }
        } catch (IOException ioe) {
            return -1;
        } finally {
            try {
                if (puskuroituLukija != null) {
                    puskuroituLukija.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(System.err);
                return -2;
            }
        }
        return jasenmaara;
    }

    /**
     * Tulostaa lasketut rivit(j‰senet) j‰rjestelm‰‰n
     */
    public void tulostaJasenet() {
        int apu;
        apu = laskeJasenet("nimet.dat");
        String s = Integer.toString(apu);       
        maara.setText(s);
    }
}

