package enums;

public enum Uloga {
	ADMINISTRATOR,KUPAC,PRODAVAC;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch(this) {
		case ADMINISTRATOR:
			return "Administrator";
		case KUPAC:
			return "Kupac";
		case PRODAVAC:
			return "Prodavac";
		default:
			return "Nepoznat tip!";
		}
	}
	
	
}
