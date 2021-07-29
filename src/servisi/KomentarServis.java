package servisi;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;

import beans.Komentar;

public class KomentarServis {
	private ArrayList<Komentar> komentari = new ArrayList<Komentar>();

	public KomentarServis() {
		Gson gson = new Gson();
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/komentari.json"));
			
			Komentar[] lista = gson.fromJson(citac, Komentar[].class);
			Collections.addAll(komentari, lista);
		   
		    citac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	//id - id manifestacije
	public ArrayList<Komentar> komentariNaManifestaciju(String id){
		ArrayList<Komentar> zaManifestaciju = new ArrayList<Komentar>();
		
		for(Komentar k : this.komentari) {
			if(k.getIdManifestacije().equals(id)) zaManifestaciju.add(k);
		}
		
		return zaManifestaciju;
	}
}
