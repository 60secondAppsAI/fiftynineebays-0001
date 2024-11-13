te
<template>
  <div class="signup-view">
    <div class="card">
      <app-input-field
        v-model="email"
        placeholder="example@gmail.com"
        class="input-field"
      />
      <app-button label="Sign up with email" @click="handleClick" class="btn" />
      <div class="seperator"></div>
      <app-button
        label="Sign up with google"
        @click="handleClick"
        class="btn"
      />
    </div>
  </div>
</template>

<script>
import AppButton from "@/components/AppButton.vue";
import AppInputField from "@/components/AppInputField.vue";
export default {
  name: "SignupView",
  components: {
    "app-button": AppButton,
    "app-input-field": AppInputField,
  },
  data() {
    return {
      email: "",
    };
  },
  methods: {
    handleClick() {
      this.$router.push("/arts-gallery");
    },
  },
};
</script>

<style scoped>
.signup-view {
  margin-top: 4rem;
}
.card {
  box-shadow: 4px 6px 42px #cfc9c9;
  width: 400px;
  margin: 0 auto;
  padding: 3rem;
  border-radius: 10px;
}

.input-field {
  border-color: #444444e0;
  margin-bottom: 1rem;
  color: #444;
}

.input-field::placeholder {
  color: #444444e0;
}

.seperator {
  margin: 2rem auto;
  width: 180px;
  position: relative;
  border-bottom: 2px solid #d7d4d4;
}
.seperator::before {
  content: "OR";
  position: absolute;
  top: -7px;
  left: 70px;
  padding: 0 8px;
  background: #fff;
}

.btn {
  width: 100%;
}
</style>
