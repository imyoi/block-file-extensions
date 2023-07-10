<template>
  <FileExtension title="파일 확장자 차단" />
</template>

<script>
import FileExtension from './components/FileExtension.vue'

export default {
  name: 'App',
  components: {
    FileExtension
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  /* -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; */
  color: #2c3e50;
  margin-top: 60px;
}
</style>
