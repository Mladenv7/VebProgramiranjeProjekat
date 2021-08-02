package enums;

public enum TipKupca {
	BRONZANI(500,2),SREBRNI(1000,3),ZLATNI(2000,7);
	
	public final int procenatPopusta;
	public final int kriterijumZaTip;
	
	private TipKupca() {
		this.procenatPopusta = 0;
		this.kriterijumZaTip = 0;
	}
	
	private TipKupca(int kzt, int pp) {
		this.procenatPopusta = pp;
		this.kriterijumZaTip = kzt;
	}

	@Override
	public String toString() {
		switch(this) {
			case BRONZANI:
				return "Bronzani";
			case SREBRNI:
				return "Srebrni";
			case ZLATNI:
				return "Zlatni";
			default:
				return "Nepoznat tip kupca";
		}
	}
	
	
}
