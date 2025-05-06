<template lang="">
  <div class="header">
    <div class="logo">
      <div class="img">
        <img v-bind:src="logo" alt="logo" />
      </div>
      <h3><router-link to="/">PC04</router-link></h3>
    </div>
    <ul class="menu">
      <li class="small">
        <router-link to="/book"> <i class="fas fa-book"></i> Book</router-link>
      </li>
      <li class="small">
        <router-link to="/category">
          <i class="fas fa-list"></i> Category</router-link
        >
      </li>
      <li class="small">
        <router-link to="/transaction"
          ><i class="fas fa-check"></i> Transaction</router-link
        >
      </li>
    </ul>

    <div class="profile" v-if="statusLogin">
      <div class="avatar">
        <img v-bind:src="avatar" alt="logo" />
      </div>
      <div class="infomation">
        <p v-on:click="handleOpenInfo()">
          <i class="fas fa-user"></i> <span>Admin</span>
          <i class="fas fa-caret-down"></i>
        </p>
      </div>
    </div>

    <div class="login" v-else></div>
  </div>
  <div v-if="openInfo"><Infomation></Infomation></div>
</template>
<script lang="ts">
import Infomation from "./header/Infomation.vue"

export default {
  components: {
    Infomation,
  },

  data() {
    return {
      logo: "./assets/media/imgs/logoPage.png",
      avatar:
        "https://thuvienmeme.com/wp-content/uploads/2024/03/meme-cong-an-bao-thanh-thien.jpg",
      statusLogin: true,
      openInfo: false,
    }
  },

  methods: {
    handleOpenInfo() {
      this.openInfo = !this.openInfo
    },
  },
}
</script>
