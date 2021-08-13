Vue.component("svi-komentari", {
    data: function(){
        return {
            prijavljenKorisnik: {},
            komentari: [],
            samoNeodobreni: false,
            sveManifestacije: {},
        }
    },
    template: `
    <div class="container">
    <h3>Svi komentari</h3>

    <div class="row" v-if="prijavljenKorisnik.uloga == 'PRODAVAC'">
        <div class="col-auto">
        <label class="form-check-label" for="flexCheckDefault">
          Samo neodobreni komentari 
        </label>
            <input class="form-check-input" type="checkbox" v-model="samoNeodobreni" id="flexCheckDefault">
        </div>
    </div>
    
    <div id="komentariDiv" style="overflow-y: scroll; height: 500px;">   
        <div class="card p-3 mt-2" v-for="komentar in komentari" v-if="!komentar.obrisan && filterNeodobreni(komentar.aktivan)">
            <div class="d-flex justify-content-between align-items-center">
                <div class="user d-flex flex-row align-items-center"><span><small class="font-weight-bold text-primary">{{komentar.autor}}</small> <small class="font-weight-bold">{{komentar.tekst}}</small></span> </div> <small>Ocena: {{komentar.ocena}}</small>
            </div>
            <div class="action d-flex justify-content-between mt-2 align-items-center">
                <div class="row">
                <p>na manifestaciju "{{sveManifestacije[komentar.idManifestacije].naziv}}"</p>
                </div>
                <div class="row g-1" v-if="!komentar.aktivan && prijavljenKorisnik.uloga == 'PRODAVAC'" >
                
                    <div class="col-auto">
                    <button class="btn btn-success" v-on:click="odobriKomentar(komentar)">Odobri</button>
                    </div>
                    <div class="col-auto">
                    <button class="btn btn-danger" v-on:click="odbijKomentar(komentar)">Odbij</button>
                    </div>
                    
                </div>
                
                <div class="row g-1" v-if="prijavljenKorisnik.uloga == 'ADMINISTRATOR'">
                
                    <div class="col-auto">
                    <button class="btn btn-danger" v-on:click="obrisiKomentar(komentar)">Obriši</button>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
    <br>

    </div>
    `,
    methods: {
        postaviKomentareProdavac(){
            axios.get("/rest/manifestacije/sveManifestacije").then(response => {
                this.sveManifestacije = response.data;
            });

            axios.get("/rest/komentari/sviKomentari").then(response => {
                for(komentar of response.data){
                    if(this.prijavljenKorisnik.sveManifest.indexOf(komentar.idManifestacije) != -1) this.komentari.push(komentar);
                }
            });

            
        },
        postaviKomentareAdmin(){
            axios.get("/rest/manifestacije/sveManifestacije").then(response => {
                this.sveManifestacije = response.data;
            });

            axios.get("/rest/komentari/sviKomentari").then(response => {
                this.komentari = response.data;
            });
        },
        filterNeodobreni(odobreni){
            if(this.samoNeodobreni == false) return true;
            else return this.samoNeodobreni == !odobreni;
        },
        odobriKomentar(komentar){
            axios.post("/rest/komentari/odobravanje", komentar).then(response => {
                alert(response.data);
                komentar.aktivan = true;
            });
        },
        odbijKomentar(komentar){
            axios.post("/rest/komentari/brisanje", komentar).then(response => {
                alert("Komentar uspešno odbijen");
                komentar.obrisan = true;
            });
        },
        obrisiKomentar(komentar){
            axios.post("/rest/komentari/brisanje", komentar).then(response => {
                alert(response.data);
                komentar.obrisan = true;
            });
        }
    },
    created(){
        this.prijavljenKorisnik = JSON.parse(localStorage.getItem("prijavljeni"));
        if (this.prijavljenKorisnik.uloga == "PRODAVAC") this.postaviKomentareProdavac();
        else if (this.prijavljenKorisnik.uloga == "ADMINISTRATOR") this.postaviKomentareAdmin();
    },
});