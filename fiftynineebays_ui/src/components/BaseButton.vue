<template>
  <component
    :is="tag"
    :type="tag === 'button' ? nativeType : ''"
    :disabled="isDisabled"
    @click="handleClick"
    class="btn"
    :class="buttonClasses"
  >
    <template v-if="loading">
      <i class="fas fa-spinner fa-spin"></i>
    </template>
    <slot></slot>
  </component>
</template>

<script>
export default {
  name: "BaseButton",
  props: {
    tag: {
      type: String,
      default: "button",
    },
    round: Boolean,
    icon: Boolean,
    block: Boolean,
    disabled: Boolean,
    loading: Boolean,
    type: {
      type: String,
      default: "default",
    },
    nativeType: {
      type: String,
      default: "button",
    },
    size: {
      type: String,
      default: "",
    },
    simple: Boolean,
    link: Boolean,
  },
  computed: {
    isDisabled() {
      return this.disabled || this.loading;
    },
    buttonClasses() {
      return [
        { 'btn-round': this.round },
        { 'btn-block': this.block },
        { 'btn-icon btn-fab': this.icon },
        { [`btn-${this.type}`]: this.type },
        { [`btn-${this.size}`]: this.size },
        { 'btn-simple': this.simple },
        { 'btn-link': this.link },
        { disabled: this.disabled && this.tag !== 'button' },
      ];
    },
  },
  methods: {
    handleClick(evt) {
      this.$emit("click", evt);
    },
  },
};
</script>

<style scoped>
.btn {
  display: inline-block;
  font-weight: 400;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.btn-round {
  border-radius: 50px;
}

.btn-block {
  display: block;
  width: 100%;
}

.btn-icon.btn-fab {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  padding: 0;
}

.btn-default {
  color: #212529;
  background-color: #f8f9fa;
  border-color: #f8f9fa;
}

.btn-default:hover {
  color: #212529;
  background-color: #e2e6ea;
  border-color: #dae0e5;
}

.btn-simple {
  background: transparent;
  border: none;
}

.btn-link {
  font-weight: 400;
  color: #007bff;
  background-color: transparent;
  border-color: transparent;
}

.btn-link:hover {
  color: #0056b3;
  text-decoration: underline;
}

.fas.fa-spinner.fa-spin {
  margin-right: 0.5rem;
}
</style>

