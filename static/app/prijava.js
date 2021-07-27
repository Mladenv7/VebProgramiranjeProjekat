Vue.component("prijava", {
    data: function() {
        return {
            kredencijali: {korisnickoIme: "", lozinka: ""},
        }
    },
    template: `
    <div align="center">
    <h3>Prijavite se</h3>


    <form style="width: 30%;">
        <div class="form-floating">
        <input type="text" class="form-control" id="kIme" placeholder="Korisničko ime" oninvalid="this.setCustomValidity('Morate uneti korisničko ime')"
            oninput="this.setCustomValidity('')" v-model="kredencijali.korisnickoIme" required>
        <label for="kIme">Korisničko ime</label>
        </div><br>
        <div class="form-floating">
        <input type="password" class="form-control" id="lozinka" placeholder="Lozinka" oninvalid="this.setCustomValidity('Morate uneti lozinku')"
            oninput="this.setCustomValidity('')" v-model="kredencijali.lozinka" required>
        <label for="lozinka">Lozinka</label>
        </div><br>
        <input class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="prijava()" value="Prijava" />
    </form>

    </div>
    `,
    methods: {
        prijava(){
            axios
                .post("/rest/korisnici/prijava", this.kredencijali).then(response => {
                    localStorage.setItem("prijavljeni", JSON.stringify(response.data));
                    //localStorage mora da cuva parove kljuc:string i da bi se koristio korisnik kao objekat mora se pozvati JSON.parse nad dobavljenim stringom iz localStorage
                }).then(response => {this.$router.push('/'); this.$router.go();});
        },
    },
});