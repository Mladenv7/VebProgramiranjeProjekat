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
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import beans.Karta;
import beans.Komentar;
import beans.Korisnik;
import beans.Manifestacija;
import beans.dto.KarteUpitDTO;
import beans.dto.KorisnikUpitDTO;
import beans.dto.KupacRegistracijaDTO;
import beans.dto.ManifestOcenaDTO;
import beans.dto.ManifestUpitDTO;
import beans.dto.ManifestacijaDTO;
import beans.dto.ManifestacijaKomentariDTO;
import beans.dto.OtkazDTO;
import beans.dto.PrijavaDTO;
import beans.dto.RezervacijaDTO;
import enums.Pol;
import enums.Uloga;
import servisi.KartaServis;
import servisi.KomentarServis;
import servisi.KorisnikServis;
import servisi.ManifestacijaServis;

public class RezervacijaKarataMain {

	private static ManifestacijaServis manifestacije = new ManifestacijaServis();
	private static KorisnikServis korisnikServis = new KorisnikServis();
	private static KomentarServis komentarServis = new KomentarServis();
	private static KartaServis kartaServis = new KartaServis();
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
		
		get("/rest/komentari/manifestacijaKomentari/:id", (req, res) -> {
			String id = req.params("id");
			
			ManifestacijaKomentariDTO dto = new ManifestacijaKomentariDTO();
			dto.setManifestacija(manifestacije.getManifestacije().get(id));
			dto.setKomentari(komentarServis.komentariNaManifestaciju(id));
			
			res.type("application/json");
			return g.toJson(dto);
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
					if(!k.isAktivan() || k.isObrisan()) continue;
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
		
		get("/rest/manifestacije/neaktivneManifestacije", (req, res) -> {
			res.type("application/json");
			return g.toJson(manifestacije.neaktivneManifestacije());
		});
		
		post("/rest/manifestacije/aktivacija", (req, res) -> {
			String zaOdobrenje = req.body();
			manifestacije.odobrenjeManifestacije(zaOdobrenje);
			return "";
		});
		
		get("rest/manifestacije/preostaleKarteKolicine", (req, res) -> {
			HashMap<String, Integer> kolicine = new HashMap<>(); 
			
			for(Manifestacija m : manifestacije.getManifestacije().values()) {
				kolicine.put(m.getId(), kartaServis.karteOdManifestacije(m.getId()).size());
			}
			
			res.type("application/json");
			return g.toJson(kolicine);
		});
		
		post("/rest/manifestacije/registracijaManifestacije", (req, res) -> {
			
			ManifestacijaDTO dto = g.fromJson(req.body(), ManifestacijaDTO.class);
			
			Manifestacija novaManifestacija = new Manifestacija();
			novaManifestacija.setNaziv(dto.getNaziv());
			novaManifestacija.setTipManifestacije(dto.getTip());
			novaManifestacija.setBrojMesta(dto.getBrMesta());
			novaManifestacija.setVremeOdrzavanja(dto.getVremeOdrzavanja());
			novaManifestacija.setCenaRegular(dto.getCenaRegular());
			novaManifestacija.setStatus(false);
			novaManifestacija.setLokacija(dto.getLokacija());
			novaManifestacija.setPoster(dto.getPoster());
			novaManifestacija.setObrisana(false);
			
			int retVal=manifestacije.dodajManifestaciju(novaManifestacija);
			if(retVal == -1)
				return retVal;
			korisnikServis.dodajManifestacijuKorisniku(novaManifestacija, dto.getProdavac());
			return retVal;

		});
		
		
		post("/rest/manifestacije/brisanjeManifestacije", (req, res) -> {
			Manifestacija zaObrisati = g.fromJson(req.body(), Manifestacija.class);
			
			manifestacije.getManifestacije().get(zaObrisati.getId()).setObrisana(true);
			
			for(Karta k : kartaServis.getKarte().values()) {
				if(k.getManifestacijaId().equals(zaObrisati.getId())) {
					k.setObrisana(true);
				}
			}
			
			manifestacije.upisManifestacijaUDatoteku();
			kartaServis.upisKarataUDatoteku();
			
			return "Manifestacija je uspešno obrisana";
		});
		
		//KARTE
		//----------------------------------------------------------------------------------------
		
		post("/rest/karte/rezervacija", (req, res) -> {
			RezervacijaDTO dto = g.fromJson(req.body(), RezervacijaDTO.class);
			
			kartaServis.rezervisiKarte(dto, korisnikServis.getKorisnici().get(dto.getKorisnickoIme()));
			
			korisnikServis.upisKorisnikaUDatoteku();
			
			return "Rezervacija uspešna";
		});
		
		get("/rest/karte/rezervacije/:korisnickoIme", (req, res) -> {
			String korisnickoIme = req.params("korisnickoIme");
			
			List<Karta> karte = kartaServis.karteOdKorisnika(korisnikServis.getKorisnici().get(korisnickoIme));
			res.type("application/json");
			return g.toJson(karte);
		});
		
		post("/rest/karte/pretraga", (req, res) -> {
			KarteUpitDTO dto = g.fromJson(req.body(), KarteUpitDTO.class);
			System.out.println(dto);
			
			List<Karta> korisnikoveKarte;
			
			if(dto.getKorisnickoIme().equals("")) korisnikoveKarte = new ArrayList<>(kartaServis.getKarte().values());
			else korisnikoveKarte = kartaServis.karteOdKorisnika(korisnikServis.getKorisnici().get(dto.getKorisnickoIme()));
			
			List<Karta> rezultatPretrage = kartaServis.pretragaKarata(korisnikoveKarte, dto, manifestacije.getManifestacije());
			
			res.type("application/json");
			return g.toJson(rezultatPretrage);
		});
		
		post("rest/karte/otkazivanje", (req,res) -> {
			OtkazDTO dto = g.fromJson(req.body(), OtkazDTO.class);
			
			kartaServis.otkazivanjeKarte(dto.getIdKarte(), korisnikServis.getKorisnici().get(dto.getKorisnickoIme()));
			
			korisnikServis.upisKorisnikaUDatoteku();
			
			return "Otkazivanje uspešno";
		});
		
		get("rest/karte/karteOdManifestacije/:manifestacijaId", (req, res) -> {
			List<Karta> karte = kartaServis.karteOdManifestacije(req.params("manifestacijaId"));
			res.type("application/json");
			return g.toJson(karte);
		});
		
		get("rest/karte/sveKarte", (req, res) -> {
			res.type("application/json");
			return g.toJson(kartaServis.getKarte());
		});
		
		post("rest/karte/brisanje", (req, res) -> {
			String idZaObrisati = req.body();
			
			kartaServis.getKarte().get(idZaObrisati).setObrisana(true);
			kartaServis.upisKarataUDatoteku();
			
			for(Korisnik k : korisnikServis.getKorisnici().values()) {
				k.getSveKarte().remove(idZaObrisati);
			}
			
			return "Brisanje uspešno";
		});
		
		//KOMENTARI
		//----------------------------------------------------------------------------------------
		
		get("/rest/komentari/sviKomentari", (req, res) -> {
			List<Komentar> sviKomentari = komentarServis.getKomentari();
			res.type("application/json");
			return g.toJson(sviKomentari);
		});
		
		post("/rest/komentari/odobravanje", (req, res) -> {
			
			Komentar komentar = g.fromJson(req.body(), Komentar.class);
			
			for(Komentar k : komentarServis.getKomentari()) {
				if(k.equals(komentar)) {
					k.setAktivan(true);
					break;
				}
			}
			
			komentarServis.upisKomentaraUDatoteku();
			
			return "Komentar uspešno odobren";
		});
		
		
		post("/rest/komentari/brisanje", (req, res) -> {
			
			Komentar komentar = g.fromJson(req.body(), Komentar.class);
			
			for(Komentar k : komentarServis.getKomentari()) {
				if(k.equals(komentar)) {
					k.setObrisan(true);
					break;
				}
			}
			
			komentarServis.upisKomentaraUDatoteku();
			
			return "Komentar uspešno obrisan";
		});
		
		post("/rest/komentari/novi", (req, res) -> {
			Komentar komentar = g.fromJson(req.body(), Komentar.class);
			
			komentarServis.getKomentari().add(komentar);
			komentarServis.upisKomentaraUDatoteku();
			
			return "Uspešno ste ostavili komentar";
		});
		
		//KORISNICI
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
		
		post("/rest/korisnici/registracijaProdavca", (req, res) -> {
			
			KupacRegistracijaDTO dto = g.fromJson(req.body(), KupacRegistracijaDTO.class);
			
			Korisnik noviProdavac = new Korisnik();
			noviProdavac.setKorisnickoIme(dto.getKorisnickoIme());
			noviProdavac.setIme(dto.getIme());
			noviProdavac.setPrezime(dto.getPrezime());
			noviProdavac.setLozinka(dto.getLozinka());
			noviProdavac.setUloga(Uloga.PRODAVAC);
			noviProdavac.setDatumRodjenja(LocalDate.parse(dto.getDatumRodjenja(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			
			if(dto.getPol().equals("m")) noviProdavac.setPol(Pol.MUSKI);
			else if(dto.getPol().equals("ž")) noviProdavac.setPol(Pol.ZENSKI);
			
			return korisnikServis.dodajKorisnika(noviProdavac);
		});
		
		post("/rest/korisnici/prijava", (req, res) -> {
			PrijavaDTO dto = g.fromJson(req.body(), PrijavaDTO.class);
			Korisnik prijavljeni = korisnikServis.dobaviKorisnika(dto.getKorisnickoIme());
			if(prijavljeni == null || prijavljeni.isObrisan()) return "Ovaj korisnik ne postoji";
			if(!prijavljeni.getLozinka().equals(dto.getLozinka())) return "Pogrešna lozinka";
			if(korisnikServis.getBlokirani().get(prijavljeni.getKorisnickoIme()) != null) return "Vaš nalog je blokiran";
			return g.toJson(prijavljeni);
		});
		
		get("/rest/korisnici/sviKorisnici", (req, res) -> {
			List<Korisnik> svi = new ArrayList<>(korisnikServis.getKorisnici().values());
			
			svi.removeIf(k -> k.isObrisan());
			
			res.type("application/json");
			return g.toJson(svi);
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
		
		post("/rest/korisnici/brisanje", (req , res) -> {
			Korisnik zaObrisati = g.fromJson(req.body(),Korisnik.class);
			
			switch(zaObrisati.getUloga()) {
			case KUPAC:
				for(String idKarte : zaObrisati.getSveKarte()) {
					kartaServis.getKarte().get(idKarte).setObrisana(true);
				}
				kartaServis.upisKarataUDatoteku();
				break;
			case PRODAVAC:
				for(String idManifestacije : zaObrisati.getSveManifest()) {
					Manifestacija manifestZaBrisanje = manifestacije.getManifestacije().get(idManifestacije);
					
					manifestZaBrisanje.setObrisana(true);
					
					for(Karta k : kartaServis.getKarte().values()) {
						if(k.getManifestacijaId().equals(manifestZaBrisanje.getId())) {
							k.setObrisana(true);
						}
					}
					
					manifestacije.upisManifestacijaUDatoteku();
					kartaServis.upisKarataUDatoteku();
				}
				break;
			case ADMINISTRATOR:
				return "Nije moguće izbrisati druge administratore";
			default:
				
			}
			korisnikServis.getKorisnici().get(zaObrisati.getKorisnickoIme()).setObrisan(true);
			korisnikServis.upisKorisnikaUDatoteku();
			
			return "Korisnik je uspešno obrisan";
		});
		
		post("/rest/korisnici/blokiranje", (req, res) -> {
			Korisnik korisnik = g.fromJson(req.body(), Korisnik.class);
			korisnikServis.blokirajKorisnika(korisnik);
			return "Korisnik je uspešno blokiran";
		});
		
		get("/rest/korisnici/sumnjivi", (req,res) -> {
			ArrayList<Korisnik> sumnjivi = korisnikServis.dobaviSumnjiveKupce();			
			res.type("application/json");
			return g.toJson(sumnjivi);
		});
		
		get("/rest/korisnici/blokirani", (req, res) -> {
			HashMap<String, Korisnik> blokirani = korisnikServis.getBlokirani();
			res.type("application/json");
			return g.toJson(blokirani);
		});
	}

}
