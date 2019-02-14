package cn.xtits.icode.controller;

import cn.xtits.icode.core.ReadDirectory;
import cn.xtits.icode.dto.FileInfo;
import cn.xtits.xtf.common.web.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @fileName: Project}Controller.java
 * @author: Dan
 * @createDate:
 * @description:
 */
@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {

    /**
     * 模板路径
     */
    @Value("${TEMPLATE_PATH}")
    private String TEMPLATE_PATH;

    /**
     * 项目生成路径
     */
    @Value("${PROJECT_PATH}")
    public String PROJECT_PATH;

    /**
     * 项目生成路径
     */
    @Value("${TEMPLATE_SVN}")
    public String TEMPLATE_SVN;

    @Value("${SVN_USER}")
    public String SVN_USER;

    @Value("${SVN_PWD}")
    public String SVN_PWD;


    private String UserCode = "hail";


    /**
     * 获取所有模板列表
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "listTemplate")
    public AjaxResult listProject(
            @RequestParam(value = "path", defaultValue = "") String path) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(path);
        fileInfo.setDirectory(true);
        String root = TEMPLATE_PATH + File.separator + UserCode + File.separator + path;
        fileInfo.setPath(root);
        ReadDirectory.readFile(fileInfo, root);
        return new AjaxResult(fileInfo);
    }

    /**
     * 获取模板内容
     *
     * @param path 模板路径
     * @return
     */
    @RequestMapping(value = "getTemplate")
    public AjaxResult getTemplate(@RequestParam(value = "path", required = false) String path) {
        String file = ReadDirectory.readFile(TEMPLATE_PATH + File.separator + UserCode + File.separator + path);
        return new AjaxResult(file);
    }

    /**
     * 更新或者新增模板
     *
     * @param path
     * @param content
     * @return
     */
    @RequestMapping(value = "updateTemplate")
    public AjaxResult getTemplategetTemplate(
            @RequestParam(value = "path", required = false) String path,
            @RequestParam(value = "content", required = false) String content) {
        //path="A\\Controller.ftl";
        boolean b = ReadDirectory.writeFile(TEMPLATE_PATH + File.separator + UserCode + File.separator + path, content);
        return new AjaxResult(b);
    }

    /**
     * 生成代码
     *
     * @param templateName
     * @return
     * @throws Exception
     */
    //解决跨域注解
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "generateTemplateZip")
    public AjaxResult generateTemplateZip(
            @RequestParam(value = "templateName", required = false) String templateName,
            @RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "type", required = false) String type,
            HttpServletResponse response) throws Exception {
        byte[] data = ReadDirectory.generateTemplateZip(projectName, TEMPLATE_PATH, fileName, tableName, TEMPLATE_PATH + File.separator + UserCode + File.separator + templateName, PROJECT_PATH,type);
        //设置响应头和客户端保存文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + templateName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
        return new AjaxResult(data.length > 0 ? 1 : 0);
    }

    @RequestMapping(value = "generateTemplate")
    public AjaxResult generateTemplate(
            @RequestParam(value = "templateName", required = false) String templateName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "type", required = false) String type) throws Exception {
        //path="A\\Controller.ftl";
        boolean b = ReadDirectory.generateTemplate(projectName, TEMPLATE_PATH, TEMPLATE_PATH + File.separator + UserCode + File.separator + templateName,tableName, PROJECT_PATH,type);
        return new AjaxResult(b);
    }

    /**
     * 创建文件夹(新模板)
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "createDirectory")
    public AjaxResult createDirectory(
            @RequestParam(value = "path", required = false) String path) {
        //path="A\\Controller.ftl";
        boolean b = ReadDirectory.createDirectory(TEMPLATE_PATH + File.separator + UserCode + File.separator + path);
        return new AjaxResult(b);
    }

    /**
     * 删除模板(目录,文件)
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "deleteTemplate")
    public AjaxResult deleteTemplate(
            @RequestParam(value = "path", required = false) String path) {
        //path="A\\Controller.ftl";
        boolean b = ReadDirectory.deleteDirectory(TEMPLATE_PATH + File.separator + UserCode + File.separator + path);
        return new AjaxResult(b);
    }

    /**
     * 获取最新模板内容
     */
    @RequestMapping(value = "updateCode")
    public AjaxResult updateCode(@RequestParam(value = "path", required = false) String path) {

        try {
            SVNClientManager clientManager = SVNUtil.authSvn(TEMPLATE_SVN, SVN_USER, SVN_PWD);

            SVNURL repositoryURL = null;    // trunk
            try {
                repositoryURL = SVNURL.parseURIEncoded(TEMPLATE_SVN);
            } catch (SVNException e) {
            }

            File wc_project = new File(TEMPLATE_PATH);

            if (!SVNUtil.isWorkingCopy(wc_project)) {
                SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, wc_project, SVNDepth.INFINITY);
            } else {
                SVNUtil.update(clientManager, wc_project, SVNRevision.HEAD, SVNDepth.INFINITY);
            }
            return new AjaxResult(1);
        } catch (Exception e) {
            return new AjaxResult(-1, e.getMessage());
        }
    }

}