<template>
  <div class="checkout-view">
    <div class="chekout-details-card" v-if="currentPackage">
      <p class="package-name">
        Package Name: <span>{{ currentPackage.name }}</span>
      </p>
      <p class="package-price">
        Package Price: <span>${{ currentPackage.price }}</span>
      </p>
      <div class="features">
        <p class="feature-label">Package Features</p>
        <div
          class="package"
          v-for="(pkg, i) in currentPackage.package"
          :key="i"
        >
          <img
            v-if="pkg.access"
            src="../assets/access-icon.png"
            alt="icon"
            class="package-icon"
          />
          <img
            v-else
            src="../assets/not-access-icon.png"
            alt="icon"
            class="package-icon"
          />
          <span class="package-label">{{ pkg.name }}</span>
        </div>
      </div>
      <app-button label="Pay Now" class="pay-btn" @click="handlePay" />
    </div>
  </div>
</template>

<script>
import json from "@/json/data.json";
import AppButton from "@/components/AppButton.vue";
export default {
  name: "CheckoutView",
  components: {
    "app-button": AppButton,
  },
  data() {
    return {
      currentPackage: {},
    };
  },
  created() {
    const id = this.$route.params.id;
    if (id && json) {
      this.currentPackage = json.packages.find((p) => p.name === id);
    }
  },
  methods: {
    handlePay() {
      this.$router.push("/stripe-checkout");
    },
  },
};
</script>

<style scoped>
.checkout-view {
}
.chekout-details-card {
  max-width: 800px;
  color: #444;
  text-align: left;
  border-radius: 1rem;
  box-shadow: 0.5rem 0.5rem 2rem rgba(51, 51, 51, 0.2);
  padding: 1.5rem;
  background: #fff;
  margin: 1rem auto;
}

.package-name,
.package-price {
  font-size: 18px;
  margin-bottom: 4px;
}
.package-name span,
.package-price span {
  text-transform: uppercase;
  font-weight: 600;
  font-size: 16px;
}

.features {
  margin-top: 16px;
}
.feature-label {
  margin: 10px 0;
}

.package {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.package-label {
  margin-left: 8px;
}

.pay-btn {
  margin-top: 12px;
  /* width: 100%; */
}

p {
  margin: 0;
  padding: 0;
}
</style>
