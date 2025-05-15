<template lang="">
  <div v-if="show" class="notifi">
    <div :class="`alert alert-${localType}`">
      <h3 class="text-center mb-3 title">
        <i :class="getIconClass(localType)"></i> Notification
      </h3>
      <h3 class="mt-2 content">
        {{ localMessage }}
      </h3>
    </div>
  </div>
</template>
<script lang="ts">
export default {
  props: {
    type: {
      type: String,
      required: false,
      default: "info",
    },
    message: {
      type: String,
      required: false,
      default: "",
    },
  },

  data() {
    return {
      show: false,
      localType: this.type,
      localMessage: this.message,
    }
  },

  methods: {
    getIconClass(type: string) {
      switch (type) {
        case "success":
          return "fa-solid fa-circle-check"
        case "warning":
          return "fa-solid fa-circle-exclamation"
        case "danger":
          return "fa-solid fa-triangle-exclamation"
        case "info":
          return "fa-solid fa-circle-info"
        default:
          return ""
      }
    },
    showAlert(type: string, message: string) {
      this.localType = type
      this.localMessage = message
      this.show = true

      setTimeout(() => {
        this.show = false
      }, 3000)
    },
  },
}
</script>
