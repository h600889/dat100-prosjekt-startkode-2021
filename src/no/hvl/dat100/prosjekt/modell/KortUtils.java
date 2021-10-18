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
		//lager en ny kortsamling som skal ha alle kortene, og legger til alle kort fra samling.
		KortSamling alleKort = new KortSamling();
		for (int i = 0; i < samling.getAllekort().length; i++) {
			alleKort.leggTil(samling.getAllekort()[i]);
		}

		int length = alleKort.getAllekort().length;
		int pos = 0;
		//fjerner alle kort fra samling, slik at vi kan legge til kortene igjen i sortert rekkefølge.
		samling.fjernAlle();

		//Nøstet løkke som gjentar seg like mange ganger som antallet kort i alleKort.
		//i løkken går man gjennom alle kortene, og lagrer kortet som er før alle andre kort ifølge compareTo.
		while (pos < length) {
			Kort m = new Kort(Kortfarge.Spar, 13);
			for (int i = 0; i < alleKort.getAllekort().length; i++) {
				if (alleKort.getAllekort()[i].compareTo(m) < 0) {
					m = alleKort.getAllekort()[i];
				}
			}
			//Legger til sortert kort til samling og fjerner den fra alleKort
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

		//Lager en samling alleKort som skal holde på kortene i samlingen slik at man kan legge inn tilfeldige kort fra alleKort inn i samling.
		KortSamling alleKort = new KortSamling();
		for (int i = 0; i < samling.getAllekort().length; i++) {
			alleKort.leggTil(samling.getAllekort()[i]);
		}

		//løkke som gjentar like mange ganger som lengden av alleKort, og legger inn et tilfeldig kort fra alleKort inn i samling.
		int length = alleKort.getAllekort().length;
		samling.fjernAlle();
		for (int k = 0; k < length; k++) {
			Kort stokkKort = alleKort.getAllekort()[random.nextInt(alleKort.getAllekort().length)];
			alleKort.fjern(stokkKort);
			samling.leggTil(stokkKort);
		}

	}
	
}
