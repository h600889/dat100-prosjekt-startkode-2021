package no.hvl.dat100.prosjekt.kontroll;

import no.hvl.dat100.prosjekt.TODO;
import no.hvl.dat100.prosjekt.kontroll.dommer.Regler;
import no.hvl.dat100.prosjekt.kontroll.spill.Handling;
import no.hvl.dat100.prosjekt.kontroll.spill.HandlingsType;
import no.hvl.dat100.prosjekt.kontroll.spill.Spillere;
import no.hvl.dat100.prosjekt.kontroll.Spill;
import no.hvl.dat100.prosjekt.modell.Kort;
import no.hvl.dat100.prosjekt.modell.KortSamling;
import no.hvl.dat100.prosjekt.modell.Kortfarge;

/**
 * Klasse som for å representere en vriåtter syd-spiller. Strategien er å lete
 * gjennom kortene man har på hand og spille det første som er lovlig.
 *
 */
public class SydSpiller extends Spiller {

	/**
	 * Konstruktør.
	 * 
	 * @param spiller
	 *            posisjon for spilleren (nord eller syd).
	 */
	public SydSpiller(Spillere spiller) {
		super(spiller);
	}

	/**
	 * Metode for å implementere strategi. Strategien er å spille det første
	 * kortet som er lovlig (også en åtter selv om man har andre kort som også
	 * kan spilles). Dersom man ikke har lovlige kort å spille, trekker man om
	 * man ikke allerede har trukket maks antall ganger. I så fall sier man
	 * forbi.
	 * 
	 * @param topp
	 *            kort som ligg øverst på til-bunken.
	 *
	 * @param spill spillet (slik at man kan bruke informasjon fra spillet til strategien sin)
	 */
	@Override
	public Handling nesteHandling(Kort topp, Spill spill) {

		//Ser etter gyldige kort og legger de alle til i en kortsamling.
		KortSamling gyldigeKort = new KortSamling();
		boolean gyldigeKortFinnes = false;

		for (Kort k : getHand().getAllekort()) {
			if (k.sammeFarge(topp) || k.sammeVerdi(topp) || k.getVerdi()==8) {
				gyldigeKort.leggTil(k);
				gyldigeKortFinnes = true;
			}
		}

		//Hvis gyldige kort finnes, endrer man poengverdiene på de gyldige kortene for å bestemme hvilket kort som er best å legge ned.
		if (gyldigeKortFinnes) {

			//Hvis man bare har et kort, og det er gyldig, legger man det ned uansett hva.
			if (getHand().getAntalKort() == 1) {
				return new Handling(HandlingsType.LEGGNED, gyldigeKort.getAllekort()[0]);
			}



			//fjerner poeng fra kort som er i tilbunken, slik at de blir spilt mindre
			removePoeng(spill.getBord().getBunkeTil(), 2, gyldigeKort);

			//Sjekker hvilke verdier som det er mest av, og legger til poeng.
			leggTilPoeng(gyldigeKort, 4, gyldigeKort);

			//legger til poeng til kort som har en verdi som andre kort i hånden også har.
			leggTilPoeng(getHand(), 2, gyldigeKort);

			//Fjerner poeng fra åttere, slik at de bare spilles hvis ingen bedre kort finnes.
			for (Kort k : gyldigeKort.getAllekort()) {
				if (k.getVerdi() == 8) {
					k.setPoeng(-6);
					for (Kort ko : getHand().getAllekort()) {
						if (ko.sammeFarge(k)) {
							k.setPoeng(5);
						}
					}
				}
			}


			//juks👿👿
			removePoeng(spill.getNord().getHand(), 5, gyldigeKort);

			//Ikke legg ned kort med veldig lav poengverdi
			if (gyldigeKort.finnBesteKort().getPoeng() >= -5 && getHand().getAntalKort() >= spill.getNord().getHand().getAntalKort()) {
				return new Handling(HandlingsType.LEGGNED, gyldigeKort.finnBesteKort());
			} else if (getHand().getAntalKort() < spill.getNord().getHand().getAntalKort()) {
				return new Handling(HandlingsType.LEGGNED, gyldigeKort.finnBesteKort());
			}
		}

		//Hvis ingen kort er gyldige, og man har brukt opp alle trekkene sine, melder man forbi.
		if (getAntallTrekk() >= Regler.maksTrekk()) {
			return new Handling(HandlingsType.FORBI, null);
		}
		//Hvis man fortsatt har trekk, trekker man et kort.
		return new Handling(HandlingsType.TREKK,null);
	}
}
