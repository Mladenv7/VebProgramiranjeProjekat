package servisi;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Korisnik;

public class KorisnikServis {


	private HashMap<String, Korisnik> korisnici = new HashMap<String, Korisnik>();

	public KorisnikServis() {
		Gson gson = new Gson();
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/korisnici.json"));
			
			Korisnik[] lista = gson.fromJson(citac, Korisnik[].class);

			if(lista != null) {
				for (int i = 0;i < lista.length;++i) {
			        korisnici.put(lista[i].getKorisnickoIme(), lista[i]);
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
	
	//1 - korisnicko ime je zauzeto 0 - sve uredu
	public int dodajKorisnika(Korisnik korisnik) {
		if(this.korisnici.get(korisnik.getKorisnickoIme()) != null) return 1;
		
		this.korisnici.put(korisnik.getKorisnickoIme(), korisnik);
		System.out.println(korisnik);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
		try {
			Writer stampac = Files.newBufferedWriter(Paths.get("./static/podaci/korisnici.json"));
			
			stampac.append(gson.toJson(korisnici.values().toArray(), Korisnik[].class));
			
			
			stampac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	public Korisnik dobaviKorisnika(String korisnickoIme) {
		return this.korisnici.get(korisnickoIme);
	}
}
