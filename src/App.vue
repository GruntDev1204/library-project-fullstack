<template>
  <div v-if="isCheckApi">
    <DisconnectionAPI></DisconnectionAPI>
  </div>
  <div v-else>
    <Header></Header>
    <Main></Main>
  </div>
</template>

<script lang="ts">
import Main from "./components/Main.vue"
import Header from "./components/Header.vue"
import DisconnectionAPI from "./components/incident/DisconnectionAPI.vue"
import baseAPI from "./assets/api/baseAPI"

export default {
  components: {
    DisconnectionAPI,
    Main,
    Header,
  },

  data() {
    return {
      isCheckApi: false,
    }
  },

  mounted() {
    this.checkApi()
  },

  methods: {
    async checkApi() {
      this.isCheckApi = await baseAPI.check()
    },
  },
}
</script>
