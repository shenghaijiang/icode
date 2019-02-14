<template>
  <section>
    <m-searcher :kw="{}" :placeholders="{}" @search="handleSearch">
      <el-input v-model="filters.code" placeholder="编码" size="small" class="search-box__input" style="width:230px"
                clearable @keyup.enter.native="handleSearch"></el-input>
      <el-input v-model="filters.name" placeholder="名称" size="small" class="search-box__input" style="width:230px"
                clearable @keyup.enter.native="handleSearch"></el-input>
      <el-input v-model="filters.packageName" placeholder="PackageName" size="small" class="search-box__input"
                style="width:230px"
                clearable @keyup.enter.native="handleSearch"></el-input>
      <el-date-picker v-model="filters.startDate" type="date"
                      placeholder="开始时间" size="small" :editable="false"
                      class="search-box__input" @change="handleSearch"></el-date-picker>
      <el-date-picker v-model="filters.endDate" type="date"
                      placeholder="结束时间" size="small" :editable="false"
                      class="search-box__input" @change="handleSearch"></el-date-picker>
      <template slot="actions">
        <el-button size="small" class="search-box__button" type="primary" icon="plus" @click="addClick">新增</el-button>
      </template>
    </m-searcher>

    <el-table :data="tableData" v-loading="loading.listLoading" highlight-current-row style="width: 100%;" stripe
              border>
      <el-table-column align="left" header-align="center" type="index" width="56" label="序号">
        <template slot-scope="scope">
          {{(pageInfo.pageIndex - 1) * pageInfo.pageSize + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column align="left" header-align="center" prop="code" label="编码"
                       :show-overflow-tooltip="true"></el-table-column>
      <el-table-column align="left" header-align="center" prop="name" label="名称"> :show-overflow-tooltip="true"
      </el-table-column>
      <el-table-column align="left" header-align="center" prop="sqlType" label="数据库类型" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <div v-for="item in defaultSqlTypeList" v-if="scope.row.sqlType==item.id">{{item.name}}</div>
        </template>
      </el-table-column>
      <el-table-column align="left" header-align="center" prop="packageName" label="PackageName"
                       :show-overflow-tooltip="true"></el-table-column>
      <el-table-column align="left" header-align="center" prop="dbUser" label="数据库用户名"
                       :show-overflow-tooltip="true"></el-table-column>
      <!-- <el-table-column align="left" header-align="center" prop="dbPassword" label="数据库密码" ></el-table-column>-->
      <el-table-column align="left" header-align="center" prop="dbName" label="数据库名称"
                       :show-overflow-tooltip="true"></el-table-column>
      <el-table-column align="left" header-align="center" prop="dbHost" label="数据库链接"
                       :show-overflow-tooltip="true"></el-table-column>
      <el-table-column align="left" header-align="center" label="操作" width="240">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleCodeGenerationClick(scope.row)" size="small">生成</el-button>
          <el-button type="warning" @click="handleUpdate(scope.row)" size="small">修改</el-button>
          <el-button type="danger" @click="deleteData(scope.row)" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条 分页-->
    <el-row>
      <el-col :span="24" class="toolbar">
        <el-pagination
          style="float:right;"
          @size-change="handledSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageInfo.pageIndex"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageInfo.pageSize"
          layout="sizes, prev, pager, next"
          :total="pageInfo.count">
        </el-pagination>
      </el-col>
    </el-row>

    <!--新增，修改-->
    <el-dialog width="70%" top="150px" :title="entityInfo.id==0?'新增':'修改'" :visible.sync="dialog.dialogFormVisible"
               :close-on-click-modal="false" :modal-append-to-body="false">
      <!--label-position 对齐  top、left、right-->
      <el-form class="main-table" label-position="right" :rules="rules" :model="entityInfo" label-width="120px"
               ref="addForm">
        <el-form-item label="编码" prop="code">
          <el-input v-model="entityInfo.code" placeholder="请输入编码" auto-complete="off" :maxlength="30" :minlength="2"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="entityInfo.name" placeholder="请输入名称" auto-complete="off" :maxlength="30" :minlength="3"></el-input>
        </el-form-item>
        <el-form-item label="数据库类型" prop="sqlType">
          <el-select v-model="entityInfo.sqlType" placeholder="请选择数据库类型" style="width: 100%">
            <el-option v-for='item in defaultSqlTypeList' :label="item.name" :key="item.id"
                       :value="item.id"></el-option>
            <!--<p v-for='item in defaultSqlTypeList'>
                <el-option :label="item.name" :value="item.id"></el-option>
            </p>-->
          </el-select>
        </el-form-item>
        <el-form-item label="PackageName" prop="packageName">
          <el-input v-model="entityInfo.packageName" placeholder="请输入packageName,如:cn.xtit.icode" auto-complete="off" :maxlength="30"
                    :minlength="1"></el-input>
        </el-form-item>
        <el-form-item label="数据库用户名" prop="dbUser">
          <el-input v-model="entityInfo.dbUser" placeholder="请输入数据库用户名" auto-complete="off" :maxlength="30" :minlength="1"></el-input>
        </el-form-item>
        <el-form-item label="数据库密码" prop="dbPassword">
          <el-input v-model="entityInfo.dbPassword" placeholder="请输入数据库密码" auto-complete="off" :maxlength="30"
                    :minlength="1"></el-input>
        </el-form-item>
        <el-form-item label="数据库名称" prop="dbName">
          <el-input v-model="entityInfo.dbName" placeholder="请输入数据库名" auto-complete="off" :maxlength="20" :minlength="1"></el-input>
        </el-form-item>
        <el-form-item label="数据库链接" prop="dbHost">
          <el-input v-model="entityInfo.dbHost" placeholder="请输入数据库链接" auto-complete="off" :maxlength="20" :minlength="1"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitMeasure('addForm')">提交</el-button>
        <el-button @click="resetForm('addForm')">取消</el-button>
      </div>
    </el-dialog>

    <!--代码生成-->
    <el-dialog width="70%" top="150px" title="代码生成" :visible.sync="dialog.generateDialogVisible"
               :close-on-click-modal="false" :modal="true" append-to-body
               :modal-append-to-body="false" :show-close="false">
      <el-form label-position="right" :rules="rules" :model="entityInfo" label-width="120px" ref="form_Dialog">
        <el-form-item label="模板名称" prop="templateName">
          <el-select v-model="templateName" placeholder="请选择模板名称" style="width:100%">
            <el-option v-for='item in templateList' :label="item.name" :key="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <template>
            <el-radio v-model="type" label="table">Table</el-radio>
            <el-radio v-model="type" label="project">项目</el-radio>
          </template>
        </el-form-item>
        <el-form-item v-show="(type==='table')" label="Table" prop="tableName">
          <el-select v-model="tableName" placeholder="请选择Table" style="width:100%" filterable multiple>
            <el-option v-for='item in tableList'
                       :label="item.tableName"
                       :key="item.tableName"
                       :value="item.code">
              <span style="float: left;width: 220px;">{{ item.tableName }}</span>
              <span style="float: left;width: 200px;">{{ item.code }}</span>
              <span style="float: left;;width: 320px; color: #8492a6;">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="generate">生成</el-button>
        <el-button @click="dialog.generateDialogVisible = false;type='table';tableName=[]">关闭</el-button>
      </div>
    </el-dialog>

  </section>
</template>

<script>
  import {ICODE} from "../../xhr/";
  import {StrUtils} from "./src/util";

  const ProjectApi = ICODE.project;
  const TemplateApi = ICODE.template;

  export default {
    methods: {
      getParams() {
        let filter = {};
        Object.keys(this.filters).map(item => {
          if (this.filters[item]) {
            if (
              item == "createDate" ||
              item == "modifyDate" ||
              item == "startDate" ||
              item == "endDate"
            ) {
              filter[item] = new Date(this.filters[item]).Format(
                "yyyy-MM-dd hh:mm:ss"
              );
            } else {
              if (typeof this.filters[item] == "string")
                filter[item] = "%" + this.filters[item] + "%";
              else filter[item] = this.filters[item];
            }
          }
        });
        let newPageInfo = Object.assign({}, this.pageInfo);
        if (newPageInfo.count) {
          delete newPageInfo.count;
        }
        let _newPageInfo = JSON.parse(JSON.stringify(newPageInfo));
        let params = {
          ...filter,
          ..._newPageInfo
        };
        return params;
      },

      handledSizeChange(val) {
        this.pageInfo.pageSize = val;
        this.listProject();
      },

      handleCurrentChange(val) {
        this.pageInfo.pageIndex = val;
        this.listProject();
      },

      handleSearch() {
        this.pageInfo.pageIndex = 1;
        this.listProject();
      },

      loadData() {
        this.menus = [];
        this.folders = [];
        TemplateApi.list().then(res => {
          if (res.data.code != undefined && res.data.code == 1) {
            this.folders = res.data.data.list;
            this.templateList = res.data.data.list;
          }
        });
      },

      listProject() {
        let _this = this;
        let params = {
          ...this.getParams()
        };
        params.orderBy = "Id desc";
        ProjectApi.list(params).then(res => {
          let _date = res.data.data;
          _this.tableData = _date.data;
          _this.pageInfo.pageIndex = _date.currentPage;
          _this.pageInfo.count = _date.count;
        });
      },

      listTable() {
        let _this = this;
        let params = {projectCode: this.projectName};
        TemplateApi.listTable(params).then(res => {
          let _date = res.data.data;
          _this.tableList = _date.data;
          //console.info(_this.tableList);
        });
      },

      addClick() {
        this.entityInfo = Object.assign({}, this.defaultResetDataCopy);
        this.dialog.dialogFormVisible = true;
      },

      handleCodeGenerationClick(row) {
        this.dialog.generateDialogVisible = true;
        this.projectName = row.code;
        this.tableName = [];
        this.listTable();
      },

      handleUpdate(row) {
        console.info(row);
        this.entityInfo = Object.assign(this.entityInfo, row);
        this.dialog.dialogFormVisible = true;
      },

      deleteData(row) {
        this.$confirm("删除之后将无法恢复, 是否继续?", "提示", {
          type: "warning"
        }).then(() => {
          ProjectApi.destroy(row.id).then(res => {
            if (res.data.code == 1) {
              this.$message({
                message: StrUtils.isStrNotNull(res.data.msg)
                  ? res.data.msg
                  : "删除成功",
                type: "success"
              });
              this.listProject();
            } else {
              let _this = this;
              _this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          });
        })
          .catch(function () {
          });
      },

      generate() {
        let _this = this;
        if (this.templateName === "") {
          this.$message({
            message: "请选择模板!",
            type: "error"
          });
          return;
        }

        if (this.type === "table" && this.tableName.length === 0) {
          this.$message({
            message: "请选择Table!",
            type: "error"
          });
          return;
        }
        this.$msgbox({
          title: "系统提示",
          message: "该操作大概需要几分钟，确定要执行代码生成吗？",
          showCancelButton: true,
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnClickModal: false,
          closeOnPressEscape: false,
          beforeClose: (action, instance, done) => {
            if (action === "confirm") {
              instance.confirmButtonLoading = true;
              instance.confirmButtonText = "执行中...";
              instance.showCancelButton = false;
              instance.showClose = false;
              let table = "";

              if (this.type !== "table") {
                table = "";
              } else {
                table = this.tableName.join(",");
              }
              let para = {projectName: this.projectName, templateName: this.templateName, type: this.type, tableName: table};
              TemplateApi.downloadPdfGenerateTemplateZip(para).then(res => {

                if (res && res.status != undefined && res.status == 200) {
                  let blob = new Blob([res.data], {
                    type: "application/zip"
                  });
                  let objectUrl = URL.createObjectURL(blob);
                  window.location.href = objectUrl;
                  _this.$message({
                    type: "success",
                    message: "模板生成成功"
                  });
                  _this.entityInfo = Object.assign({}, _this.defaultEntityCopy);
                  _this.dialog.generateDialogVisible = false;
                } else {
                  _this.$message({
                    type: "error",
                    message:
                    "模板: " +
                    _this.entityInfo.templateName +
                    ",项目Code: " +
                    _this.entityInfo.projectName +
                    "生成失败,请检查对应项目数据库是否能正常连接!"
                  });
                  _this.dialog.generateDialogVisible = false;
                }
                done();
                instance.confirmButtonLoading = false;
              });
            } else {
              done();
            }
          }
        })
          .then(action => {
            this.type = 'table';
            this.tableName = [];
          });
      },

      submitMeasure(formName) {
        let _this = this;
        let newEntityInfo = Object.assign({}, this.entityInfo);
        this.$refs[formName].validate(valid => {
          if (valid) {
            if (newEntityInfo.id == 0) {
              ProjectApi.create(newEntityInfo).then(function (res) {
                let data = res.data;
                if (data.code == 1) {
                  _this.$message({
                    showClose: true,
                    message: StrUtils.isStrNotNull(data.msg)
                      ? data.msg
                      : "添加成功",
                    type: "success"
                  });
                  _this.dialog.dialogFormVisible = false;
                  _this.$refs[formName].resetFields();
                  _this.listProject();
                } else {
                  _this.$message({
                    showClose: true,
                    message: StrUtils.isStrNotNull(data.msg) ? data.msg : "失败",
                    type: "error"
                  });
                }
              });
            } else {
              ProjectApi.update(newEntityInfo).then(function (res) {
                let data = res.data;
                if (data.code == 1) {
                  _this.$message({
                    showClose: true,
                    message: StrUtils.isStrNotNull(data.msg)
                      ? data.msg
                      : "修改成功",
                    type: "success"
                  });
                  _this.dialog.dialogFormVisible = false;
                  _this.$refs[formName].resetFields();
                  _this.listProject();
                } else {
                  _this.$message({
                    showClose: true,
                    message: StrUtils.isStrNotNull(data.msg) ? data.msg : "失败",
                    type: "error"
                  });
                }
              });
            }
          }
        });
      },

      resetForm(formName) {
        console.info(formName);
        this.$refs[formName].resetFields();
        console.info("resetFields");
        this.$set(this.dialog, "dialogFormVisible", false);
        this.dialog.dialogFormVisible = false;
        console.info(this.dialog)
      }
    },

    mounted() {
      this.listProject();
    },

    created() {
      this.loadData();
      this.defaultResetDataCopy = Object.assign({}, this.entityInfo);
    },

    updated() {
      //console.log('updated...');
      if (!this.dialog.dialogFormVisible) {
      }
    },

    data() {
      return {
        tableData: [],
        templateList: [],
        tableList: [],
        defaultSqlTypeList: [
          {id: 1, name: "mysql"},
          {id: 2, name: "sql server"}
        ],
        projectName: "",
        templateName: "",
        tableName: [],
        type: "table",
        dialog: {generateDialogVisible: false, dialogFormVisible: false},
        filters: {
          id: 0,
          code: "",
          name: "",
          sqlType: null,
          packageName: "",
          dbUser: "",
          dbPassword: "",
          dbName: "",
          dbHost: "",
          startDate: null,
          endDate: new Date()
        },
        entityInfo: {
          id: 0,
          code: "",
          name: "",
          sqlType: null,
          packageName: "",
          dbUser: "",
          dbPassword: "",
          dbName: "",
          dbHost: ""

        },
        defaultResetDataCopy: {},
        pageInfo: {pageIndex: 1, pageSize: 10, count: 0},
        loading: {addLoading: false, listLoading: false},
        rules: {
          name: [
            {required: true, message: "请输入名称", trigger: "blur"},
            {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"}
          ],
          code: [
            {required: true, message: "请输入编码", trigger: "blur"},
            {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"}
          ],
          sqlType: [
            {
              type: "number",
              required: true,
              message: "请选择数据库类型",
              trigger: "change"
            }
          ],
          packageName: [
            {required: true, message: "请输入packageName", trigger: "blur"}
          ],
          dbUser: [
            {required: true, message: "请输入数据库用户名", trigger: "blur"}
          ],
          dbPassword: [
            {required: true, message: "请输入数据库密码", trigger: "blur"}
          ],
          dbName: [
            {required: true, message: "请输入数据库名称", trigger: "blur"}
          ],
          dbHost: [
            {required: true, message: "请输入数据库链接", trigger: "blur"}
          ]
        }
      };
    },
  };
</script>

<style scoped lang="scss">
  .main-table {
    .el-form-item {
      width: 48%;
      display: inline-block;
    }
  }
</style>
