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
            <wishlist-table
            v-if="wishlists && wishlists.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:wishlists="wishlists"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-wishlists="getAllWishlists"
             >

            </wishlist-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import WishlistTable from "@/components/WishlistTable";
import WishlistService from "../services/WishlistService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    WishlistTable,
  },
  data() {
    return {
      wishlists: [],
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
    async getAllWishlists(sortBy='wishlistId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await WishlistService.getAllWishlists(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.wishlists.length) {
					this.wishlists = response.data.wishlists;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching wishlists:", error);
        }
        
      } catch (error) {
        console.error("Error fetching wishlist details:", error);
      }
    },
  },
  mounted() {
    this.getAllWishlists();
  },
  created() {
    this.$root.$on('searchQueryForWishlistsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllWishlists();
    })
  }
};
</script>
<style></style>
