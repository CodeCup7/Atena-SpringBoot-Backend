package server.atena.app;

public enum TypeRateCC {
    RATTING_("Karta Oceny"),
    CURRENT_("Bieżący Odsłuch"),
    MYSTERY_("Tajemniczy Klient");

    private String value;

    TypeRateCC(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeRateCC fromValue(String value) {
        for (TypeRateCC type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value for TypeRateCC: " + value);
    }
}
