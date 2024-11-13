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
            <address-table
            v-if="addresss && addresss.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:addresss="addresss"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-addresss="getAllAddresss"
             >

            </address-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import AddressTable from "@/components/AddressTable";
import AddressService from "../services/AddressService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    AddressTable,
  },
  data() {
    return {
      addresss: [],
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
    async getAllAddresss(sortBy='addressId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await AddressService.getAllAddresss(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.addresss.length) {
					this.addresss = response.data.addresss;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching addresss:", error);
        }
        
      } catch (error) {
        console.error("Error fetching address details:", error);
      }
    },
  },
  mounted() {
    this.getAllAddresss();
  },
  created() {
    this.$root.$on('searchQueryForAddresssChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllAddresss();
    })
  }
};
</script>
<style></style>
