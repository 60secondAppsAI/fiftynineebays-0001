<template>
  <div
    class="form-group"
    :class="{
      'input-group': hasIcon,
      'input-group-focus': focused,
    }"
  >
    <template v-if="label">
      <label class="control-label">{{ label }}</label>
    </template>
    <template v-if="addonLeftIcon">
      <span class="input-group-prepend">
        <div class="input-group-text">
          <i :class="addonLeftIcon"></i>
        </div>
      </span>
    </template>
    <input
      :value="value"
      v-bind="$attrs"
      @input="onInput"
      @focus="onFocus"
      @blur="onBlur"
      class="form-control"
      aria-describedby="addon-right addon-left"
    />
    <template v-if="addonRightIcon">
      <span class="input-group-append">
        <div class="input-group-text">
          <i :class="addonRightIcon"></i>
        </div>
      </span>
    </template>
    <slot name="helperText"></slot>
  </div>
</template>

<script>
export default {
  name: "BaseInput",
  props: {
    label: String,
    value: [String, Number],
    addonRightIcon: String,
    addonLeftIcon: String,
  },
  data() {
    return {
      focused: false,
    };
  },
  computed: {
    hasIcon() {
      return this.addonRightIcon || this.addonLeftIcon;
    },
  },
  methods: {
    onInput(event) {
      this.$emit("input", event.target.value);
    },
    onFocus() {
      this.focused = true;
    },
    onBlur() {
      this.focused = false;
    },
  },
};
</script>

<style scoped>
.form-group {
  position: relative;
  margin-bottom: 1rem;
}

.input-group {
  display: flex;
  align-items: center;
}

.input-group-prepend,
.input-group-append {
  display: flex;
}

.input-group-text {
  display: flex;
  align-items: center;
  padding: 0.375rem 0.75rem;
  margin-bottom: 0;
}

.control-label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-control {
  display: block;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.input-group-focus .form-control {
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
</style>

