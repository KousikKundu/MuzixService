import { Component, OnInit } from '@angular/core';
import { Track } from 'src/app/modules/muzix/track';
import { Artist } from 'src/app/modules/muzix/artist';
import { Image } from 'src/app/modules/muzix/image';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  tracks: Track [];
  tarckObj: Track ;
  artistObj: Artist;
  imageObj: Image;
  constructor(private muzixServcie: MuzixService) {
    this.tracks = [];
   }

  ngOnInit() {
    this.muzixServcie.getTrackDetails().subscribe(tracks => {
      console.log(tracks);
      const data = tracks['tracks']['track'];
      data.forEach(tragetdata => {

        this.tarckObj = new Track();
        this.artistObj = new Artist();
        this.imageObj = new Image();
        this.artistObj = tragetdata['artist'];
        this.imageObj.text = tragetdata['image'][2]['#text'];
        this.imageObj.size = tragetdata['image'][2]['#size'];
        this.tarckObj = tragetdata;
        this.tarckObj.artist = this.artistObj;
        this.artistObj.image = this.imageObj;
        this.tracks.push(this.tarckObj);

      });
    });
  }

}
