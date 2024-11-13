<template>
  <transition name="slide-y-up">
    <div
      class="modal fade"
      @click.self="closeModal"
      :class="modalClasses"
      v-show="show"
      tabindex="-1"
      role="dialog"
      :aria-hidden="!show"
    >
      <div class="modal-dialog" :class="dialogClasses">
        <div class="modal-content" :class="contentClasses">
          <div class="modal-header" :class="headerClasses" v-if="$slots.header">
            <slot name="header"></slot>
            <button
              type="button"
              class="close"
              v-if="showClose"
              @click="closeModal"
              aria-label="Close"
            >
              <i class="icon-close"></i>
            </button>
          </div>
          <div v-if="$slots.default" class="modal-body" :class="bodyClasses">
            <slot></slot>
          </div>
          <div class="modal-footer" :class="footerClasses" v-if="$slots.footer">
            <slot name="footer"></slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "BaseModal",
  props: {
    show: {
      type: Boolean,
      required: true,
    },
    showClose: {
      type: Boolean,
      default: true,
    },
    centered: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "",
      validator(value) {
        return ["", "notice", "mini"].includes(value);
      },
    },
    modalClasses: {
      type: [Object, String],
      default: "",
    },
    modalContentClasses: {
      type: [Object, String],
      default: "",
    },
    gradient: {
      type: String,
      default: "",
    },
    headerClasses: {
      type: [Object, String],
      default: "",
    },
    bodyClasses: {
      type: [Object, String],
      default: "",
    },
    footerClasses: {
      type: [Object, String],
      default: "",
    },
  },
  watch: {
    show(val) {
      if (val) {
        document.body.classList.add("modal-open");
      } else {
        document.body.classList.remove("modal-open");
      }
    },
  },
  computed: {
    dialogClasses() {
      return [
        { 'modal-notice': this.type === 'notice' },
        { 'modal-dialog-centered': this.centered },
        this.modalClasses,
      ];
    },
    contentClasses() {
      return [
        this.gradient ? `bg-gradient-${this.gradient}` : '',
        this.modalContentClasses,
      ];
    },
  },
  methods: {
    closeModal() {
      this.$emit("update:show", false);
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal {
  display: none;
  overflow: hidden;
}

.modal-open .modal {
  display: block;
}

.modal.fade .modal-dialog {
  transition: transform 0.3s ease-out;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #dee2e6;
}

.modal-body {
  position: relative;
  padding: 1rem;
}

.modal-footer {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-end;
  padding: 1rem;
  border-top: 1px solid #dee2e6;
}

.close {
  font-size: 1.5rem;
  font-weight: 700;
  line-height: 1;
  color: #000;
  text-shadow: 0 1px 0 #fff;
  opacity: 0.5;
}

.icon-close {
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1;
  color: #000;
}
</style>

