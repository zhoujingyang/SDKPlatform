<template>
  <el-carousel :interval="5000" arrow="always">
    <el-carousel-item v-for="item in 1" :key="item">
      <h3>
      	<img src="../assets/images/androidAndIOS.png" v-if="item == 1">
      </h3>
    </el-carousel-item>
  </el-carousel>
</template>
<script>
	export default {
		data() {
			return {

			}
		}}
	
</script>

<style>
	
  .el-carousel{
  	height: 300px;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
    width: 600px;
    margin-left: auto; 
    margin-right: auto;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>