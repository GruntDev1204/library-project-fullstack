<template>
  <div class="container mt-5">
    <div v-bind:class="class">
      <h3 class="medium">
        API server is disconnected or is under maintenance!
        <i class="fa-solid fa-plug-circle-xmark" v-on:click="callApi()"></i>
      </h3>
      <h3 class="mt-3 small">
        Đừng ấn vào cái nút này!
        <i class="fa-solid fa-face-sad-cry" v-on:click="activeEffect()"></i>
      </h3>
    </div>
  </div>
  <Notification ref="notificationRef"></Notification>
</template>

<script lang="ts">
import Notification from "../notification/Notification.vue"

export default {
  components: {
    Notification,
  },
  data(): { alert: any; effectSet: boolean; class: string } {
    return {
      class: "alert alert-notice text-center",
      effectSet: false,
      alert: null,
    }
  },
  mounted() {
    this.alert = this.$refs.notificationRef
  },
  methods: {
    activeEffect() {
      this.effectSet = !this.effectSet
      if (this.effectSet) {
        this.class = "alert alert-notice text-center effect-fan"
      }
    },
    callApi() {
      this.alert.showAlert(
        "danger",
        "API server is disconnected or is under maintenance! Please try later!"
      )
    },
  },
}
</script>
