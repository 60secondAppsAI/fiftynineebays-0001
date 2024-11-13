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
            <return-table
            v-if="returns && returns.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:returns="returns"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-returns="getAllReturns"
             >

            </return-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ReturnTable from "@/components/ReturnTable";
import ReturnService from "../services/ReturnService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ReturnTable,
  },
  data() {
    return {
      returns: [],
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
    async getAllReturns(sortBy='returnId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ReturnService.getAllReturns(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.returns.length) {
					this.returns = response.data.returns;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching returns:", error);
        }
        
      } catch (error) {
        console.error("Error fetching return details:", error);
      }
    },
  },
  mounted() {
    this.getAllReturns();
  },
  created() {
    this.$root.$on('searchQueryForReturnsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllReturns();
    })
  }
};
</script>
<style></style>
