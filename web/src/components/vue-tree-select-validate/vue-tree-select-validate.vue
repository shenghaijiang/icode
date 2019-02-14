<style scoped>
  .vue-treeselect__control {
    border: 0px solid #fff;
  }

  .vue-select-box {
    border-color: white
  }

  .vue-treeselect__placeholder, .vue-treeselect__single-value {
    line-height: 28px;
  }

  .vue-line-height__0 {
    line-height: 0px
  }
</style>

<template>
  <section>
    <div :class="[
            inputSize ? 'el-input--' + inputSize : '',
            {
            'is-disabled': disabled
            },
            'vue-line-height__0'
        ]">
      <tree-select
        v-model="currentValue"
        :multiple="multiple"
        :options="options"
        :placeholder="placeholder"
        :noChildrenText="noChildrenText"
        :noOptionsText="noOptionsText"
        :noResultsText="noResultsText"
        @input="handleInput"
        class="vue-select-box el-input__inner"
        :disabled="disabled"
        style="padding: 0px 0px 0px 0px;margin-top: 3px;"
      />
    </div>

  </section>
</template>

<script>
  import TreeSelect from '@riophae/vue-treeselect'
  import Emitter from '../../mixins/emitter'

  export default {
    name: "vue-tree-select-validate",
    mixins: [Emitter],
    inject: {
      elForm: {
        default: ''
      },

      elFormItem: {
        default: ''
      }
    },
    components: {
      TreeSelect
    },
    props: {
      value: [String, Number],
      validateEvent: {
        type: Boolean,
        default: true
      },
      placeholder: String,
      options: Array,
      multiple: Boolean,
      noChildrenText: {
        type: String,
        default: () => {
          return '暂无数据'
        }
      },
      noOptionsText: {
        type: String,
        default: () => {
          return '暂无数据'
        }
      },
      noResultsText: {
        type: String,
        default: () => {
          return '暂无数据'
        }
      },
      size: String,
      disabled: {
        type: Boolean,
        default: () => {
          return false
        }
      },
    },
    data() {
      return {
        currentValue: this.value,
      }
    },
    watch: {
      'value'(val, oldValue) {
        if (val && (oldValue !== undefined) && val != oldValue) {
          this.elFormItem && this.elFormItem.validate('change');
        }
        this.setCurrentValue(val);
      }
    },
    computed: {
      inputSize() {
        return this.size;
      },
    },
    methods: {
      handleInput(value, id) {
        this.$emit('input', value, id);
        this.elFormItem && this.elFormItem.validate('blur');
        this.setCurrentValue(value);
      },
      setCurrentValue(value) {
        if (value === this.currentValue) return;
        this.currentValue = value;
        // this.dispatch('ElFormItem', 'el.form.change', value);
      },
    },
    created() {
    }
  }
</script>


