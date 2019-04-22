package com.stackroute.usertrackservice.service;

import com.stackroute.rabbitmq.domain.UserDTO;
import com.stackroute.usertrackservice.config.Producer;
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

  private Producer producer;
  private UserTrackRepository userTrackRepository;

  @Autowired
  public UserTrackServiceImpl(UserTrackRepository userTrackRepository, Producer producer) {
    this.userTrackRepository = userTrackRepository;
    this.producer = producer;
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
      UserDTO userDTO = new UserDTO();
      userDTO.setUserName(userName);
      userDTO.setEmail(fetchedUserObj.getEmail());
      List list = new ArrayList();
      list.add(fetchedTracks);
      userDTO.setTrackList(list);
      userTrackRepository.save(fetchedUserObj);
      producer.sendToRabbitMqTrackObj(userDTO);
    }else {

      fetchedTracks = new ArrayList<Track>();
      fetchedTracks.add(track);
      fetchedUserObj.setTrackList(fetchedTracks);

      UserDTO userDTO = new UserDTO();
      userDTO.setUserName(userName);
      userDTO.setEmail(fetchedUserObj.getEmail());
      List list = new ArrayList();
      list.add(fetchedTracks);
      userDTO.setTrackList(list);

      userTrackRepository.save(fetchedUserObj);
      producer.sendToRabbitMqTrackObj(userDTO);
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
    UserDTO userDTO = new UserDTO();
    userDTO.setUserName(user.getUserName());
    userDTO.setEmail(user.getEmail());
    userDTO.setPassword(user.getPassword());
    User fetchedUserObj = userTrackRepository.findByUserName(user.getUserName());
    if(fetchedUserObj != null ) {
      throw new UserAlreadyExistsException();
    }
    else{
      userTrackRepository.save(user) ;
      producer.sendMessageToRabbitMq(userDTO);
    }
    return user;
  }
}
