import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MuzixService } from './muzix.service';
import {Track} from './track';

describe('MuzixService', () => {

    let track = new Track();
    track = {
      trackId: "track123",
      name: "trackname",
      listeners: "123",
      url: "new url",
      comments: "new comment",
      artist: {
        artistId: 123,
        name: "artname",
        url: "art url",
        image: {
          imageId: 123,
          text: "new text",
          size: "large"
        }
      }
    }

  const springEndpoint = "http://localhost:8086/usertrackservice/api/usertrackservice/";  
  let muzixService: MuzixService;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
  
  TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [MuzixService]
  })
  muzixService = TestBed.get(MuzixService);
  httpTestingController= TestBed.get(HttpTestingController);
});

  it('should be created', () => {
   // const service: MuzixService = TestBed.get(MuzixService);
    expect(muzixService).toBeTruthy();
  });

  it('addtracktowishlist should fetch proper response from http call',() => {

    muzixService.addTrackToWishLish(track).subscribe(result =>{
      console.log(result);
      expect(result.body).toBe(track);
    });

    const url =  springEndpoint + 'user/' +'test'+ '/track';
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('getAlltrackfromWishList should fetch proper response from http call',() => {

    muzixService.getAllTracksForWishList().subscribe(result =>{
      console.log(result);
    });

    const url =  springEndpoint + 'user/' +'test'+ '/track';
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('GET');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('deletetrackfromWishList should fetch proper response from http call',() => {

    muzixService.deleteFromwishList(track).subscribe(result =>{
      console.log(result);
    });

    const url =  springEndpoint + 'user/' +'test/'+ track.trackId;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('DELETE');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('updatetrackcomments should fetch proper response from http call',() => {

    muzixService.updateComments(track).subscribe(result =>{
      console.log(result);
      expect(result.body).toBe(track);
    });

    const url =  springEndpoint + 'user/' +'test/'+ 'track';
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('PATCH');
    expect(httpMockReq.request.url).toEqual(url);

  });


});
