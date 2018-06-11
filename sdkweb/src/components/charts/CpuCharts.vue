<template>
	<div>

		<div id="cpuChart" class="chart"></div>
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
				let myChart = this.$echarts.init(document.getElementById('cpuChart'));
				// 绘制图表
				axios.get('/ocr/sdk/report/get/performance/result/' + this.$route.query.idNo).then((response) => {

					//获取cpu数据
					var cpuData = JSON.parse(response.data.cpu).data;
					var cpuLength = cpuData.length;
					if(cpuLength == 0) {
						alert(this.$route.query.idNo + "查无数据");
						window.history.back(-1); 
					}
					var x = new Array();
					for(var i = 0; i < cpuLength; i++) {
						x.push(i);
					}
					myChart.setOption({

						title: {
							text: 'CPU监控信息'
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
								lte: 20,
								color: '#096'
							}, {
								gt: 20,
								lte: 30,
								color: '#ffde33'
							}, {
								gt: 30,
								lte: 40,
								color: '#ff9933'
							}, {
								gt: 40,
								lte: 50,
								color: '#cc0033'
							}, {
								gt: 50,
								lte: 60,
								color: '#660099'
							}, {
								gt: 60,
								color: '#7e0023'
							}],
							outOfRange: {
								color: '#999'
							}
						},
						series: {
							name: 'CPU使用率',
							type: 'line',
							data: cpuData,
							markLine: {
								silent: true,
								data: [{
									yAxis: 20
								}, {
									yAxis: 30
								}, {
									yAxis: 40
								}, {
									yAxis: 50
								}, {
									yAxis: 60
								}]
							}
						}
					});
					//					window.onresize = myChart.resize;
					//绘制图标完成
					//浏览器大小改变时重置大小

				}
				);
				
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