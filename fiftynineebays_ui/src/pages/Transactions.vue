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
            <transaction-table
            v-if="transactions && transactions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:transactions="transactions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-transactions="getAllTransactions"
             >

            </transaction-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import TransactionTable from "@/components/TransactionTable";
import TransactionService from "../services/TransactionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    TransactionTable,
  },
  data() {
    return {
      transactions: [],
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
    async getAllTransactions(sortBy='transactionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await TransactionService.getAllTransactions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.transactions.length) {
					this.transactions = response.data.transactions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching transactions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching transaction details:", error);
      }
    },
  },
  mounted() {
    this.getAllTransactions();
  },
  created() {
    this.$root.$on('searchQueryForTransactionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllTransactions();
    })
  }
};
</script>
<style></style>
