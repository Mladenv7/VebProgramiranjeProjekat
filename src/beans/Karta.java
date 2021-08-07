package beans;

import java.time.LocalDateTime;

import enums.StatusKarte;
import enums.TipKarte;

public class Karta {
	
 	private String id;
	private String manifestacijaId;
	private LocalDateTime vremeOdrzavanja;
	private double cena;
	private String imePrezime;
	private StatusKarte status;
	private TipKarte tip;
	private boolean obrisana;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getManifestacijaId() {
		return manifestacijaId;
	}
	public void setManifestacijaId(String manifestacijaId) {
		this.manifestacijaId = manifestacijaId;
	}
	public LocalDateTime getVremeOdrzavanja() {
		return vremeOdrzavanja;
	}
	public void setVremeOdrzavanja(LocalDateTime vremeOdrzavanja) {
		this.vremeOdrzavanja = vremeOdrzavanja;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getImePrezime() {
		return imePrezime;
	}
	public void setImePrezime(String imePrezime) {
		this.imePrezime = imePrezime;
	}
	public StatusKarte getStatus() {
		return status;
	}
	public void setStatus(StatusKarte status) {
		this.status = status;
	}
	public TipKarte getTip() {
		return tip;
	}
	public void setTip(TipKarte tip) {
		this.tip = tip;
	}
	public boolean isObrisana() {
		return obrisana;
	}
	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	public Karta(String id, String manifestacijaId, LocalDateTime vremeOdrzavanja, double cena, String imePrezime,
			StatusKarte status, TipKarte tip, boolean obrisana) {
		super();
		this.id = id;
		this.manifestacijaId = manifestacijaId;
		this.vremeOdrzavanja = vremeOdrzavanja;
		this.cena = cena;
		this.imePrezime = imePrezime;
		this.status = status;
		this.tip = tip;
		this.obrisana = obrisana;
	}
	
	public Karta() {
		super();
		this.id = "";
		this.manifestacijaId = "";
		this.vremeOdrzavanja = null;
		this.cena = 0;
		this.imePrezime = "";
		this.status = null;
		this.tip = null;
		this.obrisana = false;
	}
	@Override
	public String toString() {
		return "Karta [id=" + id + ", manifestacijaId=" + manifestacijaId + ", vremeOdrzavanja=" + vremeOdrzavanja
				+ ", cena=" + cena + ", imePrezime=" + imePrezime + ", status=" + status + ", tip=" + tip
				+ ", obrisana=" + obrisana + "]";
	}
	
	
}
