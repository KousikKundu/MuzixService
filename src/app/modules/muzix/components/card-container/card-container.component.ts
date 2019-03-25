import { Component, OnInit } from '@angular/core';
import { Track } from 'src/app/modules/muzix/track';
import { Artist } from 'src/app/modules/muzix/artist';
import { Image } from 'src/app/modules/muzix/image';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  tracks: Track[];
  tarckObj: Track;
  artistObj: Artist;
  imageObj: Image;
  country: string;
  id: number;
  statusCode: number;
  errorStatus: string;
  artistName: string;
  serachTracks: Array<Track>;

  constructor(
    private muzixServcie: MuzixService,
    private routes: ActivatedRoute,
    private matSnackBar: MatSnackBar) {
    this.tracks = [];
  }

  ngOnInit() {
    const tempData = this.routes.data.subscribe(newData => {
      this.country = newData.country;
    });

    this.muzixServcie.getTrackDetails(this.country).subscribe(tracks => {
      console.log(tracks);
      this.id = 0;
      this.tracks = [];
      const data = tracks['tracks']['track'];
      data.forEach(tragetdata => {
        this.id++;
        this.tarckObj = new Track();
        this.artistObj = new Artist();
        this.imageObj = new Image();
        this.artistObj = tragetdata['artist'];
        this.imageObj.text = tragetdata['image'][2]['#text'];
        this.imageObj.size = tragetdata['image'][2]['#size'];
        this.tarckObj = tragetdata;
        this.tarckObj.artist = this.artistObj;
        this.artistObj.image = this.imageObj;
        this.tarckObj.trackId = this.country.slice(0, 3) + this.id;
        this.tracks.push(this.tarckObj);
        this.serachTracks = this.tracks;
      });
    });
  }

  oneKey(event: any) {

    this.artistName = event.target.value;
    const result = this.serachTracks.filter(track => {
      return track.artist.name.match(this.artistName);
    });
    this.tracks = result;
  }

  addtowishList(track) {
    console.log('Conatiner track', track);
    this.muzixServcie.addTrackToWishLish(track).subscribe (
      data => {
        console.log(data);
        this.statusCode = data.status;
        if (this.statusCode === 201) {
          this.matSnackBar.open('Track Successfully added !!!', ' ', {
            duration: 1000
          });
        }
      },
      // tslint:disable-next-line:no-shadowed-variable
      error => {
        this.errorStatus = `${error.status}`;
        const errorMsg = `${error.error.message}`;
        this.statusCode = parseInt(this.errorStatus, 10);
        if (this.statusCode === 400) {
          this.matSnackBar.open(errorMsg, '', {
            duration: 1000
          });
          this.statusCode = 0;
        }

      }

    );

  }

}
