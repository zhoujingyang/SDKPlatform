<template>
	<div>

		<el-table :data="tableData">
			<el-table-column label="日期">
				<template slot-scope="scope">
					<i class="el-icon-time"></i>
					<span style="margin-left: 10px">{{ scope.row.time }}</span>
				</template>
			</el-table-column>
			<el-table-column label="idNo">
				<template slot-scope="scope">
					<el-popover trigger="hover" placement="top">
						<p>idNo: {{ scope.row.idNo }}</p>
						<div slot="reference" class="name-wrapper">
							<el-tag size="medium">{{ scope.row.idNo }}</el-tag>
						</div>
					</el-popover>
				</template>
			</el-table-column>
			<el-table-column label="查看图表">
				<template slot-scope="scope">
					<el-button size="mini" @click="toMemCharts(scope.$index, scope.row.idNo)">查看</el-button>
					<!--<i class="el-icon-view" @click="toMemCharts(scope.$index, scope.row.idNo)"></i>-->
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				tableData: []
			}
		},
		mounted() {
			this.getCpuList();
			//			this.refresh();
		},
		methods: {
			refresh() {
				var flush = location.href.indexOf('#reloaded');
				if(flush == -1) {
					location.href = location.href + "#reloaded";
					location.reload();
				}
				flush = 1;
			},
			getCpuList() {
				axios.get('/ocr/sdk/report/get/performance/result/list', {
					params: {
						start: 0,
						end: 10000,
						target: 'mem'
					}
				}).then(response => {
					this.tableData = response.data
				})
			},
			toMemCharts(index, id) {
				this.$router.push({
					path: '/MemCharts',
					query: {
						idNo: id
					}
				});
			}

		}
	}
</script>

<style>

</style>