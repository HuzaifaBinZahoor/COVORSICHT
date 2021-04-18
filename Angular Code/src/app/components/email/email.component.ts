import { Component, OnInit } from '@angular/core';
import { MapHttpService } from 'src/app/map-http.service';
import { EmailModel } from 'src/app/email-model';


@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  email: EmailModel = new EmailModel();
  report: any = [];

  myResponse :any;

  constructor( private mapHttpService: MapHttpService) { }

  ngOnInit(): void {

    
  }

  getreport(obj : any){

    let reportURL = 'http://localhost:8080/subscriber/getReport?id='+obj;
    window.open(reportURL, "_blank");

    // this.mapHttpService.getmaphttp().subscribe((data: MapHttp[]) => this.maphttps = data);
      
    // this.mapHttpService.downloadReport(obj).subscribe((Response : any ) =>{
    //   // console.log(Response);
      
    //   this.report = JSON.parse(Response) ;
    //   console.log(this.report);

    // });
    
  }

  saveemail(obj : any){


    console.log("save Enmail called");
    console.log(obj);

    

    this.mapHttpService.sendEmail(obj).subscribe( data => {
      console.log(data);
      console.log(data['payLoad']); 
     let parsedRes= JSON.parse(data['payLoad']);

      this.myResponse = parsedRes['id'];
    
      console.log(this.myResponse);

  },

    error => console.log(error));

    console.log("out logger prininting saved id");
    console.log(this.myResponse);

  }


  onSubmit(){

   
    this.saveemail(this.email);

  }

  download(){

    console.log(this.myResponse);
    console.log("in Download");
    this.getreport(this.myResponse);

  }

}
