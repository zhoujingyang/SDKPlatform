<template>
	<div>

		<div id="generalWithPositionChart" class="chart"></div>
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
				let myChart = this.$echarts.init(document.getElementById('generalWithPositionChart'));
				// 绘制图表
				axios.get('/ocr/sdk/eval/getGeneralWithPositionEvalResult/' + this.$route.query.idNo).then((response) => {

						//绘制图标开始
						var option = {
							title: {
								text: '通用带位置信息'
							},
							tooltip: {
								trigger: 'axis'
							},
							legend: {
								data: ['准确率', '召回率']
							},
							toolbox: {
								show: true,
								feature: {
									dataView: {
										show: true,
										readOnly: false
									},
									magicType: {
										show: true,
										type: ['line', 'bar']
									},
									restore: {
										show: true
									},
									saveAsImage: {
										show: true
									}
								}
							},
							calculable: true,
							xAxis: [{
								type: 'category',
								data: ['charLevel', 'imageLevel']
							}],
							yAxis: [{
								type: 'value'
							}],
							series: [{
									name: '准确率',
									type: 'bar',
									data: [response.data.charLevelPrecision,response.data.imageLevelPrecision],
								},
								{
									name: '召回率',
									type: 'bar',
									data: [response.data.charLevelRecall,response.data.imageLevelRecall],

								}
							]
						};
						//绘制图标完成
						myChart.setOption(option);
	})
				}}}
</script>

<style>
	.chart {
		width: 100%;
		height: 550px;
	}
</style>