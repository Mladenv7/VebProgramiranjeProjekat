package servisi;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Karta;
import beans.Korisnik;
import beans.dto.RezervacijaDTO;
import enums.StatusKarte;
import enums.TipKarte;
import enums.TipKupca;

public class KartaServis {

	private HashMap<String, Karta> karte = new HashMap<String, Karta>();
	
	public KartaServis() {
		
		Gson gson = new Gson();
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/karte.json"));
			
			Karta[] list = gson.fromJson(citac, Karta[].class);

			if(list != null) {
			    for (int i = 0;i < list.length;++i) {
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
}
