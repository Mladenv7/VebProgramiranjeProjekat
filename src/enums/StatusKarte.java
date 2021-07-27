package enums;

public enum StatusKarte {
	REZERVISANA,OTKAZANA;

	@Override
	public String toString() {
		switch(this) {
			case REZERVISANA:
				return "Rezervisana";
			case OTKAZANA:
				return "Otkazana";
			default:
				return "Nepoznat Status karte";
		}
	}
	
	
}
