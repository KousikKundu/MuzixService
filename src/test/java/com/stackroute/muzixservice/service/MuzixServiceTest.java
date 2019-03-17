package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.repository.MuzixRepository;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuzixServiceTest {

    @Mock
    private MuzixRepository muzixRepository;

    private Image image;
    private Artist artist;
    private Track track;
    private Optional optional;

    private List<Track> listTrack = null;

    @InjectMocks
    private MuzixServiceImpl muzixService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(testClass:  this);
        image = new Image( imageId: 1, imageUrl: "http:url" , imageSize="large");
        artist = new Artist( artisId: 101, artistName: "Jonhn", url: "new url" ,image);
        track = new Track( trackId: "Track1", trackName: "MyNewTracks",comments:"new comments", trackListeners: "123" , trackUrl: "new track url",artist);
        listTrack = new ArrayList<>();
        listTrack.add(track);
        optional=Optional.of(track);
    }

    @After
    public void tearDown(){
        image = null;
        artist = null;
        track = null;
    }
}
