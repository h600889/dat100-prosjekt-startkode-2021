package no.hvl.dat100.prosjekt.modell;

import java.util.Arrays;
import java.util.Random;

import no.hvl.dat100.prosjekt.TODO;

public class KortUtils {

	/**
	 * Sorterer en samling. Rekkefølgen er bestemt av compareTo() i Kort-klassen.
	 * 
	 * @see Kort
	 * 
	 * @param samling
	 * 			samling av kort som skal sorteres. 
	 */
	
	public static void sorter(KortSamling samling) {
		KortSamling alleKort = new KortSamling();
		for (int i = 0; i < samling.getAllekort().length; i++) {
			alleKort.leggTil(samling.getAllekort()[i]);
		}
		int length = alleKort.getAllekort().length;
		int pos = 0;
		samling.fjernAlle();
		while (pos < length) {
			Kort m = new Kort(Kortfarge.Spar, 13);
			for (int i = 0; i < alleKort.getAllekort().length; i++) {
				if (alleKort.getAllekort()[i].compareTo(m) < 0) {
					m = alleKort.getAllekort()[i];
				}
			}
			samling.leggTil(m);
			alleKort.fjern(m);
			pos++;
		}
	}
	
	/**
	 * Stokkar en kortsamling. 
	 * 
	 * @param samling
	 * 			samling av kort som skal stokkes. 
	 */
	public static void stokk(KortSamling samling) {
		Random random = new Random();

		KortSamling alleKort = new KortSamling();
		for (int i = 0; i < samling.getAllekort().length; i++) {
			alleKort.leggTil(samling.getAllekort()[i]);
		}
		int length = alleKort.getAllekort().length;
		samling.fjernAlle();
		for (int k = 0; k < length; k++) {
			Kort stokkKort = alleKort.getAllekort()[random.nextInt(alleKort.getAllekort().length)];
			alleKort.fjern(stokkKort);
			samling.leggTil(stokkKort);
		}

	}
	
}
