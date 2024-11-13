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
            <cart-table
            v-if="carts && carts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:carts="carts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-carts="getAllCarts"
             >

            </cart-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CartTable from "@/components/CartTable";
import CartService from "../services/CartService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CartTable,
  },
  data() {
    return {
      carts: [],
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
    async getAllCarts(sortBy='cartId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CartService.getAllCarts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.carts.length) {
					this.carts = response.data.carts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching carts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching cart details:", error);
      }
    },
  },
  mounted() {
    this.getAllCarts();
  },
  created() {
    this.$root.$on('searchQueryForCartsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCarts();
    })
  }
};
</script>
<style></style>
