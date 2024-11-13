import http from "../http-common"; 

class ReportService {
  getAllReports(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/report/reports`, searchDTO);
  }

  get(reportId) {
    return this.getRequest(`/report/${reportId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/report?field=${matchData}`, null);
  }

  addReport(data) {
    return http.post("/report/addReport", data);
  }

  update(data) {
  	return http.post("/report/updateReport", data);
  }
  
  uploadImage(data,reportId) {
  	return http.postForm("/report/uploadImage/"+reportId, data);
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

export default new ReportService();
