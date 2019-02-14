// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './app'
import { TOKEN_KEY } from './xhr/'
import Router from 'vue-router'
import routers from './router'
import store from './store'
// import './assets/styles/reset.scss'
import 'font-awesome/css/font-awesome.min.css'
import './assets/styles/element-variables.scss'
import ElementUI from 'element-ui'
import components from './components'

Vue.use(Router)
Vue.use(ElementUI)
Vue.use(components)

Vue.config.productionTip = false

let router =new Router({
  routes:routers
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
  if (sessionStorage.getItem(TOKEN_KEY) === null && localStorage.getItem(TOKEN_KEY) === null) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
  // next()
} else {
  next()
}
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App },
  data:{
    Bus:new Vue()
  }
})
