import http from "../http-common"; 

class WishlistService {
  getAllWishlists(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/wishlist/wishlists`, searchDTO);
  }

  get(wishlistId) {
    return this.getRequest(`/wishlist/${wishlistId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/wishlist?field=${matchData}`, null);
  }

  addWishlist(data) {
    return http.post("/wishlist/addWishlist", data);
  }

  update(data) {
  	return http.post("/wishlist/updateWishlist", data);
  }
  
  uploadImage(data,wishlistId) {
  	return http.postForm("/wishlist/uploadImage/"+wishlistId, data);
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

export default new WishlistService();
