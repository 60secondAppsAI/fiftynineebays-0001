import http from "../http-common"; 

class OrderService {
  getAllOrders(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/order/orders`, searchDTO);
  }

  get(orderId) {
    return this.getRequest(`/order/${orderId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/order?field=${matchData}`, null);
  }

  addOrder(data) {
    return http.post("/order/addOrder", data);
  }

  update(data) {
  	return http.post("/order/updateOrder", data);
  }
  
  uploadImage(data,orderId) {
  	return http.postForm("/order/uploadImage/"+orderId, data);
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

export default new OrderService();
