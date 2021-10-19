package no.hvl.dat100.prosjekt.kontroll;

import no.hvl.dat100.prosjekt.modell.KortSamling;
import no.hvl.dat100.prosjekt.kontroll.spill.Spillere;
import no.hvl.dat100.prosjekt.modell.Kort;
import no.hvl.dat100.prosjekt.modell.Kortfarge;

//Oppgave 2

/**
 * Abstrakt klasse som implementerer alle metodene i kontrakten (interfacet) ISpiller,
 * bortsett fra nesteHandling(). Dette er grunnen til at klassen er abstrakt.
 * For å lage "virkelige" spillere, må vi arve fra denne klassen og implementere
 * nesteHandling (fra ISpiller).
 * 
 * Klassen har objektvariablene hand (Hand), antalltrekk (heltall) og spiller
 * (Spillere). Den har to konstruktører. Se beskrivelse av disse.
 * 
 */
public abstract class Spiller implements ISpiller {

	// hand for spilleren (samling)
	private KortSamling hand; 
	
	// antall trekk spilleren har gjort fra fra-bunken
	private int antalltrekk; 
	
	// hvem spilleren er (Nord,Syd,Ingen) - se oppramsklassen Spillere
	private Spillere spiller;

	/**
	 * Standard konstruktør som oppretter en Spiller med en hånd uten kort,
	 * antalltrekk som 0 og setter spiller til Spillere.INGEN.
	 */
	public Spiller() {
		
		hand = new KortSamling();
		antalltrekk = 0;
		spiller = Spillere.INGEN;
	}

	/**
	 * Konstruktør der vi kan sette hvilken spiller det er (NORD, SYD eller
	 * INGEN).
	 * 
	 * @param spiller
	 *            hvilken spiller det er.
	 */
	public Spiller(Spillere spiller) {
		hand = new KortSamling();
		antalltrekk = 0;
		this.spiller = spiller;
	}

	public int getAntallKort() {
		
		return hand.getAntalKort();
	}

	public KortSamling getHand() {
		
		return hand;
	}

	public int getAntallTrekk() {
		
		return antalltrekk;
	}

	public Spillere hvem() {
		
		return spiller;
		
	}

	public void setAntallTrekk(int t) {
		
		antalltrekk = t;
	}

	public boolean erFerdig() {
		return hand.erTom();
		
	}

	public void leggTilKort(Kort kort) {
		hand.leggTil(kort);
		
	}

	public void fjernKort(Kort kort) {
		hand.fjern(kort);
		
	}

	public void fjernAlleKort() {
		hand.fjernAlle();
	}

	public Kort trekker(Kort kort) {

		hand.leggTil(kort);
		setAntallTrekk(antalltrekk+1);

		return kort;
	}


	//Oppgave 4

	/**
	 * Legger til poeng til alle kort i en samling gyldigeKort som har samme verdi eller farge som kort i en samling
	 *
	 * @param samling samlingen man søker i
	 * @param poengSum poengsummen man legger til
	 * @param gyldigeKort samlingen man legger til
	 */
	public void leggTilPoeng(KortSamling samling, int poengSum, KortSamling gyldigeKort) {
		for (Kort k : samling.getAllekort()) {
			for (int i = 1; i <= 13; i++) {
				if (k.getVerdi() == i) {
					gyldigeKort.addPoeng(i,poengSum);
				}
			}
			for (Kortfarge f : Kortfarge.values()) {
				if (k.getFarge().equals(f)) {
					gyldigeKort.addPoeng(f,poengSum);
				}
			}
		}
	}

	/**
	 * Trekker fra poeng fra alle kort i en samling gyldigeKort som har samme verdi eller farge som kort i en samling
	 *
	 * @param samling samlingen man søker i
	 * @param poengSum poengsummen man trekker fra
	 * @param gyldigeKort samlingen man trekker fra
	 */
	public void removePoeng(KortSamling samling, int poengSum, KortSamling gyldigeKort) {
		for (Kort k : samling.getAllekort()) {
			for (int i = 1; i <= 13; i++) {
				if (k.getVerdi() == i) {
					gyldigeKort.removePoeng(i,poengSum);
				}
			}
			for (Kortfarge f : Kortfarge.values()) {
				if (k.getFarge().equals(f)) {
					gyldigeKort.removePoeng(f,poengSum);
				}
			}
		}
	}
}
