<template>
  <section>
    <!--#region 搜索条-->

    <xt-search @search="handleSearch">
    <#list model.columns as column>
      <#if column.code=="null"||column.code?lower_case=="id"||column.code?lower_case=="id"||column.code?lower_case=="makebillman"||column.code?lower_case=="modifydate"||column.code?lower_case=="modifier"||column.code?lower_case=="deleteflag">
      <#elseif column.generated>
        <#switch column.dataType?lower_case>
          <#case "varchar">
        <el-input v-model="filters.${column.code?uncap_first}" placeholder="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" size="small" class="search-box__input" clearable></el-input>
            <#break>
          <#case "int">
        <xt-input-number type="number" v-model.number="filters.${column.code?uncap_first}" placeholder="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" size="small" :controls="false" class="search-box__input"/>
            <#break>
          <#case "datetime">
            <#if column.code?lower_case=="createdate">
        <el-date-picker v-model="filters.startDate" type="date" placeholder="开始时间" size="small" :editable="false" class="search-box__input"></el-date-picker>
        <el-date-picker v-model="filters.endDate" type="date" placeholder="结束时间" size="small" :editable="false" class="search-box__input"></el-date-picker>
            </#if>
            <#break>
        </#switch>
      <#else>
      </#if>
    </#list>
        <template slot="actions">
          <el-button size="small" class="search-box__button" type="primary" icon="plus" @click="handleAdd">新增</el-button>
        </template>
      </xt-search>

    <!--#endregion-->

    <!--#region 页面主要列表-->

    <!--列表-->

    <el-table :data="${model.code?uncap_first}List" highlight-current-row v-loading="loading.listLoading" style="width: 100%;" stripe border>
      <el-table-column align="left" header-align="center" type="index" width="56" label="序号">
        <template slot-scope="scope">
          {{(pageInfo.pageIndex-1)*pageInfo.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
        <#list model.columns as column>
          <#if column.code=="null"||column.code?lower_case=="id"||column.code?lower_case=="deleteflag">
          <#elseif column.generated>
      <el-table-column align="left" header-align="center" prop="${column.code?uncap_first}" label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" :show-overflow-tooltip="true"></el-table-column>
          <#else>
          </#if>
        </#list>
      <el-table-column align="left" header-align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="handleEdit(scope.row)">修改</el-button>
          <el-button size="mini" type="danger" @click="deleteData(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--#endregion-->

    <!--#region 工具条-->

    <!--工具条-->

    <el-row>
      <el-col :span="24" class="toolbar">
        <el-pagination
          style="float:right;"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageInfo.pageIndex"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageInfo.pageSize"
          layout="sizes, prev, pager, next"
          :total="pageInfo.count">
        </el-pagination>
      </el-col>
    </el-row>

    <!--#endregion-->

    <!--#region 新增修改页面-->

    <!--新增界面-->

    <el-dialog width="95%" top="20px" :title="formData.id==0?'新增':'修改'" :visible.sync="dialog.formDataVisible" :close-on-click-modal="false" :modal-append-to-body="false">
      <el-form :model="formData" label-width="100px" :rules="formRules" ref="formData">
      <#list model.columns as column>
        <#if column.code=="null"||column.code?lower_case=="id"||column.code?lower_case=="id"||column.code?lower_case=="makebillman"||column.code?lower_case=="createdate"||column.code?lower_case=="modifydate"||column.code?lower_case=="modifier"||column.code?lower_case=="deleteflag">
        <#elseif column.code?lower_case=="remark">
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <el-input type="textarea" resize="none" v-model="formData.${column.code?uncap_first}" auto-complete="off" :maxlength="${column.characterMaximumLength}" placeholder="请输入${column.name}" size="small"></el-input>
        </el-form-item>
        <#elseif column.generated==true>
          <#switch column.dataType?lower_case>
            <#case "varchar">
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <el-input v-model="formData.${column.code?uncap_first}" auto-complete="off" :maxlength="${column.characterMaximumLength}" placeholder="请输入${column.name}" size="small"></el-input>
        </el-form-item>
              <#break>
            <#case "text">
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <el-input type="textarea" v-model="formData.${column.code?uncap_first}" auto-complete="off" :maxlength="${column.characterMaximumLength}" placeholder="请输入${column.name}" size="small"></el-input>
        </el-form-item>
              <#break>
            <#case "int">
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
            <xt-input-number type="number" v-model.number="formData.${column.code?uncap_first}" auto-complete="off" :maxlength="${column.characterMaximumLength}" placeholder="请输入${column.name}" size="small" style="width:100%"></xt-input-number>
        </el-form-item>
              <#break>
            <#case "decimal">
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <xt-input-number type="number" v-model.number="formData.${column.code?uncap_first}" auto-complete="off" :maxlength="${column.characterMaximumLength}" placeholder="请输入${column.name}" size="small" style="width:100%"></xt-input-number>
        </el-form-item>
              <#break>
            <#case "datetime">
              <#if column.code?lower_case=="createdate"||column.code?lower_case=="modifydate">
              <#else >
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <el-date-picker v-model="formData.${column.code?uncap_first}" type="date" placeholder="请选择${column.name}" size="small" :editable="false" style="width:100%"></el-date-picker>
        </el-form-item>
              </#if>
              <#break>
            <#case "bit">
              <#if column.code?lower_case!="deleteflag" >
        <el-form-item label="<#if column.name!="">${column.name}<#else>${column.code?uncap_first}</#if>" prop="${column.code?uncap_first}">
          <el-radio-group v-model="formData.${column.code?uncap_first}" size="small">
            <el-radio-button :label="true">是</el-radio-button>
            <el-radio-button :label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
              </#if>
          </#switch>
        </#if>
      </#list>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="handleCancel">取消</el-button>
        <el-button type="primary" @click.native="submitData" :loading="loading.addLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--#endregion-->

  </section>
</template>

<script>

/*#region 导入函数*/

import PagePlugIn from "../../mixins/page-plug-in";
import {${model.code?cap_first}API} from "../../modules";

/*#endregion*/

export default {
  name: "${model.code?cap_first}",
  methods: {

    /*#region 自定义函数(组件change事件等)*/

    /*#endregion*/

    /*#region 数据提交事件*/

    //提交事件

    submitData() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          const params = JSON.parse(JSON.stringify(this.formData));
          if (params.id === 0) {
            this.loading.addLoading = true;
            ${model.code?cap_first}API.insert${model.code?cap_first}(params).then(({data}) => {
              if (data.code === 1) {
                this.$refs.formData.resetFields();
                this.getMainList();
                this.dialog.formDataVisible = false;
              }
              this.loading.addLoading = false;
            });
        } else {
          this.loading.addLoading = true;
          ${model.code?cap_first}API.update${model.code?cap_first}(params).then(({data}) => {
            if (data.code === 1) {
              this.$refs.formData.resetFields();
              this.getMainList();
              this.dialog.formDataVisible = false;
            }
            this.loading.addLoading = false;
          });
          }
        }
      });
    },

    //删除

    deleteData(row) {
      this.$confirm("删除之后将无法恢复, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        ${model.code?cap_first}API.delete${model.code?cap_first}({id: row.id}).then(({data}) => {
          if (data.code === 1) {
            this.getMainList();
          }
        });
      });
    },


    /*#endregion*/

    /*#region 操作函数(handle)*/

    //修改

    handleEdit(row) {
      this.$refs.formData && this.$refs.formData.resetFields();
      this.formData = JSON.parse(JSON.stringify(row));
      this.dialog.formDataVisible = true;
    },
    handleCancel() {
      this.dialog.formDataVisible = false;
    },
    handleAdd() {
      this.$refs.formData && this.$refs.formData.resetFields();
      this.formData = ${model.code?cap_first}API.init();
      this.dialog.formDataVisible = true;
    },

    /*#endregion*/

    /*#region 获取数据*/

    getMainList() {
      const params = {
        ...this.getParams()
      };
      ${model.code?cap_first}API.list${model.code?cap_first}(params).then(({data, list}) => {
        this.${model.code?uncap_first}List = list;
        this.pageInfo.pageIndex = data.data ? data.data.currentPage : 1;
        this.pageInfo.count = data.data ? data.data.count : 0;
      });
    }

    /*#endregion*/

  },

  /*#region 生命周期函数(created、mount、等)*/

  created() {
    this.getMainList();
  },

  /*#endregion*/

  /*#region 注册绑定数据*/

  mixins: [PagePlugIn],
  data() {
    return {
      filters: ${model.code?cap_first}API.init(),
      formData: ${model.code?cap_first}API.init(),
      formRules: {
      <#list model.columns as column>
      <#if column.code!="null"&&column.code?lower_case!="id"&&column.code?lower_case!="makebillman"&&column.code?lower_case!="createdate"&&column.code?lower_case!="modifydate"&&column.code?lower_case="modifier"&&column.code?lower_case!="deleteflag">
      <#if column.required>
        ${column.code?uncap_first}: [{required: true, message: "请输入${column.name}", trigger: "change"}]<#list model.columns as p><#if p?index gt column?index ><#if p.code!='null'&&p.code?lower_case!='makebillman'&&p.code?lower_case!='createdate'&&p.code?lower_case!='modifydate'&&p.code?lower_case!='modifier'&&p.code?lower_case!='deleteflag'&&p.required>,<#break></#if></#if></#list>
      </#if>
      </#if>
      </#list>
      },
      dialog: { formDataVisible: false },
      loading: { addLoading: false, listLoading: false },
      ${model.code?uncap_first}List: []
    };
  }

  /*#endregion */

};
</script>

<style scoped>

</style>
