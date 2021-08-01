Vue.component("svi-korisnici", {
    data: function(){
        return {
            korisnici : [],
        }
    },
    template: 
    `
    <div class="container">
        <h3>Registrovani korisnici</h3>

        <table class="table">
        <thead>
            <tr>
            <th scope="col">Korisničko ime</th>
            <th scope="col">Ime</th>
            <th scope="col">Prezime</th>
            <th scope="col">Uloga</th>
            </tr>
        </thead>
        <tbody >
            <tr v-for="korisnik in korisnici">
                <td>{{korisnik.korisnickoIme}}</td>
                <td>{{korisnik.ime}}</td>
                <td>{{korisnik.prezime}}</td>
                <td>{{korisnik.uloga}}</td>
            </tr>
        </tbody>
        </table>
    </div>
    `,
    methods: {

    },
    created(){
        axios.get("/rest/korisnici/sviKorisnici").then(response => {
            this.korisnici = response.data;
            console.log(this.korisnici);
        });
    }
});