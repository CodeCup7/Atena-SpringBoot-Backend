package server.atena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RatePart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String key;
	private long ocena;
	private long waga;
	private String nieprawidlowosci;
	private String opis;
	private String uwagi;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getOcena() {
		return ocena;
	}

	public void setOcena(long ocena) {
		this.ocena = ocena;
	}

	public long getWaga() {
		return waga;
	}

	public void setWaga(long waga) {
		this.waga = waga;
	}

	public String getNieprawidlowosci() {
		return nieprawidlowosci;
	}

	public void setNieprawidlowosci(String nieprawidlowosci) {
		this.nieprawidlowosci = nieprawidlowosci;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}


}
