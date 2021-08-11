Vue.component("karte-korisnika", {
    data: function(){
        return {
            prijavljenKorisnik : {},
            karte : [],
            sveManifestacije : {},
            upit : {manifestacija: "", cenaOd: 0, cenaDo: 0, datumOd: null, datumDo: null, sort: "", korisnickoIme: ""},
            tip: "",
            status: "",
            izabranaId: "",
        }
    },
    template: `
    <div class="container ">
    <h3>Moje rezervacije</h3>
    <br>

    <div class="row g-2 align-items-center">
        <div class="col-auto">Filteri</div>
        <div class="col-auto">
            <select id="tip" class="form-control" v-model="tip">
                    <option value="">Tip karte</option>
                    <option value="REGULAR">Regularna</option>
                    <option value="FAN_PIT">Fan pit</option>
                    <option value="VIP">VIP</option>
                </select>
        </div>
        <div class="col-auto">
            <select id="status" class="form-control" v-model="status">
                    <option value="">Status</option>
                    <option value="REZERVISANA">Rezervisana</option>
                    <option value="OTKAZANA">Otkazana</option>
                </select>
        </div>
    </div>
    <br>

    <div style="height: 400px;overflow-y: scroll;overflow-x: hidden;">
        <div class="card" v-for="karta in karte" v-if="filterTip(karta.tip) && filterStatus(karta.status)">
            <div class="card-header">
                Manifestacija: {{sveManifestacije[karta.manifestacijaId].naziv}}
            </div>
            <div class="card-body" data-bs-toggle="tooltip" data-bs-placement="right" title="Karte je moguće otkazati najkasnije 7 dana pre početka manifestacije">
                <div class="row">
                    <div class="col-auto">
                        <p class="card-text">
                            Vreme održavanja: {{formatirajDatum(karta.vremeOdrzavanja)}}<br>
                            Cena: {{karta.cena}}<br>
                            Status: {{pretvoriStatus(karta.status)}}<br>
                            Tip karte: {{pretvoriTip(karta.tip)}}<br>
                        </p>

                        <button class="w-150 btn btn-md btn-danger" v-if="jelMoguceOtkazivanje(karta.vremeOdrzavanja) && karta.status == 'REZERVISANA'" data-bs-toggle="modal" 
                        data-bs-target="#otkazivanjeModal" v-on:click="izabranaId = karta.id"
                            >Otkaži
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <hr>
    <div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="manifestacijaPretraga" class="col-form-label">Manifestacija</label>
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" id="manifestacijaPretraga" v-model="upit.manifestacija">
            </div>

            <div class="col-auto">
                <label class="col-form-label">Cena</label>
            </div>
            <div class="col-auto">
                <input type="number" class="form-control" id="cenaOd" v-model="upit.cenaOd" v-bind:min="0">
            </div>
            <div class="col-auto">
                <label class="col-form-label">-</label>
            </div>
            <div class="col-auto">
                <input type="number" class="form-control" id="cenaDo" v-model="upit.cenaDo" v-bind:min="0">
            </div>
            <div class="col-auto">
                <label class="col-form-label">Sortiranje</label>
            </div>
            <div class="col-auto">
                <select id="sort" class="form-control" v-model="upit.sort">
                        <option value="MANIFEST_RAST">MANIFEST_RAST</option>
                        <option value="MANIFEST_OPAD">MANIFEST_OPAD</option>
                        <option value="DATUM_RAST">DATUM_RAST</option>
                        <option value="DATUM_OPAD">DATUM_OPAD</option>
                        <option value="CENA_RAST">CENA_RAST</option>
                        <option value="CENA_OPAD">CENA_OPAD</option>
                    </select>
            </div>
        </div>
        <br>
        <div class="row g-4 align-items-center">
            <div class="col-auto">
                <label for="datumOd" class="col-form-label">Vreme od</label>
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
                <input class="w-150 btn btn-md btn-primary" type="submit" v-on:click="posaljiUpit()" value="Pretraga" />
            </div>
        </div>
    </div>
    <br>


    <div class="modal fade" id="otkazivanjeModal" tabindex="-1" aria-labelledby="otkazivanjeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="otkazivanjeModalLabel">Otkazivanje rezervacije</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <p>
            Da li ste sigurni da želite da otkažete ovu kartu?<br>

            (Napomena: izgubićete bodove u slučaju da otkažete ovu kartu)
            </p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Nazad</button>
            <button type="button" class="btn btn-primary" v-on:click="otkaziKartu()" data-bs-dismiss="modal">Potvrdi</button>
        </div>
        </div>
    </div>
    </div>

    </div>
    `,
    methods: {
        formatirajDatum(vreme){
            return vreme.date.day+"."+vreme.date.month+"."+vreme.date.year+". - "+vreme.time.hour+":"+vreme.time.minute;
        },
        pretvoriTip(tip){
            switch(String(tip)){
                case "REGULAR":
                    return "Regularna";
                case "FAN_PIT":
                    return "Fan pit";
                case "VIP":
                    return "VIP";
            }
        },
        pretvoriStatus(status){
            switch(String(status)){
                case "REZERVISANA":
                    return "Rezervisana";
                case "OTKAZANA":
                    return "Otkazana";
            }
        },
        jelMoguceOtkazivanje(vreme){
            let vremeOdrzavanja = new Date(vreme.date.year,vreme.date.month-1, vreme.date.day, 
                                           vreme.time.hour,vreme.time.minute,vreme.time.second,vreme.time.nano/1000);

            let danas = new Date();

            console.log(danas , new Date(vremeOdrzavanja - 7 * 24 * 60 * 60 * 1000));

            return danas <= new Date(vremeOdrzavanja - 7 * 24 * 60 * 60 * 1000);
        },
        filterTip(tip){
            return this.tip == "" || this.tip == tip;
        },
        filterStatus(status){
            return this.status == "" || this.status == status;
        },
        posaljiUpit(){
            this.upit.korisnickoIme = this.prijavljenKorisnik.korisnickoIme;
            axios.post("/rest/karte/pretraga", this.upit).then(response => {
                this.karte = response.data;
            });
        },
        otkaziKartu(){
            axios.post("rest/karte/otkazivanje", {idKarte: this.izabranaId, korisnickoIme: this.prijavljenKorisnik.korisnickoIme}).then(response => {
                alert(response.data);
                this.$router.go();
            });
        }
    },
    created() {
        this.prijavljenKorisnik = JSON.parse(localStorage.getItem("prijavljeni"));
        axios.get("/rest/manifestacije/sveManifestacije").then(response => {
            this.sveManifestacije = response.data;
        });
        axios.get("/rest/karte/rezervacije/"+this.prijavljenKorisnik.korisnickoIme).then(response => {
            this.karte = response.data;

            console.log(this.karte);
        });
    }
});