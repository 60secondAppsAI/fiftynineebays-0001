<template>
  <div id="app" class="container">
    <component :is="layout">
      <router-view/>
    </component>
  </div>
</template>

<script>
import DefaultLayout from "./layouts/DefaultLayout.vue";

export default {
  name: "App",
  computed: {
    layout() {
      // Use the route meta to determine the layout, defaulting to DefaultLayout
      const routeMeta = this.$route.meta;
      return routeMeta && routeMeta.layout ? routeMeta.layout : "DefaultLayout";
    },
  },
  components: {
    DefaultLayout,
  },
};
</script>

<style>
body {
  margin: 0;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100vw;
  height: 100vh;
  overflow-x: hidden;
}
.container {
  margin: 0 auto;
}
</style>

