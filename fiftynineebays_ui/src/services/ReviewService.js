import http from "../http-common"; 

class ReviewService {
  getAllReviews(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/review/reviews`, searchDTO);
  }

  get(reviewId) {
    return this.getRequest(`/review/${reviewId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/review?field=${matchData}`, null);
  }

  addReview(data) {
    return http.post("/review/addReview", data);
  }

  update(data) {
  	return http.post("/review/updateReview", data);
  }
  
  uploadImage(data,reviewId) {
  	return http.postForm("/review/uploadImage/"+reviewId, data);
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

export default new ReviewService();
