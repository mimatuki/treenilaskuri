package kanta;

/**
 * Luokka puhelinnumeron tarkistamiseksi
 * @author Milla Tukiainen
 * @version 8.3.2019
 */
public class PuhelinTarkistus {

	/** Arvotaan satunnainen kokonaisluku välillä [ala, yla]
	 * @param ala arvonnan alaraja
	 * @param yla arvonnan yläraja
	 * @return satunnainen luku väliltä [ala, yla]
	 */
	public static int rand(int ala, int yla) {
		double luku = (yla - ala) * Math.random() + ala;
		return (int)Math.round(luku);
	}
	
	public static String arvoPuhelin() {
		String apupuhelin = String.format("%02d",rand(4,5)) + "0" +
		String.format("%01d",rand(0, 9)) +	
		String.format("%01d",rand(0, 9)) +
		String.format("%01d",rand(0, 9)) +
		String.format("%01d",rand(0, 9)) +
		String.format("%01d",rand(0, 9)) +
		String.format("%01d",rand(0, 9)) +
		String.format("%01d",rand(0, 9));
		return apupuhelin;
	}
}
