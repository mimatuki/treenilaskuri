package treenilaskuri;
/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Milla Tukiainen
 * @version 1.0, 7.3.2019
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
