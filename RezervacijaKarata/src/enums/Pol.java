package enums;

public enum Pol {
	MUSKI,ZENSKI;

	@Override
	public String toString() {
		switch(this) {
		case MUSKI:
			return "Muški";
		case ZENSKI:
			return "Ženski";
		default:
			return "nepoznat pol!";
		}
	}
	
	
}
