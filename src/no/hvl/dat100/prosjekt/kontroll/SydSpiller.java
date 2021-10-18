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
	 */
	@Override
	public Handling nesteHandling(Kort topp, Spill spill) {
		KortSamling gyldigeKort = new KortSamling();
		boolean gyldigeKortFinnes = false;

		for (Kort k : getHand().getAllekort()) {
			if (k.sammeFarge(topp) || k.sammeVerdi(topp) || k.getVerdi()==8) {
				gyldigeKort.leggTil(k);
				gyldigeKortFinnes = true;
			}
		}

		if (gyldigeKortFinnes) {
			final int POENG_LIK_VERDI = 4;
			final int VERDI_I_FRABUNKE = 2;

			//juks👿👿
			removePoeng(spill.getNord().getHand(), VERDI_I_FRABUNKE, gyldigeKort);

			//fjerner poeng fra kort som er i tilbunken, slik at de blir spilt mindre
			removePoeng(spill.getBord().getBunkeTil(), VERDI_I_FRABUNKE, gyldigeKort);

			//Sjekker hvilke verdier som det er mest av, og legger til poeng.
			leggTilPoeng(gyldigeKort, POENG_LIK_VERDI, gyldigeKort);

			//legger til poeng til kort som har en verdi som andre kort i hånden også har.
			for (Kort k : getHand().getAllekort()) {
				for (int i = 1; i <= 13; i++) {
					if (k.getVerdi() == i) {
						gyldigeKort.addPoengVerdi(i,POENG_LIK_VERDI);
					}
				}
			}
			return new Handling(HandlingsType.LEGGNED, gyldigeKort.finnBesteKort());
		}

		if (getAntallTrekk() >= Regler.maksTrekk()) {
			return new Handling(HandlingsType.FORBI, null);
		}
		return new Handling(HandlingsType.TREKK,null);
	}
}
