import http from "../http-common"; 

class FeedbackService {
  getAllFeedbacks(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/feedback/feedbacks`, searchDTO);
  }

  get(feedbackId) {
    return this.getRequest(`/feedback/${feedbackId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/feedback?field=${matchData}`, null);
  }

  addFeedback(data) {
    return http.post("/feedback/addFeedback", data);
  }

  update(data) {
  	return http.post("/feedback/updateFeedback", data);
  }
  
  uploadImage(data,feedbackId) {
  	return http.postForm("/feedback/uploadImage/"+feedbackId, data);
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

export default new FeedbackService();
