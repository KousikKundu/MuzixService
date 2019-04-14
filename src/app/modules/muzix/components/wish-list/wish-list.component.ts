import { Component, OnInit } from '@angular/core';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { Track } from 'src/app/modules/muzix/track';
import { MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.css']
})
export class WishListComponent implements OnInit {
  tracks: Array<Track> ;
  wishData: boolean;

  constructor(
    private muzixService: MuzixService,
    private matSnackBar: MatSnackBar
    ) { }

  ngOnInit() {
    this.wishData = true;
    const message = 'WishList is empty';
    this.muzixService.getAllTracksForWishList().subscribe( data => {
      this.tracks = data;
      if (data.length === 0) {
        this.matSnackBar.open(message, ' ', {
          duration: 1000});
      }
      });
  }

  deleteFromwishList(track) {
    this.muzixService.deleteFromwishList(track).subscribe (
      data => {
        console.log('deleted', track);
        const index = this.tracks.indexOf(track);
        this.tracks.splice(index, 1);
      });
    
  }

  updateComments(track) {
    this.muzixService.updateComments(track).subscribe(
      data => {
        console.log('update data', data);
        this.matSnackBar.open('Successfully updated', '' , {
          duration: 1000
        });
    },
    error => {
      console.log('error', error);
    });
  }

}
