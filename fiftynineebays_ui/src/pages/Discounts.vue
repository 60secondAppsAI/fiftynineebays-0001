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
            <discount-table
            v-if="discounts && discounts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:discounts="discounts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-discounts="getAllDiscounts"
             >

            </discount-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import DiscountTable from "@/components/DiscountTable";
import DiscountService from "../services/DiscountService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    DiscountTable,
  },
  data() {
    return {
      discounts: [],
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
    async getAllDiscounts(sortBy='discountId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await DiscountService.getAllDiscounts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.discounts.length) {
					this.discounts = response.data.discounts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching discounts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching discount details:", error);
      }
    },
  },
  mounted() {
    this.getAllDiscounts();
  },
  created() {
    this.$root.$on('searchQueryForDiscountsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllDiscounts();
    })
  }
};
</script>
<style></style>
