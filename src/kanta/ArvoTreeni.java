package kanta;

import static kanta.PuhelinTarkistus.rand;

public class ArvoTreeni {
	
	public static String ArvoLaji() {
		String[] lajit = {"salitreeni", "lenkki", "palloilu"};
		int a = rand(0,2);
		return lajit[a];
	}
}
