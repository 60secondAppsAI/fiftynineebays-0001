import http from "../http-common"; 

class OfferService {
  getAllOffers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/offer/offers`, searchDTO);
  }

  get(offerId) {
    return this.getRequest(`/offer/${offerId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/offer?field=${matchData}`, null);
  }

  addOffer(data) {
    return http.post("/offer/addOffer", data);
  }

  update(data) {
  	return http.post("/offer/updateOffer", data);
  }
  
  uploadImage(data,offerId) {
  	return http.postForm("/offer/uploadImage/"+offerId, data);
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

export default new OfferService();
