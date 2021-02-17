package treenilaskuri;

import java.util.*;

/**
 * Treenilaskurin treenit, jolla voi lisätä mm. uuden treenilajin
 *
 * @author Milla Tukiainen
 * @version 1.0, 20.3.2019
 */
public class Treenit implements Iterable<Treeni> {
	
	private String tiedostonPerusNimi = "";
	
	/** 
     * Treenien taulukko
     */
    private final ArrayList<Treeni> alkiot = new ArrayList<Treeni>();

    /**
     * Treenien alustaminen
     */
    public Treenit() {
        // 
    }

    /**
     * Alustetaan treenit
     */
    public void alusta() {
        Treeni lenkki = new Treeni();
        lenkki.rekisteroi();
        lenkki.setNimi("Lenkki");
        alkiot.add(lenkki);
        
        Treeni sali = new Treeni();
        sali.rekisteroi();
        sali.setNimi("Sali");
        alkiot.add(sali);
 
        Treeni palloilu = new Treeni();
        palloilu.rekisteroi();
        palloilu.setNimi("Palloilu");
        alkiot.add(palloilu);
        
        Treeni hiihto = new Treeni();
        hiihto.rekisteroi();
        hiihto.setNimi("Hiihto");
        alkiot.add(hiihto);
        
        Treeni tanssi = new Treeni();
        tanssi.rekisteroi();
        tanssi.setNimi("Tanssi");
        alkiot.add(tanssi);
    }
    
    /**
     * Lisää uuden treenin tietorakenteeseen ja ottaa treenin omistukseensa.
     * @param treeni lisättävä treeni.
     */
    public void lisaa(Treeni treeni) {
        alkiot.add(treeni);
    }

    /**
    * Palauttaa viitteen i:teen treeniin.
    * @param i monennenko treenin viite halutaan
    * @return viite treeniin, jonka indeksi on i
    * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella 
    */
    public Treeni anna(int i) throws IndexOutOfBoundsException {           
        
        for (int k = 0; k < alkiot.size(); k++) {   
            Treeni alkio = alkiot.get(k);
            if (i == alkio.getTunnusNro()) return alkio;
        }   
        return null;
    }

    /**
     * Palauttaa treenilaskurin treenien lukumäärän
     * @return treenien lukumäärä
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
     * Luokka treenien iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     * Treenit treenit = new Treenit();
     * Treeni sali = new Treeni(), lenkki = new Treeni();
     * sali.rekisteroi(); lenkki.rekisteroi();
     *
     * treenit.lisaa(sali);
     * treenit.lisaa(lenkki);
     * treenit.lisaa(sali);
     *
     * StringBuffer ids = new StringBuffer(30);
     * for (Treeni treeni:treenit)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+treeni.getTunnusNro());           
     *
     *
     *
     * ids = new StringBuffer(30);
     * for (Iterator<Treeni>  i=treenit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Treeni treeni = i.next();
     *   ids.append(" "+treeni.getTunnusNro());           
     * } 
     * </pre>
     */
    public class TreenitIterator implements Iterator<Treeni> {
        private int kohdalla = 0;
        /**
         * Onko olemassa vielä seuraavaa treeniä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä treenejä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        /**
         * Annetaan seuraava treeni
         * @return seuraava treeni
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Treeni next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Ei poisteta");
        }
    }

    /**
     * Palautetaan iteraattori lajeistaan.
     * @return laji iteraattori
     */
    @Override
    public Iterator<Treeni> iterator() {
        return new TreenitIterator();       
    }
    
    /**
     * Haetaan kaikki treenilajit
     * @return tietorakenne, jossa viiteet löydetteyihin treenilajeihin
     */ 
    public List<Treeni> annaTreenit(int TreeniNro) {
        List<Treeni> loydetyt = new ArrayList<Treeni>();
        for (Treeni sali : alkiot)
        	if (sali.getTunnusNro() == TreeniNro) return loydetyt;
        return null;
    }

    /**
     * Testiohjelma treenikerroille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Treenit treenit = new Treenit();
        Treeni sali1 = new Treeni();
        sali1.taytaTreeni(1);
        Treeni sali2 = new Treeni();
        sali2.taytaTreeni(2);
        Treeni sali3 = new Treeni();
        sali3.taytaTreeni(3);
        Treeni sali4 = new Treeni();
        sali4.taytaTreeni(4);

        treenit.lisaa(sali1);
        treenit.lisaa(sali2);
        treenit.lisaa(sali3);
        treenit.lisaa(sali2);
        treenit.lisaa(sali4);

        System.out.println("============= Harrastukset testi =================");

        List<Treeni> treenit2 = treenit.annaTreenit(1);

        for (Treeni tre : treenit2) {
            System.out.print(tre.getTunnusNro() + " ");
            tre.tulosta(System.out);
        }

    }


}
