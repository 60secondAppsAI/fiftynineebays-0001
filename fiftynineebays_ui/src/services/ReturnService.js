import http from "../http-common"; 

class ReturnService {
  getAllReturns(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/return/returns`, searchDTO);
  }

  get(returnId) {
    return this.getRequest(`/return/${returnId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/return?field=${matchData}`, null);
  }

  addReturn(data) {
    return http.post("/return/addReturn", data);
  }

  update(data) {
  	return http.post("/return/updateReturn", data);
  }
  
  uploadImage(data,returnId) {
  	return http.postForm("/return/uploadImage/"+returnId, data);
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

export default new ReturnService();
