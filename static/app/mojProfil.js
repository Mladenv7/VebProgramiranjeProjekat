Vue.component("moj-profil", {
  data: function () {
    return {
      prijavljeniKorisnik: {},
    };
  },
  template: `
    <div class="container" >
    <div class="row" >
		<div class="col-lg-3 col-sm-6">

            <div class="card hovercard" >
                <div class="cardheader">

                </div>
                <div class="avatar">
                    <img alt="" src="./podaci/elementi/korisnik.png">
                </div>
                <div class="info">
                    <div class="title">
                        <div>{{prijavljeniKorisnik.korisnickoIme}}</div>
                    </div>                    
                </div>
              </div>

        </div>

        <div class="col-8">
        
        
        <form style="width: 30%;" class="d-grid gap-2">
            <div class="form-floating">
            
          
            </div>
            <div class="form-floating">
            <input type="text" class="form-control" id="ime" placeholder="Ime" v-model="prijavljeniKorisnik.ime">
            <label for="ime">Ime</label>
            </div>
            <div class="form-floating">
            <input type="text" class="form-control" id="prezime" placeholder="Prezime" v-model="prijavljeniKorisnik.prezime">
            <label for="prezime">Prezime</label>
            </div>
            <div class="form-floating">
            <input type="text" class="form-control" id="lozinka" placeholder="Lozinka" v-model="prijavljeniKorisnik.lozinka">
            <label for="lozinka">Lozinka</label>
            </div>
            <div class="form-floating">
            <select id="pol" class="form-control" v-model="prijavljeniKorisnik.pol">
                            <option value="m">Muški</option>
                            <option value="ž">Ženski</option>
                        </select>
            <label for="pol">Pol</label>
            </div>
            <div class="form-floating">
            <input type="date" class="form-control" id="datumRodjenja" v-model="prijavljeniKorisnik.datumRodjenja"
                v-bind:max="(new Date()).toISOString().substring(0, 10)">
            <label for="datumRodjenja">Datum rođenja</label>
            </div>
            <input class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="azurirajKorisnika()" value="Ažuriranje" />
        </form>
        </div>
   
    </div>
    </div>
    `,
  methods: {
    dobaviPrijavljenog() {
      axios
        .get("/rest/korisnici/jedanKorisnik/" + this.$route.query.korisnickoIme)
        .then((response) => {
          this.prijavljeniKorisnik = response.data;
          this.prijavljeniKorisnik.datumRodjenja = this.formatirajDatum(
            this.prijavljeniKorisnik.datumRodjenja
          );
          if (this.prijavljeniKorisnik.pol == "MUSKI")
            this.prijavljeniKorisnik.pol = "m";
          else if (this.prijavljeniKorisnik.pol == "ZENSKI")
            this.prijavljeniKorisnik.pol = "ž";
        });
    },
    formatirajDatum(datum) {
      return (
        datum.year.toLocaleString("en-US", {
          minimumIntegerDigits: 4,
          useGrouping: false,
        }) +
        "-" +
        datum.month.toLocaleString("en-US", {
          minimumIntegerDigits: 2,
          useGrouping: false,
        }) +
        "-" +
        datum.day.toLocaleString("en-US", {
          minimumIntegerDigits: 2,
          useGrouping: false,
        })
      );
    },
    azurirajKorisnika() {
      if (this.prijavljeniKorisnik.lozinka == "") return;

      if (this.prijavljeniKorisnik.pol == "m")
        this.prijavljeniKorisnik.pol = "MUSKI";
      else if (this.prijavljeniKorisnik.pol == "ž")
        this.prijavljeniKorisnik.pol = "ZENSKI";

      console.log(this.prijavljeniKorisnik);

      axios
        .post("/rest/korisnici/azuriranjeKorisnika", this.prijavljeniKorisnik)
        .then((response) => {
          alert(response.data);
        });
    },
  },
  created() {
    this.dobaviPrijavljenog();
  },
});
