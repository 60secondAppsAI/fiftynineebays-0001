import http from "../http-common"; 

class SubscriptionService {
  getAllSubscriptions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/subscription/subscriptions`, searchDTO);
  }

  get(subscriptionId) {
    return this.getRequest(`/subscription/${subscriptionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/subscription?field=${matchData}`, null);
  }

  addSubscription(data) {
    return http.post("/subscription/addSubscription", data);
  }

  update(data) {
  	return http.post("/subscription/updateSubscription", data);
  }
  
  uploadImage(data,subscriptionId) {
  	return http.postForm("/subscription/uploadImage/"+subscriptionId, data);
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

export default new SubscriptionService();
