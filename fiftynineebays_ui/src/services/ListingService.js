import http from "../http-common"; 

class ListingService {
  getAllListings(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/listing/listings`, searchDTO);
  }

  get(listingId) {
    return this.getRequest(`/listing/${listingId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/listing?field=${matchData}`, null);
  }

  addListing(data) {
    return http.post("/listing/addListing", data);
  }

  update(data) {
  	return http.post("/listing/updateListing", data);
  }
  
  uploadImage(data,listingId) {
  	return http.postForm("/listing/uploadImage/"+listingId, data);
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

export default new ListingService();
