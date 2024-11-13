import http from "../http-common"; 

class CartService {
  getAllCarts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/cart/carts`, searchDTO);
  }

  get(cartId) {
    return this.getRequest(`/cart/${cartId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/cart?field=${matchData}`, null);
  }

  addCart(data) {
    return http.post("/cart/addCart", data);
  }

  update(data) {
  	return http.post("/cart/updateCart", data);
  }
  
  uploadImage(data,cartId) {
  	return http.postForm("/cart/uploadImage/"+cartId, data);
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

export default new CartService();
