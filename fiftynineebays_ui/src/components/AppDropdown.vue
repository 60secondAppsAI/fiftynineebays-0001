<template>
  <div class="select-dropdown">
    <select
      class="custom-select"
      name=""
      id=""
      @input="
        (evt) => {
          $emit('change', evt.target.value);
        }
      "
      v-model="value"
    >
      <template v-for="(option, index) in options">
        <option
          class="option"
          :key="index"
          :value="option.value"
          v-text="option.name"
        ></option>
      </template>
    </select>
  </div>
</template>

<script>
export default {
  name: "AppDropdown",
  data() {
    return {
      value: "all",
    };
  },
  props: {
    options: {
      type: Array,
      default: () => [],
    },
  },
  model: {
    prop: "value",
    event: "change",
  },
};
</script>

<style scoped>
.select-dropdown,
.select-dropdown * {
  margin: 0;
  padding: 0;
  position: relative;
  box-sizing: border-box;
}
.select-dropdown {
  position: relative;
  border-radius: 4px;
  background: transparent;
  border: 1px solid #fff;
}
.select-dropdown select {
  font-size: 1rem;
  font-weight: normal;
  max-width: 100%;
  padding: 8px 24px 10px 10px;
  border: none;
  background-color: transparent;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  color: #fff;
  margin-bottom: -2px;
}
.select-dropdown select:active,
.select-dropdown select:focus {
  outline: none;
  box-shadow: none;
}
.select-dropdown:after {
  content: "";
  position: absolute;
  top: 50%;
  right: 8px;
  width: 0;
  height: 0;
  margin-top: -2px;
  border-top: 5px solid #aaa;
  border-right: 5px solid transparent;
  border-left: 5px solid transparent;
}
.select-dropdown option {
  background: #eee;
  color: #444;
  font-size: 14px;
}
</style>
