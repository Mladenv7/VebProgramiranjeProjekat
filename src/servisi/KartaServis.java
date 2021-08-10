package servisi;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import beans.dto.KarteUpitDTO;
import beans.dto.RezervacijaDTO;
import enums.StatusKarte;
import enums.TipKarte;
import enums.TipKupca;

public class KartaServis {

	private HashMap<String, Karta> karte = new HashMap<String, Karta>();
	
	public HashMap<String, Karta> getKarte() {
		return karte;
	}

	public void setKarte(HashMap<String, Karta> karte) {
		this.karte = karte;
	}

	public KartaServis() {
		
		Gson gson = new Gson();
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/karte.json"));
			
			Karta[] list = gson.fromJson(citac, Karta[].class);

			if(list != null) {
			    for (int i = 0;i < list.length;++i) {
			    	if(list[i].isObrisana()) continue;
			        karte.put(list[i].getId(), list[i]);
			    }
			}

		    citac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upisKarataUDatoteku() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer stampac = Files.newBufferedWriter(Paths.get("./static/podaci/karte.json"));
			
			stampac.append(gson.toJson(karte.values().toArray(), Karta[].class));
			
			
			stampac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rezervisiKarte(RezervacijaDTO dto, Korisnik korisnik) {
		for(int i = 0;i < dto.getKolicine().length;++i) {
			for(int j = 0;j < dto.getKolicine()[i];++j) {
				Karta karta = new Karta();
				karta.setImePrezime(dto.getImePrezime());
				karta.setManifestacijaId(dto.getManifestacijaId());
				karta.setVremeOdrzavanja(dto.getVremeOdrzavanja());
				karta.setStatus(StatusKarte.REZERVISANA);
				switch(i) {
				case 0:
					karta.setTip(TipKarte.REGULAR);
					karta.setCena(dto.getCenaRegular());
					break;
				case 1:
					karta.setTip(TipKarte.FAN_PIT);
					karta.setCena(dto.getCenaRegular()*2);
					break;
				case 2:
					karta.setTip(TipKarte.VIP);
					karta.setCena(dto.getCenaRegular()*4);
				}
				
				karta.setId(generisiIdKarte());
				while(this.karte.get(karta.getId()) != null) {
					karta.setId(generisiIdKarte());
				}
				
				this.karte.put(karta.getId(), karta);
				korisnik.getSveKarte().add(karta.getId());
				
				korisnik.setBrBodova(korisnik.getBrBodova()+ (int)Math.floor(karta.getCena()/1000 * 133));
				
				if(korisnik.getBrBodova() > TipKupca.BRONZANI.kriterijumZaTip) {
					korisnik.setTip(TipKupca.BRONZANI);
				}
				if(korisnik.getBrBodova() > TipKupca.SREBRNI.kriterijumZaTip) {
					korisnik.setTip(TipKupca.SREBRNI);
				}
				if(korisnik.getBrBodova() > TipKupca.ZLATNI.kriterijumZaTip) {
					korisnik.setTip(TipKupca.ZLATNI);
				}
			}
		}
		
		upisKarataUDatoteku();
	}
	
	public String generisiIdKarte() {
		int lGranica = 48; // '0'
        int rGranica = 122; // 'z'
        int duzina = 10;
        Random random = new Random();
        
        String id = random.ints(lGranica, rGranica + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(duzina)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
        
        return id;
	}
	
	public List<Karta> karteOdKorisnika(Korisnik k){
		ArrayList<Karta> karteKorisnika = new ArrayList<>();
		
		for(String id : k.getSveKarte()) {
			karteKorisnika.add(this.karte.get(id));
		}
		
		return karteKorisnika;
	}
	
	public List<Karta> pretragaKarata(List<Karta> karte, KarteUpitDTO dto, HashMap<String, Manifestacija> sveManifestacije){
		if (dto == null) return karte;
        final Map<String, Comparator<Karta>> critMap = new HashMap<String, Comparator<Karta>>();
        critMap.put("MANIFEST_RAST", (o1,o2)->{return sveManifestacije.get(o1.getManifestacijaId()).getNaziv().compareToIgnoreCase(sveManifestacije.get(o2.getManifestacijaId()).getNaziv()); });
        critMap.put("MANIFEST_OPAD", (o2,o1)->{return sveManifestacije.get(o1.getManifestacijaId()).getNaziv().compareToIgnoreCase(sveManifestacije.get(o2.getManifestacijaId()).getNaziv()); });
        critMap.put("DATUM_RAST", Comparator.comparing(Karta::getVremeOdrzavanja));
        critMap.put("DATUM_OPAD", Comparator.comparing(Karta::getVremeOdrzavanja).reversed());
        critMap.put("CENA_RAST", Comparator.comparingDouble(Karta::getCena));
        critMap.put("CENA_OPAD", Comparator.comparingDouble(Karta::getCena).reversed());
        Comparator<Karta> comp = critMap.getOrDefault(dto.getSort().toUpperCase().trim(), critMap.values().iterator().next());

        return karte.stream()
                .filter(k -> dto.getManifestacija().isEmpty() || 
                			 sveManifestacije.get(k.getManifestacijaId()).getNaziv().toLowerCase().contains(dto.getManifestacija().toLowerCase()))
                .filter(k -> {
                				if(dto.getCenaOd() > 0 && dto.getCenaDo() > 0) {
                					return dto.getCenaOd() <= k.getCena() && k.getCena() <= dto.getCenaDo();
                				}else if(dto.getCenaOd() > 0) {
                					return dto.getCenaOd() <= k.getCena();
                				}else if(dto.getCenaDo() > 0) {
                					return k.getCena() <= dto.getCenaDo();
                				}else return true;
                			 })
                .filter(k -> {
                				if(dto.getDatumOd() != null && dto.getDatumDo() != null) {
                					return dto.getDatumOd().isBefore(k.getVremeOdrzavanja()) && k.getVremeOdrzavanja().isBefore(dto.getDatumDo());
                				}else if(dto.getDatumOd() != null) {
                					return dto.getDatumOd().isBefore(k.getVremeOdrzavanja());
                				}else if(dto.getDatumDo() != null){
                					return k.getVremeOdrzavanja().isBefore(dto.getDatumDo());
                				}else return true;
                			 })
                .sorted(comp).collect(Collectors.toList());
	}
	
	public int otkazivanjeKarte(String idKarte, Korisnik korisnik) {
		Karta karta = this.karte.get(idKarte);
		
		this.karte.get(idKarte).setStatus(StatusKarte.OTKAZANA);
		
		korisnik.setBrBodova(korisnik.getBrBodova()- (int)Math.floor(karta.getCena()/1000 * 133 * 4));
		
		if(korisnik.getBrBodova() > TipKupca.BRONZANI.kriterijumZaTip) {
			korisnik.setTip(TipKupca.BRONZANI);
		}
		if(korisnik.getBrBodova() > TipKupca.SREBRNI.kriterijumZaTip) {
			korisnik.setTip(TipKupca.SREBRNI);
		}
		if(korisnik.getBrBodova() > TipKupca.ZLATNI.kriterijumZaTip) {
			korisnik.setTip(TipKupca.ZLATNI);
		}
		
		this.upisKarataUDatoteku();
		
		return 0;
	}
	
	public List<Karta> karteOdManifestacije(String manifestacijaId){
		ArrayList<Karta> karte = new ArrayList<>();
		
		for(Karta k : this.karte.values()) {
			if(k.getManifestacijaId().equals(manifestacijaId)) {
				karte.add(k);
			}
		}
		
		return karte;
	}
}
