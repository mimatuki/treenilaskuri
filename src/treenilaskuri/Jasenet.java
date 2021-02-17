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
import java.util.NoSuchElementException;

/**
 * Treenilaskurin jäsenistö joka osaa mm. lisätä uuden jäsenen
 *
 * @author Milla Tukiainen
 * @version 1.0, 20.3.2019
 */
public class Jasenet implements Iterable<Jasen> {
    private static final int MAX_JASENIA   = 5;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
    private boolean muutettu = false;
    private Jasen alkiot[] = new Jasen[MAX_JASENIA];  


    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        // 
    }


    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param jasen lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Jasenet jasenet = new Jasenet();
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(pekka1); jasenet.getLkm() === 1;
     * jasenet.lisaa(pekka2); jasenet.getLkm() === 2;
     * jasenet.lisaa(pekka1); jasenet.getLkm() === 3;
     * jasenet.anna(0) === pekka1;
     * jasenet.anna(1) === pekka2;
     * jasenet.anna(2) === pekka1;
     * jasenet.anna(1) == pekka1 === false;
     * jasenet.anna(1) == pekka2 === true;
     * jasenet.anna(3) === pekka1; #THROWS IndexOutOfBoundsException 
     * jasenet.lisaa(pekka1); jasenet.getLkm() === 4;
     * jasenet.lisaa(pekka1); jasenet.getLkm() === 5;
     * jasenet.lisaa(pekka1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if ( lkm >= alkiot.length ) {
            Jasen alkiot_uusi[] = new Jasen[alkiot.length+lkm];
            for(int i = 0; i < alkiot.length; i++) {
                alkiot_uusi[i] = alkiot[i];
            }
            alkiot = alkiot_uusi;
        }
        alkiot[lkm] = jasen;
        lkm++;
        muutettu = true;
    }


    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko jäsenen viite halutaan
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee jäsenistön tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Jasenet jasenet = new Jasenet();
     *  Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
     *  pekka1.taytaPekkaAllo();
     *  pekka2.taytaPekkaAllo();
     *  String hakemisto = "testiryhma";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  jasenet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  jasenet.lisaa(pekka1);
     *  jasenet.lisaa(pekka2);
     *  jasenet.talleta();
     *  jasenet = new Jasenet();            // Poistetaan vanhat luomalla uusi
     *  jasenet.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Jasen> i = jasenet.iterator();
     *  i.next() === pekka1;
     *  i.next() === pekka2;
     *  i.hasNext() === false;
     *  jasenet.lisaa(pekka2);
     *  jasenet.talleta();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {  
            String rivi;
            while ( (rivi = fi.readLine()) != null ) { 
                rivi = rivi.trim(); 
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue; 
                Jasen jasen = new Jasen(); 
                jasen.parse(rivi); 
                lisaa(jasen); 
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
     * Tallentaa jäsenistön tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        File fbak = new File(getBakNimi()); 
        File ftied = new File(getTiedostonNimi()); 
        fbak.delete(); 
        ftied.renameTo(fbak); 

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            System.out.println("Saved: "+ ftied.getCanonicalPath()); 
            for (Jasen jasen : this) { 
                fo.println(jasen.toString()); 
            }  
        } catch ( FileNotFoundException ex ) { 
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea"); 
        } catch ( IOException ex ) { 
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia"); 
        } 
        muutettu = false; 
    }

    /**
     * Palauttaa treeniryhmän koko nimen
     * @return treeniryhmän koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    /**
     * Palauttaa treeniryhmän jäsenten lukumäärän
     * @return jäsenten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
     * pekka1.rekisteroi(); pekka2.rekisteroi();
     *
     * jasenet.lisaa(pekka1); 
     * jasenet.lisaa(pekka2); 
     * jasenet.lisaa(pekka1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Jasen jasen:jasenet)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+jasen.getTunnusNro());           
     * 
     * String tulos = " " + pekka1.getTunnusNro() + " " + pekka2.getTunnusNro() + " " + pekka1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Jasen jasen = i.next();
     *   ids.append(" "+jasen.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Jasen>  i=jasenet.iterator();
     * i.next() == pekka1  === true;
     * i.next() == pekka2  === true;
     * i.next() == pekka1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class JasenetIterator implements Iterator<Jasen> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Jasen next() throws NoSuchElementException {
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
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator() {
        return new JasenetIterator();
    }
    
    /** 
     * Palauttaa taulukossa hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     *
     */ 
    @SuppressWarnings("unused")
    public Collection<Jasen> etsi(String hakuehto, int k) { 
        Collection<Jasen> loytyneet = new ArrayList<Jasen>(); 
        for (Jasen jasen : this) { 
        	if(jasen != null && jasen.toString().toLowerCase().contains(hakuehto.toLowerCase()))
            loytyneet.add(jasen);  
        } 
        return loytyneet; 
    }

    /** 
     * Poistaa jäsenen jolla on valittu tunnusnumero  
     * @param id poistettavan jäsenen tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen(), pekka3 = new Jasen(); 
     * pekka1.rekisteroi(); pekka2.rekisteroi(); pekka3.rekisteroi(); 
     * int id1 = pekka1.getTunnusNro(); 
     * jasenet.lisaa(pekka1); jasenet.lisaa(pekka2); jasenet.lisaa(pekka3); 
     * jasenet.poista(id1+1) === 1; 
     * jasenet.annaId(id1+1) === null; jasenet.getLkm() === 2; 
     * jasenet.poista(id1) === 1; jasenet.getLkm() === 1; 
     * jasenet.poista(id1+3) === 0; jasenet.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    
    /** 
     * Etsii jäsenen id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen jäsenen indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen(), pekka3 = new Jasen(); 
     * pekka1.rekisteroi(); pekka2.rekisteroi(); pekka3.rekisteroi(); 
     * int id1 = pekka1.getTunnusNro(); 
     * jasenet.lisaa(pekka1); jasenet.lisaa(pekka2); jasenet.lisaa(pekka3); 
     * jasenet.etsiId(id1+1) === 1; 
     * jasenet.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
    
    /** 
     * Etsii jäsenen id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return jäsen jolla etsittävä id tai null 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen pekka1 = new Jasen(), pekka2 = new Jasen(), pekka3 = new Jasen(); 
     * pekka1.rekisteroi(); pekka2.rekisteroi(); pekka3.rekisteroi(); 
     * int id1 = pekka1.getTunnusNro(); 
     * jasenet.lisaa(pekka1); jasenet.lisaa(pekka2); jasenet.lisaa(pekka3); 
     * jasenet.annaId(id1  ) == pekka1 === true; 
     * jasenet.annaId(id1+1) == pekka2 === true; 
     * jasenet.annaId(id1+2) == pekka3 === true; 
     * </pre> 
     */ 
    public Jasen annaId(int id) { 
        for (Jasen jasen : this) { 
            if (id == jasen.getTunnusNro()) return jasen; 
        } 
        return null; 
    }

    /**
     * Testiohjelma jäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Jasenet jasenet = new Jasenet();

        Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
        pekka1.rekisteroi();
        pekka1.taytaPekkaAllo();
        pekka2.rekisteroi();
        pekka2.taytaPekkaAllo();

        try {
            jasenet.lisaa(pekka1);
            jasenet.lisaa(pekka2);

            System.out.println("============= Jäsenet testi =================");

            for (int i = 0; i < jasenet.getLkm(); i++) {
                Jasen jasen = jasenet.anna(i);
                System.out.println("Jäsen nro: " + i);
                jasen.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}