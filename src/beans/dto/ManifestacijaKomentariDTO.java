package beans.dto;

import java.util.ArrayList;

import beans.Komentar;
import beans.Manifestacija;

public class ManifestacijaKomentariDTO {
	private Manifestacija manifestacija;
	private ArrayList<Komentar> komentari;
	private int prodatoKarata;
	
	public Manifestacija getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}
	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}
	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	public int getProdatoKarata() {
		return prodatoKarata;
	}
	public void setProdatoKarata(int prodatoKarata) {
		this.prodatoKarata = prodatoKarata;
	}
	
}
