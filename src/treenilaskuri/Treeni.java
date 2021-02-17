package treenilaskuri;
   
   import java.io.*;



import fi.jyu.mit.ohj2.Mjonot;
   
   /**
    * Treenilaji, joka huolehtii mm. tunnus_nro:staan.
    *
    * @author Milla Tukiainen
    * @version 1.0, 20.3.2019
    */
   public class Treeni {
      private int tunnusNro;
      private String laji = "";

      private static int seuraavaNro = 1;
 
      /**
       * Alustetaan treeni.
       */
       public Treeni() {
           // 
       }
       
       /**
        * @return treenin nimi
        * @example
        * <pre name="test">
        *   Treeni sali = new Treeni();
        *   sali.taytaTreeni(3);
        *   sali.getNimi() =R= "Sali";
        * </pre>
        */
       public String getNimi() {
           return laji;
       }
       
       /**
        * Asetetaan treenin nimi
        * @param s treenin nimi
        */
       public void setNimi(String s) {
          laji = s;
       }
       
       /**
        * Apumetodi, jolla saadaan täytettyä testiarvot Treenille.
        * Treenilaji arvotaan.
        * @param nro viite henkilöön, jonka treenikerrasta on kyse
        */
       public void taytaTreeni(int nro) {
    	   tunnusNro = nro;
           laji = "Valitse treeni";
       }
       
       /**
        * Tulostetaan treenin tiedot
       * @param out tietovirta johon tulostetaan
       */
       public void tulosta(PrintStream out) {
           out.println(String.format("%03d", tunnusNro, 3) + " " + laji);
       }

       /**
        * Tulostetaan treenin tiedot
        * @param os tietovirta johon tulostetaan
        */
       public void tulosta(OutputStream os) {
           tulosta(new PrintStream(os));
       }

       /**
        * Antaa treenille seuraavan rekisterinumeron.
        * @return treenin uusi tunnus_nro
        * @example
        * <pre name="test">
        *   Treeni sali = new Treeni();
        *   sali.getTunnusNro() === 0;
        *   sali.rekisteroi();
        *   Treeni lenkki = new Treeni();
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
        * Palautetaan treenin oma id
        * @return treenin id
        */
       public int getTunnusNro() {
           return tunnusNro;
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
        * Palauttaa treenin tiedot merkkijonona jonka voi tallentaa tiedostoon.
        * @return treeni tolppaeroteltuna merkkijonona 
        * @example
        * <pre name="test">
        *   Treeni treeni = new Treeni();
        *   treeni.parse("   2   |  sali ");
        *   treeni.toString()    === "2|sali";
        * </pre>
        */
       @Override
       public String toString() {
           return "" + getTunnusNro() + "|" + laji;
       }
       
       /**
        * Selvitää treenin tiedot | erotellusta merkkijonosta.
        * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
        * @param rivi josta treenin tiedot otetaan
        * @example
        * <pre name="test">
        *   Treeni treeni = new Treeni();
        *   treeni.parse("   2   |  sali ");
        *   treeni.getTunnusNro() === 2;
        *   treeni.toString()    === "2|sali";
        *   
        *   treeni.rekisteroi();
        *   int n = treeni.getTunnusNro();
        *   treeni.parse(""+(n+20));
        *   treeni.rekisteroi();
        *   treeni.getTunnusNro() === n+20+1;
        *   treeni.toString()     === "" + (n+20+1) + "|sali";
        * </pre>
        */
       public void parse(String rivi) {
           StringBuffer sb = new StringBuffer(rivi);
           setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
           laji = Mjonot.erota(sb, '|', laji);
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
           Treeni laji = new Treeni();
           laji.taytaTreeni(2);
           laji.tulosta(System.out);
           Treeni laji1 = new Treeni();
           laji1.taytaTreeni(3);
           laji1.tulosta(System.out);
       }
   }

 