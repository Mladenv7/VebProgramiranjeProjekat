Vue.component("manifestacije-pretraga", {
    data: function() {
        return {
            upit : {naziv: "", lokacija: "", sort: "",cenaOd: 0, cenaDo: 0, datumOd: null, datumDo: null},
            rezultat : [],
        }
    },
    template: `
    `,
    methods: {

    },
    created(){
        this.upit.naziv = this.$route.query.naziv;
        this.upit.lokacija = this.$route.query.lokacija;
        this.upit.sort = this.$route.query.sort;
        this.upit.cenaOd = this.$route.query.cenaOd;
        this.upit.cenaDo = this.$route.query.cenaDo;
        this.upit.datumOd = this.$route.query.datumOd;
        this.upit.datumDo = this.$route.query.datumDo;
        axios.post("/rest/manifestacije/pretraga", this.upit).then(response => {
            this.rezultat = response.data;
            console.log(this.rezultat);
        });
    },
});