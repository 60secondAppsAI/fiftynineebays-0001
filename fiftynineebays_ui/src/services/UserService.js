import http from "../http-common"; 

class UserService {
  getAllUsers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/user/users`, searchDTO);
  }

  get(userId) {
    return this.getRequest(`/user/${userId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/user?field=${matchData}`, null);
  }

  addUser(data) {
    return http.post("/user/addUser", data);
  }

  update(data) {
  	return http.post("/user/updateUser", data);
  }
  
  uploadImage(data,userId) {
  	return http.postForm("/user/uploadImage/"+userId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new UserService();
