package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import beans.Komentar;
import beans.Korisnik;
import beans.Manifestacija;
import beans.dto.KorisnikUpitDTO;
import beans.dto.KupacRegistracijaDTO;
import beans.dto.ManifestOcenaDTO;
import beans.dto.ManifestUpitDTO;
import beans.dto.ManifestacijaDTO;
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

							}).registerTypeAdapter(LocalDate.class,  new JsonDeserializer<LocalDate>() { 
								@Override 
								public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException { 

								return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); } 

							})
							.create();
	
	


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		
		//MANIFESTACIJE
		//----------------------------------------------------------------------------------------
		
		get("/rest/manifestacije/sveManifestacije", (req, res) -> {
			res.type("application/json");
			return g.toJson(manifestacije.getManifestacije());
		});
		
		get("/rest/manifestacije/najskorije", (req, res) -> {
			res.type("application/json");
			ArrayList<Manifestacija> najskorije = new ArrayList<>(manifestacije.getManifestacije().values());
			Collections.sort(najskorije, Comparator.comparing(Manifestacija::getVremeOdrzavanja).reversed());
			return g.toJson(najskorije);
		});
		
		get("/rest/manifestacije/licne/:username", (req, res) ->{
			String id = req.params("username");
			HashMap<String,Korisnik> korisnici= korisnikServis.getKorisnici();
			res.type("application/json");
			Korisnik k=new Korisnik();
			ArrayList<Manifestacija> sve = new ArrayList<>(manifestacije.getManifestacije().values());	
			for (HashMap.Entry<String,Korisnik> entry : korisnici.entrySet())
			{
				if(entry.getKey().equals(id)) {
					k=entry.getValue();
				}
			}
			
			ArrayList<Manifestacija> licne=new ArrayList<Manifestacija>();
			
			for(String man: k.getSveManifest()) {

				for (Manifestacija m : sve)
				{

					if(man.equals(m.getId())) {
						licne.add(m);
					}
				}
			}
			return g.toJson(licne);
		});
		
		post("/rest/manifestacije/pretraga", (req, res) -> {
			ManifestUpitDTO upit = g.fromJson(req.body(), ManifestUpitDTO.class);
			System.out.println(upit);
			ArrayList<Manifestacija> rezultatPretrage = new ArrayList<>(manifestacije.pretragaManifestacija(new ArrayList<Manifestacija>(manifestacije.getManifestacije().values()), upit));
			ArrayList<ManifestOcenaDTO> saOcenom = new ArrayList<>();
			for(Manifestacija m : rezultatPretrage) {
				double prosecna = 0d;
				ArrayList<Komentar> komentari = komentarServis.komentariNaManifestaciju(m.getId());
				ManifestOcenaDTO dto = new ManifestOcenaDTO();
				dto.setManifestacija(m);
				
				if(komentari.size() == 0) {
					dto.setProsecnaOcena(0d);
					saOcenom.add(dto);
					continue;
				}
				
				for(Komentar k : komentari) {
					prosecna += k.getOcena();
				}
				prosecna /= komentari.size();
				dto.setProsecnaOcena(prosecna);
				saOcenom.add(dto);
			}
			res.type("application/json");
			return g.toJson(saOcenom);
		});
		
		get("/rest/manifestacijaAzuriraj/:id", (req, res) -> {
			String id = req.params("id");
			Manifestacija man = new Manifestacija();
			man=manifestacije.getManifestacije().get(id);
			
			ManifestacijaDTO dto= new ManifestacijaDTO();
			dto.setBrMesta(man.getBrojMesta());
			dto.setCenaRegular(man.getCenaRegular());
			dto.setNaziv(man.getNaziv());
			dto.setPoster(man.getPoster());
			dto.setVremeOdrzavanja(man.getVremeOdrzavanja());
			dto.setTip(man.getTipManifestacije());
			
			res.type("application/json");
			return g.toJson(dto);
		});
		
		post("/rest/manifestacije/azuriranjeManifestacije", (req, res) -> {
			Manifestacija zaAzurirati = g.fromJson(req.body(), Manifestacija.class);
			return manifestacije.azurirajManifestaciju(zaAzurirati);
		});
		
		//KOMENTARI
		//----------------------------------------------------------------------------------------
		
		
		get("/rest/komentari/manifestacijaKomentari/:id", (req, res) -> {
			String id = req.params("id");
			
			ManifestacijaKomentariDTO dto = new ManifestacijaKomentariDTO();
			dto.setManifestacija(manifestacije.getManifestacije().get(id));
			dto.setKomentari(komentarServis.komentariNaManifestaciju(id));
			
			res.type("application/json");
			return g.toJson(dto);
		});
		
		//KORISNICI
		post("/rest/manifestacije/registracijaManifestacije", (req, res) -> {
					
					ManifestacijaDTO dto = g.fromJson(req.body(), ManifestacijaDTO.class);
					
					Manifestacija novaManifestacija = new Manifestacija();
					novaManifestacija.setNaziv(dto.getNaziv());
					novaManifestacija.setTipManifestacije(dto.getTip());
					novaManifestacija.setBrojMesta(dto.getBrMesta());
					novaManifestacija.setVremeOdrzavanja(dto.getVremeOdrzavanja());
					novaManifestacija.setCenaRegular(dto.getCenaRegular());
					novaManifestacija.setStatus(false);
					novaManifestacija.setLokacija(null);
					novaManifestacija.setPoster(dto.getPoster());
					novaManifestacija.setObrisana(false);
					
					int retVal=manifestacije.dodajManifestaciju(novaManifestacija);
					korisnikServis.dodajManifestacijuKorisniku(novaManifestacija, dto.getProdavac());
					return retVal;
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
			noviKupac.setDatumRodjenja(LocalDate.parse(dto.getDatumRodjenja(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			
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
		
		get("/rest/korisnici/sviKorisnici", (req, res) -> {
			res.type("application/json");
			return g.toJson(new ArrayList<>(korisnikServis.getKorisnici().values()));
		});
		
		get("/rest/korisnici/jedanKorisnik/:korisnickoIme", (req, res) -> {
			String korisnickoIme = req.params("korisnickoIme");
			res.type("application/json");
			return g.toJson(korisnikServis.getKorisnici().get(korisnickoIme));
		});
		
		post("/rest/korisnici/azuriranjeKorisnika", (req, res) -> {
			Korisnik zaAzurirati = g.fromJson(req.body(), Korisnik.class);
			return korisnikServis.azurirajKorisnika(zaAzurirati);
		});
		
		post("/rest/korisnici/pretraga", (req, res) -> {
			KorisnikUpitDTO dto = g.fromJson(req.body(), KorisnikUpitDTO.class);
			res.type("application/json");
			return g.toJson(korisnikServis.pretragaKorisnika(new ArrayList<Korisnik>(korisnikServis.getKorisnici().values()), dto));
		});
	}

}
