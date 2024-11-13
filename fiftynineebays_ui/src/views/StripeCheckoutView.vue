<template>
  <div class="stripe-checkout-view">
    <div class="stripe-card">
      <img src="../assets/stripe-checkout.png" alt="" />
    </div>
  </div>
</template>

<script>
export default {
  name: "StripeCheckoutView",
  data() {
    return {};
  },
};
</script>

<style scoped>
.stripe-card {
  max-width: 400px;
  text-align: left;
  border-radius: 1rem;
  box-shadow: 0.5rem 0.5rem 2rem rgba(51, 51, 51, 0.2);
  padding: 1.5rem;
  background: #fff;
  margin: 1rem auto;
}
</style>
