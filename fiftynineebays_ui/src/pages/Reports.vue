<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <report-table
            v-if="reports && reports.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:reports="reports"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-reports="getAllReports"
             >

            </report-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ReportTable from "@/components/ReportTable";
import ReportService from "../services/ReportService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ReportTable,
  },
  data() {
    return {
      reports: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllReports(sortBy='reportId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ReportService.getAllReports(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.reports.length) {
					this.reports = response.data.reports;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching reports:", error);
        }
        
      } catch (error) {
        console.error("Error fetching report details:", error);
      }
    },
  },
  mounted() {
    this.getAllReports();
  },
  created() {
    this.$root.$on('searchQueryForReportsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllReports();
    })
  }
};
</script>
<style></style>
