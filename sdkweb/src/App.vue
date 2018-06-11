<template>
  <div id="app">
    <!--<img src="./assets/logo.png">-->
    	<app-header></app-header>
    <router-view/>
  </div>
</template>

<script>
import AppHeader from './components/header/Header.vue'
export default {
  name: 'App',
  components:{
  	AppHeader
  }
}
</script>

<style>

</style>
