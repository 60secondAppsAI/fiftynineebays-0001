import http from "../http-common"; 

class TransactionService {
  getAllTransactions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/transaction/transactions`, searchDTO);
  }

  get(transactionId) {
    return this.getRequest(`/transaction/${transactionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/transaction?field=${matchData}`, null);
  }

  addTransaction(data) {
    return http.post("/transaction/addTransaction", data);
  }

  update(data) {
  	return http.post("/transaction/updateTransaction", data);
  }
  
  uploadImage(data,transactionId) {
  	return http.postForm("/transaction/uploadImage/"+transactionId, data);
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

export default new TransactionService();
