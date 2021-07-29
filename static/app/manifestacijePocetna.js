Vue.component("manifestacije-pocetna", {
    data: function () {
        return {
            manifestacije : [],
            izabranaId : "",
            upit : {naziv: "", lokacija: "", cenaOd: 0, cenaDo: 0, datumOd: {}, datumDo: {}},
        }
    },
    template : `
    <div style="overflow-x: hidden;width: 100%;" align="center">
    <h2 align="center">Manifestacije</h2>
    <br>
    <div style="width: 75%;height: 500px;overflow-y: scroll;overflow-x: hidden;" align="center" >
        <div class="row row-cols-1 row-cols-md-3 g-4" align="center">
            <div class="col" v-for="manifestacija in manifestacije">
                <div class="card" style="width: 300px;">
                    <img v-bind:src="manifestacija.poster" class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">{{manifestacija.naziv}}</h5>
                        <p class="card-text">{{manifestacija.tipManifestacije}}</p>
                        <a class="btn btn-secondary" v-on:click="izaberiManifestaciju(manifestacija.id)">Detalji</a>
                    </div>
                </div>
            </div>
        </div>
    </div><br>

    
    
    <div class="container">
        <hr>

        <div class="row g-4 align-items-center">
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
            <input class="w-150 btn btn-md btn-primary" type="submit" v-on:click="posaljiUpit()" value="Pretraga" />
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
        </div><br>
    </div>


    </div>
    `,
    methods : {
        izaberiManifestaciju(id){
            this.izabranaId = id;
            console.log(this.izabranaId);
            this.$router.push({ path: '/manifestacija', query: { id: this.izabranaId }});
        },
        posaljiUpit(){
            console.log(this.upit);
        }
    },
    mounted(){
        axios.get('/rest/manifestacije/sveManifestacije').then(response => {
            this.manifestacije = response.data;
            console.log(this.manifestacije);
        });
    }
});