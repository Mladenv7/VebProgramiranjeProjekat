Vue.component("manifestacije-pretraga", {
    data: function() {
        return {
            upit : {naziv: "", lokacija: "", sort: "",cenaOd: 0, cenaDo: 0, datumOd: null, datumDo: null},
            rezultat : [],
            izabranaId : "",
            tipFilter: "",
            nerasprodate: false,
        }
    },
    template: `
    <div class="container">
    <h3>Pretraga manifestacija</h3>

    <div class="row">
        <div class="col-auto">
        <p>Filter tipa</p>
        </div>
        <div class="col-auto">
        <input type="text" v-model="tipFilter">
        </div>
        <div class="col-auto">
        <label class="form-check-label" for="flexCheckDefault">
          Samo nerasoprodate manifestacije 
        </label>
        <input class="form-check-input" type="checkbox" v-model="nerasprodate" id="flexCheckDefault">
        </div>
    </div>

    <div style="height: 350px;overflow-y: scroll;overflow-x: hidden;">
        <div class="card" v-for="stavka in rezultat" v-if="filterTipa(stavka.manifestacija.tipManifestacije)">
            <div class="card-header">
                {{stavka.manifestacija.naziv}}
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-4" style="width: 80%;">
                        <h5 class="card-title">{{formatirajDatum(stavka.manifestacija.vremeOdrzavanja)}}</h5>
                        <p class="card-text">
                            {{stavka.manifestacija.tipManifestacije}}<br>
                            {{stavka.manifestacija.lokacija.ulicaBroj+" "+stavka.manifestacija.lokacija.grad+" "+stavka.manifestacija.lokacija.postanskiBroj}}<br>
                            Cena regularne karte: {{stavka.manifestacija.cenaRegular}}
                            
                        </p>
                        <p class="card-text" v-if="stavka.prosecnaOcena > 0 && jelProsla(stavka.manifestacija)">
                            Prosečna ocena: {{stavka.prosecnaOcena.toFixed(2)}}
                        </p>
                        <a href="#" class="btn btn-secondary" v-on:click="pregledManifestacije(stavka.manifestacija.id)">Detalji</a>
                    </div>
                    <div class="col">
                        <img v-bind:src="stavka.manifestacija.poster" class="card-img-top" style="max-width:200px;max-height:200px;">
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <hr>

        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <label for="nazivPretraga" class="col-form-label">Naziv</label>
            </div>
            <div class="col-auto">
            <input type="text" class="form-control" id="nazivPretraga" v-model="upit.naziv">
            </div>
            <div class="col-auto">
                <label for="nazivPretraga" class="col-form-label">Cena od</label>
            </div>
            <div class="col-auto">
            <input type="number" class="form-control" id="cenaOd" v-bind:min="0" v-model="upit.cenaOd">
            </div>
            <div class="col-auto">
                <label for="nazivPretraga" class="col-form-label">do</label>
            </div>
            <div class="col-auto">
            <input type="number" class="form-control" id="cenaDo" v-bind:min="0" v-model="upit.cenaDo">
            </div>
            <div class="col-auto">
                <label for="datumOd" class="col-form-label">Sortiranje</label>
            </div>
            <div class="col-auto">
                <select id="sort" class="form-control" v-model="upit.sort">
                        <option value="NAZIV_RAST">NAZIV_RAST</option>
                        <option value="NAZIV_OPAD">NAZIV_OPAD</option>
                        <option value="DATUM_RAST">DATUM_RAST</option>
                        <option value="DATUM_OPAD">DATUM_OPAD</option>
                        <option value="CENA_RAST">CENA_RAST</option>
                        <option value="CENA_OPAD">CENA_OPAD</option>
                        <option value="LOKACIJA_RAST">LOKACIJA_RAST</option>
                        <option value="LOKACIJA_OPAD">LOKACIJA_OPAD</option>
                    </select>
            </div>
            
        </div><br>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="datumOd" class="col-form-label">Datum od</label>
            </div>
            <div class="col-auto">
            <input type="datetime-local" class="form-control" id="datumOd" v-model="upit.datumOd">
            </div>
            <div class="col-auto">
                <label for="datumDo" class="col-form-label">do</label>
            </div>
            <div class="col-auto">
            <input type="datetime-local" class="form-control" id="datumDo" v-model="upit.datumDo">
            </div>
            <div class="col-auto">
                <label for="lokacijaPretraga" class="col-form-label">Lokacija</label>
            </div>
            <div class="col-auto">
            <input type="text" class="form-control" id="lokacijaPretraga" v-model="upit.lokacija">
            </div>
            <div class="col-auto">
            <input class="w-150 btn btn-md btn-primary" type="submit" v-on:click="posaljiUpit()" value="Pretraga" />
            </div>
        </div><br>
    </div>

    </div>
    `,
    methods: {
        pretvoriStatus(status){
            if(status) return "Aktivna";
            if(!status) return "Neaktivna";
        },
        formatirajDatum(vreme){
            return vreme.date.day+"."+vreme.date.month+"."+vreme.date.year+". - "+vreme.time.hour+":"+vreme.time.minute;
        },
        jelProsla(manifestacija){
            var vremeOdrzavanja = new Date(manifestacija.vremeOdrzavanja.date.year,
                                           manifestacija.vremeOdrzavanja.date.month, 
                                           manifestacija.vremeOdrzavanja.date.day, 
                                           manifestacija.vremeOdrzavanja.time.hour, 
                                           manifestacija.vremeOdrzavanja.time.minute, 
                                           manifestacija.vremeOdrzavanja.time.second, 
                                           manifestacija.vremeOdrzavanja.time.nano/1000);
            return vremeOdrzavanja < new Date();
        },
        filterTipa(tip){
            if(tip.includes(this.tipFilter)) return true;
            else return false;
        },
        pregledManifestacije(id){
            this.izabranaId = id;
            console.log(this.izabranaId);
            this.$router.push({ path: '/manifestacija', query: { id: this.izabranaId }});
        },
        posaljiUpit(){
            console.log(this.upit);
            this.$router.push({ path: '/manifestacijePretraga', query: this.upit});
            this.$router.go();
        }
    },
    created(){
        this.upit.naziv = this.$route.query.naziv;
        this.upit.lokacija = this.$route.query.lokacija;
        this.upit.sort = this.$route.query.sort;
        this.upit.cenaOd = this.$route.query.cenaOd;
        this.upit.cenaDo = this.$route.query.cenaDo;
        this.upit.datumOd = this.$route.query.datumOd;
        this.upit.datumDo = this.$route.query.datumDo;
        axios.post("/rest/manifestacije/pretraga", this.upit).then(response => {
            this.rezultat = response.data;
        });
    },
});