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
			<el-table-column label="CaseId">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.caseId }}</span>
				</template>
			</el-table-column>
			<el-table-column label="InterfaceName">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.interfaceName }}</span>
				</template>
			</el-table-column>
			<el-table-column label="status">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.status }}</span>
				</template>
			</el-table-column>
			<el-table-column label="TestTime">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.testTime }}</span>
				</template>
			</el-table-column>
			<el-table-column label="Message">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.message }}</span>
				</template>
			</el-table-column>
			<el-table-column label="ImageResult">
				<template slot-scope="scope">
					<el-tooltip placement="top">
						<div slot="content" style="margin-left: 20px">{{ scope.row.imgResult }}</div>
						<el-button type="text" >{{ scope.row.imgResult }}</el-button>
					</el-tooltip>
					<!--<span style="margin-left: 10px">{{ scope.row.imgResult }}</span>-->
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
				dialogVisible: false,
				tableData: []
			}
		},
		mounted() {
			this.getFunctionTestResultList();
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
			getFunctionTestResultList() {
				axios.get('/ocr/sdk/function/getFunctionTestResultList', {
					params: {
						start: 0,
						end: 10000,
						idNo: this.$route.query.idNo
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