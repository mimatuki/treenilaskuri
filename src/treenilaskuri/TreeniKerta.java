package treenilaskuri;

import java.io.OutputStream;
import java.util.*;


import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat; 

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Treenikerta, joka huolehtii mm. tunnusNro:staan
 * @author Milla Tukiainen
 * @version 1.0 22.3.2019
 *
 */
public class TreeniKerta {
    private int tunnusNro;
    private int jasenNro;
    private int treeniNro;
    private String pvm;

    private static int seuraavaNro = 1;


    /**
    * Alustetaan treenikerta.
    */
    public TreeniKerta() {
        // 
    }

    /**
     * Alustetaan tietyn jäsenen ja treenin treenikerta.  
     * @param jasenNro jäsenen viitenumero 
     */
    public TreeniKerta(int jasenNro, int treeniNro) {
        this.jasenNro = jasenNro;
        this.treeniNro = treeniNro;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot TreeniKerralle.
     * Treenilaji arvotaan.
     * @param nro viite henkilöön, jonka treenikerrasta on kyse
     */
    public void taytaTreeniKerta(int jnro, int tnro) {
    	tunnusNro = 1;
        jasenNro = jnro;
        treeniNro = tnro;
        Date today = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
        pvm = dateFormat.format(today);
    }
    
    /**
     * Tulostetaan treenikerran tiedot
    * @param out tietovirta johon tulostetaan
    */
    public void tulosta(PrintStream out) {
        out.println(tunnusNro + " " + jasenNro + " " + treeniNro + " " + pvm);
    }

    /**
     * Tulostetaan treenikerran tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * @return treenikertojen kenttien lukumäärä
     */
    public int getKenttia() {
        return 4;
    }
    
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public int ekaKentta() {
        return 2;
    }
    
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   TreeniKerta tre = new Treenikerta();
     *   tre.parse("   1   |  2  |   3  | 25.3.2019");
     *   tre.anna(0) === "1";   
     *   tre.anna(1) === "2"; 
     *   tre.anna(2) === "3";     
     *   tre.anna(3) === "22.3.2019";     
     * </pre>
     */
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + jasenNro;
            case 2:
                return "" + treeniNro;
            case 3:
                return "" + pvm;
            default:
                return "";               
        }
    }
    /**
     * Antaa treenikerralle seuraavan rekisterinumeron.
     * @return treenikerran uusi tunnus_nro
     * @example
     * <pre name="test">
     *   TreeniKerta sali = new TreeniKerta();
     *   sali.getTunnusNro() === 0;
     *   sali.rekisteroi();
     *   TreeniKerta lenkki = new TreeniKerta();
     *   lenkki.rekisteroi();
     *   int n1 = sali.getTunnusNro();
     *   int n2 = lenkki.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
   public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palautetaan treenikerran oma id
     * @return treenikerran id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
  
    /**
     * Palautetaan mille jäsenelle treenikerta kuuluu
     * @return jäsenen id
     */
    public int getJasenNro() {
        return jasenNro;
    }
   
    /**
     * Palautetaan treenikerran treenin id
     * @return treenin id
     */
    public int getTreeniNro() {
    	return treeniNro;
    }
    
    /**
     * Palautetaan treenikerran päivämäärä
     * @return päivämäärä
     */
    public String getPvm() {
    	return pvm;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nro asetettava tunnusnumero
     */
    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Asettaa treenikerran päivämäärän
     * @param s päivämäärä
     */
    public void setPvm(String s) {
        pvm = s;
    }
    
    /**
     * Asettaa treenikerran treenin
     * @param i treeni
     */
    public void setLajiNro(int i) {
        treeniNro = i;
    }

    /**
     * Palauttaa treenikerran tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return treenikerta tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   TreeniKerta kerta = new TreeniKerta();
     *   kerta.parse("   2   |  3  |   2  | 11.12.2018 ");
     *   kerta.toString()    === "2|3|2|11.12.2018";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + jasenNro + "|" + treeniNro + "|" + pvm;
    }
    
    /**
     * Selvitää treenikerran tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta treenikerran tiedot otetaan
     * @example
     * <pre name="test">
     *   TreeniKerta kerta = new TreeniKerta();
     *   kerta.parse("   2   |  3  |   2  | 11.12.1998 ");
     *   kerta.getJasenNro() === 3;
     *   kerta.toString()    === "2|3|2|11.12.1998";
     *   
     *   kerta.rekisteroi();
     *   int n = kerta.getTunnusNro();
     *   kerta.parse(""+(n+20));
     *   kerta.rekisteroi();
     *   kerta.getTunnusNro() === n+20+1;
     *   kerta.toString()     === "" + (n+20+1) + "|3|2|11.12.1998";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        jasenNro = Mjonot.erota(sb, '|', jasenNro);
        treeniNro = Mjonot.erota(sb, '|', treeniNro);
        pvm = Mjonot.erota(sb, '|', pvm);
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    /**
     * Testiohjelma TreeniKerralle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        TreeniKerta laji = new TreeniKerta();
        laji.taytaTreeniKerta(2, 3);
        laji.tulosta(System.out);
        TreeniKerta laji1 = new TreeniKerta();
        laji1.taytaTreeniKerta(3, 4);
        laji1.tulosta(System.out);
    }
}
