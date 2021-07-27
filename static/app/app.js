const ManifestacijePocetna = { template: '<manifestacije-pocetna></manifestacije-pocetna>' } 
const Navbar = { template: '<navbar></navbar>' }
const RegistracijaKorisnika = { template: '<registracija-korisnik></registracija-korisnik>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/', name: 'manifestacijePocetna' , component: ManifestacijePocetna },
		{ path: '/navbar', name: 'navbar' , component: Navbar },
		{ path: '/registracija', name: 'registracijaKorisnika', component: RegistracijaKorisnika}
	  ]
});

var app = new Vue({
	router,
	el: '#registracijaKarata'
});

