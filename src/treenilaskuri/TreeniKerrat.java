package treenilaskuri;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Treenilaskurin treenikerrat, voi mm. lisätä uuden treenikerran
 * @author Milla Tukiainen
 * @version 1.0 22.3.2019
 *
 */
public class TreeniKerrat implements Iterable<TreeniKerta> {
	
	private boolean muutettu = false;
	private String tiedostonPerusNimi = "treenikerrat";
	
	/** 
     * Treenienkertojen taulukko
     */
    private final Collection<TreeniKerta> alkiot = new ArrayList<TreeniKerta>();

    /**
     * Treenienkertojen alustaminen
     */
    public TreeniKerrat() {
        // 
    }

    /**
     * Lisää uuden treenikerran tietorakenteeseen ja ottaa treenikerran omistukseensa.
     * @param laji lisättävä treenikerta.
     */
    public void lisaa(TreeniKerta kerta) {
        alkiot.add(kerta);
        muutettu = true;
    }

    /**
     * Iteraattori treenikertojen läpikäymiseen
     */
	@Override
	public Iterator<TreeniKerta> iterator() {
		return alkiot.iterator();
	}

    /**
     * Lukee treenikerran tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  TreeniKerrat kerrat = new TreeniKerrat();
     *  TreeniKerta sali1 = new TreeniKerta(); sali1.taytaTreeniKerta(2, 1);
     *  TreeniKerta sali2 = new TreeniKerta(); sali2.taytaTreeniKerta(1, 2);
     *  TreeniKerta sali3 = new TreeniKerta(); sali3.taytaTreeniKerta(2, 1); 
     *  TreeniKerta sali4 = new TreeniKerta(); sali4.taytaTreeniKerta(1, 2); 
     *  TreeniKerta sali5 = new TreeniKerta(); sali5.taytaTreeniKerta(2, 1); 
     *  String tiedNimi = "testiryhma";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kerrat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kerrat.lisaa(sali1);
     *  kerrat.lisaa(sali2);
     *  kerrat.lisaa(sali3);
     *  kerrat.lisaa(sali4);
     *  kerrat.lisaa(sali5);
     *  kerrat.tallenna();
     *  kerrat = new TreeniKerrat();
     *  kerrat.lueTiedostosta(tiedNimi);
     *  Iterator<TreeniKerta> i = kerrat.iterator();
     *  i.next().toString() === sali1.toString();
     *  i.next().toString() === sali2.toString();
     *  i.next().toString() === sali3.toString();
     *  i.next().toString() === sali4.toString();
     *  i.next().toString() === sali5.toString();
     *  i.hasNext() === false;
     *  kerrat.lisaa(sali5);
     *  kerrat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                TreeniKerta kr = new TreeniKerta();
                kr.parse(rivi);
                lisaa(kr);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Tallentaa treenikerran tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak); 

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (TreeniKerta kr : this) {
                fo.println(kr.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Palauttaa treenilaskurin treenikertojen lukumäärän
     * @return treenikertojen lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Haetaan kaikki jäsenen treenikerrat
     * @param tunnusnro jäsenen tunnusnumero jolle treenikertoja haetaan
     * @return tietorakenne, jossa viiteet löydetteyihin treenikertoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  TreeniKerrat kerrat = new TreeniKerrat();
     *  TreeniKerta sali1 = new TreeniKerta(2); kerrat.lisaa(sali1);
     *  TreeniKerta sali2 = new TreeniKerta(1); kerrat.lisaa(sali2);
     *  TreeniKerta sali3 = new TreeniKerta(2); kerrat.lisaa(sali3);
     *  TreeniKerta sali4 = new TreeniKerta(1); kerrat.lisaa(sali4);
     *  TreeniKerta sali5 = new TreeniKerta(2); kerrat.lisaa(sali5);
     *  TreeniKerta sali6 = new TreeniKerta(5); kerrat.lisaa(sali6);
     *  
     *  List<Treeni> loytyneet;
     *  loytyneet = kerrat.annaTreeniKerrat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kerrat.annaTreeniKerrat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == sali2 === true;
     *  loytyneet.get(1) == sali4 === true;
     *  loytyneet = kerrat.annaTreeniKerrat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == sali6 === true;
     * </pre> 
     */
    public List<TreeniKerta> annaTreeniKerrat(int tunnusnro) {
        List<TreeniKerta> loydetyt = new ArrayList<TreeniKerta>();
        for (TreeniKerta sali : alkiot)
            if (sali.getJasenNro() == tunnusnro) loydetyt.add(sali);
        return loydetyt;
    }
    
