Vue.component("navbar", {
    data: function() {
        return {
            prijavljeni : {uloga: ""},
        }
    },
    template: `
    <div>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" id="navBezKorisnika" 
        v-if="!this.jeLiPrijavljen() || this.prijavljeni == 'Ovaj korisnik ne postoji' || this.prijavljeni == 'Pogrešna lozinka'">
    <div class="container-fluid">
        <a class="navbar-brand" href="#/">Rezervacija karata</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
            <a class="nav-link" href="#/">Početna</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/registracija">Registracija</a>
            </li>
        </ul>
        
            <a class="btn btn-outline-primary" href="#/prijava">Prijava</a>
        
        </div>
    </div>
    </nav>

    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" id="navAdmin" v-if="this.prijavljeni.uloga == 'ADMINISTRATOR'">
    <div class="container-fluid">
        <a class="navbar-brand" href="#/">Rezervacija karata</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
            <a class="nav-link" href="#/">Početna</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/sviKorisnici">Svi korisnici</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Sve karte</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Svi komentari</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/odobravanje">Nove manifestacije</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Novi prodavac</a>
            </li>
        </ul>
        
            <a class="nav-link" href="#/" v-on:click="naProfil()">Moj nalog</a>
            <a class="btn btn-outline-primary" v-on:click="odjava()">Odjava</a>
        
        </div>
    </div>
    </nav>

    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" id="navProdavac" v-if="this.prijavljeni.uloga == 'PRODAVAC'">
    <div class="container-fluid">
        <a class="navbar-brand" href="#/">Rezervacija karata</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
            <a class="nav-link" href="#/">Početna</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/mojeManifestacije">Moje manifestacije</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/manifestacijaDodaj">Nova manifestacija</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Rezervisane karte</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Svi komentari</a>
            </li>
        </ul>
        
            <a class="nav-link" href="#/" v-on:click="naProfil()">Moj nalog</a>
            <a class="btn btn-outline-primary" v-on:click="odjava()">Odjava</a>
        
        </div>
    </div>
    </nav>

    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" id="navKupac" v-if="this.prijavljeni.uloga == 'KUPAC'">
    <div class="container-fluid">
        <a class="navbar-brand" href="#/">Rezervacija karata</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
            <a class="nav-link" href="#/">Početna</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/mojeRezervacije">Moje karte</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="#/">Komentar</a>
            </li>
        </ul>
        
            <a class="nav-link" href="#/" v-on:click="naProfil()">Moj nalog</a>
            <a class="btn btn-outline-primary" v-on:click="odjava()">Odjava</a>
        
        </div>
    </div>
    </nav>


    </div>
    `,
    methods: {
        jeLiPrijavljen(){
            console.log(JSON.parse(localStorage.getItem("prijavljeni")));
            return JSON.parse(localStorage.getItem("prijavljeni"));
        },
        odjava(){
            localStorage.removeItem("prijavljeni");
            this.prijavljeni = {uloga: "" };
            this.$router.push("/");
            this.$router.go();
        },
        naProfil(){
            this.$router.push({path: "/profil", query: this.prijavljeni});
            this.$router.go();
        }
    },
    mounted(){
        this.prijavljeni = this.jeLiPrijavljen();
    }
});