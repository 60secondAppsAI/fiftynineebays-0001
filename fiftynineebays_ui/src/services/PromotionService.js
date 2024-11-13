import http from "../http-common"; 

class PromotionService {
  getAllPromotions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/promotion/promotions`, searchDTO);
  }

  get(promotionId) {
    return this.getRequest(`/promotion/${promotionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/promotion?field=${matchData}`, null);
  }

  addPromotion(data) {
    return http.post("/promotion/addPromotion", data);
  }

  update(data) {
  	return http.post("/promotion/updatePromotion", data);
  }
  
  uploadImage(data,promotionId) {
  	return http.postForm("/promotion/uploadImage/"+promotionId, data);
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

export default new PromotionService();
