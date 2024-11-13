package com.fiftynineebays.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.fiftynineebays.dao.GenericDAO;
import com.fiftynineebays.service.GenericService;
import com.fiftynineebays.service.impl.GenericServiceImpl;
import com.fiftynineebays.dao.ReportDAO;
import com.fiftynineebays.domain.Report;
import com.fiftynineebays.dto.ReportDTO;
import com.fiftynineebays.dto.ReportSearchDTO;
import com.fiftynineebays.dto.ReportPageDTO;
import com.fiftynineebays.dto.ReportConvertCriteriaDTO;
import com.fiftynineebays.dto.common.RequestDTO;
import com.fiftynineebays.dto.common.ResultDTO;
import com.fiftynineebays.service.ReportService;
import com.fiftynineebays.util.ControllerUtils;





@Service
public class ReportServiceImpl extends GenericServiceImpl<Report, Integer> implements ReportService {

    private final static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	ReportDAO reportDao;

	


	@Override
	public GenericDAO<Report, Integer> getDAO() {
		return (GenericDAO<Report, Integer>) reportDao;
	}
	
	public List<Report> findAll () {
		List<Report> reports = reportDao.findAll();
		
		return reports;	
		
	}

	public ResultDTO addReport(ReportDTO reportDTO, RequestDTO requestDTO) {

		Report report = new Report();

		report.setReportId(reportDTO.getReportId());


		report.setReportReason(reportDTO.getReportReason());


		report.setReportDate(reportDTO.getReportDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		report = reportDao.save(report);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Report> getAllReports(Pageable pageable) {
		return reportDao.findAll(pageable);
	}

	public Page<Report> getAllReports(Specification<Report> spec, Pageable pageable) {
		return reportDao.findAll(spec, pageable);
	}

	public ResponseEntity<ReportPageDTO> getReports(ReportSearchDTO reportSearchDTO) {
	
			Integer reportId = reportSearchDTO.getReportId(); 
 			String reportReason = reportSearchDTO.getReportReason(); 
   			String sortBy = reportSearchDTO.getSortBy();
			String sortOrder = reportSearchDTO.getSortOrder();
			String searchQuery = reportSearchDTO.getSearchQuery();
			Integer page = reportSearchDTO.getPage();
			Integer size = reportSearchDTO.getSize();

	        Specification<Report> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, reportId, "reportId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, reportReason, "reportReason"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("reportReason")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Report> reports = this.getAllReports(spec, pageable);
		
		//System.out.println(String.valueOf(reports.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(reports.getTotalPages()));
		
		List<Report> reportsList = reports.getContent();
		
		ReportConvertCriteriaDTO convertCriteria = new ReportConvertCriteriaDTO();
		List<ReportDTO> reportDTOs = this.convertReportsToReportDTOs(reportsList,convertCriteria);
		
		ReportPageDTO reportPageDTO = new ReportPageDTO();
		reportPageDTO.setReports(reportDTOs);
		reportPageDTO.setTotalElements(reports.getTotalElements());
		return ResponseEntity.ok(reportPageDTO);
	}

	public List<ReportDTO> convertReportsToReportDTOs(List<Report> reports, ReportConvertCriteriaDTO convertCriteria) {
		
		List<ReportDTO> reportDTOs = new ArrayList<ReportDTO>();
		
		for (Report report : reports) {
			reportDTOs.add(convertReportToReportDTO(report,convertCriteria));
		}
		
		return reportDTOs;

	}
	
	public ReportDTO convertReportToReportDTO(Report report, ReportConvertCriteriaDTO convertCriteria) {
		
		ReportDTO reportDTO = new ReportDTO();
		
		reportDTO.setReportId(report.getReportId());

	
		reportDTO.setReportReason(report.getReportReason());

	
		reportDTO.setReportDate(report.getReportDate());

	

		
		return reportDTO;
	}

	public ResultDTO updateReport(ReportDTO reportDTO, RequestDTO requestDTO) {
		
		Report report = reportDao.getById(reportDTO.getReportId());

		report.setReportId(ControllerUtils.setValue(report.getReportId(), reportDTO.getReportId()));

		report.setReportReason(ControllerUtils.setValue(report.getReportReason(), reportDTO.getReportReason()));

		report.setReportDate(ControllerUtils.setValue(report.getReportDate(), reportDTO.getReportDate()));



        report = reportDao.save(report);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ReportDTO getReportDTOById(Integer reportId) {
	
		Report report = reportDao.getById(reportId);
			
		
		ReportConvertCriteriaDTO convertCriteria = new ReportConvertCriteriaDTO();
		return(this.convertReportToReportDTO(report,convertCriteria));
	}







}
