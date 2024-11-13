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
            <bid-table
            v-if="bids && bids.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:bids="bids"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-bids="getAllBids"
             >

            </bid-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import BidTable from "@/components/BidTable";
import BidService from "../services/BidService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    BidTable,
  },
  data() {
    return {
      bids: [],
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
    async getAllBids(sortBy='bidId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await BidService.getAllBids(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.bids.length) {
					this.bids = response.data.bids;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching bids:", error);
        }
        
      } catch (error) {
        console.error("Error fetching bid details:", error);
      }
    },
  },
  mounted() {
    this.getAllBids();
  },
  created() {
    this.$root.$on('searchQueryForBidsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllBids();
    })
  }
};
</script>
<style></style>
