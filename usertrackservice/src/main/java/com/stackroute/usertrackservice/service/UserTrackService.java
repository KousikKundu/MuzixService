package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserTrackService {

  User SaveUserTrackToWishList(Track track, String userName) throws TrackAlreadyExistsException;
  User deleteUserTrackFromWishList(String userName, String id)  throws TrackNotFoundException;
  User updateCommentForTrack(String comment , String trackId, String userName) throws TrackNotFoundException ;
  List<Track> getAllUserTrackFromWishList(String userName) throws Exception ;

  User registerUser(User user) throws UserAlreadyExistsException ;
}
