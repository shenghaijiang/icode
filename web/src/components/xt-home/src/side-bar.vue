<template>
    <section >
        <!-- <div class="bar" :class="{sideBar:!isCollapse}">
            <label>
                {{isCollapse ? '' : sysName}}
                <span style="cursor: pointer;margin-left:8px" @click.prevent="collapse">
                    <i class="fa fa-align-justify" ></i>
                </span>
            </label>
        </div> -->

        <div>
          <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" v-for="(item,index) in menuList" :key="index" v-if="item.displayFlag" :router="true" background-color="rgb(238, 241, 246)" style="position: inherit" >
            <el-submenu :index="item.path"  v-if="item.children.length>=1">
              <template slot="title" >
                <i :class="item.icon" ></i>
                <span slot="title" v-if="!isCollapse">{{item.name}}</span>
              </template>
              <div v-for="(child,childIndex) in item.children" :key="childIndex" v-if="child.displayFlag" >
                <el-submenu :index="child.path"  v-if="child.children.length>=1" style="background-color:white">
                  <template slot="title" >
                    <i :class="item.icon"></i>
                    <span slot="title" >{{item.name}}</span>
                  </template>
                  <div v-for="(grandChild,grandChildIndex) in child.children" :key="grandChildIndex" v-if="grandChild.displayFlag" >
                    <el-submenu :index="grandChild.path"  v-if="grandChild.children.length>=1" style="background-color:white">
                      <template slot="title" >
                        <i :class="child.icon"></i>
                        <span slot="title" >{{child.name}}</span>
                      </template>
                    </el-submenu>
                    <el-menu-item :index="grandChild.path" :route="grandChild.path" v-else >
                      <i :class="grandChild.icon"></i>
                      <span slot="title">{{grandChild.name}}</span>
                    </el-menu-item>
                  </div>
                </el-submenu>
                <el-menu-item :index="child.path" :route="child.path" v-else >
                  <i :class="child.icon"></i>
                  <span slot="title">{{child.name}}</span>
                </el-menu-item>
              </div>
            </el-submenu>
            <el-menu-item :index="item.path" :route="item.path" v-else >
              <i :class="item.icon"></i>
              <span slot="title" >{{item.name}}</span>
            </el-menu-item>
          </el-menu>
        </div>
    </section>
</template>

<script>
import menus from '../menus'
import { mapGetters } from 'vuex'

export default {
    name:'SideBar',
    data(){
        return{
            menuList:[],
            sysName:'ICode代码生成系统',
            // isCollapse:false
        }
    },
    computed: {
        ...mapGetters(['isCollapse'])
	},
    methods:{
        collapse: function () {
            this.$store.dispatch('changeIsCollapse')
        },
    },
    created(){
        this.menuList=menus;
    },
    activated(){
    }
}
</script>

<style lang="scss" scoped>
.bar{
    // position: fixed;
    // z-index: 1000px;
    background-color: #1885DD;
    height: 2.8rem;
    line-height: 2.8rem;
    color:#ffffff;
    font-size: 18px;
    border-color: rgba(238, 241, 146, 0.3);
    border-right-width: 1px;
    border-right-style: solid;
    label{
        margin-left: 0.5rem;
    }
}
.sideBar{
    // width: 200px;
}
</style>


