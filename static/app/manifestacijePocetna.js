Vue.component("manifestacije-pocetna", {
    data: function () {
        return {
            manifestacije : [],
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
                        <a href="#" class="btn btn-primary">Detalji</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    </div>
    
    `,
    methods : {

    },
    mounted(){
        axios.get('/rest/manifestacije/sveManifestacije').then(response => {
            this.manifestacije = response.data;
            console.log(this.manifestacije);
        });
    }
});