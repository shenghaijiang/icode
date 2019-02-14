// stupid webpack build will compile the component's name to a single letter.
// so component.name is not accessable for production.
// import { kebabCase } from 'lodash'

export const compConfig = (modules, baseUrl) => modules.components.map((component,index) => ({
  sort:index+2,
  title:component.title,
  name: component.name,
  path: baseUrl + component.path||baseUrl + component.name,
  icon:'fa fa-bookmark',
  displayFlag:true,
  component: modules[component.component],
  meta:{ title:component.title},
  children:[]
}))
