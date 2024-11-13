<template>
  <button class="app-button" @click="handleClick">{{ label }}</button>
</template>

<script>
export default {
  name: "AppButton",
  props: {
    label: {
      default: "",
      type: String,
    },
  },
  methods: {
    handleClick() {
      this.$emit("click");
    },
  },
};
</script>

<style scoped>
.app-button {
  background: #0000ff;
  border: none;
  color: #fff;
  font-size: 16px;
  padding: 10px 0;
  border-radius: 6px;
  width: 250px;
  cursor: pointer;
}
</style>
