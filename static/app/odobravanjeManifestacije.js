Vue.component("odobravanje-manifestacije", {
    data: function(){
        return {
            manifestacije : [],
            izabranaId : "",
        }
    },
    template: `
    <div class="container">
    <h3>Neaktivne manifestacije</h3>
    <br>
    <div style="height: 500px;overflow-y: scroll;overflow-x: hidden;">
        <div class="card" v-for="manifestacija in manifestacije">
            <div class="card-header">
                {{manifestacija.naziv}}
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-4" style="width: 80%;">
                        <h5 class="card-title">{{formatirajDatum(manifestacija.vremeOdrzavanja)}}</h5>
                        <p class="card-text">
                            {{manifestacija.tipManifestacije}}<br>
                            {{manifestacija.lokacija.ulicaBroj+" "+manifestacija.lokacija.grad+" "+manifestacija.lokacija.postanskiBroj}}<br>
                            Cena regularne karte: {{manifestacija.cenaRegular}}
                            
                        </p>
                        <a href="#" class="btn btn-secondary" v-on:click="pregledManifestacije(manifestacija.id)">Detalji</a>
                        <a href="#" class="btn btn-success" v-on:click="odobri(manifestacija)">Odobri</a>
                    </div>
                    <div class="col">
                        <img v-bind:src="manifestacija.poster" class="card-img-top" style="max-width:200px;max-height:200px;">
                    </div>
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
        pregledManifestacije(id){
            this.izabranaId = id;
            console.log(this.izabranaId);
            this.$router.push({ path: '/manifestacija', query: { id: this.izabranaId }});
        },
        odobri(manifestacija){
            manifestacija.status = true;
            axios.post("/rest/manifestacije/aktivacija", manifestacija.id);
            this.$router.go();
        }
    },
    created(){
        axios.get("/rest/manifestacije/neaktivneManifestacije").then(response => {
            this.manifestacije = response.data;
            console.log(this.manifestacije);
        });
    },
});