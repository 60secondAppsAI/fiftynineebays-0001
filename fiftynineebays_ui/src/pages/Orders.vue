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
            <order-table
            v-if="orders && orders.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:orders="orders"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-orders="getAllOrders"
             >

            </order-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import OrderTable from "@/components/OrderTable";
import OrderService from "../services/OrderService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OrderTable,
  },
  data() {
    return {
      orders: [],
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
    async getAllOrders(sortBy='orderId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OrderService.getAllOrders(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.orders.length) {
					this.orders = response.data.orders;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching orders:", error);
        }
        
      } catch (error) {
        console.error("Error fetching order details:", error);
      }
    },
  },
  mounted() {
    this.getAllOrders();
  },
  created() {
    this.$root.$on('searchQueryForOrdersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOrders();
    })
  }
};
</script>
<style></style>
