package enums;

public enum Pol {
	MUSKI,ZENSKI;

	@Override
	public String toString() {
		switch(this) {
		case MUSKI:
			return "Mu�ki";
		case ZENSKI:
			return "�enski";
		default:
			return "nepoznat pol!";
		}
	}
	
	
}
