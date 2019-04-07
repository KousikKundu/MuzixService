import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
export const USER_NAME="userName";
export const TOKEN_NAME= "jwt_token";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private springEndPointRegister: string;
  private springEndPointSave: string;
  constructor(private httpClient: HttpClient) { 
    this.springEndPointRegister="http://localhost:8085/api/usertrackservice/" ;
    this.springEndPointSave="http://localhost:8084/api/userservice/";
  }

  registerUser(newUser) {
    const url = this.springEndPointRegister + "register";
    return this.httpClient.post(url, newUser, {observe: "response"});
  }

  saveUser(newUser) {
    console.log('get username b4 saving' ,newUser.userName);
    console.log('get username b4 saving' ,newUser.username);
    const url = this.springEndPointSave + "save";
    return this.httpClient.post(url, newUser);
  }
  loginUser(newUser){
    const url = this.springEndPointSave + "login";
    sessionStorage.setItem(USER_NAME,newUser.userName);
    return this.httpClient.post(url, newUser, {observe: "response"});
  }
  getToken() {
    return localStorage.getItem(TOKEN_NAME);
  }

  isTokenExpired(token?: string): boolean {
    if(localStorage.getItem(TOKEN_NAME)){
      return true;
    }else {
      return false;
    }
  }

  logout(){
    sessionStorage.removeItem(USER_NAME);
    sessionStorage.clear();
    localStorage.removeItem(TOKEN_NAME);
    localStorage.clear();
    console.log('logged out');
  }
}
