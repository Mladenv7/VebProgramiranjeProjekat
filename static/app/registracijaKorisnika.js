Vue.component("registracija-korisnik", {
    data: function() {
        return {
            noviKorisnik : {
                korisnickoIme : "",
                lozinka : "",
                ime : "", 
                prezime : "",
                pol : "",
                datumRodjenja : ""
            }
        }
    },
    computed: {
        routeName() {
          return this.$route.name;
        },
    },
    template: `
    <div align="center" class="container">
    <h3>Unesite vaše podatke</h3>
    <br>
    <form style="width: 30%;" class="d-grid gap-2">
        <div class="form-floating">
        <input type="text" class="form-control" id="kIme" placeholder="Korisničko ime" oninvalid="this.setCustomValidity('Morate uneti korisničko ime')"
            oninput="this.setCustomValidity('')" v-model="noviKorisnik.korisnickoIme" required>
        <label for="kIme">Korisničko ime</label>
        </div>
        <div class="form-floating">
        <input type="text" class="form-control" id="ime" placeholder="Ime" v-model="noviKorisnik.ime">
        <label for="ime">Ime</label>
        </div>
        <div class="form-floating">
        <input type="text" class="form-control" id="prezime" placeholder="Prezime" v-model="noviKorisnik.prezime">
        <label for="prezime">Prezime</label>
        </div>
        <div class="form-floating">
        <input type="password" class="form-control" id="lozinka" placeholder="Lozinka" oninvalid="this.setCustomValidity('Morate uneti lozinku')"
            oninput="this.setCustomValidity('')" v-model="noviKorisnik.lozinka" required>
        <label for="lozinka">Lozinka</label>
        </div>
        <div class="form-floating">
        <select id="pol" class="form-control" v-model="noviKorisnik.pol">
                        <option value="m">Muški</option>
                        <option value="ž">Ženski</option>
                    </select>
        <label for="pol">Pol</label>
        </div>
        <div class="form-floating">
        <input type="date" class="form-control" id="datumRodjenja" v-model="noviKorisnik.datumRodjenja"
            v-bind:max="(new Date()).toISOString().substring(0, 10)">
        <label for="datumRodjenja">Datum rođenja</label>
        </div>
        <input class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="posaljiKorisnika()" value="Registracija" />
    </form>

    </div>
    `,
    methods: {
        posaljiKorisnika(){
            if(this.noviKorisnik.korisnickoIme == '' || this.noviKorisnik.lozinka == ''){
                return;
            }
            axios
			.post('/rest/korisnici/registracijaKorisnika', this.noviKorisnik);
        },
    },
});