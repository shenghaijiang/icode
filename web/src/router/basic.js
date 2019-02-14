import * as modules from '../views/index'
import { compConfig } from './utils'

const moduleRoutes=compConfig(modules, modules.baseUrl)

export default {
    moduleRoutes
}
