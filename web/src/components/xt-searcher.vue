<template>
  <div class="search-box">
    <el-input v-for="(item, index) in fields" :key="index"
      :placeholder="placeholders[item]" size="small" icon="close"
      v-model="kw[item]" @keyup.13.native="search"
      class="search-box__input" :on-icon-click="() => { kw[item] = undefined; search();}"></el-input>

    <slot></slot>

    <div class="search-box__button-wrap">
      <el-button size="small" @click="search" class="search-box__button" icon="search">查询</el-button>
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name:'MSearcher',
  props: {
    kw: { type: Object, required: true },
    placeholders: { type: Object, required: true }
  },

  computed: {
    fields() {
      return Object.keys(this.placeholders)
    }
  },

  methods: {
    search() {
      this.$emit('search')
    }
  }
}
</script>

<style lang="sass" scoped>
.search-box
  display: flex
  background-color: #eee
  margin-bottom: 8px
  .search-box__input
    width: 230px
    margin: 2px 2px 2px
  .search-box__button-wrap
    display: flex
    align-items: center
    margin-left: 8px
</style>

