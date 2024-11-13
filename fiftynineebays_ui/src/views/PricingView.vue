<template>
  <div class="pricing-view">
    <!-- <app-header /> -->
    <div class="content">
      <div class="topbar">
        <h2 class="title">See Our Pricing</h2>
        <p class="label">
          You have Free Unlimited Updates and Premium Support on each package.
        </p>

        <router-link class="link" to="/arts-gallery"
          >Create your art</router-link
        >

        <div class="package-types">
          <div
            v-for="(type, index) in packageTypes"
            :key="index"
            class="package-type"
            :class="{ active: activePackageType === type }"
            @click="() => (activePackageType = type)"
          >
            {{ type }}
          </div>
        </div>
      </div>
      <div class="pricing-table-wrapper">
        <div class="card" v-for="(card, index) in packages" :key="index">
          <p class="card-title">{{ card.name }}</p>
          <p class="card-price">
            ${{ activePackageType === "annual" ? card.price * 6 : card.price }}
          </p>
          <div class="packages-list">
            <div class="package" v-for="(pkg, i) in card.package" :key="i">
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
          <app-button
            label="Buy Now"
            class="buy-btn"
            @click="handleBuy(card)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import json from "@/json/data.json";
import AppButton from "@/components/AppButton.vue";
export default {
  name: "PricingView",
  components: {
    "app-button": AppButton,
  },
  data() {
    return {
      packageTypes: ["monthly", "annual"],
      activePackageType: "monthly",
      packages: json.packages,
    };
  },
  methods: {
    handleBuy(card) {
      this.$router.push({
        name: "CheckoutView",
        params: { id: card.name },
      });
    },
  },
};
</script>

<style scoped>
.pricing-view {
}

.topbar {
  height: 400px;
  background: rgb(65, 9, 121);
  background: linear-gradient(
    90deg,
    rgba(65, 9, 121, 1) 0%,
    rgba(121, 9, 118, 0.8939950980392157) 55%
  );
  color: white;
}

.title {
  font-size: 22px;
  padding-top: 2.5rem;
}

.label {
  font-size: 14px;
  padding-top: 0.8rem;
  padding-bottom: 1rem;
}
.package-types {
  width: 300px;
  margin: 3rem auto;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  color: #000;
  gap: 20px;
  padding: 12px 0;
  border-radius: 4px;
}
.package-type {
  cursor: pointer;
  font-size: 16px;
  letter-spacing: 0.04rem;
  text-transform: capitalize;
}

.active {
  color: #0000ff;
}
.pricing-table-wrapper {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: -100px;
  flex-wrap: wrap;
}

.card {
  min-width: 400px;
  color: #444;
  text-align: center;
  border-radius: 1rem;
  box-shadow: 0.5rem 0.5rem 2rem rgba(51, 51, 51, 0.2);
  padding: 1.5rem;
  background: #fff;
}

.card-title {
  font-size: 12px;
  background: #ccc;
  width: max-content;
  margin: 0 auto;
  padding: 6px 10px;
  text-transform: uppercase;
  border-radius: 1rem;
  font-weight: 600;
}
.card-price {
  font-size: 42px;
  font-weight: 600;
  margin-top: 14px;
}
.packages-list {
  margin-top: 24px;
}

.package {
  display: flex;
  align-items: center;
  margin-bottom: 14px;
}

.package-icon {
}

.package-label {
  margin-left: 8px;
}

.buy-btn {
  margin-top: 12px;
  width: 100%;
}

.link {
  color: #fff;
  font-size: 16px;
}

.link:hover {
  color: #0000ff;
}
p,
h2 {
  margin: 0;
  padding: 0;
}
</style>
