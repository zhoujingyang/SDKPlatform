<template>
	<div>

		<div id="memChart" class="chart"></div>
	</div>
	
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
//				idNo
			}
		},
		mounted() {
			this.drawLine();
		},
		methods: {
			drawLine() {
				var _this = this
				// 基于准备好的dom，初始化echarts实例
				let myChart = this.$echarts.init(document.getElementById('memChart'));
				// 绘制图表
				axios.get('/ocr/sdk/report/get/performance/result/'+this.$route.query.idNo).then((response) => {
					//获取内存数据
					var memData = JSON.parse(response.data.mem).data;
					var memLength = memData.length;
					if(memLength==0){
						alert(this.$route.query.idNo + "查无数据");
						window.history.back(-1); 
					}
					
					var x = new Array();
					for(var i = 0; i < memLength; i++) {
						x.push(i);
					}

					myChart.setOption({

						title: {
							text: '内存监控信息'
						},
	
						tooltip: {
							trigger: 'axis'
						},
						xAxis: {
							data: x
						},
						yAxis: {
							splitLine: {
								show: false
							}
						},
						toolbox: {
							left: 'center',
							feature: {
								dataZoom: {
									yAxisIndex: 'none'
								},
								restore: {},
								saveAsImage: {}
							}
						},
						dataZoom: [{
							startValue: '2014-06-01'
						}, {
							type: 'inside'
						}],
						visualMap: {
							top: 10,
							right: 10,
							pieces: [{
								gt: 0,
								lte: 5,
								color: '#096'
							}, {
								gt: 5,
								lte: 10,
								color: '#ffde33'
							}, {
								gt: 10,
								lte: 15,
								color: '#ff9933'
							}, {
								gt: 15,
								lte: 20,
								color: '#cc0033'
							}, {
								gt: 20,
								lte: 50,
								color: '#660099'
							}, {
								gt: 50,
								color: '#7e0023'
							}],
							outOfRange: {
								color: '#999'
							}
						},
						series: {
							name: '内存使用大小',
							type: 'line',
							data: memData,
							markLine: {
								silent: true,
								data: [{
									yAxis: 5
								}, {
									yAxis: 10
								}, {
									yAxis: 15
								}, {
									yAxis: 20
								}, {
									yAxis: 50
								}]
							}
						}
					});
					//					window.onresize = myChart.resize;
					//绘制图标完成
					//浏览器大小改变时重置大小

				});
			}
		}
	}
</script>

<style>

.chart {
		width: 100%;
		height: 550px;
	}

</style>