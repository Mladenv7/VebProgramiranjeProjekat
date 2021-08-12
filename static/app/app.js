const ManifestacijePocetna = { template: "<manifestacije-pocetna></manifestacije-pocetna>", };
const Navbar = { template: "<navbar></navbar>" };
const RegistracijaKorisnika = { template: "<registracija-korisnik></registracija-korisnik>", };
const Prijava = { template: "<prijava></prijava>" };
const Manifestacija = { template: "<pregled-manifestacije></pregled-manifestacije>", };
const ManfiestacijePretraga = { template: "<manifestacije-pretraga></manifestacije-pretraga>", };
const SviKorisnici = { template: "<svi-korisnici></svi-korisnici>" };
const MojProfil = { template: "<moj-profil></moj-profil>" };
const ManifestacijaDodaj = { template: "<manifestacije-dodaj></manifestacije-dodaj>", };
const MojeManifestacije = { template: "<manifestacije-licne></manifestacije-licne>", };
const AzurirajManifestacije = { template: "<manifestacija-azuriraj></manifestacija-azuriraj>", };
const OdobravanjeManifestacije = { template: '<odobravanje-manifestacije></odobravanje-manifestacije>' }
const KarteKorisnika = { template: '<karte-korisnika></karte-korisnika>' }
const KarteAdmin = { template: '<karte-admin></karte-admin>'}
const KarteProdavac = { template: '<karte-prodavac></karte-prodavac>' }
const RegistracijaProdavca = { template: '<registracija-prodavac></registracija-prodavac>' } 
const SviKomentari = { template: '<svi-komentari></svi-komentari>'}

const router = new VueRouter({
  mode: "hash",
  routes: [
    { path: "/", component: ManifestacijePocetna },
    { path: "/navbar", component: Navbar },
    { path: "/registracija", component: RegistracijaKorisnika },
    { path: "/prijava", component: Prijava },
    { path: "/manifestacija", component: Manifestacija },
    { path: "/manifestacijePretraga", component: ManfiestacijePretraga },
    { path: "/sviKorisnici", component: SviKorisnici },
    { path: "/profil", component: MojProfil },
    { path: "/manifestacijaDodaj", component: ManifestacijaDodaj },
    { path: "/manifestacijaAzuriraj", component: AzurirajManifestacije },
    { path: "/mojeManifestacije", component: MojeManifestacije },
    { path: '/odobravanje', component: OdobravanjeManifestacije },
    { path: '/mojeRezervacije', component: KarteKorisnika },
    { path: '/sveKarte', component: KarteAdmin },
    { path: '/sveRezervisaneKarte', component: KarteProdavac },
    { path: '/noviProdavac', component: RegistracijaProdavca },
    { path: '/sviKomentari', component: SviKomentari }
  ],
});

var app = new Vue({
  router,
  el: "#registracijaKarata",
});
