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
            <subscription-table
            v-if="subscriptions && subscriptions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:subscriptions="subscriptions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-subscriptions="getAllSubscriptions"
             >

            </subscription-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import SubscriptionTable from "@/components/SubscriptionTable";
import SubscriptionService from "../services/SubscriptionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    SubscriptionTable,
  },
  data() {
    return {
      subscriptions: [],
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
    async getAllSubscriptions(sortBy='subscriptionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await SubscriptionService.getAllSubscriptions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.subscriptions.length) {
					this.subscriptions = response.data.subscriptions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching subscriptions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching subscription details:", error);
      }
    },
  },
  mounted() {
    this.getAllSubscriptions();
  },
  created() {
    this.$root.$on('searchQueryForSubscriptionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllSubscriptions();
    })
  }
};
</script>
<style></style>
