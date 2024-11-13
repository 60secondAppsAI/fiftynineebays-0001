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
            <payment-table
            v-if="payments && payments.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:payments="payments"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-payments="getAllPayments"
             >

            </payment-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PaymentTable from "@/components/PaymentTable";
import PaymentService from "../services/PaymentService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PaymentTable,
  },
  data() {
    return {
      payments: [],
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
    async getAllPayments(sortBy='paymentId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PaymentService.getAllPayments(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.payments.length) {
					this.payments = response.data.payments;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching payments:", error);
        }
        
      } catch (error) {
        console.error("Error fetching payment details:", error);
      }
    },
  },
  mounted() {
    this.getAllPayments();
  },
  created() {
    this.$root.$on('searchQueryForPaymentsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPayments();
    })
  }
};
</script>
<style></style>
