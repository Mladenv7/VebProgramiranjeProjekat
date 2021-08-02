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
    </div>
    `,
    methods: {
        posaljiUpit(){
            axios.post("/rest/korisnici/pretraga", this.upit).then(response => {
                this.korisnici = response.data;
            });
        }
    },
    created(){
        axios.get("/rest/korisnici/sviKorisnici").then(response => {
            this.korisnici = response.data;
        });
    }
});