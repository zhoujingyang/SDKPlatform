<template>
	<div>

		<div id="IdCardFrontChart" class="chart"></div>
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
				let myChart = this.$echarts.init(document.getElementById('IdCardFrontChart'));
				// 绘制图表
				axios.get('/ocr/sdk/eval/get/idcard/front/eval/' + this.$route.query.idNo).then((response) => {

						//绘制图标开始
						var option = {
							title: {
								text: '身份证正面评测结果'
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
								data: ['birthday', 'name','idNo','sex','nation','address']
							}],
							yAxis: [{
								type: 'value'
							}],
							series: [{
									name: '准确率',
									type: 'bar',
									data: [response.data.birthdayAccuracy,response.data.nameAccuracy,response.data.idNoAccuracy,response.data.sexAccuracy,response.data.nationAccuracy,response.data.addressAccuracy],
								},
								{
									name: '召回率',
									type: 'bar',
									data: [response.data.birthdayRecall,response.data.nameRecall,response.data.idNoRecall,response.data.sexRecall,response.data.nationRecall,response.data.addressRecall],

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