package enums;

public enum TipKarte {
	REGULAR,FAN_PIT,VIP;

	@Override
	public String toString() {
		switch(this) {
		case VIP:
			return "VIP";
		case REGULAR:
			return "Regular";
		case FAN_PIT:
			return "Fan pit";
		default:
			return "Nepoznat tip karte";
	}
	}
	
	
}
