Vue.component("manifestacije-dodaj", {
    data: function () {
        return {
            novaManifestacija : {
                naziv : "",
                tip : "",
                brojMesta : "", 
                vremeOdrzavanja : {
            		date:{month:6,day:12,year:2020},
            		time:{hour:8,minute:5}
            	},
            	cenaRegular :"" ,
            	status : false,
            	lokacija : {},
            	poster : "",
            	obrisana : false
            },

        }},
    template : `
    
   <div align="center" class="container">
    <h3>Unesite podatke o manifestaciji</h3>
    <br>
    <form style="width: 30%;" class="d-grid gap-2">
        <div class="form-floating">
        <input type="text" class="form-control" id="naziv" placeholder="Naziv" oninvalid="this.setCustomValidity('Morate uneti naziv manifestacije')"
            oninput="this.setCustomValidity('')" v-model="novaManifestacija.naziv" required>
        <label for="naziv">Naziv manifestacije</label>
        </div>
        <div class="form-floating">
        <input type="text" class="form-control" id="tip" placeholder="Tip manifestacije" v-model="novaManifestacija.tip">
        <label for="tip">Tip manifestacije</label>
        </div>
        <div class="form-floating">
        <input type="number" v-bind:min="0" class="form-control" id="brojMesta" placeholder="Broj Mesta" v-model="novaManifestacija.brojMesta">
        <label for="brojMesta">Broj Mesta</label>
        </div>
    	<div class="form-floating">
        <input type="number" class="form-control" id="cenaRegular" v-bind:min="0" placeholder="Cena regular" v-model="novaManifestacija.cenaRegular">
        <label for="cenaRegular">Cena regular</label>
        </div>

        <label for="poster" align="left">Poster</label>
        <form action="/action_page.php">
        <input type="file" class="form-control" id="poster" placeholder="Poster"  v-on:change="novaManifestacija.poster" accept="image/*">
        </form>
        
        
        <div class="form-floating">
        <input type="datetime-local" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" class="form-control" id="vremeOdrzavanja" v-model="novaManifestacija.vremeOdrzavanja"
            v-bind:min="(new Date()).toISOString().substring(0, 10)">
        <label for="vremeOdrzavanja">Vreme održavanja</label>
        </div>
        <input class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="posaljiManifestaciju()" value="Kreiraj manifestaciju" />
    </form>

    </div>
   
    ` ,
    	methods: {
        posaljiManifestaciju(){
        	this.novaManifestacija.poster="./podaci/posteri/"+(poster.value).split('\\')[2];
        	console.log(this.novaManifestacija)
            axios
			.post('/rest/manifestacije/registracijaManifestacije', this.novaManifestacija);
        },
    }
   
});