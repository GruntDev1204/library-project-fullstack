<template lang="">
  <div :class="classEffect">
    <div class="row">
      <div class="col-md-6">
        <button class="button mb-2" @click="backVideo">
          <i class="fa-solid fa-backward"></i>
        </button>
      </div>
      <div class="col-md-6 flex-end">
        <button class="button mb-2" @click="nextVideo">
          <i class="fa-solid fa-forward"></i>
        </button>
      </div>
    </div>

    <div class="slide-container">
      <div
        class="slider"
        :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
      >
        <div class="slide" v-for="(video, index) in videos" :key="index">
          <video controls autoplay muted>
            <source :src="video" type="video/mp4" />
          </video>
        </div>
      </div>
    </div>
    <button
      class="button mb-2 mt-4"
      @click="activeEffect"
      title="tuyệt đối đừng ấn nút này!"
    >
      <i class="fa-solid fa-skull-crossbones"></i>
    </button>
  </div>
</template>
<script lang="ts">
export default {
  data() {
    return {
      isEffect: false,
      classEffect: "alert alert-form-2 mb-5",
      currentIndex: 0,
      videos: [
        "./assets/media/videos/0.mp4",
        "./assets/media/videos/1.mp4",
        "./assets/media/videos/2.mp4",
        "./assets/media/videos/3.mp4",
        "./assets/media/videos/4.mp4",
        "./assets/media/videos/5.mp4",
        "./assets/media/videos/6.mp4",
        "./assets/media/videos/7.mp4",
        "./assets/media/videos/8.mp4",
      ],
    }
  },
  methods: {
    activeEffect() {
      this.isEffect = true
      this.classEffect = "alert alert-form-2 mb-5 effect-fan"
      setTimeout(() => {
        this.isEffect = false
        this.classEffect = "alert alert-form-2 mb-5"
      }, 10000)
    },

    nextVideo() {
      this.currentIndex = (this.currentIndex + 1) % this.videos.length
    },
    backVideo() {
      this.currentIndex =
        (this.currentIndex - 1 + this.videos.length) % this.videos.length
    },
  },
}
</script>
