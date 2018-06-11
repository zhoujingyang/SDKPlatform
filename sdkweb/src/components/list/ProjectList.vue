<template>
	<div>
		<br />
		<el-button type="primary" @click="dialogFormVisible = true" style="margin-left: 87%;">创建提测项目</el-button>
		<el-dialog title="请填写提测项目信息" :visible.sync="dialogFormVisible" :before-close="handleClose">
			<el-form :model="form">
				<el-tabs v-model="activeName2" type="card" @tab-click="tabClick">
					<el-tab-pane label="IOS" name="first">
						<el-form ref="form" :model="form" label-width="80px">
							<el-form-item label="项目名称">
								<el-input v-model="form.name"></el-input>
							</el-form-item>
							<el-form-item label="项目描述">
								<el-input type="textarea" v-model="form.desc"></el-input>
							</el-form-item>
							<el-form-item label="项目版本">
								<el-input v-model="form.version"></el-input>
							</el-form-item>
							<el-form-item label="使用厂商">
								<el-input type="textarea" v-model="form.business"></el-input>
							</el-form-item>
							<el-form-item label="上传Model">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('model')">点击上传Model</el-button>
									<!--<div slot="tip" class="el-upload__tip">data.bundle</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传libs">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('libs')">点击上传libs</el-button>
									<!--<div slot="tip" class="el-upload__tip">libs文件</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传md5">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('md5')">点击上传md5</el-button>
									<!--<div slot="tip" class="el-upload__tip">.md5</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传demo">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('demo')">点击上传demo</el-button>
									<!--<div slot="tip" class="el-upload__tip">.rar</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传许可">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('license')">点击上传license</el-button>
									<!--<div slot="tip" class="el-upload__tip">.txt</div>-->
								</el-upload>
							</el-form-item>
						</el-form>
					</el-tab-pane>
					<el-tab-pane label="Android" name="second">

						<el-form ref="form" :model="form" label-width="80px">
							<el-form-item label="项目名称">
								<el-input v-model="form.name"></el-input>
							</el-form-item>
							<el-form-item label="项目描述">
								<el-input type="textarea" v-model="form.desc"></el-input>
							</el-form-item>
							<el-form-item label="项目版本">
								<el-input v-model="form.version"></el-input>
							</el-form-item>
							<el-form-item label="使用厂商">
								<el-input type="textarea" v-model="form.business"></el-input>
							</el-form-item>
							<el-form-item label="上传Model">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('model')">点击上传Model</el-button>
									<!--<div slot="tip" class="el-upload__tip">data.bundle</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传so">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('so')">点击上传so</el-button>
									<!--<div slot="tip" class="el-upload__tip">libs文件</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传md5">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('md5')">点击上传md5</el-button>
									<!--<div slot="tip" class="el-upload__tip">.md5</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传demo">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('demo')">点击上传demo</el-button>
									<!--<div slot="tip" class="el-upload__tip">.rar</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传jar">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('jar')">点击上传jar</el-button>
									<!--<div slot="tip" class="el-upload__tip">.rar</div>-->
								</el-upload>
							</el-form-item>
							<el-form-item label="上传许可">
								<el-upload class="upload-demo" action="/ocr/sdk/project/upload" :on-success="uploadSuccess" :data="uploadData" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :file-list="fileList">
									<el-button size="small" type="primary" @click="setFileType('license')">点击上传license</el-button>
									<!--<div slot="tip" class="el-upload__tip">.txt</div>-->
								</el-upload>
							</el-form-item>
						</el-form>

					</el-tab-pane>
				</el-tabs>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="onSubmit">保 存</el-button>
			</div>
		</el-dialog>

		<el-table :data="tableData4" style="width: 100%">
			<el-table-column type="expand">
				<template slot-scope="props">
					<el-form label-position="left" inline class="demo-table-expand">
						<el-form-item label="使用场景:">
							<span>{{ props.row.projectBusiness }}</span>
						</el-form-item>
						<el-form-item label="Model:">
							<span>{{ props.row.model }}</span>
						</el-form-item>
						<el-form-item label="libs:">
							<span>{{ props.row.libs }}</span>
						</el-form-item>
						<el-form-item label="md5:">
							<span>{{ props.row.md5 }}</span>
						</el-form-item>
						<el-form-item label="demo:">
							<span>{{ props.row.demo }}</span>
						</el-form-item>
						<el-form-item label="license:">
							<span>{{ props.row.license }}</span>
						</el-form-item>
						<el-form-item label="so:">
							<span>{{ props.row.so }}</span>
						</el-form-item>
						<el-form-item label="jar:">
							<span>{{ props.row.jar }}</span>
						</el-form-item>
					</el-form>
				</template>
			</el-table-column>
			<el-table-column label="创建时间" prop="createTime">
			</el-table-column>
			<el-table-column label="项目ID" prop="taskID">
			</el-table-column>
			<el-table-column label="项目类别" prop="projectType">
			</el-table-column>
			<el-table-column label="项目名称" prop="projectName">
			</el-table-column>
			<el-table-column label="项目版本" prop="projectVersion">
			</el-table-column>
			<el-table-column label="项目描述" prop="projectDesc">
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template slot-scope="scope">
					<el-button  @click.native.prevent="editTableInfo(scope.$index, tableData4)" type="text" size="small">
						编辑
					</el-button>
					<el-button @click.native.prevent="downloadPackage(scope.$index, tableData4)" type="text" size="small">
						下载
					</el-button>
				</template>

			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import axios from 'axios'
	import fileDownload from 'js-file-download'
	export default {
		data() {
			return {
				uploadData: {
					saveDir: "",
					projectType: "",
					fileType: "",
					taskID: ""
				},
				tabName: "IOS",
				fileList: [],
				activeName2: 'first',
				dialogFormVisible: false,
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: ''
				},
				formLabelWidth: '120px',
				tableData4: []
			}
		},
		mounted() {
			this.getProjectist();
		},
		methods: {
			editTableInfo(index, tableData) {
//				alert(tableData[index].taskID);
				axios.get('/ocr/sdk/project/getOneProjectInfo', {
					params: {
						taskID: tableData[index].taskID
					}
				}).then(response => {
//						alert(response.data.taskID);
						this.dialogFormVisible = true;
						this.form.name = response.data.projectName;
						this.form.desc = response.data.projectDesc;
						this.form.version = response.data.projectVersion;
						this.form.business = response.data.projectBusiness;

				})
			},
			downloadPackage(index, tableData) {

				axios.get('/ocr/sdk/project/download', {
					params: {
						taskID: tableData[index].taskID
					}
				}).then(response => {
					//					alert(response.data);
					window.open(response.data);
				})
			},
			setFileType(fileType) {
				this.uploadData.projectType = this.tabName;
				this.uploadData.fileType = fileType;
			},
			uploadSuccess(response, file, fileList) {
				console.log(response);
				this.uploadData.saveDir = response.saveDir;
				//				alert(this.uploadData.saveDir);
				this.uploadData.projectType = response.projectType;
				this.uploadData.taskID = response.taskID;
				//				alert(this.uploadData.projectType);
				return true;
			},
			onSubmit() {
				this.dialogFormVisible = false;
				//获取到所有要提交的信息,然后调用后台接口
				axios.get('/ocr/sdk/project/saveProjectFormInfo', {
					params: {
						saveDir: this.uploadData.saveDir,
						taskID: this.uploadData.taskID,
						projectType: this.uploadData.projectType,
						projectName: this.form.name,
						projectDesc: this.form.desc,
						projectVersion: this.form.version,
						projectBusiness: this.form.business
					}
				}).then(response => {
					location.reload();
				})

				location.reload();
			},
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
						location.reload();
					})
					.catch(_ => {});

			},
			handleRemove(file, fileList) {
				console.log(file, fileList);
			},
			handlePreview(file) {
				console.log(file);
			},
			handleExceed(files, fileList) {
				this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
			},
			beforeRemove(file, fileList) {
				return this.$confirm(`确定移除 ${ file.name }？`);
			},
			tabClick(tab, event) {
				this.tabName = tab.label;

				console.log(tab, event);
			},
			getProjectist() {
				axios.get('/ocr/sdk/project/getProjectFormInfo', {
					params: {
						start: 0,
						end: 10000
					}
				}).then(response => {
					this.tableData4 = response.data
				})
			}

		}
	}
</script>

<style>

</style>