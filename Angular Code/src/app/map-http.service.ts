import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmailModel } from './email-model';
import { MapHttp } from './map-http';
import {DownloadReport} from './download-report'


@Injectable({
  providedIn: 'root'
})
export class MapHttpService {

  private baseURL = "http://localhost:8080/citycases/getByCity?cityId=4500"
  private specificCity = "http://localhost:8080/citycases/getByCity?cityId=4504"


  // private emailURL = "http://localhost:8080/subscriber/add?email&citiesIds=4504"
  
  constructor(private httpClient: HttpClient) { }


  sendEmail(email: EmailModel): Observable<object> {
    // console.log("I am in Send Email Above Line");
    // console.log("printing email recived");
    // console.log(email);
    // console.log(email['email']);
    // console.log(email['Id']);
    
   // http://localhost:8080/subscriber/add?email=huzaifazahoor4@gmail.com&citiesIds=4504
    let emailURL2 = 'http://localhost:8080/subscriber/add?email='+email['email'] + '&citiesIds='+email['Id'];
    return this.httpClient.post(`${emailURL2}`, JSON.stringify({ email: email }));
    //return this.httpClient.post(`${emailURL2}`, JSON.stringify({ email: email }))


  }

  getmaphttp(): Observable<MapHttp[]> {

    return this.httpClient.get<MapHttp[]>(`${this.baseURL}`);
  }

  getspecificCity(): Observable<MapHttp[]> {

    return this.httpClient.get<MapHttp[]>(`${this.specificCity}`);
  }

 


}
