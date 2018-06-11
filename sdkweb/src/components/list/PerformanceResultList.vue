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
			<el-table-column label="InterfaceName">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.interfaceName }}</span>
				</template>
			</el-table-column>
			<el-table-column label="TestImages">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.testImages }}</span>
				</template>
			</el-table-column>
			<el-table-column label="AvgTime">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.averageTime }}</span>
				</template>
			</el-table-column>
			<el-table-column label="SuccessNumber">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.successNumber }}</span>
				</template>
			</el-table-column>
			<el-table-column label="FailNumber">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.failNumber }}</span>
				</template>
			</el-table-column>
			<el-table-column label="TotalNumber">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.totalNumber }}</span>
				</template>
			</el-table-column>
			<el-table-column label="Version">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.version }}</span>
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
			this.getPerformanceResultList();
			//			this.refresh();
		},
		methods: {
			//			goBack(){
			//				window.history.back(-1); 
			//			},
			refresh() {
				var flush = location.href.indexOf('#reloaded');
				if(flush == -1) {
					location.href = location.href + "#reloaded";
					location.reload();
				}
				flush = 1;
			},
			getPerformanceResultList() {
				axios.get('/ocr/sdk/performance/getPerformanceTestResultList', {
					params: {
						start: 0,
						end: 10000
					}
				}).then(response => {
					this.tableData = response.data
				})
			}

		}
	}
</script>

<style>

</style>