const ManifestacijePocetna = { template: '<manifestacije-pocetna></manifestacije-pocetna>' } 
const Navbar = { template: '<navbar></navbar>' }
const RegistracijaKorisnika = { template: '<registracija-korisnik></registracija-korisnik>'}
const Prijava = { template: '<prijava></prijava>'}
const Manifestacija = { template: '<pregled-manifestacije></pregled-manifestacije>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/', component: ManifestacijePocetna },
		{ path: '/navbar', component: Navbar },
		{ path: '/registracija', component: RegistracijaKorisnika },
		{ path: '/prijava', component: Prijava },
		{ path: '/manifestacija', component: Manifestacija }
	  ]
});

var app = new Vue({
	router,
	el: '#registracijaKarata'
});

