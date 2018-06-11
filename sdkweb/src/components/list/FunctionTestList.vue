<template>
	<div>

		<el-table :data="tableData">
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
			<el-table-column label="type">
				<template slot-scope="scope">
					<!--<i class="el-icon-time"></i>-->
					<span style="margin-left: 10px">{{ scope.row.type }}</span>
				</template>
			</el-table-column>
			<el-table-column label="imageName">
				<template slot-scope="scope">
					<!--<i class="el-icon-time"></i>-->
					<span style="margin-left: 10px">{{ scope.row.imgName }}</span>
				</template>
			</el-table-column>
			<el-table-column label="version">
				<template slot-scope="scope">
					<!--<i class="el-icon-time"></i>-->
					<span style="margin-left: 10px">{{ scope.row.version }}</span>
				</template>
			</el-table-column>
			<el-table-column label="详细信息">
				<template slot-scope="scope">
			<el-button
          size="mini"
          @click="toFunctionTestResultList(scope.$index, scope.row.idNo)">查看</el-button>

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
				tableData: [],
			}
		},
		mounted() {
			this.getFunctionTestList();
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
			getFunctionTestList() {
				axios.get('/ocr/sdk/function/getFunctionTestList', {
					params: {
						start: 0,
						end: 10000
					}
				}).then(response => {
					this.tableData = response.data
				})
			},
			toFunctionTestResultList(index, id) {
				this.$router.push({
					path: '/FunctionTestResultList',
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