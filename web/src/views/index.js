export const Disk = () => import(/* webpackChunkName: "modules" */ './code/disk.vue')
export const TemplateList = () => import(/* webpackChunkName: "modules" */ './code/template-list.vue')
export const ProjectList = () => import(/* webpackChunkName: "modules" */ './code/project-list.vue')

export const components = [
  {title: '模板管理', name: '模板管理', component: 'Disk', path: 'disk'},
  {title: '模板', name: 'TemplateList', component: 'TemplateList', path: 'template-list'},
  {title: '项目管理', name: '项目管理', component: 'ProjectList', path: 'project-list'},
]

export const baseUrl = '/modules/'
