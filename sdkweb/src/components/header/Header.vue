<template>
	<div>
		<div class="line"></div>
		<el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
			<el-menu-item index="1">OCR SDK自动化测试平台</el-menu-item>
			<el-menu-item index="project">项目</el-menu-item>

			<el-submenu index="function">
				<template slot="title">功能测试</template>
				<el-menu-item index="functionResultList">结果列表</el-menu-item>
			</el-submenu>
			<el-submenu index="performance">
				<template slot="title">性能测试</template>
				<el-menu-item index="performanceResultList">结果列表</el-menu-item>
			</el-submenu>
			<el-submenu index="basic">
				<template slot="title">稳定性监控</template>
				<el-menu-item index="mem">内存监控</el-menu-item>
				<el-menu-item index="cpu">CPU监控</el-menu-item>
			</el-submenu>
			<el-submenu index="eval">
				<template slot="title">评测结果</template>

				<el-submenu index="general">
					<template slot="title">通用</template>
					<el-menu-item index="withPosition">有位置</el-menu-item>
					<el-menu-item index="noPosition">无位置</el-menu-item>
				</el-submenu>
				<el-submenu index="idCard">
					<template slot="title">身份证</template>
					<el-menu-item index="front">正面</el-menu-item>
					<el-menu-item index="back">反面</el-menu-item>
				</el-submenu>
			</el-submenu>
			<el-menu-item index="report" @click="dialogVisible = true">测试报告</el-menu-item>
		</el-menu>
		<el-dialog title="友情提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
			<span>紧密开发中，敬请期待！</span>
			<span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
		</el-dialog>
		<!--<i class="el-icon-back" @click="goBack()"></i>-->
	</div>
</template>

<script>
	export default {
		data() {
			return {
				activeIndex: '1',
				activeIndex2: '1',
				dialogVisible: false
			};
		},
		methods: {
			handleClose() {

			},
			handleSelect(key, keyPath) {
				console.log(key, keyPath);
				if(keyPath == "basic,cpu") {
					this.getCpuList();
				} else if(keyPath == "basic,mem") {
					this.getMemList();
				} else if(keyPath == "function,functionResultList") {
					this.getFunctionResultList();
				} else if(keyPath == "performance,performanceResultList") {
					this.getPerformanceResultList();
				} else if(keyPath == "eval,general,withPosition") {
					this.getGeneralWithPositionList();
				} else if(keyPath == "eval,general,noPosition") {
					this.getGeneralNoPositionList();
				} else if(keyPath == "eval,idCard,front") {
					this.getIdCardFrontList();
				} else if(keyPath == "eval,idCard,back") {
					this.getIdCardBackList();
				} else if(keyPath == "1") {
					this.toIndex();
				} else if(keyPath == "project"){
					this.getProjectList();
				}

			},
			toIndex() {
				this.$router.push({
					path: '/',
				});
			},
			getMemList() {
				this.$router.push({
					path: '/MemList',
				});
			},
			getCpuList() {
				this.$router.push({
					path: '/CpuList'
				});
			},
			getGeneralWithPositionList() {
				this.$router.push({
					path: '/GeneralWithPositionList'
				});
			},
			getGeneralNoPositionList() {
				this.$router.push({
					path: '/GeneralNoPositionList'
				});
			},
			getIdCardFrontList() {
				this.$router.push({
					path: '/IdCardFrontList'
				});
			},
			getIdCardBackList() {
				this.$router.push({
					path: '/IdCardBackList'
				});
			},
			getFunctionResultList() {
				this.$router.push({
					path: '/FunctionTestList'
				});
			},
			getPerformanceResultList() {
				this.$router.push({
					path: '/PerformanceResultList'
				});
			},
			getProjectList(){
								this.$router.push({
					path: '/ProjectList'
				});
			}
		}
	}
</script>
<style>

</style>