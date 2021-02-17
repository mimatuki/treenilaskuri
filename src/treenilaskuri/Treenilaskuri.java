package treenilaskuri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Treenilaskuri-luokka, joka huolehtii j‰senistˆst‰. Suurin osa metodeista kutsuu 
 * j‰senistˆ‰.
 *
 * @author Milla Tukiainen
 * @version 1.0, 7.3.2019
 */
public class Treenilaskuri {
	private Jasenet jasenet = new Jasenet();
	private Treenit treenit = new Treenit();
	private TreeniKerrat kerrat = new TreeniKerrat();
	
    /**
     * Palautaa treeniryhm‰n j‰senm‰‰r‰n
     * @return j‰senm‰‰r‰
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    /**
     * Poistaa j‰senistˆst‰ ja treeneikerroista j‰senen tiedot
     * @param jasen j‰sen joka poistetaan
     * @return montako j‰sent‰ poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   Treenilaskuri laskuri = new Treenilaskuri(); 
     *   Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
     *   laskuri.etsi("*",0).size() === 0;
     *   laskuri.annaTreeniKerrat(pekka1).size() === 0;
     *   laskuri.poista(pekka1) === 0;
     *   laskuri.etsi("*",0).size() === 0;
     *   laskuri.annaTreeniKerrat(pekka1).size() === 0;
     *   laskuri.annaTreeniKerrat(pekka2).size() === 0;
     * </pre>
     */
    public int poista(Jasen jasen) {
        if ( jasen == null ) return 0;
        int ret = jasenet.poista(jasen.getTunnusNro()); 
        kerrat.poistaTreeniKerrat(jasen.getTunnusNro()); 
        return ret; 
    }
    
    /** 
     * Poistaa treenikerran 
     * @param kerta poistettava treenikerta 
     */ 
    public void poistaTreeniKerta(TreeniKerta kerta) { 
        kerrat.poista(kerta); 
    }
    
    /**
     * Lis‰‰ treeniryhm‰‰n uuden j‰senen
     * @param jasen lis‰tt‰v‰ j‰sen
     * @throws SailoException jos lis‰yst‰ ei voida tehd‰
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treenilaskuri treeniryhma = new Treenilaskuri();
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
     * pekka1.rekisteroi(); pekka2.rekisteroi();
     * treeniryhma.getJasenia() === 0;
     * treeniryhma.lisaa(pekka1); treeniryhma.getJasenia() === 1;
     * treeniryhma.lisaa(pekka2); treeniryhma.getJasenia() === 2;
     * treeniryhma.lisaa(pekka1); treeniryhma.getJasenia() === 3;
     * treeniryhma.getJasenia() === 3;
     * treeniryhma.annaJasen(0) === pekka1;
     * treeniryhma.annaJasen(1) === pekka2;
     * treeniryhma.annaJasen(2) === pekka1;
     * treeniryhma.annaJasen(3) === pekka1; #THROWS IndexOutOfBoundsException 
     * treeniryhma.lisaa(pekka1); treeniryhma.getJasenia() === 4;
     * treeniryhma.lisaa(pekka1); treeniryhma.getJasenia() === 5;
     * treeniryhma.lisaa(pekka1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    /**
     * Lis‰t‰‰n uusi treenilaji kerhoon
     * @param har lis‰tt‰v‰ treenilaji 
     */
    public void lisaa(Treeni tre) throws SailoException {
        treenit.lisaa(tre);
    }
    
    /**
     * Lis‰t‰‰n uusi treenikerta kerhoon
     * @param har lis‰tt‰v‰ treenilaji 
     */
    public void lisaa(TreeniKerta kerta) throws SailoException {
        kerrat.lisaa(kerta);
    }  
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien j‰senten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsitt‰v‰n kent‰n indeksi  
     * @return tietorakenteen lˆytyneist‰ j‰senist‰ 
     * @throws SailoException Jos jotakin menee v‰‰rin
     */ 
    public Collection<Jasen> etsi(String hakuehto, int k) throws SailoException { 
        return jasenet.etsi(hakuehto, k); 
    } 

