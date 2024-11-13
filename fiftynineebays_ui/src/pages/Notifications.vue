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
            <notification-table
            v-if="notifications && notifications.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:notifications="notifications"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-notifications="getAllNotifications"
             >

            </notification-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import NotificationTable from "@/components/NotificationTable";
import NotificationService from "../services/NotificationService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    NotificationTable,
  },
  data() {
    return {
      notifications: [],
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
    async getAllNotifications(sortBy='notificationId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await NotificationService.getAllNotifications(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.notifications.length) {
					this.notifications = response.data.notifications;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching notifications:", error);
        }
        
      } catch (error) {
        console.error("Error fetching notification details:", error);
      }
    },
  },
  mounted() {
    this.getAllNotifications();
  },
  created() {
    this.$root.$on('searchQueryForNotificationsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllNotifications();
    })
  }
};
</script>
<style></style>
