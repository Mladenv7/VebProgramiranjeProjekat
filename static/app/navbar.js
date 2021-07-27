Vue.component("navbar", {
    data: function() {
        return {
        }
    },
    template: `
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
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
        <form class="d-flex">
            <button class="btn btn-outline-primary" type="submit">Prijava</button>
        </form>
        </div>
    </div>
    </nav>
    `,
    methods: {

    },
    
});