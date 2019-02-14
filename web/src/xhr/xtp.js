import axios,{ XTRequest, xtpUrl } from './_configure'

export const XTP = new XTRequest(xtpUrl + 'xtp-api', [
    'organize',
    'user'
  ]).models

XTP.organize.listOrganizeWithDetailsTree=(params) =>{
return axios.post(xtpUrl + 'xtp-api/organize/listOrganizeWithDetailsTree',params);
}

XTP.user.loginUser=(params) =>{
  return axios.post(xtpUrl + '/xtp-api/user/loginUser',params);
}
