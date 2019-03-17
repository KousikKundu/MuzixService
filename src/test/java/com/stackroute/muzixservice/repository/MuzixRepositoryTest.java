package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MuzixRepositoryTest {

    @Autowired
    private MuzixRepository muzixRepository ;

    private Image image;
    private Artist artist;
    private Track track;

    @Before
    public void setUp(){
        image = new Image( imageId: 1, imageUrl: "http:url" , imageSize="large");
        artist = new Artist( artisId: 101, artistName: "Jonhn", url: "new url" ,image);
        track = new Track( trackId: "Track1", trackName: "MyNewTracks",comments:"new comments", trackListeners: "123" , trackUrl: "new track url",artist);
    }

    @After
    public void tearDown(){
        image = null;
        artist = null;
        track = null;
        muzixRepository.deleteAll();
    }

   @Test
    public void testSaveTrack(){
        muzixRepository.insert(track);
        Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
       Assert.assertEquals(fetchTrack.getTrackNamne(),track.getTrackNamne());
    }

    @Test
    public void testUpdateComments(){
        muzixRepository.insert(track);
        Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
        fetchTrack.setComments("updated comments");
        muzixRepository.save(fetchTrack);
        Track fetchTrackObj= muzixRepository.findById(track.getTrackId()).get();
        Assert.assertEquals(expected: "updated comments",fetchTrackObj.getComments());
    }

    @Test
    public void testDeleteTrack(){
        muzixRepository.insert(track);
        Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
        muzixRepository.delete(fetchTrack);
        Assert.assertEquals(Optional.empty(),muzixRepository.findById(track.getTrackId()));
    }

    @Test
    public void testGetAllTrack(){
        muzixRepository.insert(track);
        Image image = new Image( imageId: 2, imageUrl: "http:url:another" , imageSize="lextraarge");
        Artist artist = new Artist( artisId: 102, artistName: "Joanha", url: "new url" ,image);
        track = new Track( trackId: "Track2", trackName: "MyAnotherNewTracks",comments:"new comments updated", trackListeners: "123" , trackUrl: "new track url",artist);
        muzixRepository.insert(track);
        List<Track> list = muzixRepository.findAll();
        Assert.assertEquals(expected: 2 , list.size());
        Assert.assertEquals(expected: "Track2", list.get(1).getTrackId());
    }
}
