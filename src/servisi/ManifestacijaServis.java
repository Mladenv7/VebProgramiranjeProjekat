package servisi;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;

import beans.Manifestacija;

public class ManifestacijaServis {
	
	private HashMap<String, Manifestacija> manifestacije = new HashMap<String, Manifestacija>();
	
	public ManifestacijaServis() {
		
		Gson gson = new Gson();
		try {
			Reader citac = Files.newBufferedReader(Paths.get("./static/podaci/manifestacije.json"));
			
			Manifestacija[] list = gson.fromJson(citac, Manifestacija[].class);

			if(list != null) {
			    for (int i = 0;i < list.length;++i) {
			        manifestacije.put(list[i].getId(), list[i]);
			    }
			}

		   
		    citac.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(HashMap<String, Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
}
