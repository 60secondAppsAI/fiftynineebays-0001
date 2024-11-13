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
            <offer-table
            v-if="offers && offers.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:offers="offers"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-offers="getAllOffers"
             >

            </offer-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import OfferTable from "@/components/OfferTable";
import OfferService from "../services/OfferService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OfferTable,
  },
  data() {
    return {
      offers: [],
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
    async getAllOffers(sortBy='offerId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OfferService.getAllOffers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.offers.length) {
					this.offers = response.data.offers;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching offers:", error);
        }
        
      } catch (error) {
        console.error("Error fetching offer details:", error);
      }
    },
  },
  mounted() {
    this.getAllOffers();
  },
  created() {
    this.$root.$on('searchQueryForOffersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOffers();
    })
  }
};
</script>
<style></style>
