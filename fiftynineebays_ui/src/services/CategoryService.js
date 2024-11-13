import http from "../http-common"; 

class CategoryService {
  getAllCategorys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/category/categorys`, searchDTO);
  }

  get(categoryId) {
    return this.getRequest(`/category/${categoryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/category?field=${matchData}`, null);
  }

  addCategory(data) {
    return http.post("/category/addCategory", data);
  }

  update(data) {
  	return http.post("/category/updateCategory", data);
  }
  
  uploadImage(data,categoryId) {
  	return http.postForm("/category/uploadImage/"+categoryId, data);
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

export default new CategoryService();
