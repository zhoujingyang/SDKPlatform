<template>
	<div>

		<div id="generalNoPositionChart" class="chart"></div>
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
				let myChart = this.$echarts.init(document.getElementById('generalNoPositionChart'));
				// 绘制图表
				axios.get('/ocr/sdk/eval/getGeneralNoPositionEvalResult/' + this.$route.query.idNo).then((response) => {

					//绘制图标开始
					app.title = '通用无位置信息';

					var option = {
						angleAxis: {
							type: 'category',
							data: ['err', 'subErr', 'insertErr', 'delErr', 'lineErr'],
							z: 10
						},
						radiusAxis: {},
						polar: {},
						series: [{
							type: 'bar',
							data: [response.data.err, response.data.subErr, response.data.insertErr, response.data.delErr, response.data.lineErr],
							coordinateSystem: 'polar',
							name: '通用无位置信息错误率',
							stack: 'a'
						}],
						legend: {
							show: true,
							data: ['通用无位置信息错误率']
						}
					};

					//绘制图标完成
					myChart.setOption(option);
				})
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