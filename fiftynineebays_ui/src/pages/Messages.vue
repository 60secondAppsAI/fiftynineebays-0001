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
            <message-table
            v-if="messages && messages.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:messages="messages"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-messages="getAllMessages"
             >

            </message-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import MessageTable from "@/components/MessageTable";
import MessageService from "../services/MessageService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    MessageTable,
  },
  data() {
    return {
      messages: [],
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
    async getAllMessages(sortBy='messageId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await MessageService.getAllMessages(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.messages.length) {
					this.messages = response.data.messages;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching messages:", error);
        }
        
      } catch (error) {
        console.error("Error fetching message details:", error);
      }
    },
  },
  mounted() {
    this.getAllMessages();
  },
  created() {
    this.$root.$on('searchQueryForMessagesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllMessages();
    })
  }
};
</script>
<style></style>
