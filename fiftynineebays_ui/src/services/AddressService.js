import http from "../http-common"; 

class AddressService {
  getAllAddresss(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/address/addresss`, searchDTO);
  }

  get(addressId) {
    return this.getRequest(`/address/${addressId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/address?field=${matchData}`, null);
  }

  addAddress(data) {
    return http.post("/address/addAddress", data);
  }

  update(data) {
  	return http.post("/address/updateAddress", data);
  }
  
  uploadImage(data,addressId) {
  	return http.postForm("/address/uploadImage/"+addressId, data);
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

export default new AddressService();
