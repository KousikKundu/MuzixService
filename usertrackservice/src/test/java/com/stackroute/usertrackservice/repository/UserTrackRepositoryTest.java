package com.stackroute.usertrackservice.repository;

import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserTrackRepositoryTest {

    @Autowired
    private  UserTrackRepository userTrackRepository;

    private User user;
    private Track track;

    @Before
    public void setUp(){
       Image image = new Image(  1, "http:url" , "large");
       Artist artist = new Artist( 101, "Jonhn",  "new url" ,image);
       track = new Track( "Track1", "MyNewTracks","new track url", "123" , "new commets",artist);
       List<Track> trackList = new ArrayList<>();
       trackList.add(track);
       user = new User("John123","john@gmail.com", trackList);
    }

    @After
    public void tearDown(){

        userTrackRepository.deleteAll();
    }

    @Test
    public void testSaveUserTrack() {
        userTrackRepository.save(user);
        User fetchedUserObj = userTrackRepository.findByUserName(user.getUserName());
        List<Track> listTrack = fetchedUserObj.getTrackList();
        Assert.assertEquals(listTrack.get(0).getTrackId(),user.getTrackList().get(0).getTrackId());
    }
}
