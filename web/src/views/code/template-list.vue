<template>

    <div>
        <el-button @click="createTemplate()">生成</el-button>
        <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
        <el-tree
                class="filter-tree"
                :data="tableData"
                :props="defaultProps"
                default-expand-all
                :filter-node-method="filterNode"
                ref="tree2">
        </el-tree>

        <!--生成-->
        <el-dialog title="生成" :visible.sync="dialog.dialogFormVisible" :close-on-click-modal="false" :modal-append-to-body="false">
            <el-form label-position="right" :rules="rules" :model="entityInfo" label-width="120px" ref="form_Dialog">
                <el-form-item label="保存目录" prop="path">
                    <el-input v-model="entityInfo.path" auto-complete="off" :maxlength="30" :minlength="3"></el-input>
                </el-form-item>
                <el-form-item label="模板名称" prop="name">
                    <el-select v-model="entityInfo.name" placeholder="请选择模板名称">
                        <el-option v-for='item in templateList' :key="item.id" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
              <el-form-item label="项目Code" prop="projectName">
                <el-select v-model="entityInfo.projectName" filterable placeholder="请选择项目Code">
                  <el-option v-for="item in projectList" :key="item.code" :label="item.code" :value="item.code">
                    <span style="float: left">{{ item.code }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.name }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitMeasure('form_Dialog')">提交</el-button>
                <el-button @click="resetForm('form_Dialog')">取消</el-button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
    import {ICODE} from '../../xhr/'
    import {StrUtils} from "./src/util";
    const TemplateApi = ICODE.template
    const ProjectApi = ICODE.project
    let fileLevel = 1000;
    export default {
        watch: {
            filterText(val) {
                this.$refs.tree2.filter(val);
            }
        },
        data() {
            return {
                filterText: '',
                tableData: [],
                templateList: [],
                projectList:[],
                dialog: {dialogFormVisible: false},
                entityInfo: {id: 0, fileLevel: null, isDirectory: null, name: '', path: '', list: []},
                defaultResetDataCopy: {},
                defaultProps: {
                    children: 'list',
                    label: 'name'
                }, rules: {name: [{required: true, message: '请选择模板名称', trigger: 'change'}]},
            }
        }, methods: {
            filterNode(value, data) {
                if (!value) {
                    return true;
                }
                return data.name.indexOf(value) !== -1;
            },listProject(){
              let _this = this;
              let para = Object.assign({}, this.pageInfo);
              para.pageSize = 999999;
              ProjectApi.list(para).then(res => {
                let _date = res.data.data;
                _this.projectList = _date.data;
              });
            },listTemplate() {
                let _this = this;
                TemplateApi.list().then(res => {
                    _this.tableData = res.data.data.list;
                    _this.templateList = res.data.data.list;
                    console.log(res.data.code);
                });
            }, createTemplate() {
                this.dialog.dialogFormVisible = true;
            }, resetForm(formName) {
                this.$refs[formName].resetFields();
                this.dialog.dialogFormVisible = false;
            }, submitMeasure(formName) {
                console.log(formName);
                let _this = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let newEntityInfo = Object.assign({}, _this.entityInfo);
                        TemplateApi.generateTemplate(newEntityInfo).then(function (res) {
                            let data = res.data;
                            if (data.code == 1 && data.data) {
                                _this.$message({
                                    showClose: true,
                                    message: StrUtils.isStrNotNull(data.msg) ? data.msg : '模板生成成功',
                                    type: 'success'
                                });
                                _this.dialog.dialogFormVisible = false;
                                _this.$refs[formName].resetFields();
                            } else {
                                _this.$message({
                                    showClose: true,
                                    message: StrUtils.isStrNotNull(data.msg) ? data.msg : '模板生成失败',
                                    type: 'error'
                                });
                            }
                        });
                    }
                });
            }, addClick() {
                console.log('=====template-list.vue==addClick===');
            }, updateClick() {
                console.log('=====template-list.vue==updateClick===');
            }, deleteClick() {
                console.log('=====template-list.vue==deleteClick===');
            }, append(data) {
                const newChild = {fileLevel: fileLevel++, name: 'testtest', list: []};
                if (!data.list) {
                    this.$set(data, 'list', []);
                }
                data.children.push(newChild);
            }, remove(node, data) {
                const parent = node.parent;
                const children = parent.data.list || parent.data;
                const index = children.findIndex(d => d.fileLevel === data.fileLevel);
                children.splice(index, 1);

            },
        }, mounted() {
            this.listTemplate();
            this.listProject();
        }, updated() {
            if (!this.dialog.dialogFormVisible) {
                this.entityInfo = Object.assign(this.entityInfo, this.defaultResetDataCopy);
            }
        },

    }
</script>

<style scoped>

</style>
