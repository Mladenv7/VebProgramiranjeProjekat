Vue.component("pregled-manifestacije", {
    data: function(){ 
        return {
            podaci : {manifestacija : {naziv: ""}, komentari: [] } //sa komentarima
        }
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
            Status: {{pretvoriStatus(podaci.manifestacija.status)}}<br>
            
        </p>
        <p class="fs-5 col-md-8" v-if="jelProsla() && podaci.komentari.length > 0">Prosečna ocena: {{prosecnaOcena().toFixed(2)}}</p>
        </div>

        <img v-bind:src="podaci.manifestacija.poster" style="width: 100%; height: 100%;max-width:300px;max-height:300px;" v-if="podaci.manifestacija.poster">
        

    </div>

    <div id="lokacijaDiv" style="width: 60%;">
        <h4>Lokacija</h4>
        <hr>
        <p>Ulica/broj<br>Grad<br>Poštanski broj<br> </p>
    </div>
    
    <div id="ceneDiv" style="width: 60%;">
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

    <h4>Komentari</h4>
    <hr>
    <div id="komentariDiv" style="overflow-y: scroll; height: 200px;">   
        <div class="card p-3 mt-2" v-for="komentar in podaci.komentari">
            <div class="d-flex justify-content-between align-items-center">
                <div class="user d-flex flex-row align-items-center"><span><small class="font-weight-bold text-primary">{{komentar.autor}}</small> <small class="font-weight-bold">{{komentar.tekst}}</small></span> </div> <small>Ocena: {{komentar.ocena}}</small>
            </div>
            <div class="action d-flex justify-content-between mt-2 align-items-center">
                <div class="reply px-4"> </div>
                <div class="icons align-items-center"> <i class="fa fa-user-plus text-muted"></i> <i class="fa fa-star-o text-muted"></i> <i class="fa fa-check-circle-o check-icon text-primary"></i> </div>
            </div>
        </div>
    </div>

    </div>
    `,
    methods: {
        manifestacijaSaKomentarima(){
            axios
            .get("/rest/manifestacije/manifestacijaKomentari/"+this.$route.query.id).then(response => {
                this.podaci = response.data;
                console.log(this.podaci);
            });
        },
        pretvoriStatus(status){
            if(status) return "Aktivna";
            if(!status) return "Neaktivna";
        },
        formatirajDatum(vreme){
            return vreme.date.day+"."+vreme.date.month+"."+vreme.date.year+". - "+vreme.time.hour+":"+vreme.time.minute;
        },
        prosecnaOcena(){
            let prosecna = 0.0;

            for(let komentar of this.podaci.komentari){
                prosecna += komentar.ocena;
            }

            prosecna = prosecna /= this.podaci.komentari.length;

            return prosecna;
        },
        jelProsla(){
            var vremeOdrzavanja = new Date(this.podaci.manifestacija.vremeOdrzavanja.date.year,
                                           this.podaci.manifestacija.vremeOdrzavanja.date.month, 
                                           this.podaci.manifestacija.vremeOdrzavanja.date.day, 
                                           this.podaci.manifestacija.vremeOdrzavanja.time.hour, 
                                           this.podaci.manifestacija.vremeOdrzavanja.time.minute, 
                                           this.podaci.manifestacija.vremeOdrzavanja.time.second, 
                                           this.podaci.manifestacija.vremeOdrzavanja.time.nano/1000);
            return vremeOdrzavanja < new Date();
        }
    },
    mounted(){
        this.manifestacijaSaKomentarima();
    }
});