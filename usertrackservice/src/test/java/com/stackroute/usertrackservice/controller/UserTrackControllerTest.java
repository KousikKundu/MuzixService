package com.stackroute.usertrackservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserTrackController.class)
public class UserTrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserTrackService userTrackService;

    private Image image;
    private Artist artist;
    private Track track;
    private User user;
    private List<Track> trackList;

    @Before
    public void setUp() throws Exception {
        trackList = new ArrayList<>();
        image = new Image(1,"http:url","large");
        artist = new Artist(101,"Jonhn","new url",image);
        track = new Track("Track1","TrackName","new track url","123","new comments",artist);
        trackList.add(track);
        image = new Image(2,"http:url","large");
        artist = new Artist(102,"Jonhn","new url",image);
        track = new Track("Track2","TrackName123","new track url","123","new comments updated",artist);
        trackList.add(track);
        user = new User("john123","john@gmail.com", trackList);
    }

    @After
    public void tearDown() throws Exception {
        image = null;
        artist = null;
        track = null;
        user = null;
    }

    @Test
    public void testSaveTrackSuccess() throws Exception {
        when(userTrackService.SaveUserTrackToWishList(any(),eq(user.getUserName()))).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(post("/api/usertrackservice/user/{userName}/track" , user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track))).andExpect(status().isConflict()).andDo(print());

        verify(userTrackService,times( 1)).SaveUserTrackToWishList(any(),eq(user.getUserName()));

    }

    @Test
    public void testSaveTrackFailure() throws Exception {
        when(userTrackService.SaveUserTrackToWishList(any(),eq(user.getUserName()))).thenReturn(user);
        mockMvc.perform(post("/api/usertrackservice/user/{userName}/track" , user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track))).andExpect(status().isCreated()).andDo(print());

        verify(userTrackService,times( 1)).SaveUserTrackToWishList(any(),eq(user.getUserName()));

    }

    @Test
    public void testUpdateCommentSuccess() throws Exception {
        when(userTrackService.updateCommentForTrack(track.getComments(),track.getTrackId(),user.getUserName())).thenReturn(user);
        mockMvc.perform(patch("/api/usertrackservice/user/{userName}/track" , user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track))).andExpect(status().isOk()).andDo(print());

        verify(userTrackService,times( 1)).updateCommentForTrack(track.getComments(),track.getTrackId(),user.getUserName());

    }


    @Test
    public void testDeleteUserTrack() throws Exception {
        when(userTrackService.deleteUserTrackFromWishList(user.getUserName(),track.getTrackId())).thenReturn(user);
        mockMvc.perform(delete("/api/usertrackservice/user/{userName}/track" , user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track))).andExpect(status().isOk()).andDo(print());

        verify(userTrackService,times( 1)).deleteUserTrackFromWishList(user.getUserName(),track.getTrackId());

    }

    @Test
    public void testGetAllTracksFromWishList() throws Exception {
        when(userTrackService.getAllUserTrackFromWishList(user.getUserName())).thenReturn(trackList);
        mockMvc.perform(get("/api/usertrackservice/user/{userName}/track" , user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track))).andExpect(status().isOk()).andDo(print());

        verify(userTrackService,times( 1)).getAllUserTrackFromWishList(user.getUserName());

    }



    private static  String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper() ;
            String jsonContent = objectMapper.writeValueAsString(obj);
            result = jsonContent;
        }catch(JsonProcessingException e){
            result = "JSON Processing error";
        }
        return result;
    }

}
