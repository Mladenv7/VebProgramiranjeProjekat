Vue.component("l-map", window.Vue2Leaflet.LMap);
Vue.component("l-tile-layer", window.Vue2Leaflet.LTileLayer);
Vue.component("l-marker", window.Vue2Leaflet.LMarker);

Vue.component("mapa", {
  props: ["lat", "lng"],
  data: function () {
    return {
      lokacija: {
        geoSirina: "",
        geoDuzina: "",
        ulicaBroj: "",
        grad: "",
        postanskiBroj: "",
      },
      startlan: 45.267136,
      startlng: 19.833549,
    };
  },
  template: `
  <div id="map" style="height: 400px; width: 500px">     
    <h1>mape</h1>
  </div>
      `,
  mounted() {
    if (this.lat != null) {
      this.startlan = this.lat;
      this.startlng = this.lng;
    }
    var marker = null;
    var _this = this;
    var lok = this.lokacija;
    var map = L.map("map")
      .setView([this.startlan, this.startlng], 15)
      .on("click", function addMarker(e) {
        if (marker !== null) {
          map.removeLayer(marker);
        }
        marker = L.marker(e.latlng).addTo(map);
        var latlong = e.latlng;
        var address;
        axios
          .get(
            `https://nominatim.openstreetmap.org/reverse?lat=${latlong.lat}&lon=${latlong.lng}&format=json`
          )
          .then((data) => {
            address = data.data.address;

            lok.geoSirina = latlong.lat;
            lok.geoDuzina = latlong.lng;
            lok.grad = address.city;
            lok.postanskiBroj = address.postcode;
            lok.ulicaBroj = address.road + " " + address.house_number;

            _this.$emit("clicked", lok);
            marker
              .bindPopup(
                `<b>${lok.ulicaBroj} </b><br>${lok.grad} ${lok.postanskiBroj}<br>${lok.geoSirina},${lok.geoDuzina}`
              )
              .openPopup();
          });
      });

    L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);
    marker = L.marker([this.startlan, this.startlng], {
      title: "Coordinates",
      alt: "Coordinates",
      draggable: true,
    }).addTo(map);
  },
});
