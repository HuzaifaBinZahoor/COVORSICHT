import { Component, OnInit, Input } from '@angular/core';
import { MapHttpService } from 'src/app/map-http.service';


@Component({
  selector: 'app-dashboard-card',
  templateUrl: './dashboard-card.component.html',
  styleUrls: ['./dashboard-card.component.css']
})
export class DashboardCardComponent implements OnInit {

  constructor( private mapHttpService: MapHttpService ) {}

  maphttps: any = [];
  maphttpsCity: any = [];

  date: string;

  @Input('totalConfirmed')
  totalConfirmed;
  @Input('totalDeaths')
  totalDeaths;
  @Input('totalActive')
  totalActive;
  @Input('totalRecovered')
  totalRecovered;


  ngOnInit(): void {
    

    this.getMapHttp();
    this.getSpecifiCity();

  }

  getSpecifiCity(){

    // this.mapHttpService.getmaphttp().subscribe((data: MapHttp[]) => this.maphttps = data);
      
    this.mapHttpService.getspecificCity().subscribe((Response : any ) =>{
      // console.log(Response);
      
      this.maphttpsCity = JSON.parse(Response.payLoad) ;
      console.log(this.maphttpsCity);

    });
    
  }

     getMapHttp(){

      

    // this.mapHttpService.getmaphttp().subscribe((data: MapHttp[]) => this.maphttps = data);
      
    this.mapHttpService.getmaphttp().subscribe((Response : any ) =>{
      // console.log(Response);
      this.maphttps = JSON.parse(Response.payLoad);

      


    });
    
  }

}
