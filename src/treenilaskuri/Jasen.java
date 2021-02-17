package treenilaskuri;

import java.io.OutputStream;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
/**
 * Treenilaskurin jäsen, joka huoltehtii mm. omasta tunnusNro:staan
 * @author Milla Tukiainen
 * @version 1.0  20.3.2019
 *
 */
public class Jasen {
	private int tunnusNro;
	private String nimi = "";
	private String ika;
	private String paino;
	private String puhelin = "";
	private static int seuraavaNro = 1;

    /**
     * @return jäsenen nimi
     * @example
     * <pre name="test">
     *   Jasen pekka = new Jasen();
     *   pekka.taytaPekkaAllo();
     *   pekka.getNimi() =R= "Allo Pekka .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * @return jäsenen ikä
     */
    public String getIka() {
        return ika;
    }
    
    /**
     * @return jäsenen paino
     */
    public String getPaino() {
        return paino;
    }
    
    /**
     * @return jäsenen puhelinnumero
     */
    public String getPuhelin() {
        return puhelin;
    }
    
    /**
     * Asettaa jäsenen nimen
     * @param n nimi
     */
    public void setNimi(String n) {
    	nimi = n;
    }
   
    /**
     * Asettaa jäsenen iän
     * @param i ikä
     */
    public void setIka(String i) {
    	ika = i;
    }
    
    /**
     * Asettaa jäsenen painon
     * @param p paino
     */
    public void setPaino(String p) {
    	paino = p;
    }
   
    /**
     * Asettaa jäsenen puhelinnumeron
     * @param s puhelinnumero
     */
    public void setPuhelin(String s) {
    	puhelin = s;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * @param apupuhelin numero joka annetaan henkilölle 
     */
    public void taytaPekkaAllo(String apupuhelin) {
        nimi = "Uusi jäsen " + kanta.PuhelinTarkistus.rand(1000, 9999);
        ika = "";
        paino = "";
        puhelin = apupuhelin;
    }
    

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * Puhelinnumero arvotaan, jotta kahdella jäsenellä ei olisi
     * samoja tietoja.
     */
    public void taytaPekkaAllo() {
        String apupuhelin = kanta.PuhelinTarkistus.arvoPuhelin();
        taytaPekkaAllo(apupuhelin);
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + "  " + nimi);
        out.println("  Ikä " + ika);
        out.println("  Paino " + paino);
        out.println("  Puh " + puhelin);
    }


    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return jäsenen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Jasen pekka1 = new Jasen();
     *   pekka1.getTunnusNro() === 0;
     *   pekka1.rekisteroi();
     *   Jasen pekka2 = new Jasen();
     *   pekka2.rekisteroi();
     *   int n1 = pekka1.getTunnusNro();
     *   int n2 = pekka2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }


    /**
     * Palauttaa jäsenen tunnusnumeron.
     * @return jäsenen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   1  |  Allo Pekka   | 15");
     *   jasen.toString().startsWith("1|Allo Pekka|15|") === true; 
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                ika + "|" +
                paino + "|" +
                puhelin;
    }
    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   1  |  Allo Pekka   | 15");
     *   jasen.getTunnusNro() === 1;
     *   jasen.toString().startsWith("1|Allo Pekka|15|") === true; 
     *   
     *   jasen.rekisteroi();
     *   int n = jasen.getTunnusNro();
     *   jasen.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   jasen.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   jasen.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        ika = Mjonot.erota(sb, '|', ika);
        paino = Mjonot.erota(sb, '|', paino);
        puhelin = Mjonot.erota(sb, '|', puhelin);
    }
    
    @Override
    public boolean equals(Object jasen) {
        if ( jasen == null ) return false;
        return this.toString().equals(jasen.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Jasen pekka1 = new Jasen(), pekka2 = new Jasen();
        pekka1.rekisteroi();
        pekka2.rekisteroi();
        pekka1.tulosta(System.out);
        pekka1.taytaPekkaAllo();
        pekka1.tulosta(System.out);

        pekka2.taytaPekkaAllo();
        pekka2.tulosta(System.out);

        pekka2.taytaPekkaAllo();
        pekka2.tulosta(System.out);
    }
}
