import Login from '../components/xt-login/login.vue'
import BasicRoutes from './basic.js'
import Home from '../components/xt-home/home.vue'
import NotFound from '../views/404'

const routes= [
    {
      sort:1,
      icon:'fa fa-th-large',
      displayFlag:false,
      path: '/login',
      name: 'Login',
      component: Login,
      meta:{ title:'登录'},
      children:[]
    },
    {
      sort:2,
      path: '/',
      icon:'fa fa-bookmark',
      component: Home,
      redirect: '/modules/project-list',
      displayFlag:true,
      name:'基础信息',
      meta:{ title:'基础信息'},
      children: [
        {
          sort:3,
          icon:'fa fa-bookmark',
          displayFlag:false,
          path: '/404',
          name: 'NotFound',
          component: NotFound,
          meta:{ title:'404'},
          children:[]
        }
      ].concat(BasicRoutes.moduleRoutes).map(route => {
        route.meta = {
          title:route.name,
          requiresAuth: true
        }
        return route
      })
    },
    {
      path: '*',
      redirect: '/404'
    }
  ]

export default routes
