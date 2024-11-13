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
            <listing-table
            v-if="listings && listings.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:listings="listings"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-listings="getAllListings"
             >

            </listing-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ListingTable from "@/components/ListingTable";
import ListingService from "../services/ListingService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ListingTable,
  },
  data() {
    return {
      listings: [],
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
    async getAllListings(sortBy='listingId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ListingService.getAllListings(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.listings.length) {
					this.listings = response.data.listings;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching listings:", error);
        }
        
      } catch (error) {
        console.error("Error fetching listing details:", error);
      }
    },
  },
  mounted() {
    this.getAllListings();
  },
  created() {
    this.$root.$on('searchQueryForListingsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllListings();
    })
  }
};
</script>
<style></style>
