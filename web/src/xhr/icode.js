import axios, {icodeUrl, XTRequest} from './_configure'

export const ICODE = new XTRequest(icodeUrl + 'icode-api', [
  'project', 'template'
]).models

ICODE.template.__update = params => axios.post(icodeUrl + "icode-api/template/updateTemplate", params);
ICODE.template.__retrieve = params => axios.post(icodeUrl + "icode-api/template/getTemplate", params);
ICODE.template.generateTemplate = params => axios.post(icodeUrl + "icode-api/template/generateTemplate", params);
ICODE.template.generateTemplateZip = params => {
  return icodeUrl + 'icode-api/template/generateTemplateZip' + params
}

ICODE.template.downloadPdfGenerateTemplateZip = params => axios.post(
  icodeUrl + "icode-api/template/generateTemplateZip",
  params,
  {responseType: 'blob'},
);


ICODE.template.updateCode= params => axios.post(icodeUrl + "icode-api/template/updateCode", params);


ICODE.template.listTable= (params) => axios.post(icodeUrl + "icode-api/project/listTable", params);
