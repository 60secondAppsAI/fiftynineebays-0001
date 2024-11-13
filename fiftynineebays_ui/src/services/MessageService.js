import http from "../http-common"; 

class MessageService {
  getAllMessages(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/message/messages`, searchDTO);
  }

  get(messageId) {
    return this.getRequest(`/message/${messageId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/message?field=${matchData}`, null);
  }

  addMessage(data) {
    return http.post("/message/addMessage", data);
  }

  update(data) {
  	return http.post("/message/updateMessage", data);
  }
  
  uploadImage(data,messageId) {
  	return http.postForm("/message/uploadImage/"+messageId, data);
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

export default new MessageService();
