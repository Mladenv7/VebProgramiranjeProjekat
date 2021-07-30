package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import beans.Korisnik;
import beans.Manifestacija;
import beans.dto.KupacRegistracijaDTO;
import beans.dto.ManifestUpitDTO;
import beans.dto.ManifestacijaKomentariDTO;
import beans.dto.PrijavaDTO;
import enums.Pol;
import enums.Uloga;
import servisi.KomentarServis;
import servisi.KorisnikServis;
import servisi.ManifestacijaServis;

public class RezervacijaKarataMain {

	private static ManifestacijaServis manifestacije = new ManifestacijaServis();
	private static KorisnikServis korisnikServis = new KorisnikServis();
	private static KomentarServis komentarServis = new KomentarServis();
	private static Gson g = new GsonBuilder()
							.registerTypeAdapter(LocalDateTime.class,  new JsonDeserializer<LocalDateTime>() { 
								@Override 
								public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException { 

								return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); } 

							})
							.create();
	
	


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		get("/rest/manifestacije/sveManifestacije", (req, res) -> {
			res.type("application/json");
			return g.toJson(manifestacije.getManifestacije());
		});
		
		post("/rest/manifestacije/pretraga", (req, res) -> {
			ManifestUpitDTO upit = g.fromJson(req.body(), ManifestUpitDTO.class);
			System.out.println(upit.getDatumOd());
			ArrayList<Manifestacija> rezultatPretrage = new ArrayList<>(manifestacije.pretragaManifestacija(new ArrayList<Manifestacija>(manifestacije.getManifestacije().values()), upit));
			System.out.println(rezultatPretrage.size());
			res.type("application/json");
			return g.toJson(rezultatPretrage);
		});
		
		//----------------------------------------------------------------------------------------
		
		
		get("/rest/manifestacije/manifestacijaKomentari/:id", (req, res) -> {
			String id = req.params("id");
			
			ManifestacijaKomentariDTO dto = new ManifestacijaKomentariDTO();
			dto.setManifestacija(manifestacije.getManifestacije().get(id));
			dto.setKomentari(komentarServis.komentariNaManifestaciju(id));
			
			res.type("application/json");
			return g.toJson(dto);
		});
		
		
		//----------------------------------------------------------------------------------------
		
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
		
		post("/rest/korisnici/prijava", (req, res) -> {
			PrijavaDTO dto = g.fromJson(req.body(), PrijavaDTO.class);
			Korisnik prijavljeni = korisnikServis.dobaviKorisnika(dto.getKorisnickoIme());
			if(prijavljeni == null) return "Ovaj korisnik ne postoji";
			if(!prijavljeni.getLozinka().equals(dto.getLozinka())) return "Pogrešna lozinka";
			return g.toJson(prijavljeni);
		});
	}

}
