package cn.xtits.icode.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import javax.annotation.Resource;
import java.io.File;
import java.util.ResourceBundle;

@Service("svnProjectService")
public class SvnProjectService {

//    private Logger logger = Logger.getLogger(SvnProjectService.class);
//
//    // 项目的存放位置
//    private String workspace = null;
//
//    private ResourceBundle rb = ResourceBundle.getBundle("application");
//
//    // SVN的用户名、密码
//    private String username = null;
//    private String password = null;
//
//    private String templete = null;
//
//    @Resource(name="xcodeService")
//    private XcodeService xcodeService;
//
//
//    private void init(){
//        String webapp = System.getProperty("webapp.root");
//        if(null!=webapp&&!webapp.endsWith("/") && !webapp.endsWith("\\")){
//            webapp = webapp + "/";
//        }
//        // 发布到web服务器以后，有可能WebContent没了
//        if(new File(webapp + "WebContent").exists()){
//            webapp = webapp + "WebContent";
//        }
//        username = rb.getString("svn.username");
//        password = rb.getString("svn.password");
//        workspace = rb.getString("project.svn.path");
//        templete = webapp + "templete";
//    }
//
//    public SvnProjectService(){
//        super();
//        init();
//    }
//
//    /**
//     * 创建项目框架
//     * @param project
//     *          Project
//     * @return
//     */
//    public boolean createProjectFrame(Project project,List<String> tableNames) {
//        if(project == null){
//            return false;
//        }
//        File src = new File(templete);  // 模板项目的位置
//        File ws = new File(workspace);  // work copy
//        if(!ws.exists()){
//            ws.mkdirs();
//        }
//        File dest = new File(workspace + "/" + project.getName());
//        if(!dest.exists()){
//            dest.mkdirs();
//        }
//
//        checkWorkCopy(project); // 确定工作空间
//
//        // 复制模板项目到工作空间
//        try {
//            FileUtils.copyDirectory(src, dest);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//            return false;
//        }
//
//        //修改.project文件中的内容
//        editProjectFile(project);
//
//        // 生成框架代码
//        xcodeService.createBaseFrameWithDatasource(project,tableNames);
//
//        // 提交到SVN
//        commitProjectToSvn(project);
//
//        return true;
//    }
//
//    /**
//     * 修改项目文件
//     * @param project
//     * @throws DocumentException
//     * @throws IOException
//     */
//    @SuppressWarnings("unchecked")
//    private void editProjectFile(Project project) {
//
//        String pro = workspace + "/" + project.getName() + "/";
//
//        String settings = pro + ".settings/";
//
//        // 1. 修改.settings/org.eclipse.wst.common.component
//        Document document = null;
//        try {
//            document = XmlReaderUtil.getDocument(new File(settings
//                    + "org.eclipse.wst.common.component"));
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        Element root = document.getRootElement();
//
//        root.element("wb-module").attribute("deploy-name")
//                .setValue(project.getName());
//
//        if (root.element("wb-module").element("property").attribute("name")
//                .getValue().equals("java-output-path")) {
//
//            root.element("wb-module").element("property").attribute("value")
//                    .setValue("/" + project.getName() + "/build/classes");
//        }
//
//        Iterator<Element> itr = (Iterator<Element>) XmlReaderUtil.getElements(
//                document, "//wb-module//property").iterator();
//
//        while (itr.hasNext()) {
//            Element element = itr.next();
//            if ("context-root".equals(element.attribute("name").getValue())) {
//                element.attribute("value").setValue("/" + project.getName());
//            }
//        }
//
//        // 将修改后的值写入
//        XmlReaderUtil.writerXml(document, settings
//                + "org.eclipse.wst.common.component");
//
//        // 2. 修改.project
//        try {
//            document = XmlReaderUtil.getDocument(new File(pro + ".project"));
//            XmlReaderUtil.setElementText(document, "//projectDescription",
//                    "name", project.getName());
//            XmlReaderUtil.writerXml(document, pro + ".project");
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 从SVN更新项目到work copy
//     * @param project
//     *          Project
//     * @return
//     */
//    public boolean updateProjectFromSvn(Project project) {
//        if(null == project || null == rb.getString("svn.url")){
//            return false;
//        }
//        project.setSvnUrl(rb.getString("svn.url"));
//
//        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
//        if (null == clientManager) {
//            logger.error("SVN login error! >>> url:" + project.getSvnUrl()
//                    + " username:" + username + " password:" + password);
//            return false;
//        }
//
//        // 注册一个更新事件处理器
//        clientManager.getCommitClient().setEventHandler(new UpdateEventHandler());
//
//        SVNURL repositoryURL = null;
//        try {
//            // eg: http://svn.ambow.com/wlpt/bsp
//            repositoryURL = SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk/"+project.getName(), false);
//        } catch (SVNException e) {
//            logger.error(e.getMessage(),e);
//            return false;
//        }
//
//        File ws = new File(new File(workspace), project.getName());
//        if(!SVNWCUtil.isVersionedDirectory(ws)){
//            SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, new File(workspace), SVNDepth.INFINITY);
//        }else{
//            SVNUtil.update(clientManager, ws, SVNRevision.HEAD, SVNDepth.INFINITY);
//        }
//        return true;
//    }
//
//    /**
//     * 提交项目到SVN
//     * @param project
//     *          Project
//     * @return
//     */
//    public boolean commitProjectToSvn(Project project) {
//        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
//
//        clientManager.getCommitClient().setEventHandler(new CommitEventHandler());
//
//        File wc_project = new File( workspace + "/" + project.getName());
//
//        checkVersiondDirectory(clientManager,wc_project);
//
//        SVNUtil.commit(clientManager, wc_project, false, "svnkit");
//
//        return true;
//    }
//
//    /**
//     * 递归检查不在版本控制的文件，并add到svn
//     * @param clientManager
//     * @param wc
//     */
//    private void checkVersiondDirectory(SVNClientManager clientManager,File wc){
//        if(!SVNWCUtil.isVersionedDirectory(wc)){
//            SVNUtil.addEntry(clientManager, wc);
//        }
//        if(wc.isDirectory()){
//            for(File sub:wc.listFiles()){
//                if(sub.isDirectory() && sub.getName().equals(".svn")){
//                    continue;
//                }
//                checkVersiondDirectory(clientManager,sub);
//            }
//        }
//    }

    public static void main(String[] args) {
        String svnUrl = "https://192.168.2.101/svn/mes/icode/trunk";
        String workspace = "P:";
        String projectname = "testsvnkit";
        SVNClientManager clientManager = SVNUtil.authSvn(svnUrl, "shenghaijiang", "12345678");

        SVNURL repositoryURL = null;    // trunk
        try {
            repositoryURL = SVNURL.parseURIEncoded(svnUrl);
        } catch (SVNException e) {
        }

//        File wc = new File(workspace);
        File wc_project = new File(workspace + "/" + projectname);

//        SVNURL projectURL = null;   // projectName
//        try {
//            projectURL = repositoryURL.appendPath(projectname, false);
//        } catch (SVNException e) {
//        }

        if (!SVNUtil.isWorkingCopy(wc_project)) {
            SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, wc_project, SVNDepth.INFINITY);
//            if (!SVNUtil.isURLExist(repositoryURL, "shenghaijiang", "12345678")) {
//                SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, wc, SVNDepth.EMPTY);
//            } else {
//                SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, wc_project, SVNDepth.INFINITY);
//            }
        } else {
            SVNUtil.update(clientManager, wc_project, SVNRevision.HEAD, SVNDepth.INFINITY);
        }
    }

}
