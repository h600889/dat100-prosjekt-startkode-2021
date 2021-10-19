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
 * Klasse som for å representere en vriåtter syd-spiller. Strategien er å gi alle kort en poengverdi,
 * basert på hvor mange andre kort man har av samme typen, og om motstanderen også har kort av den typen i sin hånd.
 * Når man har flere kort enn Nord, så unngår man å spille kort som Nord har, og trekker i stedet for å legge ned hvis man må det for å tvinge Nord til å trekke.
 * Når man har færre kort enn Nord, så prioriterer man fortsatt kort som Nord har, men man spiller et gyldig kort hvis man har ett uansett hva.
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
	 * Metode for å implementere strategi. Strategien er å gi alle kort en poengverdi,
	 * basert på hvor mange andre kort man har av samme typen, og om motstanderen også har kort av den typen i sin hånd.
	 * Når man har flere kort enn Nord, så unngår man å spille kort som Nord har, og trekker i stedet for å legge ned hvis man må det for å tvinge Nord til å trekke.
	 * Når man har færre kort enn Nord, så prioriterer man fortsatt kort som Nord har, men man spiller et gyldig kort hvis man har ett uansett hva.
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
			leggTilPoeng(gyldigeKort, 5, gyldigeKort);

			//legger til poeng til kort som har en verdi som andre kort i hånden også har.
			leggTilPoeng(getHand(), 3, gyldigeKort);

			//Fjerner poeng fra åttere, slik at de bare spilles hvis ingen bedre kort finnes.
			for (Kort k : gyldigeKort.getAllekort()) {
				if (k.getVerdi() == 8) {
					k.setPoeng(-10);
					for (Kort ko : getHand().getAllekort()) {
						if (ko.sammeFarge(k)) {
							k.setPoeng(0);
						}
					}
				}
			}

			//juks (ser på motstanderen sin hånd + toppkortet i frabunken.
			removePoeng(spill.getNord().getHand(), 5, gyldigeKort);
			if (!spill.getBord().bunkefraTom()) {
				if (spill.getBord().getBunkeFra().seSiste().sammeFarge(topp) || spill.getBord().getBunkeFra().seSiste().sammeVerdi(topp)) {
					for (Kort k : gyldigeKort.getAllekort()) {
						if (k.sammeVerdi(spill.getBord().getBunkeFra().seSiste()) || k.sammeFarge(spill.getBord().getBunkeFra().seSiste())) {
							k.removePoeng(5);
						}
					}
				}
			}

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
