Vue.component("pregled-manifestacije", {
  data: function () {
    return {
      podaci: { manifestacija: { naziv: "" }, komentari: [] },
      prijavljenKorisnik: {},
      tipKupca: "",
      karta: {
        imePrezime: "",
        manifestacijaId: "",
        cena: 0,
        vremeOdrzavanja: "",
      },
      kolicine: { regular: 0, fanPit: 0, vip: 0 },
      popust: 1,
      preostaloKarata: 0,
      komentar: {
        idManifestacije: "",
        autor: "",
        tekst: "",
        ocena: 1,
        aktivan: false,
        obrisan: false,
      },
      karteKorisnika: [],
    };
  },
  template: `
    <div class="container">
        <h2>{{podaci.manifestacija.naziv}}</h2>
    <div class="container d-inline-flex p-2">
        
        <div style="width: 80%;">
        <p class="fs-5 col-md-8">
            {{podaci.manifestacija.tipManifestacije}}<br>
            Vreme održavanja: {{formatirajDatum(podaci.manifestacija.vremeOdrzavanja)}}<br>
            Broj mesta: {{podaci.manifestacija.brojMesta}}<br>
            Preostalo karata: {{preostaloKarata}}<br>
            Status: {{pretvoriStatus(podaci.manifestacija.status)}}<br>
            
        </p>
        <p class="fs-5 col-md-8" v-if="jelProsla() && podaci.komentari.length > 0">Prosečna ocena: {{prosecnaOcena().toFixed(2)}}</p>
        <button class="btn btn-sm btn-danger" v-if="prijavljenKorisnik && prijavljenKorisnik.uloga == 'ADMINISTRATOR' && !podaci.manifestacija.obrisana"
                        data-bs-toggle="modal" data-bs-target="#brisanjeModal">Obriši</button>
        </div>

        <img v-bind:src="podaci.manifestacija.poster" style="width: 100%; height: 100%;max-width:300px;max-height:300px;" v-if="podaci.manifestacija.poster">
       

    </div>


 <div class="d-flex justify-content-between">
    <div id="lokacijaDiv" style="width: 50%;">
        <h4>Lokacija</h4>
        <hr>
        <p class="fs-5 col-md-8">
            Ulica i broj: {{podaci.manifestacija.lokacija.ulicaBroj}}<br>
            Grad: {{podaci.manifestacija.lokacija.grad}}<br>
            Poštanski broj: {{podaci.manifestacija.lokacija.postanskiBroj}}<br>
        </p>       
    </div>

    <mapa id="map" v-bind:lat=podaci.manifestacija.lokacija.geoSirina v-bind:lng=podaci.manifestacija.lokacija.geoDuzina ></mapa>
</div>
    
    <div id="ceneDiv" style="width: 50%;">
        <h4>Cene</h4>
        <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">Regularna</th>
                <th scope="col">Fan pit</th>
                <th scope="col">Vip</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>{{podaci.manifestacija.cenaRegular}}</td>
                <td>{{podaci.manifestacija.cenaRegular*2}}</td>
                <td>{{podaci.manifestacija.cenaRegular*4}}</td>
            </tr>
        </tbody>
        </table>
    </div>

    <div id="rezervacijaDiv" style="width: 80%;" v-if="jelAktivna() && jelKupac() && preostaloKarata > 0">
    <div class="row g-2 align-items-center">
            <div class="col-3">
            <input type="number" class="form-control" id="kolicinaRegular" v-bind:min="0" v-model="kolicine.regular">
            </div>
            <div class="col-3">
            <input type="number" class="form-control" id="kolicinaRegular" v-bind:min="0" v-model="kolicine.fanPit">
            </div>
            <div class="col-3">
            <input type="number" class="form-control" id="kolicinaRegular" v-bind:min="0" v-model="kolicine.vip">
            </div>
            <div class="col-3">
            <input class="w-150 btn btn-md btn-primary" type="submit" v-on:click="pregledKarte()"
            data-bs-toggle="modal" data-bs-target="#kartaModal" value="Rezervacija" />
            </div>
        </div>
    </div>

    <div class="modal fade" id="kartaModal" tabindex="-1" aria-labelledby="kartaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="kartaModalLabel">Pregled rezervacije</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <p>
            Kupac: {{postaviImePrezime()}}<br>
            Tip kupca: {{tipKupca}}<br>
            Naručeno karata: <br>
            Regularnih x {{kolicine.regular}}<br>
            Fan pit x {{kolicine.fanPit}}<br>
            VIP x {{kolicine.vip}}<br>
            Ukupna cena = {{((kolicine.regular*karta.cena+kolicine.fanPit*karta.cena*2+kolicine.vip*karta.cena*4)*popust).toFixed(2)}}
            </p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Izlaz</button>
            <button type="button" class="btn btn-primary" v-on:click="posaljiRezervaciju()" data-bs-dismiss="modal">Potvrdi</button>
        </div>
        </div>
    </div>
    </div>


    <h4>Komentari</h4>
    <hr>
    <div id="komentariDiv" style="overflow-y: scroll; height: 200px;">   
        <div class="card p-3 mt-2" v-for="komentar in podaci.komentari" v-if="komentar.aktivan && !komentar.obrisan">
            <div class="d-flex justify-content-between align-items-center">
                <div class="user d-flex flex-row align-items-center"><span><small class="font-weight-bold text-primary">{{komentar.autor}}</small> <small class="font-weight-bold">{{komentar.tekst}}</small></span> </div> <small>Ocena: {{komentar.ocena}}</small>
            </div>
            <div class="action d-flex justify-content-between mt-2 align-items-center">
                <div class="reply px-4"> </div>
                <div class="icons align-items-center"> <i class="fa fa-user-plus text-muted"></i> <i class="fa fa-star-o text-muted"></i> <i class="fa fa-check-circle-o check-icon text-primary"></i> </div>
            </div>
        </div>
    </div>
    <br>
        <button class="btn btn-secondary btn-sm" v-if="jelProsla() && jelDosao()" data-bs-toggle="modal" data-bs-target="#komentarModal">Ostavite komentar</button>
    <br>
    <br>

    <div class="modal fade" id="komentarModal" tabindex="-1" aria-labelledby="komentarModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="komentarModalLabel">Komentar na {{podaci.manifestacija.naziv}}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-auto">
                    <textarea type="textarea" rows="4" cols="63" v-model="komentar.tekst"></textarea>
                </div>
            </div>
            <div class="row">
            <div class="col-auto">
                <p>Ocena</p>
            </div>
            <div class="col-auto">
                <input type="number" v-bind:min="1" v-bind:max="5" v-model="komentar.ocena">
            </div>
        </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Izlaz</button>
            <button type="button" class="btn btn-primary" v-on:click="posaljiKomentar()" data-bs-dismiss="modal">Potvrdi</button>
        </div>
        </div>
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
                <p>Da li ste sigurni da želite da izbrišete manifestaciju {{podaci.manifestacija.naziv}}?</p>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Izlaz</button>
                <button type="button" class="btn btn-primary" v-on:click="obrisiManifestaciju()" data-bs-dismiss="modal">Potvrdi</button>
            </div>
            </div>
        </div>
        </div>

    </div>
    `,
  methods: {
    postaviKupca() {
      this.prijavljenKorisnik = JSON.parse(localStorage.getItem("prijavljeni"));
      this.tipKupca = this.postaviTip();

      if (this.prijavljenKorisnik) {
        axios
          .get(
            "/rest/karte/rezervacije/" + this.prijavljenKorisnik.korisnickoIme
          )
          .then((response) => {
            this.karteKorisnika = response.data;
          });
      }
    },
    manifestacijaSaKomentarima() {
      axios
        .get("/rest/komentari/manifestacijaKomentari/" + this.$route.query.id)
        .then((response) => {
          this.podaci = response.data;
          console.log(this.podaci);
          this.dobaviPreostaloKarata();
        });
    },
    pretvoriStatus(status) {
      if (status) return "Aktivna";
      if (!status) return "Neaktivna";
    },
    formatirajDatum(vreme) {
      if (!vreme) return;
      return (
        vreme.date.day +
        "." +
        vreme.date.month +
        "." +
        vreme.date.year +
        ". - " +
        vreme.time.hour +
        ":" +
        ("0" + vreme.time.minute).slice(-2)
      );
    },
    prosecnaOcena() {
      let prosecna = 0.0;
      let brKomentara = 0;

      for (let komentar of this.podaci.komentari) {
        if (komentar.aktivan == false || komentar.obrisan == true) continue;
        prosecna += komentar.ocena;
        brKomentara += 1;
      }

      prosecna = prosecna /= brKomentara;

      return prosecna;
    },
    jelProsla() {
      if (!this.podaci.manifestacija.vremeOdrzavanja) return;
      var vremeOdrzavanja = new Date(
        this.podaci.manifestacija.vremeOdrzavanja.date.year,
        this.podaci.manifestacija.vremeOdrzavanja.date.month - 1,
        this.podaci.manifestacija.vremeOdrzavanja.date.day,
        this.podaci.manifestacija.vremeOdrzavanja.time.hour,
        this.podaci.manifestacija.vremeOdrzavanja.time.minute,
        this.podaci.manifestacija.vremeOdrzavanja.time.second,
        this.podaci.manifestacija.vremeOdrzavanja.time.nano / 1000
      );

      return vremeOdrzavanja < new Date();
    },
    pregledKarte() {
      this.karta.imePrezime =
        this.prijavljenKorisnik.ime + " " + this.prijavljenKorisnik.prezime;
      this.karta.cena = this.podaci.manifestacija.cenaRegular;
      this.karta.manifestacijaId = this.podaci.manifestacija.id;
      this.karta.vremeOdrzavanja =
        this.podaci.manifestacija.vremeOdrzavanja.date.year +
        "-" +
        ("0" + this.podaci.manifestacija.vremeOdrzavanja.date.month).slice(-2) +
        "-" +
        ("0" + this.podaci.manifestacija.vremeOdrzavanja.date.day).slice(-2) +
        "T" +
        ("0" + this.podaci.manifestacija.vremeOdrzavanja.time.hour).slice(-2) +
        ":" +
        ("0" + this.podaci.manifestacija.vremeOdrzavanja.time.minute).slice(-2);

      console.log(this.karta);
    },
    posaljiRezervaciju() {
      if (
        this.kolicine.regular + this.kolicine.fanPit + this.kolicine.vip ==
        0
      ) {
        alert("Morate uneti neku količinu karata");
        return;
      } else if (
        this.kolicine.regular + this.kolicine.fanPit + this.kolicine.vip >
        this.preostaloKarata
      ) {
        alert("Nije preostalo dovoljno karata");
        this.kolicine.regular = 0;
        this.kolicine.fanPit = 0;
        this.kolicine.vip = 0;
        return;
      } else {
        this.karta.cena *= this.popust;

        let rezervacija = {
          imePrezime: this.karta.imePrezime,
          manifestacijaId: this.karta.manifestacijaId,
          vremeOdrzavanja: this.karta.vremeOdrzavanja,
          korisnickoIme: this.prijavljenKorisnik.korisnickoIme,
          cenaRegular: this.karta.cena,
          kolicine: [
            this.kolicine.regular,
            this.kolicine.fanPit,
            this.kolicine.vip,
          ],
        };

        axios.post("/rest/karte/rezervacija", rezervacija).then((response) => {
          alert(response.data);
          this.kolicine.regular = 0;
          this.kolicine.fanPit = 0;
          this.kolicine.vip = 0;
          this.$router.go();
        });
      }
    },
    jelKupac() {
      if (this.prijavljenKorisnik && this.prijavljenKorisnik.uloga == "KUPAC")
        return true;
      else return false;
    },
    jelAktivna() {
      if (this.podaci.manifestacija && this.podaci.manifestacija.status)
        return true;
      else return false;
    },
    jelRasprodata() {
      return true;
    },
    postaviImePrezime() {
      if (this.prijavljenKorisnik)
        return (
          this.prijavljenKorisnik.ime + " " + this.prijavljenKorisnik.prezime
        );
      else return;
    },
    postaviTip() {
      this.popust = 1;

      if (this.prijavljenKorisnik) {
        if (this.prijavljenKorisnik.tip == "BRONZANI") {
          this.popust -= 0.02;
        } else if (this.prijavljenKorisnik.tip == "SREBRNI") {
          this.popust -= 0.03;
        } else if (this.prijavljenKorisnik.tip == "ZLATNI") {
          this.popust -= 0.07;
        } else {
          return "Standardni";
        }

        return (
          this.prijavljenKorisnik.tip +
          " (popust od " +
          Math.round((1 - this.popust) * 100) +
          " posto)"
        );
      } else return "Standardni";
    },
    dobaviPreostaloKarata() {
      axios
        .get("rest/karte/karteOdManifestacije/" + this.podaci.manifestacija.id)
        .then((response) => {
          this.preostaloKarata =
            this.podaci.manifestacija.brojMesta - response.data.length;
        });
    },
    posaljiKomentar() {
      this.komentar.autor = this.prijavljenKorisnik.korisnickoIme;
      this.komentar.idManifestacije = this.podaci.manifestacija.id;

      axios.post("/rest/komentari/novi", this.komentar).then((response) => {
        alert(response.data);
        this.podaci.komentari.push(this.komentar);
      });
    },
    jelDosao() {
      for (karta of this.karteKorisnika) {
        if (karta.manifestacijaId == this.podaci.manifestacija.id) return true;
      }

      return false;
    },
    obrisiManifestaciju() {
      let zaObrisati = {
        naziv: "",
        tipManifestacije: "",
        id: this.podaci.manifestacija.id,
        brojMesta: 0,
        vremeOdrzavanja: null,
        cenaRegular: 0,
        status: this.podaci.manifestacija.status,
        lokacija: null,
        poster: null,
        obrisana: false,
      };

      axios
        .post("/rest/manifestacije/brisanjeManifestacije", zaObrisati)
        .then((response) => {
          alert(response.data);
          this.$router.push("/");
        });
    },
  },
  mounted() {
    this.postaviKupca();
    this.manifestacijaSaKomentarima();
  },
});
