import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Track } from 'src/app/modules/muzix/track';

@Injectable({
  providedIn: 'root'
})
export class MuzixService {

  thirdPartyApi: string;
  apiKey: string;
  springEndPoint: string;

  constructor(private httpClient: HttpClient) {
    this.thirdPartyApi = 'http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=';
    this.apiKey = '&api_key=b5e020e920cb3b98d015d975d5e4fa76&format=json';
    this.springEndPoint = 'http://localhost:8083/api/muzixservice/';
   }

   getTrackDetails(country: string): Observable<any> {
     const url = `${this.thirdPartyApi}${country}${this.apiKey}`;
     console.log(url);
      return this.httpClient.get(url);
   }

   addTrackToWishLish(track: Track) {
     const url =  this.springEndPoint + 'track';
     return this.httpClient.post( url, track, {
      observe: 'response'
    }) ;
   }

   getAllTracksForWishList(): Observable< Track[] > {
    const url =  this.springEndPoint + 'tracks';
    return this.httpClient.get<Track[]>(url);
   }

   deleteFromwishList(track: Track) {
     const url = this.springEndPoint + 'track/' + `${track.trackId}`;
     return this.httpClient.delete(url, {responseType: 'text'});
   }

   updateComments(track: Track) {
     const url = this.springEndPoint + 'track/' + `${track.trackId}`;
     return this.httpClient.put(url, ' ', {
       observe: 'response'
      });
   }
}
