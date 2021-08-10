Vue.component("manifestacija-azuriraj", {
  data: function () {
    return {
      novaManifestacija: {
        id: "",
        prodavac: "",
        naziv: "",
        tipManifestacije: "",
        brojMesta: "",
        vremeOdrzavanja: {
          date: { month: 6, day: 12, year: 2020 },
          time: { hour: 8, minute: 5 },
        },
        cenaRegular: "",
        status: false,
        lokacija: {},
        poster: "",
        obrisana: false,
      },
      jsonPodaci: {},
    };
  },
  template: `
    
   <div align="center" class="container">
    <h3>Azuriraj manifestaciju</h3>
    <br>
    <form style="width: 30%;" class="d-grid gap-2">
        <div class="form-floating">
        <input type="text" class="form-control" id="naziv" placeholder="Naziv" oninvalid="this.setCustomValidity('Morate uneti naziv manifestacije')"
            oninput="this.setCustomValidity('')" v-model="novaManifestacija.naziv" required>
        <label for="naziv">Naziv manifestacije</label>
        </div>
        <div class="form-floating">
        <input type="text" class="form-control" id="tipManifestacije" placeholder="Tip manifestacije" v-model="novaManifestacija.tipManifestacije">
        <label for="tipManifestacije">Tip manifestacije</label>
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
        <input type="datetime-local" class="form-control" id="vremeOdrzavanja" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
            v-bind:min="(new Date()).toISOString().substring(0, 10)">
        <label for="vremeOdrzavanja">Vreme održavanja</label>
        </div>
        <input class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="posaljiManifestaciju()" value="Azuriraj manifestaciju" />
    </form>

    </div>
   
    `,
  methods: {
    podaci() {
      axios
        .get("/rest/manifestacijaAzuriraj/" + this.$route.query.id)
        .then((response) => {
          this.jsonPodaci = response.data;
          this.novaManifestacija.id = this.$route.query.id;
          this.novaManifestacija.naziv = this.jsonPodaci.naziv;
          this.novaManifestacija.tipManifestacije = this.jsonPodaci.tip;
          this.novaManifestacija.brojMesta = this.jsonPodaci.brojMesta;
          this.novaManifestacija.cenaRegular = this.jsonPodaci.cenaRegular;
          if (parseInt(this.jsonPodaci.vremeOdrzavanja.date.month) < 10) {
            this.jsonPodaci.vremeOdrzavanja.date.month =
              "0" + this.jsonPodaci.vremeOdrzavanja.date.month;
          }
          if (parseInt(this.jsonPodaci.vremeOdrzavanja.date.day) < 10) {
            this.jsonPodaci.vremeOdrzavanja.date.day =
              "0" + this.jsonPodaci.vremeOdrzavanja.date.day;
          }
          document.getElementById("vremeOdrzavanja").defaultValue =
            this.jsonPodaci.vremeOdrzavanja.date.year +
            "-" +
            this.jsonPodaci.vremeOdrzavanja.date.month +
            "-" +
            this.jsonPodaci.vremeOdrzavanja.date.day +
            "T" +
            this.jsonPodaci.vremeOdrzavanja.time.hour +
            ":" +
            this.jsonPodaci.vremeOdrzavanja.time.minute;
          //ne moze se postaviti za file defaultna vrijednost zbog security razloga
        });
    },
    posaljiManifestaciju() {
      if (poster.value != "") {
        this.novaManifestacija.poster =
          "./podaci/posteri/" + poster.value.split("\\")[2];
      }
      this.novaManifestacija.vremeOdrzavanja = vremeOdrzavanja.value;

      this.novaManifestacija.naziv = naziv.value;
      this.novaManifestacija.tipManifestacije = tipManifestacije.value;
      this.novaManifestacija.brojMesta = brojMesta.value;
      this.novaManifestacija.cenaRegular = cenaRegular.value;
      axios
        .post(
          "/rest/manifestacije/azuriranjeManifestacije",
          this.novaManifestacija
        )
        .then((response) => {
          alert(response.data);
        });
    },
  },
  mounted() {
    this.podaci();
  },
});
