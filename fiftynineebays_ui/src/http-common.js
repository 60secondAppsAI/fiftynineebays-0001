import axios from "axios"; 

export default axios.create({
//  baseURL: "https://drc.rocks:8080/",
  baseURL: "https://60secondApps-0007.vip:8080/",  
//  baseURL: "https://${application.domain}:8080/",  SET TO THIS baseURL when deploying to VPS
//  baseURL: "http://localhost:8090/",
  headers: {
    "Content-type": "application/json",
  },
});


