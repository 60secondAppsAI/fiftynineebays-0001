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
            <review-table
            v-if="reviews && reviews.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:reviews="reviews"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-reviews="getAllReviews"
             >

            </review-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ReviewTable from "@/components/ReviewTable";
import ReviewService from "../services/ReviewService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ReviewTable,
  },
  data() {
    return {
      reviews: [],
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
    async getAllReviews(sortBy='reviewId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ReviewService.getAllReviews(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.reviews.length) {
					this.reviews = response.data.reviews;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching reviews:", error);
        }
        
      } catch (error) {
        console.error("Error fetching review details:", error);
      }
    },
  },
  mounted() {
    this.getAllReviews();
  },
  created() {
    this.$root.$on('searchQueryForReviewsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllReviews();
    })
  }
};
</script>
<style></style>
