package com.stackroute.usertrackservice.controller;

import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/usertrackservice")
public class UserTrackController {

    private UserTrackService userTrackService;
    private ResponseEntity responseEntity;

    public UserTrackController() {
    }

    @Autowired
    public UserTrackController(UserTrackService userTrackService) {
        this.userTrackService = userTrackService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistsException {

        try {
            userTrackService.registerUser(user);
            responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

    @PostMapping("/user/{userName}/track")
    public ResponseEntity<?>  saveUserTrackToWishList(@RequestBody Track track , @PathVariable("userName") String userName) throws TrackAlreadyExistsException {

        try {
            System.out.println(" inside controller " + userName);
           User user = userTrackService.SaveUserTrackToWishList(track,userName);
            System.out.println(" service method is called " );
            responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (TrackAlreadyExistsException e){
            throw new TrackAlreadyExistsException();
        }catch (Exception e) {
            System.out.println(" inside controller " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping("/user/{userName}/track")
    public ResponseEntity<?>  deleteUserTrackToWishList(@PathVariable("userName") String userName,@RequestBody Track track ) throws TrackNotFoundException {

        try {
            User user = userTrackService.deleteUserTrackFromWishList(userName,track.getTrackId());
            responseEntity = new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (TrackNotFoundException e){
            throw new TrackNotFoundException();
        }catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @PatchMapping("/user/{userName}/track")
    public ResponseEntity<?>  updateCommentForUSerTrack(@RequestBody Track track, @PathVariable("userName") String userName) throws TrackNotFoundException {

        try {
            User user = userTrackService.updateCommentForTrack(track.getComments(),track.getTrackId(),userName);
            responseEntity = new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (TrackNotFoundException e){
            throw new TrackNotFoundException();
        }catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/user/{userName}/track")
    public ResponseEntity<?> getAllUserTrackFromWishList(@PathVariable("userName") String userName) {

        try{
            responseEntity= new ResponseEntity(userTrackService.getAllUserTrackFromWishList(userName), HttpStatus.OK);
        }catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
       return responseEntity;
    }


}
