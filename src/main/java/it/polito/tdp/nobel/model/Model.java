package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private List<Esame> esami;
	private double bestMedia;
	private Set<Esame> bestSoluzione;

	public Model() {
		EsameDAO dao = new EsameDAO();
		esami = dao.getTuttiEsami();
		this.bestMedia = 0;
		this.bestSoluzione = null;
	}

	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale = new HashSet<>();
		cerca1(parziale, 0, numeroCrediti);
		//cerca2(parziale, 0, numeroCrediti);

		return bestSoluzione;
	}

	private void cerca1(Set<Esame> parziale, int L, int m) {
		int crediti = this.sommaCrediti(parziale);
		if (crediti > m) {
			return;
		}
		if (crediti == m) {
			double media = this.calcolaMedia(parziale);

			if (media > this.bestMedia) {
				this.bestMedia = media;
				this.bestSoluzione = new HashSet<>(parziale);
			}
		}
		if (L == esami.size()) {
			return;
		}

		// aggiungo esame
		parziale.add(esami.get(L));
		cerca1(parziale, L + 1, m);
		parziale.remove(esami.get(L));
		// non aggiungo esame
		cerca1(parziale, L + 1, m);

	}

	private void cerca2(Set<Esame> parziale, int L, int m) {
		int crediti = this.sommaCrediti(parziale);
		if (crediti > m) {
			return;
		}
		if (crediti == m) {
			double media = this.calcolaMedia(parziale);

			if (media > this.bestMedia) {
				this.bestMedia = media;
				this.bestSoluzione = new HashSet<>(parziale);
			}
		}
		if (L == esami.size()) {
			return;
		}
		
		for(Esame e: esami) {
			if(!parziale.contains(e)) {
			parziale.add(e);
			cerca2(parziale,L+1, m);
			parziale.remove(e);
			}
		}

	}

	public double calcolaMedia(Set<Esame> parziale) {
		int somma = 0;
		int crediti = 0;
		for (Esame e : parziale) {
			somma += e.getCrediti() * e.getVoto();
			crediti += e.getCrediti();
		}
		return somma / crediti;

	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma = 0;
		for (Esame e : parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}

}
