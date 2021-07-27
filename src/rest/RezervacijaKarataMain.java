package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.gson.Gson;

import beans.Korisnik;
import beans.dto.KupacRegistracijaDTO;
import enums.Pol;
import enums.Uloga;
import servisi.KorisnikServis;
import servisi.ManifestacijaServis;

public class RezervacijaKarataMain {

	private static ManifestacijaServis manifestacije = new ManifestacijaServis();
	private static KorisnikServis korisnikServis = new KorisnikServis();
	private static Gson g = new Gson();


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		get("/rest/manifestacije/sveManifestacije", (req, res) -> {
			res.type("application/json");
			return g.toJson(manifestacije.getManifestacije());
		});
		
		post("/rest/korisnici/registracijaKorisnika", (req, res) -> {
			
			KupacRegistracijaDTO dto = g.fromJson(req.body(), KupacRegistracijaDTO.class);
			
			Korisnik noviKupac = new Korisnik();
			noviKupac.setKorisnickoIme(dto.getKorisnickoIme());
			noviKupac.setIme(dto.getIme());
			noviKupac.setPrezime(dto.getPrezime());
			noviKupac.setLozinka(dto.getLozinka());
			noviKupac.setUloga(Uloga.KUPAC);
			
			if(dto.getPol().equals("m")) noviKupac.setPol(Pol.MUSKI);
			else if(dto.getPol().equals("ž")) noviKupac.setPol(Pol.ZENSKI);
			
			return korisnikServis.dodajKorisnika(noviKupac);
		});
		
	}

}
