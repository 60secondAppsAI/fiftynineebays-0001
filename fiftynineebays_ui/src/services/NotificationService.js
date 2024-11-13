import http from "../http-common"; 

class NotificationService {
  getAllNotifications(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/notification/notifications`, searchDTO);
  }

  get(notificationId) {
    return this.getRequest(`/notification/${notificationId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/notification?field=${matchData}`, null);
  }

  addNotification(data) {
    return http.post("/notification/addNotification", data);
  }

  update(data) {
  	return http.post("/notification/updateNotification", data);
  }
  
  uploadImage(data,notificationId) {
  	return http.postForm("/notification/uploadImage/"+notificationId, data);
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

export default new NotificationService();
