package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;
import com.stackroute.usertrackservice.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTrackServiceImpl implements UserTrackService {

  private UserTrackRepository userTrackRepository;

  @Autowired
  public UserTrackServiceImpl(UserTrackRepository userTrackRepository) {
    this.userTrackRepository = userTrackRepository;
  }

  @Override
  public User SaveUserTrackToWishList(Track track, String userName) throws TrackAlreadyExistsException {
    User fetchedUserObj = userTrackRepository.findByUserName(userName);

    List<Track> fetchedTracks = fetchedUserObj.getTrackList();

    if(fetchedTracks != null) {

      for(Track trackObj : fetchedTracks){

        if(trackObj.getTrackId().equals(track.getTrackId())){
          throw new TrackAlreadyExistsException();
        }
      }

      fetchedTracks.add(track);
      fetchedUserObj.setTrackList(fetchedTracks);
      userTrackRepository.save(fetchedUserObj);
    }else {

      fetchedTracks = new ArrayList<Track>();
      fetchedTracks.add(track);
      fetchedUserObj.setTrackList(fetchedTracks);
      userTrackRepository.save(fetchedUserObj);
    }
    return fetchedUserObj;
  }

  @Override
  public User deleteUserTrackFromWishList(String userName, String trackId) throws TrackNotFoundException {

    User fetchedUserObj = userTrackRepository.findByUserName(userName);
    List<Track> fetchedTracks = fetchedUserObj.getTrackList();

    if(fetchedTracks.size() >0) {

      for(int i=0; i<fetchedTracks.size();i++){
        if(trackId.equals(fetchedTracks.get(i).getTrackId())){
          fetchedTracks.remove(i);
          fetchedUserObj.setTrackList(fetchedTracks);
          userTrackRepository.save(fetchedUserObj);
        }
      }

    }else {

      throw new TrackNotFoundException();
    }
    return fetchedUserObj;
  }

  @Override
  public User updateCommentForTrack(String comment, String trackId, String userName) throws TrackNotFoundException {

    User fetchedUserObj = userTrackRepository.findByUserName(userName);
    List<Track> fetchedTracks = fetchedUserObj.getTrackList();

    if(fetchedTracks.size() >0) {

      for(int i=0; i<fetchedTracks.size();i++){
        if(trackId.equals(fetchedTracks.get(i).getTrackId())){
          fetchedTracks.get(i).setComments(comment);
          fetchedUserObj.setTrackList(fetchedTracks);
          userTrackRepository.save(fetchedUserObj);
        }
      }

    }else {

      throw new TrackNotFoundException();
    }
    return fetchedUserObj;
  }

  @Override
  public List<Track> getAllUserTrackFromWishList(String userName) throws Exception {

    User fetchedUserObj = userTrackRepository.findByUserName(userName);

    return fetchedUserObj.getTrackList();
  }

  @Override
  public User registerUser(User user) throws UserAlreadyExistsException {
    User fetchedUserObj = userTrackRepository.findByUserName(user.getUserName());
    if(fetchedUserObj != null ) {
      throw new UserAlreadyExistsException();
    }
    else{
      userTrackRepository.save(user) ;
    }
    return user;
  }
}
