package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.repository.UserTrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class UserTrackServiceTest {

    @Mock
    private UserTrackRepository userTrackRepository;

    private Image image;
    private Artist artist;
    private Track track;
    private User user;
    private List<Track> listTrack;

    @InjectMocks
    private UserTrackServiceImpl userTrackService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        image = new Image( 1, "http:url" , "large");
        artist = new Artist( 101, "Jonhn", "new url" ,image);
        track = new Track( "Track1", "MyNewTracks","new track url", "123" , "new comment",artist);
        listTrack = new ArrayList<>();
        listTrack.add(track);
        user = new User("John123","john@gmail.com", listTrack);

    }

    @After
    public void tearDown(){
        userTrackRepository.deleteAll();
        listTrack= null;
        image = null;
        artist = null;
        track = null;
        user = null;
    }

    /*@Test
    public void testSaveUserTrackSuccess () throws TrackAlreadyExistsException {
        user = new User("John456","john@gmail.com", null);
        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchUser = userTrackService.SaveUserTrackToWishList(track,user.getUserName());
        Assert.assertEquals(user,fetchUser);
        verify(userTrackRepository,timeout(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }*/

    @Test
    public void testDeleteUserTrackFromWishList () throws TrackNotFoundException {

        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchUser = userTrackService.deleteUserTrackFromWishList(user.getUserName(),track.getTrackId());
        Assert.assertEquals(user,fetchUser);
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }

    @Test
    public void testUpdateCommentForTrack () throws TrackNotFoundException {

        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchUser = userTrackService.updateCommentForTrack("new comments updated", track.getTrackId(),user.getUserName());
        Assert.assertEquals(fetchUser.getTrackList().get(0).getComments(),"new comments updated");
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }

    @Test
    public void testGetAllUserTrackFromWishList () throws Exception {

        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        List<Track>  list = userTrackService.getAllUserTrackFromWishList(user.getUserName());
        Assert.assertEquals(listTrack,list);
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
    }

}
