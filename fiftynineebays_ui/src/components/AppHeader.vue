<template>
  <div class="app-header">
    <div>
      <router-link to="/">
    	<section class="section-1">
    		<div class="title">
				<span data-desktop-color="rgba(0,97,249,1)" style="color: rgb(0, 97, 249);">Fiftynineebays</span>
			</div>
			<div class="subtitle">
				<span data-desktop-color="rgba(0,97,249,1)" style="color: rgb(0, 97, 249);">built by 60secondApps.AI</span>
			</div>
		</section>
      </router-link>
    </div>
    <div>
    <router-link class="link" to="/${application.defaultViewAfterSignIn}">

	  <img src="images/drc-velocity.png"
        class="logo"
        alt=""
      />
    </router-link>

    </div>
    <div class="links">
      <nav class="nav">
	        <router-link class="link" to="/listings">My Listings</router-link>
            
																				        <router-link class="link" to="/sign-in">Menu-item-1</router-link>
        <router-link class="link" to="/users">Menu-item-2</router-link>
        <router-link class="link" to="/sign-in">Menu-item-3</router-link>
        <router-link class="link" to="/sign-in">Menu-item-4</router-link>
        <router-link class="link" to="/pricing">Pricing </router-link>

      </nav>
      <div class="avator">
        <img src="../assets/avator.jpeg" alt="avator" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppHeader",
  props: {
    msg: String,
  },
  methods: {
    handleClick() {
      if (this.$route.name && this.$route.name !== "home") {
        this.$router.push("/");
      }
    },
  },
};
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  box-shadow: 0px -10px 23px rgba(51, 51, 51, 0.2);
  position: sticky;
  top: 0;
  background: #fff;
}
.logo {
  height: 30px;
  cursor: pointer;
}
.links {
  display: flex;
  align-items: center;
}
.link {
  margin-right: 1rem;
  color: #444;
  text-transform: uppercase;
  text-decoration: none;
  font-size: 14px;
}

.link:hover {
  text-decoration: underline;
  color: #0000ff;
}

.avator img {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  border: 1px solid #ccc;
}

.section-1 {
  line-height: 34px;
  background-color: white;
  color: rgb(0, 97, 249);
  text-align: center;
  font-weight: 500;
  opacity: 1;
  font-size: 30px;
  font-style: italic;
}

.section-1 .subtitle {
  background-color: white;
  color: rgb(0, 97, 249);
  text-align: center;
  font-weight: 400;
  opacity: 1;
  font-size: 16px;
  font-style: italic;
}
</style>

