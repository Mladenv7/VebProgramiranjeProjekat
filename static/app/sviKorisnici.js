Vue.component("svi-korisnici", {
    data: function(){
        return {
            korisnici : [],
            upit: {
                    ime: "",
                    prezime: "",
                    korisnickoIme: "",
                    sort: ""
                  },
            filterUloga: "",
            filterTipKupca: "",
            izabranKorisnik : {},
        }
    },
    template: 
    `
    <div class="container">
        <h3>Registrovani korisnici</h3>

        <div style="height: 350px;overflow-y: scroll;">
            <table class="table">
            <thead>
                <tr>
                <th scope="col">Korisničko ime</th>
                <th scope="col">Ime</th>
                <th scope="col">Prezime</th>
                <th scope="col">Uloga</th>
                <th scope="col">Bodovi</th>
                <th scope="col"></th>
                <th scope="col"></th>
                </tr>
            </thead>
            <tbody >
                <tr v-for="korisnik in korisnici" v-if="(korisnik.uloga == filterUloga || filterUloga == '') 
                                                        && (korisnik.tip == filterTipKupca || filterTipKupca == '')">
                    <td>{{korisnik.korisnickoIme}}</td>
                    <td>{{korisnik.ime}}</td>
                    <td>{{korisnik.prezime}}</td>
                    <td>{{korisnik.uloga}}</td>
                    <td>{{korisnik.brBodova}}</td>
                    <td>
                    <button class="btn btn-sm btn-danger" v-on:click="izabranKorisnik = korisnik"
                        data-bs-toggle="modal" data-bs-target="#brisanjeModal">Obriši</button>
                    </td>
                    <td>
                    <button class="btn btn-sm btn-warning" v-on:click="izabranKorisnik = korisnik"
                        data-bs-toggle="modal" data-bs-target="#blokiranjeModal">Blokiraj</button>
                    </td>
                </tr>
            </tbody>
            </table>
        </div>
        <br>
        <div class="row g-1 align-items-center">
            <div class="col-auto">
            <label for="imePretraga" class="col-form-label">Ime</label>
            </div>
            <div class="col-auto">
            <input type="text" class="form-control" id="imePretraga" v-model="upit.ime">
            </div>
            <div class="col-auto">
            <label for="prezimePretraga" class="col-form-label">Prezime</label>
            </div>
            <div class="col-auto">
            <input type="text" class="form-control" id="prezimePretraga" v-model="upit.prezime">
            </div>
            <div class="col-auto">
            <label for="korisnickoImePretraga" class="col-form-label">Korisničko ime</label>
            </div>
            <div class="col-auto">
            <input type="text" class="form-control" id="korisnickoImePretraga" v-model="upit.korisnickoIme">
            </div>
            <div class="col-auto">
                <label for="datumOd" class="col-form-label">Sortiranje</label>
            </div>
            <div class="col-auto">
                <select id="sort" class="form-control" v-model="upit.sort">
                        <option value="IME_RAST">IME_RAST</option>
                        <option value="IME_OPAD">IME_OPAD</option>
                        <option value="PREZIME_RAST">PREZIME_RAST</option>
                        <option value="PREZIME_OPAD">PREZIME_OPAD</option>
                        <option value="KIME_RAST">KOR_IME_RAST</option>
                        <option value="KIME_OPAD">KOR_IME_OPAD</option>
                        <option value="BODOVI_RAST">BODOVI_RAST</option>
                        <option value="BODOVI_OPAD">BODOVI_OPAD</option>
                </select>
            </div>
            <div class="col-auto">
            <input class="w-150 btn btn-md btn-primary" type="submit" v-on:click="posaljiUpit()" value="Pretraga" />
            </div>
            <div class="col-auto">
                <label for="uloga" class="col-form-label">Uloga</label>
            </div>
            <div class="col-auto">
                <select id="uloga" class="form-control" v-model="filterUloga">
                    <option value=""></option>
                    <option value="ADMINISTRATOR">Administrator</option>
                    <option value="PRODAVAC">Prodavac</option>
                    <option value="KUPAC">Kupac</option>
                </select>
            </div>
            <div class="col-auto">
                <label for="tipKupca" class="col-form-label">Tip kupca</label>
            </div>
            <div class="col-auto">
                <select id="tipKupca" class="form-control" v-model="filterTipKupca">
                    <option value=""></option>
                    <option value="BRONZANI">Bronzani</option>
                    <option value="SREBRNI">Srebrni</option>
                    <option value="ZLATNI">Zlatni</option>
                </select>
            </div>
        </div>


        <div class="modal fade" id="brisanjeModal" tabindex="-1" aria-labelledby="brisanjeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="brisanjeModalLabel">Brisanje</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Da li ste sigurni da želite da izbrišete korisnika {{izabranKorisnik.korisnickoIme}}?</p>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Izlaz</button>
                <button type="button" class="btn btn-primary" v-on:click="obrisiKorisnika()" data-bs-dismiss="modal">Potvrdi</button>
            </div>
            </div>
        </div>
        </div>

        <div class="modal fade" id="blokiranjeModal" tabindex="-1" aria-labelledby="blokiranjeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="blokiranjeModalLabel">Blokiranje</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Da li ste sigurni da želite da blokirate korisnika {{izabranKorisnik.korisnickoIme}}?</p>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Izlaz</button>
                <button type="button" class="btn btn-primary" v-on:click="blokirajKorisnika()" data-bs-dismiss="modal">Potvrdi</button>
            </div>
            </div>
        </div>
        </div>
        
    </div>
    `,
    methods: {
        posaljiUpit(){
            axios.post("/rest/korisnici/pretraga", this.upit).then(response => {
                this.korisnici = response.data;
            });
        },
        obrisiKorisnika(){
            axios.post("/rest/korisnici/brisanje", this.izabranKorisnik).then(response => {
                alert(response.data);
            });
        },
        blokirajKorisnika(){
            if(this.izabranKorisnik.uloga == "ADMINISTRATOR"){
                alert("Nije moguće blokirati administratore");
                return;
            }

            axios.post("/rest/korisnici/blokiranje", this.izabranKorisnik).then(response => {
                alert(response.data);
            });
        }
    },
    created(){
        axios.get("/rest/korisnici/sviKorisnici").then(response => {
            this.korisnici = response.data;
        });
    }
});