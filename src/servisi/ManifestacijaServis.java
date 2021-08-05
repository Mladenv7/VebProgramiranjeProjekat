package servisi;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	
	public List<Manifestacija> pretragaManifestacija(List<Manifestacija> lista, ManifestUpitDTO dto){
		if (dto == null) return lista;
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

        return lista.stream()
                .filter(m -> dto.getNaziv().isEmpty() || m.getNaziv().toLowerCase().contains(dto.getNaziv().toLowerCase()))
                .filter(m -> dto.getLokacija().isEmpty() || m.getLokacija().getAdresa().toLowerCase().contains(dto.getLokacija().toLowerCase()))
                .filter(m -> {
                				if(dto.getCenaOd() > 0 && dto.getCenaDo() > 0) {
                					return dto.getCenaOd() <= m.getCenaRegular() && m.getCenaRegular() <= dto.getCenaDo();
                				}else if(dto.getCenaOd() > 0) {
                					return dto.getCenaOd() <= m.getCenaRegular();
                				}else if(dto.getCenaDo() > 0) {
                					return m.getCenaRegular() <= dto.getCenaDo();
                				}else return true;
                			 })
                .filter(m -> {
                				if(dto.getDatumOd() != null && dto.getDatumDo() != null) {
                					return dto.getDatumOd().isBefore(m.getVremeOdrzavanja()) && m.getVremeOdrzavanja().isBefore(dto.getDatumDo());
                				}else if(dto.getDatumOd() != null) {
                					return dto.getDatumOd().isBefore(m.getVremeOdrzavanja());
                				}else if(dto.getDatumDo() != null){
                					return m.getVremeOdrzavanja().isBefore(dto.getDatumDo());
                				}else return true;
                			 })
                .sorted(comp).collect(Collectors.toList());
	}
	
	public int dodajManifestaciju(Manifestacija manifestacija) {
		String lastObject = "1";
		 for (HashMap.Entry<String,Manifestacija> entry : manifestacije.entrySet()) {
	            
	            int tmp=Integer.valueOf(entry.getKey());
	            int max=Integer.valueOf(lastObject);
	            if(tmp>max)
	            {
	            	lastObject = entry.getKey();
	            }
	        }
		 
		 int i=(Integer.valueOf(lastObject)+1);
		 lastObject=String.valueOf(i);
		 manifestacija.setId(lastObject);
		this.manifestacije.put(lastObject, manifestacija);
		System.out.println(manifestacija);		
		
		return 0;
	}
}
