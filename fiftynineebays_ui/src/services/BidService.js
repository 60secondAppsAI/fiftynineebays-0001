import http from "../http-common"; 

class BidService {
  getAllBids(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/bid/bids`, searchDTO);
  }

  get(bidId) {
    return this.getRequest(`/bid/${bidId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/bid?field=${matchData}`, null);
  }

  addBid(data) {
    return http.post("/bid/addBid", data);
  }

  update(data) {
  	return http.post("/bid/updateBid", data);
  }
  
  uploadImage(data,bidId) {
  	return http.postForm("/bid/uploadImage/"+bidId, data);
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

export default new BidService();
