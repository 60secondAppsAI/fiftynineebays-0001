<template>
  <div class="arts-gallery-view">
    <div class="content">
      <div
        class="left-panel"
        :class="{ 'collapse-left-panel': isCollapsedSidebar }"
      >
        <div class="left-wrapper">
          <app-input-field
            v-model="inputField1"
            placeholder="input field 1..."
            type="text"
          />
          <app-input-field
            class="mt-1"
            v-model="inputField2"
            placeholder="input field 2..."
            type="text"
          />
          <app-input-field
            class="mt-1"
            v-model="inputField3"
            placeholder="input field 3..."
            type="text"
          />
          <app-input-field
            class="mt-1"
            v-model="inputField4"
            placeholder="input field 4..."
            type="text"
          />
          <app-input-field
            class="mt-1"
            v-model="inputField5"
            placeholder="input field 5..."
            type="text"
          />
          <app-input-field
            class="mt-1"
            v-model="quantities"
            placeholder="Quantities..."
            type="number"
          />
          <app-button
            class="mt-1 generate-btn"
            label="Generate My Designs"
            @click="handleClick"
          />
        </div>
      </div>
      <div
        class="right-panel"
        :class="{ 'collapse-right-panel': isCollapsedSidebar }"
      >
        <div class="topbar">
          <app-dropdown v-model="selectedFilter" :options="options" />
          <div class="menu-logo">
            <img
              src="../assets/menu-logo.png"
              alt="logo"
              @click="collapseSidebar"
            />
          </div>
        </div>
        <div class="">
          <div class="loading-message" v-if="isGeneratingImages">
            Loading...
          </div>
          <div class="image-wrapper" v-else>
            <div
              v-for="(image, index) in imagesList"
              :key="index"
              class="image"
            >
              <img
                class="img"
                :src="require(`../assets/${image.path}.png`)"
                alt="img"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AppInputField from "@/components/AppInputField.vue";
import AppButton from "@/components/AppButton.vue";
import AppDropdown from "@/components/AppDropdown.vue";

export default {
  name: "ArtsGalleryView",
  components: {
    "app-input-field": AppInputField,
    "app-button": AppButton,
    "app-dropdown": AppDropdown,
  },
  data() {
    return {
      isCollapsedSidebar: false,
      isGeneratingImages: false,
      inputField1: "",
      inputField2: "",
      inputField3: "",
      inputField4: "",
      inputField5: "",
      quantities: 4,
      selectedFilter: "all",
      options: [
        { value: "all", name: "All" },
        { value: "unranked", name: "My Unranked Art" },
        { value: "favorite", name: "My Favorite Art" },
        { value: "discarded", name: "My Discarded Art" },
      ],
      galleryList: [
        { path: "a01", type: "unranked" },
        { path: "a02", type: "favorite" },
        { path: "a03", type: "unranked" },
        { path: "a04", type: "discarded" },
        { path: "a05", type: "favorite" },
        { path: "a06", type: "unranked" },
        { path: "a07", type: "unranked" },
        { path: "a08", type: "discarded" },
        { path: "a01", type: "favorite" },
        { path: "a02", type: "unranked" },
        { path: "a04", type: "discarded" },
        { path: "a07", type: "unranked" },
        { path: "a04", type: "favorite" },
        { path: "a01", type: "discarded" },
        { path: "a03", type: "unranked" },
        { path: "a02", type: "discarded" },
        { path: "a08", type: "favorite" },
        { path: "a09", type: "discarded" },
        { path: "a10", type: "discarded" },
        { path: "a02", type: "unranked" },
      ],
      path: "a02",
    };
  },
  computed: {
    imagesList() {
      return this.selectedFilter === "all"
        ? this.galleryList
        : this.galleryList.filter((img) => img.type === this.selectedFilter);
    },
  },
  methods: {
    collapseSidebar() {
      this.isCollapsedSidebar = !this.isCollapsedSidebar;
    },
    handleClick() {
      this.isGeneratingImages = true;
      setTimeout(() => {
        this.isGeneratingImages = false;
      }, 2000);
    },
  },
};
</script>

<style scoped>
.arts-gallery-view {
  background: #2a2a2a;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}
.content {
  display: flex;
}

.left-panel {
  width: 400px;
  padding-top: 3rem;
  position: fixed;
  left: 0;
  background: #000;
  bottom: 0;
  top: 66px;
  transition: ease-in-out 0.6s;
}
.collapse-left-panel {
  width: 0;
  opacity: 0;
}

.left-wrapper {
  padding: 0 14px;
}

.generate-btn {
  width: 100%;
}

.right-panel {
  height: 100vh;
  padding-top: 3rem;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 450px;
  overflow-y: auto;
  transition: ease-in-out 0.6s;
}

.collapse-right-panel {
  padding-left: 50px;
}
.mt-1 {
  margin-top: 16px;
}
.loading-message {
  font-size: 14px;
  color: #fff;
  display: flex;
  align-items: center;
  margin-top: 1.5rem;
}
.image-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  padding: 1.5rem 0 4rem 0;
}
.image {
  width: 200px;
  height: 200px;
  border-radius: 10px;
  background: #eee;
  overflow: hidden;
}
.img {
  width: 100%;
  height: 100%;
}

.topbar {
  display: flex;
  align-items: center;
}
.menu-logo {
  margin-left: 16px;
  cursor: pointer;
}

@media screen and (max-width: 768px) {
  .left-panel {
    width: 240px;
  }
  .right-panel {
    padding-left: 20px;
  }
}
</style>
