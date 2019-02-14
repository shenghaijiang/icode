import axios from 'axios'
import {Message} from 'element-ui'
import qs from 'qs'
import {camelCase} from 'lodash'
import urljoin from 'url-join'

export const icodeUrl = `http://${settings.SERVER_HOST}:${settings.SERVER_PORT}/`
export const xtpUrl = `http://${settings.XTP_HOST}:${settings.XTP_PORT}/`

export const TOKEN_KEY = 'icode-token'

const transformRequest = (data = {}, headers) => {
  if (typeof data === 'string') return data

  let Authorization = localStorage.getItem(TOKEN_KEY) || sessionStorage.getItem(TOKEN_KEY) || null
  if (Authorization) {
    data.oauth = Authorization
  }

  return qs.stringify(data)
}

let _axios = axios.create({
  baseURL: icodeUrl,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  transformRequest: [transformRequest],
  timeout: 8000
})

_axios.interceptors.response.use(response => response, error => {
  console.log(error)
  let {response} = error
  let {data} = response ? response : {}
  let errorMsg = '服务器连接错误!'
  for (let k in data) {
    errorMsg += data[k] instanceof Array ? data[k].join(' ') : data[k]
  }
  Message.error(errorMsg)
  return response
})

export default _axios

// 页数默认值
export const pageSize = 999

// 贤二 service api.
export class XTRequest {
  map = {
    list: 'list',
    create: 'insert',
    update: 'update',
    destroy: 'delete',
    retrieve: 'get'
  }

  constructor(baseUrl, tables) {
    this.baseUrl = baseUrl
    this.tables = tables
    this.generateModels()
  }

  getRequestUrl(method, tableName) {
    let action = camelCase(`${this.map[method]}-${tableName}`)
    return urljoin(this.baseUrl, camelCase(tableName), action)
  }

  generateModels() {
    let models = {}
    this.tables.forEach(table => {
      let model = {
        list: (params) => _axios.post(this.getRequestUrl('list', table), {pageSize, ...params}),
        create: (form) => _axios.post(this.getRequestUrl('create', table), {data: JSON.stringify(form)}),
        update: (form) => _axios.post(this.getRequestUrl('update', table), {data: JSON.stringify(form)}),
        destroy: (id) => _axios.post(this.getRequestUrl('destroy', table), {id}),
        retrieve: (id) => _axios.post(this.getRequestUrl('retrieve', table), {id})
      }
      models[camelCase(table)] = model
    })
    this.models = models
  }
}
