<template>
	<div>

		<div id="IdCardBackChart" class="chart"></div>
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
				let myChart = this.$echarts.init(document.getElementById('IdCardBackChart'));
				// 绘制图表
				axios.get('/ocr/sdk/eval/get/idcard/back/eval/' + this.$route.query.idNo).then((response) => {

						//绘制图标开始
						var option = {
							title: {
								text: '身份证反面评测结果'
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
								data: ['issuingDate', 'issuingAuthority','expiryDate']
							}],
							yAxis: [{
								type: 'value'
							}],
							series: [{
									name: '准确率',
									type: 'bar',
									data: [response.data.issuingDateAccuracy,response.data.issuingAuthorityAccuracy,response.data.expiryDateAccuracy],
								},
								{
									name: '召回率',
									type: 'bar',
									data: [response.data.issuingDateRecall,response.data.issuingAuthorityRecall,response.data.expiryDateRecall],

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