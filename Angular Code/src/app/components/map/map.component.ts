import { Component, OnInit } from '@angular/core';
import { MapHttpService } from 'src/app/map-http.service';

declare const L: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})

export class MapComponent implements OnInit {

  maphttps: any = [];
  maphttpsCity: any = [];
  currentCases: any;

  constructor( private mapHttpService: MapHttpService ) {}

  ngOnInit(): void {

    this.getMapHttp();
    this.getSpecifiCity();
    this.generateMap();
  }

   getMapHttp(){

    // this.mapHttpService.getmaphttp().subscribe((data: MapHttp[]) => this.maphttps = data);
      
    this.mapHttpService.getmaphttp().subscribe((Response : any ) =>{
      // console.log(Response);
      
      this.maphttps = JSON.parse(Response.payLoad) ;

    });
    
  }

  getSpecifiCity(){

    // this.mapHttpService.getmaphttp().subscribe((data: MapHttp[]) => this.maphttps = data);
      
    this.mapHttpService.getspecificCity().subscribe((Response : any ) =>{
      // console.log(Response);
      
      this.maphttpsCity = JSON.parse(Response.payLoad) ;
      console.log(this.maphttpsCity);

    });
    
  }
  

  generateMap(){
    if (!navigator.geolocation) {
      console.log('location is not supported');
    }
    navigator.geolocation.getCurrentPosition((position) => {
      const coords = position.coords;
      const latLong = [coords.latitude, coords.longitude];
      console.log(
        `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}`
      );
      let mymap = L.map('map').setView(latLong, 13);

      L.tileLayer(
        'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1Ijoic3VicmF0MDA3IiwiYSI6ImNrYjNyMjJxYjBibnIyem55d2NhcTdzM2IifQ.-NnMzrAAlykYciP4RP9zYQ',
        {
          attribution:
            'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
          maxZoom: 18,
          id: 'mapbox/streets-v11',
          tileSize: 512,
          zoomOffset: -1,
          accessToken: 'your.mapbox.access.token',
        }
      ).addTo(mymap);

      var userIcon = L.icon({

        iconUrl: 'assets/user.png',
        iconSize: [80,80],

      });

      let marker = L.marker(latLong, {icon: userIcon}).addTo(mymap);

      
      
      // marker.bindPopup("User").openPopup();
      
      //Frankfurt 

      const latlong1 = [50.110924,8.682127];
      var circle = L.circle(latlong1, {
        color: '#f7822e',
        fillColor: '#f7822e',
        fillOpacity: 0.5,
        radius: 80000
    }).addTo(mymap);


  

    // Baden-Württemberg

    const latlong2 = [48.6616,9.3501];
    var circle = L.circle(latlong2, {
      color: '#f7822e',
      fillColor: '#f7822e',
      fillOpacity: 0.5,
      radius: 106000,
  }).addTo(mymap);

  // Bayern-Bavaria
  const latlong3 = [48.7904,11.4979];
    var circle = L.circle(latlong3, {
      color: '#f72f3e',
      fillColor: '#f72f3e',
      fillOpacity: 0.5,
      radius: 40000,
  }).addTo(mymap);

    // Berlin
    const latlong4 = [52.5200,14.4050];
    var circle = L.circle(latlong4, {
      color: '#f7822e',
      fillColor: '#f7822e',
      fillOpacity: 0.5,
      radius: 90000,
  }).addTo(mymap);

  //Brandenburg

  const latlong5 = [52.4125,12.5316];
  var circle = L.circle(latlong5, {
    color: '#f5aa2a',
    fillColor: '#f5aa2a',
    fillOpacity: 0.5,
    radius: 90000,
}).addTo(mymap);

  //Bremen

  const latlong6 = [52.0793,7.8017];
  var circle = L.circle(latlong6, {
    color: '#f5aa2a',
    fillColor: '#f5aa2a',
    fillOpacity: 0.5,
    radius: 80000,
}).addTo(mymap);

//Hamburg

const latlong7 = [53.5511,9.9937];
var circle = L.circle(latlong7, {
  color: '#f5aa2a',
  fillColor: '#f5aa2a',
  fillOpacity: 0.5,
  radius: 140000,
}).addTo(mymap);

//Mecklenburg-Vorpommern

const latlong8 = [53.6127,12.4296];
var circle = L.circle(latlong8, {
  color: '#f72f81',
  fillColor: '#f72f81',
  fillOpacity: 0.5,
  radius: 50000,
}).addTo(mymap);

//Niedersachsen

const latlong9 = [51.6367,9.8451];
var circle = L.circle(latlong9, {
  color: '#f57f2a',
  fillColor: '#f57f2a',
  fillOpacity: 0.5,
  radius: 60000,
}).addTo(mymap);

//Rheinland-Pfalz

const latlong10 = [50.80000,7.1090];
var circle = L.circle(latlong10, {
  color: '#f7822e',
  fillColor: '#f7822e',
  fillOpacity: 0.5,
  radius: 65000,
}).addTo(mymap);


//Saarland

const latlong11 = [49.3964,7.0230];
var circle = L.circle(latlong11, {
  color: '#f6ac2d',
  fillColor: '#f6ac2d',
  fillOpacity: 0.5,
  radius: 70000,
}).addTo(mymap);

//Sachsen 

const latlong12 = [51.1045,13.2017];
var circle = L.circle(latlong12, {
  color: '#f7822e',
  fillColor: '#f7822e',
  fillOpacity: 0.5,
  radius: 80000,
}).addTo(mymap);

//Sachsen-Anhalt 

const latlong13 = [51.9503,11.6923];
var circle = L.circle(latlong13, {
  color: '#f72f81',
  fillColor: '#f72f81',
  fillOpacity: 0.5,
  radius: 43000,
}).addTo(mymap);


//Thüringen 

const latlong15 = [51.0110,10.8453];
var circle = L.circle(latlong15, {
  color: '#f7ad2e',
  fillColor: '#f7ad2e',
  fillOpacity: 0.5,
  radius: 44000,
}).addTo(mymap);


    });
    this.watchPosition();
  }



  watchPosition() {
    let desLat = 0;
    let desLon = 0;
    let id = navigator.geolocation.watchPosition(
      (position) => {
        console.log(
          `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}`
        );
        if (position.coords.latitude === desLat) {
          navigator.geolocation.clearWatch(id);
        }
      },
      (err) => {
        console.log(err);
      },
      {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,
      }
    );
  }

}