    /**
     * Poistaa kaikki tietyn tietyn jäsenen treenikerrat
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
     * @example
     * <pre name="test">
     *  TreeniKerrat kerrat = new TreeniKerrat();
     *  TreeniKerta juoksu21 = new TreeniKerta(); juoksu21.taytaTreeniKerta(2,2);
     *  TreeniKerta juoksu11 = new TreeniKerta(); juoksu11.taytaTreeniKerta(1,2);
     *  TreeniKerta juoksu22 = new TreeniKerta(); juoksu22.taytaTreeniKerta(2,2); 
     *  TreeniKerta juoksu12 = new TreeniKerta(); juoksu12.taytaTreeniKerta(1,2); 
     *  TreeniKerta juoksu23 = new TreeniKerta(); juoksu23.taytaTreeniKerta(2,2); 
     *  kerrat.lisaa(juoksu21);
     *  kerrat.lisaa(juoksu11);
     *  kerrat.lisaa(juoksu22);
     *  kerrat.lisaa(juoksu12);
     *  kerrat.lisaa(juoksu23);
     *  kerrat.poistaTreeniKerrat(2) === 3;  kerrat.getLkm() === 2;
     *  kerrat.poistaTreeniKerrat(3) === 0;  kerrat.getLkm() === 2;
     *  List<TreeniKerta> t = kerrat.annaTreeniKerrat(2);
     *  t.size() === 0; 
     *  t = kerrat.AnnaTreeniKerrat(1);
     *  t.get(0) === juoksu11;
     *  t.get(1) === juoksu12;
     * </pre>
     */
    public int poistaTreeniKerrat(int tunnusNro) {
        int n = 0;
        for (Iterator<TreeniKerta> it = alkiot.iterator(); it.hasNext();) {
            TreeniKerta tre = it.next();
            if ( tre.getJasenNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * Poistaa valitun treenikerran
     * @param kerta poistettava treenikerta
     * @return true jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  TreeniKerrat kerrat = new TreeniKerrat();
     *  TreeniKerta juoksu21 = new TreeniKerta(); juoksu21.taytaTreeniKerta(2,1);
     *  TreeniKerta juoksu11 = new TreeniKerta(); juoksu11.taytaTreeniKerta(1,1);
     *  TreeniKerta juoksu22 = new TreeniKerta(); juoksu22.taytaTreeniKerta(2,1); 
     *  TreeniKerta juoksu12 = new TreeniKerta(); juoksu12.taytaTreeniKerta(1,1); 
     *  TreeniKerta juoksu23 = new TreeniKerta(); juoksu23.taytaTreeniKerta(2,1); 
     *  kerrat.lisaa(juoksu21);
     *  kerrat.lisaa(juoksu11);
     *  kerrat.lisaa(juoksu22);
     *  kerrat.lisaa(juoksu12);
     *  kerrat.poista(juoksu11) === true;   kerrat.getLkm() === 3;
     *  List<TreeniKerta> h = kerrat.annaHarjoitukset(1);
     *  h.size() === 1; 
     *  h.get(0) === juoksu12;
     * </pre>
     */
    public boolean poista(TreeniKerta kerta) {
        boolean ret = alkiot.remove(kerta);
        if (ret) muutettu = true;
        return ret;
    }

    /**
     * Testiohjelma treenikerroille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        TreeniKerrat kerrat = new TreeniKerrat();
        TreeniKerta sali1 = new TreeniKerta();
        sali1.taytaTreeniKerta(2, 1);
        TreeniKerta sali2 = new TreeniKerta();
        sali2.taytaTreeniKerta(1, 2);
        TreeniKerta sali3 = new TreeniKerta();
        sali3.taytaTreeniKerta(2, 1);
        TreeniKerta sali4 = new TreeniKerta();
        sali4.taytaTreeniKerta(2, 1);

        kerrat.lisaa(sali1);
        kerrat.lisaa(sali2);
        kerrat.lisaa(sali3);
        kerrat.lisaa(sali2);
        kerrat.lisaa(sali4);

        System.out.println("============= Harrastukset testi =================");

        List<TreeniKerta> kerrat2 = kerrat.annaTreeniKerrat(2);

        for (TreeniKerta kr : kerrat2) {
            System.out.print(kr.getJasenNro() + " ");
            kr.tulosta(System.out);
        }

    }


}
