package servisi;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Korisnik;
import beans.Manifestacija;
import beans.dto.KorisnikUpitDTO;
import beans.dto.OtkazDTO;

public class KorisnikServis {


	private HashMap<String, Korisnik> korisnici = new HashMap<String, Korisnik>();
	private HashMap<String, Korisnik> blokirani = new HashMap<String, Korisnik>();

	public KorisnikServis() {
		Gson gson = new Gson();
		
		try {
			File kDatoteka = new File("./static/podaci/korisnici.json");
			if(!kDatoteka.exists()) kDatoteka.createNewFile();
			File bDatoteka = new File("./static/podaci/blokirani.json");
			if(!bDatoteka.exists()) kDatoteka.createNewFile();
			
			
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/korisnici.json"));
			
			Korisnik[] lista = gson.fromJson(citac, Korisnik[].class);

			if(lista != null) {
				for (int i = 0;i < lista.length;++i) {
					//if(lista[i].isObrisan()) continue;
			        korisnici.put(lista[i].getKorisnickoIme(), lista[i]);
			    }
			}
		   
		    citac.close();
		    
		    
		    citac = Files.newBufferedReader(Paths.get("./static/podaci/blokirani.json"));
			
			Korisnik[] listaB = gson.fromJson(citac, Korisnik[].class);

			if(listaB != null) {
				for (int i = 0;i < listaB.length;++i) {
					//if(lista[i].isObrisan()) continue;
			        blokirani.put(listaB[i].getKorisnickoIme(), listaB[i]);
			    }
			}
		   
		    citac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public HashMap<String, Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(HashMap<String, Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
	public HashMap<String, Korisnik> getBlokirani() {
		return blokirani;
	}

	public void setBlokirani(HashMap<String, Korisnik> blokirani) {
		this.blokirani = blokirani;
	}

	public void upisKorisnikaUDatoteku() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer stampac = Files.newBufferedWriter(Paths.get("./static/podaci/korisnici.json"));
			
			stampac.append(gson.toJson(korisnici.values().toArray(), Korisnik[].class));
			
			
			stampac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//1 - korisnicko ime je zauzeto 0 - sve uredu
	public int dodajKorisnika(Korisnik korisnik) {
		if(this.korisnici.get(korisnik.getKorisnickoIme()) != null) return 1;
		
		this.korisnici.put(korisnik.getKorisnickoIme(), korisnik);
		System.out.println(korisnik);
		
		this.upisKorisnikaUDatoteku();
		
		
		return 0;
	}
	
	public String azurirajKorisnika(Korisnik korisnik) {
		if(this.korisnici.get(korisnik.getKorisnickoIme()) != null) {
			
			this.korisnici.put(korisnik.getKorisnickoIme(), korisnik);
			System.out.println(korisnik);
			
			this.upisKorisnikaUDatoteku();
			
			return "A�uriranje uspe�no";
		}else return "Ovaj korisnik ne postoji";
	}
	
	public Korisnik dobaviKorisnika(String korisnickoIme) {
		return this.korisnici.get(korisnickoIme);
	}
	
	public List<Korisnik> pretragaKorisnika(List<Korisnik> lista, KorisnikUpitDTO dto){
		if (dto == null) return lista;
        final Map<String, Comparator<Korisnik>> critMap = new HashMap<String, Comparator<Korisnik>>();
        critMap.put("IME_RAST", (o1,o2)->{return o1.getIme().trim().compareToIgnoreCase(o2.getIme().trim()); });
        critMap.put("IME_OPAD", (o2,o1)->{return o1.getIme().trim().compareToIgnoreCase(o2.getIme().trim()); });
        critMap.put("PREZIME_RAST", (o1,o2)->{return o1.getPrezime().trim().compareToIgnoreCase(o2.getPrezime().trim()); });
        critMap.put("PREZIME_OPAD", (o2,o1)->{return o1.getPrezime().trim().compareToIgnoreCase(o2.getPrezime().trim()); });
        critMap.put("KIME_RAST", (o1,o2)->{return o1.getKorisnickoIme().trim().compareToIgnoreCase(o2.getKorisnickoIme().trim()); });
        critMap.put("KIME_OPAD", (o2,o1)->{return o1.getKorisnickoIme().trim().compareToIgnoreCase(o2.getKorisnickoIme().trim()); });
        critMap.put("BODOVI_RAST", Comparator.comparingInt(Korisnik::getBrBodova));
        critMap.put("BODOVI_OPAD", Comparator.comparingInt(Korisnik::getBrBodova).reversed());
        Comparator<Korisnik> comp = critMap.getOrDefault(dto.getSort().toUpperCase().trim(), critMap.values().iterator().next());

        return lista.stream()
        		.filter(k -> !k.isObrisan())
                .filter(k -> dto.getIme().isEmpty() || k.getIme().toLowerCase().contains(dto.getIme().toLowerCase()))
                .filter(k -> dto.getPrezime().isEmpty() || k.getPrezime().toLowerCase().contains(dto.getPrezime().toLowerCase()))
                .filter(k -> dto.getKorisnickoIme().isEmpty() || k.getKorisnickoIme().toLowerCase().contains(dto.getKorisnickoIme().toLowerCase()))
                .sorted(comp).collect(Collectors.toList());
	}
	
	public void dodajManifestacijuKorisniku(Manifestacija man,String prodavac) {
		for (HashMap.Entry<String,Korisnik> entry : korisnici.entrySet()) {
				
            	if(entry.getValue().getKorisnickoIme().equals(prodavac)) {
            		ArrayList<String> tmp = entry.getValue().getSveManifest();
            		tmp.add(man.getId());
            		azurirajKorisnika(entry.getValue());
            	}
           
        }
	}
	
	public void blokirajKorisnika(Korisnik korisnik) {
		
		this.blokirani.put(korisnik.getKorisnickoIme(), korisnik);
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer stampac = Files.newBufferedWriter(Paths.get("./static/podaci/blokirani.json"));
			
			stampac.append(gson.toJson(korisnik, Korisnik.class));
			
			stampac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Korisnik> dobaviSumnjiveKupce() {
		HashMap<String, ArrayList<OtkazDTO>> korisniciOtkazi = new HashMap<>();
		
		Gson g = new Gson();
		
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/otkazivanja.json"));
			
			OtkazDTO[] otkazivanja = g.fromJson(citac, OtkazDTO[].class); 
			
			if(otkazivanja == null) return new ArrayList<>();
			for(OtkazDTO o : otkazivanja) {
				if(korisniciOtkazi.get(o.getKorisnickoIme()) == null) {
					korisniciOtkazi.put(o.getKorisnickoIme(), new ArrayList<>(Arrays.asList(o)));
				}else {
					korisniciOtkazi.get(o.getKorisnickoIme()).add(o);
				}
			}
			
			citac.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Korisnik> sumnjivi = new ArrayList<>();
		
		for(Map.Entry<String, ArrayList<OtkazDTO>> e : korisniciOtkazi.entrySet()) {
			e.getValue().removeIf(o -> o.getVremeOtkaza().isBefore(LocalDateTime.now().plusDays(-30)));
			if(e.getValue().size() > 5) sumnjivi.add(this.korisnici.get(e.getKey()));
		}
		
		
		
		return sumnjivi;
	}
}
