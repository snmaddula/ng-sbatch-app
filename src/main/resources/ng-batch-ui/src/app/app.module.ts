import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { FormUploadComponent } from './upload/form-upload/form-upload.component';
import { UploadFileService } from './upload/upload-file.service';

@NgModule({
  declarations: [
    AppComponent,
    FormUploadComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [UploadFileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
