<template>
  <div v-loading.fullscreen.lock="building" element-loading-text="正在执行代码生成...">
    <div class="mod-breadcrumb">
      <a href="javascript:;" @click="loadData">全部</a>
      <template v-if="menus.length>0"><i class="fa fa-angle-right"></i></template>
      <template v-for="(item,index) in menus">
        <a href="javascript:;" @click="menuClick(index,item)">{{ item.name }}</a>
        <template v-if="index+1<menus.length"><i class="fa fa-angle-right"></i></template>
      </template>

      <div style="float: right">
        <el-button v-if="btnRemoveVisable" type="danger" @click="deleteClick"><i class="fa fa-trash-o"></i> 删除
        </el-button>
        <el-dropdown trigger="click" @command="btnAddClick">
          <el-button type="primary"><i class="fa fa-plus"></i> 添加</el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="folder"><i class="fa fa-folder"></i> 新建目录</el-dropdown-item>
            <el-dropdown-item command="file"><i class="fa fa-file"></i> 新建文件</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-button type="success" @click="updateCode"><i class="fa fa-building"></i>更新模板</el-button>
      </div>
    </div>
    <div class="mod-item-list">
      <ul class="item-list">
        <template v-for="item in folders">
          <li v-if="item.isDirectory" :class="['item',item.checked?'item-checked':'']"
              @click="folderClick(item)">
            <div class="inner">
              <i class="icon-wrapper fa fa-folder"></i>
              <span class="txt" @click.stop="alert(1)">{{item.name || '未命名目录'}}</span>
            </div>
            <!--<i style="position: absolute;width: 48px;height: 48px;left: 0px;top: 0px;z-index: 99" :class="['fa',item.checked?'fa-check-circle':'fa-circle-o']"></i>-->
            <span @click.stop="toolClick(item)"
                  :class="['item-tool',item.checked?'item-tool-checked':'item-tool-unchecked']">
                            <i v-if="item.checked" class="fa fa-check"></i>
                        </span>
          </li>
        </template>
      </ul>
    </div>
    <div style="clear: both;border-bottom: 1px dashed #d3d4d6;padding: 5px 8px 0px 8px"></div>
    <div class="mod-item-list">
      <ul class="item-list">
        <template v-for="item in folders">
          <li v-if="!item.isDirectory" :class="['item',item.checked?'item-checked':'']"
              @click="fileClick(item)">
            <div class="inner">
              <i class="icon-wrapper fa fa-file-code-o"></i>
              <span class="txt">{{item.name || '未命名文件'}}</span>
            </div>
            <span @click.stop="toolClick(item)"
                  :class="['item-tool',item.checked?'item-tool-checked':'item-tool-unchecked']">
                            <i v-if="item.checked" class="fa fa-check"></i>
                        </span>
          </li>
        </template>
      </ul>
    </div>

    <!-- 编辑页面 -->
    <el-dialog width="86%" :title="fileInfo.path" :visible.sync="fileDialogVisable" :close-on-click-modal="false" :modal-append-to-body="false">
      <el-input type="textarea" placeholder="请输入内容" :rows="40" v-model="fileInfo.content"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fileDialogVisable = false">取 消</el-button>
        <el-button type="primary" @click="saveFileHandle">确 定</el-button>
      </div>
    </el-dialog>

    <!--生成页面-->
    <el-dialog width="70%" top="20px" title="代码生成" :visible.sync="dialog.dialogFormVisible"
               :close-on-click-modal="false" :modal="true" append-to-body
               :modal-append-to-body="false">
      <el-form label-position="right" :rules="rules" :model="entityInfo" label-width="120px" ref="form_Dialog">
        <el-form-item label="打包名称" prop="zipName">
          <el-input v-model="entityInfo.path" auto-complete="off" :maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="模板名称" prop="templateName">
          <el-select v-model="entityInfo.templateName" placeholder="请选择模板名称" style="width:100%">
            <el-option v-for='item in templateList' :label="item.name" :key="item.id" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目Code" prop="projectName">
          <el-select v-model="entityInfo.projectName" filterable placeholder="请选择项目Code" style="width:100%">
            <el-option v-for="item in projectList" :key="item.code" :label="item.code" :value="item.code">
              <span style="float: left">{{ item.code }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitMeasure('form_Dialog')">生成</el-button>
        <el-button @click="dialog.dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {ICODE} from "../../xhr/";
  import Vue from "vue";
  import {Message} from "element-ui";

  const ProjectApi = ICODE.project;
  const TemplateApi = ICODE.template;

  export default {
    name: "disk",
    data() {
      return {
        projectList: [],
        templateList: [],
        entityInfo: {
          templateName: "",
          path: "",
          zipName: "",
          projectName: ""
        },
        defaultEntityCopy: {},
        menus: [],
        folders: [],
        fileInfo: {
          path: "",
          content: ""
        },
        dialog: {
          dialogFormVisible: false
        },
        fileDialogVisable: false,
        building: false,
        rules: {
          name: [
            {
              required: true,
              message: "请选择模板名称",
              trigger: "change"
            }
          ],
          templateName: [
            {required: true, message: "请选择模板名称", trigger: "change"}
          ],
          projectName: [
            {required: true, message: "请选择项目Code", trigger: "change"}
          ]
        }
      };
    },
    methods: {
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
      folderClick(obj) {
        obj.list.map(item => {
          if (item.checked) item.checked = false;
        });
        this.menus.push(obj);
        this.folders = obj.list;
      },
      menuClick(index, obj) {
        obj.list.map(item => {
          if (item.checked) item.checked = false;
        });
        this.folders = obj.list;
        this.menus = this.menus.splice(0, index + 1);
      },
      fileClick(obj) {
        TemplateApi.__retrieve({
          path: `${obj.path}/${obj.name}`
        }).then(res => {
          if (res.data.code != undefined && res.data.code == 1) {
            this.fileDialogVisable = true;
            this.fileInfo.path = `${obj.path}/${obj.name}`;
            this.fileInfo.content = res.data.data;
          }
        });
      },
      btnAddClick(command) {
        if (this.folders.length > 0) {
          let tempData = JSON.parse(JSON.stringify(this.folders[0]));
          let title =
            command == "folder" ? "请输入文件夹名称：" : "请输入文件名称";
          let _this = this;
          this.$prompt(title, "提示", {
            center: true,
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            inputPattern: /\S/,
            inputErrorMessage: "不能为空或空格!"
          })
            .then(({value}) => {
              tempData.name = value;
              tempData.isDirectory = command == "folder";
              tempData.list = [];
              _this.folders.push(tempData);
            })
            .catch(() => {
            });
        } else {
          let tempData = JSON.parse(
            JSON.stringify(this.menus[this.menus.length - 1])
          );
          let title =
            command == "folder" ? "请输入文件夹名称：" : "请输入文件名称";
          let _this = this;
          this.$prompt(title, "提示", {
            center: true,
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            inputPattern: /\S/,
            inputErrorMessage: "不能为空或空格!"
          })
            .then(({value}) => {
              tempData.name = command == value;
              tempData.isDirectory = command == "folder";
              tempData.list = [];
              _this.folders.push(tempData);
            })
            .catch(() => {
            });
        }
      },
      toolClick(obj) {
        if (obj.checked) {
          obj.checked = !obj.checked;
        } else {
          Vue.set(obj, "checked", true);
        }
      },
      saveFileHandle() {
        this.fileDialogVisable = false;
        let newEntityInfo = Object.assign({}, this.fileInfo);
        TemplateApi.__update(newEntityInfo).then(res => {
          if (res.data.code == 1) {
            this.$message({
              type: "success",
              message: "保存成功"
            });
          }
        });
      },
      deleteClick() {
        this.$confirm("确定要删除您选中的目录、文件吗？", "系统提示", {
          type: "info",
          callback: action => {
            if (action == "confirm") {
              this.folders.map(item => {
                if (item.checked) {
                  console.log(`${item.path}/${item.name}`);
                }
              });
            }
          }
        });
      },
      addCodeGenerationClick() {
        this.dialog.dialogFormVisible = true;
        let _this = this;
        let para = Object.assign({}, this.pageInfo);
        para.pageSize = 999999;
        ProjectApi.list(para).then(res => {
          let _date = res.data.data;
          _this.projectList = _date.data ? _date.data : [];
        });
      },
      submitMeasure(formName) {
        let _this = this;
        this.$refs[formName].validate(valid => {
          if (valid) {
            _this
              .$msgbox({
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
                    console.log(instance);
                    let para = Object.assign({}, _this.entityInfo);
                    TemplateApi.downloadPdfGenerateTemplateZip(para).then(res => {
                      console.log(res);
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
                        _this.entityInfo = Object.assign(
                          {},
                          _this.defaultEntityCopy
                        );
                        _this.dialog.dialogFormVisible = false;
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
                        _this.dialog.dialogFormVisible = false;
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
              });
          }
        });
      },
      updateCode() {
        TemplateApi.updateCode().then(res => {
          if (res.data.code != undefined && res.data.code == 1) {
            Message({
              message: "更新成功",
              type: "warning"
            });
          }
        });
      }
    },
    computed: {
      btnRemoveVisable() {
        return (
          this.folders.find(item => {
            return item.checked;
          }) !== undefined
        );
      }
    },
    created() {
      this.loadData();
      this.defaultEntityCopy = Object.assign({}, this.entityInfo);
    }
  };
</script>
<style scoped>
  .item-checked {
    background-color: #c9e5f5;
  }

  .item-tool {
    display: block;
    height: 16px;
    width: 16px;
    border-radius: 50%;
    position: absolute;
    left: 6px;
    top: 6px;
  }

  .item-tool-unchecked {
    border: 2px solid #ffffff;
    color: #ffffff;
  }

  .item-tool-checked {
    background-color: #1c8de0;
    color: #ffffff;
  }

  .mod-breadcrumb a:hover {
    text-decoration: underline;
  }

  .mod-breadcrumb a {
    padding: 4px 5px;
    color: #777;
    text-decoration: none;
  }

  .mod-item-list {
    padding-top: 5px;
    margin-bottom: 20px;
  }

  .mod-item-list .item-list {
    list-style: none;
  }

  .mod-item-list .item-list .item {
    float: left;
    height: 120px;
    min-width: 116px;
    max-width: 116px;
    box-sizing: border-box;
    text-align: center;
    cursor: pointer;
    margin: 5px;
    position: relative;
  }

  .mod-item-list .item-list .item:hover {
    background-color: #c9e5f5;
  }

  .mod-item-list .item-list .item .inner {
    z-index: 3;
    display: block;
    padding-bottom: 16px;
    cursor: default;
  }

  .mod-item-list .item .icon-wrapper {
    display: block;
    z-index: 0;
    width: 100%;
    padding-top: 10%;
    font-size: 70px;
    color: #50bfff;
  }

  .mod-item-list .item .inner .txt {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 84%;
    margin: 0 auto;
    font-size: 14px;
    line-height: 20px;
    cursor: pointer;
    text-align: center;
  }
</style>
