<template>
  <input
    class="app-input-field"
    :type="type"
    :placeholder="placeholder"
    @input="onInput"
    v-model="inputField"
  />
</template>

<script>
export default {
  name: "AppInputField",
  data() {
    return {
      inputField: "",
    };
  },
  props: {
    type: {
      default: "text",
      type: String,
    },
    placeholder: {
      default: "",
      type: String,
    },
  },
  methods: {
    onInput(event) {
      this.$emit("input", event.target.value);
    },
  },
};
</script>

<style scoped>
.app-input-field {
  background: transparent;
  border: 1px solid #fff;
  padding: 12px 8px;
  border-radius: 4px;
  width: -webkit-fill-available;
  color: #fff;
  font-size: 14px;
}

.app-input-field::placeholder {
  color: #ffffffab;
}
</style>
