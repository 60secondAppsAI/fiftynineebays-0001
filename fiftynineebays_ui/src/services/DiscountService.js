import http from "../http-common"; 

class DiscountService {
  getAllDiscounts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/discount/discounts`, searchDTO);
  }

  get(discountId) {
    return this.getRequest(`/discount/${discountId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/discount?field=${matchData}`, null);
  }

  addDiscount(data) {
    return http.post("/discount/addDiscount", data);
  }

  update(data) {
  	return http.post("/discount/updateDiscount", data);
  }
  
  uploadImage(data,discountId) {
  	return http.postForm("/discount/uploadImage/"+discountId, data);
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

export default new DiscountService();
