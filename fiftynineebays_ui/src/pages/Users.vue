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
            <user-table
            v-if="users && users.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:users="users"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-users="getAllUsers"
             >

            </user-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import UserTable from "@/components/UserTable";
import UserService from "../services/UserService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    UserTable,
  },
  data() {
    return {
      users: [],
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
    async getAllUsers(sortBy='userId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await UserService.getAllUsers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.users.length) {
					this.users = response.data.users;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching users:", error);
        }
        
      } catch (error) {
        console.error("Error fetching user details:", error);
      }
    },
  },
  mounted() {
    this.getAllUsers();
  },
  created() {
    this.$root.$on('searchQueryForUsersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllUsers();
    })
  }
};
</script>
<style></style>
