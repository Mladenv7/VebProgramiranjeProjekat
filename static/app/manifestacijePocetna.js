Vue.component("manifestacije-pocetna", {
    data: function () {
        return {
            manifestacije : [],
            izabranaId : "",
        }
    },
    template : `
    <div style="overflow-x: hidden;width: 100%;" align="center">
    <h2 align="center">Manifestacije</h2>
    
    <div style="width: 75%;height: 400px;overflow-y: scroll;overflow-x: hidden;" align="center" >
        <div class="row row-cols-1 row-cols-md-3 g-4" align="center">
            <div class="col" v-for="manifestacija in manifestacije">
                <div class="card" style="width: 300px;">
                    <img v-bind:src="manifestacija.poster" class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">{{manifestacija.naziv}}</h5>
                        <p class="card-text">{{manifestacija.tipManifestacije}}</p>
                        <a class="btn btn-primary" v-on:click="izaberiManifestaciju(manifestacija.id)">Detalji</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    </div>
    
    `,
    methods : {
        izaberiManifestaciju(id){
            this.izabranaId = id;
            console.log(this.izabranaId);
            this.$router.push({ path: '/manifestacija', query: { id: this.izabranaId }});
        }
    },
    mounted(){
        axios.get('/rest/manifestacije/sveManifestacije').then(response => {
            this.manifestacije = response.data;
            console.log(this.manifestacije);
        });
    }
});