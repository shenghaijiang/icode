<template>
  <el-form ref="form" :model="row.rowForm">
    <el-tooltip :disabled="!isError" :content="message" placement="left-start">
      <el-form-item style="margin-bottom:0;" :prop="prop" :rules="rules">
        <span v-if="!row.rowEditable">{{ row[prop] }}</span>
        <el-input v-else v-model="row.rowForm[prop]" :maxlength="maxlength" size="small" style="width:96%;" @change="handleChange" @blur="handleBlur"></el-input>
      </el-form-item>
    </el-tooltip>
  </el-form>
</template>

<script>
export default {
  name:'MRowInput',
  props: {
    row: { required: true, type: Object },
    prop: { required: true, type: String },
    rules: Array,
    maxlength: Number
  },
  data() {
    return {
      isError: false,
      message: ''
    }
  },
  methods: {
    handleBlur() {
      if (this.$refs.form) {
        this.$refs.form.validateField(this.prop, errorMessage => {
          this.isError = errorMessage ? true : false
          this.message = errorMessage
        })
      }
    },
    handleChange() {
      this.isError = false
      this.message = ''
    }
  }
}
</script>
