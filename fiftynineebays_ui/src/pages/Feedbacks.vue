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
            <feedback-table
            v-if="feedbacks && feedbacks.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:feedbacks="feedbacks"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-feedbacks="getAllFeedbacks"
             >

            </feedback-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import FeedbackTable from "@/components/FeedbackTable";
import FeedbackService from "../services/FeedbackService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    FeedbackTable,
  },
  data() {
    return {
      feedbacks: [],
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
    async getAllFeedbacks(sortBy='feedbackId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await FeedbackService.getAllFeedbacks(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.feedbacks.length) {
					this.feedbacks = response.data.feedbacks;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching feedbacks:", error);
        }
        
      } catch (error) {
        console.error("Error fetching feedback details:", error);
      }
    },
  },
  mounted() {
    this.getAllFeedbacks();
  },
  created() {
    this.$root.$on('searchQueryForFeedbacksChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllFeedbacks();
    })
  }
};
</script>
<style></style>
