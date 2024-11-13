import http from "../http-common"; 

class PaymentService {
  getAllPayments(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/payment/payments`, searchDTO);
  }

  get(paymentId) {
    return this.getRequest(`/payment/${paymentId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/payment?field=${matchData}`, null);
  }

  addPayment(data) {
    return http.post("/payment/addPayment", data);
  }

  update(data) {
  	return http.post("/payment/updatePayment", data);
  }
  
  uploadImage(data,paymentId) {
  	return http.postForm("/payment/uploadImage/"+paymentId, data);
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

export default new PaymentService();
