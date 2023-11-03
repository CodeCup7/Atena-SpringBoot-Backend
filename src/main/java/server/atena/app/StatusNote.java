package server.atena.app;

public enum StatusNote {
	NO_START("Nie rozpoczęty"), CLOSE("Zamknięty"), CLOSE_WITHOUT("Zamknięty BEZ");

	private final String label;

	StatusNote(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
