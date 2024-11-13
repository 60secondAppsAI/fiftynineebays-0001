<template>
  <div class="card" :class="cardClasses">
    <div class="card-image" v-if="$slots.image">
      <slot name="image"></slot>
    </div>
    <div class="card-header" :class="headerClass" v-if="$slots.header || title">
      <slot name="header">
        <template v-if="subTitle">
          <h5 class="card-category">{{ subTitle }}</h5>
        </template>
        <h2 class="card-title">
          <i v-if="icon" class="icon" :class="icon"></i>{{ title }}
        </h2>
      </slot>
    </div>
    <div class="card-body" v-if="$slots.default">
      <slot></slot>
    </div>
    <div class="card-image" v-if="$slots['image-bottom']">
      <slot name="image-bottom"></slot>
    </div>
    <slot name="raw-content"></slot>
    <div class="card-footer text-left" v-if="$slots.footer">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "BaseCard",
  props: {
    title: String,
    subTitle: String,
    type: String,
    icon: String,
  },
  computed: {
    cardClasses() {
      return this.type ? `card-${this.type}` : '';
    },
    headerClass() {
      return this.isRTL ? 'text-right' : 'text-left';
    },
    isRTL() {
      return this.$root.isRTL;
    },
  },
};
</script>

<style scoped>
.card {
  border: 1px solid #e3e3e3;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 1rem;
}

.card-header {
  padding: 0.75rem 1.25rem;
  background-color: #f7f7f7;
  border-bottom: 1px solid #e3e3e3;
}

.card-title {
  margin-bottom: 0;
  font-size: 1.25rem;
}

.card-category {
  margin: 0;
  color: #999;
}

.card-body {
  padding: 1.25rem;
}

.card-footer {
  padding: 0.75rem 1.25rem;
  background-color: #f7f7f7;
  border-top: 1px solid #e3e3e3;
}

.icon {
  margin-right: 0.5rem;
}
</style>