    /**
     * Palauttaa i:n treenilajin
     * @param i monesko laji palautetaan
     * @return lajin nimi
     * @throws IndexOutOfBoundsException jos i v‰‰rin
     */
    public String annaTreeni(int i) throws IndexOutOfBoundsException {
        Treeni sali = new Treeni();
        sali = treenit.anna(i);
        return sali.getNimi();
    }
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        jasenet.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        treenit.setTiedostonPerusNimi(hakemistonNimi + "treenit");
        kerrat.setTiedostonPerusNimi(hakemistonNimi + "treenikerrat");
    }
    
    /**
     * Lukee treeniryhm‰n tiedot tiedostosta
     * @param nimi jota k‰yte‰‰n lukemisessa
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
    	jasenet = new Jasenet(); 
        kerrat = new TreeniKerrat();
        treenit = new Treenit();

        setTiedosto(nimi);
        jasenet.lueTiedostosta();
        kerrat.lueTiedostosta();
        treenit.alusta();
    }


    /**
     * Tallettaa treeniryhm‰n tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        String virhe = "";
        try {
            jasenet.talleta();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kerrat.talleta();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    /**
     * Palauttaa i:n j‰senen
     * @param i monesko j‰sen palautetaan
     * @return viite i:teen j‰seneen
     * @throws IndexOutOfBoundsException jos i v‰‰rin
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }
    
    
    /**
     * Haetaan kaikki j‰senen treenikerrat
     * @param jasen j‰sen jolle treenikertoja haetaan
     * @return tietorakenne jossa viiteet lˆydetteyihin treenikertoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Treenilaskuri tre = new Treenilaskuri();
     *  Jasen pekka1 = new Jasen(), pekka2 = new Jasen(), pekka3 = new Jasen();
     *  pekka1.rekisteroi(); pekka2.rekisteroi(); pekka3.rekisteroi();
     *  int id1 = pekka1.getTunnusNro();
     *  int id2 = pekka2.getTunnusNro();
     *  TreeniKerta sali1 = new TreeniKerta(id1, 2); tre.lisaa(sali1);
     *  TreeniKerta sali2 = new TreeniKerta(id1, 1); tre.lisaa(sali2);
     *  TreeniKerta sali3 = new TreeniKerta(id2, 2); tre.lisaa(sali3);
     *  TreeniKerta sali4 = new TreeniKerta(id2, 3); tre.lisaa(sali4);
     *  TreeniKerta sali5 = new TreeniKerta(id2, 3); tre.lisaa(sali5);
     *  
     *  List<TreeniKerta> loytyneet;
     *  loytyneet = tre.annaTreeniKerrat(pekka3);
     *  loytyneet.size() === 0; 
     *  loytyneet = tre.annaTreeniKerrat(pekka1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == sali1 === true;
     *  loytyneet.get(1) == sali2 === true;
     *  loytyneet = tre.annaTreeniKerrat(pekka2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == sali3 === true;
     * </pre> 
     *  @throws SailoException
     */
    public List<TreeniKerta> annaTreeniKerrat(Jasen jasen) throws SailoException {
    	List<TreeniKerta> loytyneet;
        loytyneet = kerrat.annaTreeniKerrat(jasen.getTunnusNro());
        for (TreeniKerta tre : loytyneet)
            treenit.anna(tre.getTreeniNro());
        return loytyneet;
    }  
    
    /**
     * Palauttaa treenilaskurin treenilajim‰‰r‰n
     * @return lajien m‰‰r‰
     */
    public int getTreenit() {
        return treenit.getLkm();
    }
    
    /**
     * Testiohjelma treenilaskurista
     * @param args ei k‰ytˆss‰
     */
    public static void main(String args[]) {
        Treenilaskuri treeniryhma = new Treenilaskuri();

        try {
            Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
            pekka1.rekisteroi();
            pekka1.taytaPekkaAllo();
            pekka2.rekisteroi();
            pekka2.taytaPekkaAllo();

            treeniryhma.lisaa(pekka1);
            treeniryhma.lisaa(pekka2);
            
            int id1 = pekka1.getTunnusNro();
            int id2 = pekka2.getTunnusNro();
            Treeni sali1 = new Treeni(); sali1.taytaTreeni(id1); treeniryhma.lisaa(sali1);
            Treeni sali2 = new Treeni(); sali2.taytaTreeni(id1); treeniryhma.lisaa(sali2);
            Treeni sali3 = new Treeni(); sali3.taytaTreeni(id2); treeniryhma.lisaa(sali3);
            Treeni sali4 = new Treeni(); sali4.taytaTreeni(id2); treeniryhma.lisaa(sali4);
            Treeni sali5 = new Treeni(); sali5.taytaTreeni(id2); treeniryhma.lisaa(sali5);

            System.out.println("============= Treenilaskurin testi =================");

            for (int i = 0; i < treeniryhma.getJasenia(); i++) {
                Jasen jasen = treeniryhma.annaJasen(i);
                System.out.println("J‰sen paikassa: " + i);
                jasen.tulosta(System.out);
                List<TreeniKerta> loytyneet = treeniryhma.annaTreeniKerrat(jasen);
                for (TreeniKerta kerta : loytyneet) {
                    kerta.tulosta(System.out);
                    
                }
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
