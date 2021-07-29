package servisi;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import beans.Manifestacija;
import beans.dto.ManifestUpitDTO;

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
	
	public ArrayList<Manifestacija> pretragaManifestacija(ManifestUpitDTO dto){
		if (dto == null) return (ArrayList<Manifestacija>) this.manifestacije.values();
        final Map<String, Comparator<Manifestacija>> critMap = new HashMap<String, Comparator<Manifestacija>>();
        critMap.put("NAZIV_RAST", (o1,o2)->{return o1.getNaziv().compareToIgnoreCase(o2.getNaziv()); });
        critMap.put("NAZIV_OPAD", (o2,o1)->{return o1.getNaziv().compareToIgnoreCase(o2.getNaziv()); });
        critMap.put("DATUM_RAST", Comparator.comparing(Manifestacija::getVremeOdrzavanja));
        critMap.put("DATUM_OPAD", Comparator.comparing(Manifestacija::getVremeOdrzavanja).reversed());
        critMap.put("CENA_RAST", Comparator.comparingDouble(Manifestacija::getCenaRegular));
        critMap.put("CENA_OPAD", Comparator.comparingDouble(Manifestacija::getCenaRegular).reversed());
        critMap.put("LOKACIJA_RAST", Comparator.comparing(Manifestacija::getLokacija));
        critMap.put("LOKACIJA_OPAD", Comparator.comparing(Manifestacija::getLokacija).reversed());
        Comparator<Manifestacija> comp = critMap.getOrDefault(dto.getSort().toUpperCase().trim(), critMap.values().iterator().next());

        return (ArrayList<Manifestacija>) this.manifestacije.values().stream()
                .filter(m -> dto.getNaziv().isEmpty() || m.getNaziv().toLowerCase().contains(dto.getNaziv().toLowerCase()))
                .filter(m -> dto.getLokacija().isEmpty() || m.getLokacija().getAdresa().toLowerCase().contains(dto.getLokacija().toLowerCase()))
                .filter(m -> m.getCenaRegular() >= dto.getCenaOd() && m.getCenaRegular() <= dto.getCenaDo())
                .filter(o -> dto.getDatumOd().isBefore(o.getVremeOdrzavanja()) && o.getVremeOdrzavanja().isBefore(dto.getDatumDo()))
                .sorted(comp).collect(Collectors.toList());
	}
}
