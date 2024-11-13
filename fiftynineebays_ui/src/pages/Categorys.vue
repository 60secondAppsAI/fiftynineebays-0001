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
            <category-table
            v-if="categorys && categorys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:categorys="categorys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-categorys="getAllCategorys"
             >

            </category-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CategoryTable from "@/components/CategoryTable";
import CategoryService from "../services/CategoryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CategoryTable,
  },
  data() {
    return {
      categorys: [],
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
    async getAllCategorys(sortBy='categoryId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CategoryService.getAllCategorys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.categorys.length) {
					this.categorys = response.data.categorys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching categorys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching category details:", error);
      }
    },
  },
  mounted() {
    this.getAllCategorys();
  },
  created() {
    this.$root.$on('searchQueryForCategorysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCategorys();
    })
  }
};
</script>
<style></style>
