<template>
  <div class="container" v-if="status">
    <router-view></router-view>
  </div>
  <!-- <div v-else>
        <router-view></router-view>
    </div> -->
</template>
<script lang="ts">
export default {
  data() {
    return {
      status: true,
      token: null,
    }
  },

  mounted() {
    this.checkToken()
  },

  methods: {
    checkToken(): void {
      if (this.token !== null) {
        this.status = false
      }
    },
  },
}
</script>
