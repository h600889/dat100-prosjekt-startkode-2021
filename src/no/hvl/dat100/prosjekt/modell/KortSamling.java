package no.hvl.dat100.prosjekt.modell;

import no.hvl.dat100.prosjekt.TODO;
import no.hvl.dat100.prosjekt.kontroll.dommer.Regler;

import java.util.Arrays;

/**
 * Struktur for å lagre ei samling kort. Kan lagre hele kortstokken. Det finnes
 * en konstant i klassen Regler som angir antall kort i hver av de 4 fargene. Når
 * programmet er ferdig settes denne til 13, men under utvikling / testing kan
 * det være praktisk å ha denne mindre.
 * 
 */
public class KortSamling {

	private final int MAKS_KORT = 4 * Regler.MAKS_KORT_FARGE;

	private Kort[] samling;
	private int antall;

	/**
	 * Oppretter en tom Kortsamling med plass til MAKS_KORT (hele kortstokken).
	 */
	public KortSamling() {
		
		this.samling = new Kort[MAKS_KORT];
		this.antall = 0;
	}

	/**
	 * Returnerer en tabell med kortene i samlinga. Tabellen trenger ikke være
	 * full. Kortene ligger sammenhengende fra starten av tabellen. Kan få
	 * tilgang til antallet ved å bruke metoden getAntallKort(). Metoden er
	 * først og fremst ment å brukes i testklasser. Om man trenger
	 * kortene utenfor, anbefales metoden getAlleKort().
	 * 
	 * @return tabell av kort.
	 */
	public Kort[] getSamling() {
		
		return samling;
		
	}
	
	/**
	 * Antall kort i samlingen.
	 * 
	 * @return antall kort i samlinga.
	 */
	public int getAntalKort() {
		
		return antall;
	}
	
	/**
	 * Sjekker om samlinga er tom.
	 * 
	 * @return true om samlinga er tom, false ellers.
	 */
	public boolean erTom() {
		
		return (this.antall <= 0);
	}

	/**
	 * Legg et kort til samlinga.
	 * 
	 * @param kort
	 *            er kortet som skal leggast til.
	 */
	public void leggTil(Kort kort) {
		
		samling[antall++] = kort;
		
	}
	
	/**
	 * Legger alle korta (hele kortstokken) til samlinga. Korta vil være sortert
	 * slik at de normalt må stokkes før bruk.
	 */
	public void leggTilAlle() {

		for (Kortfarge farge : Kortfarge.values()) {
			for (int j = 1; j <= Regler.MAKS_KORT_FARGE; j++) {
				leggTil(new Kort(farge, j));
			}
		}
	}

	/**
	 * Fjerner alle korta fra samlinga slik at den blir tom.
	 */
	public void fjernAlle() {

		Arrays.fill(samling, null);
		antall = 0;
	}
	
	/**
	 * Ser på siste kortet i samlinga.
	 * 
	 * @return siste kortet i samlinga, men det blir ikke fjernet. Dersom samalinga er tom, returneres
	 *         null.
	 */
	public Kort seSiste() {

		if (antall == 0) {
			return null;
		} else {
			return samling[antall-1];
		}

	}

	/**
	 * Tek ut siste kort fra samlinga.
	 * 
	 * @return siste kortet i samlinga. Dersom samalinga er tom, returneres
	 *         null.
	 */
	public Kort taSiste() {
		//bruker seSiste for å lagre hva kortet man fjerner er
		Kort sisteKort = seSiste();
		//returner null hvis samlinga er tom
		if (antall == 0) { return null; }

		//fjerner siste kort hvis samlinga ikke er tom
		samling[antall-1] = null;
		antall--;

		return sisteKort;
	}
	
	/**
	 * Undersøker om et kort finst i samlinga.
	 * 
	 * @param kort
	 * 				kortet man ønsker å finne
	 * 
	 * @return true om kortet finst i samlinga, false ellers.
	 */
	public boolean har(Kort kort) {
		//hvis korter er null, returner false. (meningsløst å vite om det finnes et ikke-kort)
		if (kort == null) { return false; }

		//går gjennom kortstokken å leter etter kortet
		for (int k = 0; k < antall; k++) {
			if (kort.equals(samling[k])) {
				return true;
			}
		}
		//returner false hvis man ikke finner kortet
		return false;

	}

	/**
	 * Fjernar et kort frå samlinga. Dersom kortet ikke finnest i samlinga,
	 * skjer ingenting med samilingen
	 * 
	 * @param kort
	 *            kortet som skal fjernast. Dersom kortet ikke finnes, skjer
	 *            ingenting.
	 * @return true om kortet blev fjernet fra samlinga, false ellers.
	 */
			 
	public boolean fjern(Kort kort) {
		//hvis korter et null, er det ikke noe poeng i å fjerne den siden man da bare ville ha byttet ut null med null.
		if (kort == null) {
			return false;
		}
		boolean isRemoved = false;
		//går gjennom samling for å finne kortet som skal fjernes
		int k = 0;
		while (!isRemoved && k < samling.length) {
			if (kort.equals(samling[k])) {
				samling[k] = null;
				antall--;
				isRemoved = true;
			}
			k++;
		}
		//etter at et kort har blitt fjernet, omorganiserer man samling slik at det ikke er null-kort før et kort.
		if (isRemoved) {
			for (int i = 1; i < samling.length; i++) {
				if (samling[i-1] == null) {
					samling[i-1] = samling[i];
					samling[i] = null;
				}

			}
		}
		//returner om et kort ble fjernet eller ikke
		return isRemoved;
	}

	/**
	 * Gir kortene som en tabell av samme lengde som antall kort i samlingen
	 * 
	 * @return tabell av kort som er i samlingen, der kort skal ha samme rekkefølge
	 *         som i kortsamlinga.
	 */
	public Kort[] getAllekort() {
		// hvis det er ingen kort, returner en tom kortstokk
		if (antall == 0) { return new Kort[0]; }

		//lager en samling som er like stor som antallet kort, og fyller opp samlingen.
		Kort[] alleKort = new Kort[antall];
		for (int i = 0; i < antall; i++) {
			alleKort[i] = samling[i];
		}
		return alleKort;
	
	}
	
}
