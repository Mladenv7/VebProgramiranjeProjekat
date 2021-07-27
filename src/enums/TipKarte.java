package enums;

public enum TipKarte {
	VIP,REGULAR,FAN_PIT;

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
