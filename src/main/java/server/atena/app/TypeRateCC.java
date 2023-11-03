package server.atena.app;

public enum TypeRateCC {
	RATTING("Karta Oceny"), CURRENT("Bieżący Odsłuch"), MYSTERY("Tajemniczy Klient");

	private final String label;

	TypeRateCC(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
