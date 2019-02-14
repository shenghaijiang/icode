package cn.xtits.icode.core;

import cn.xtits.icode.dto.FileInfo;
import cn.xtits.icode.dto.Project;
import cn.xtits.icode.dto.TableModel;
import cn.xtits.icode.util.FileZipUtil;
import com.google.common.base.CaseFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadDirectory {

    /**
     * 模板路径
     */
    @Value("${TEMPLATE_PATH}")
    private String TEMPLATE_PATH;

    @Value("${PROJECT_PATH}")
    private String PROJECT_PATH;

    // 文件所在的层数
    private static int fileLevel;

    /**
     * 输出给定目录下的文件，包括子目录中的文件
     *
     * @param fileInfo 给定的目录
     */
    public static void readFile(FileInfo fileInfo, String prefix) {
        {
            // 建立当前目录中文件的File对象
            File file = new File(fileInfo.getPath());
            fileInfo.setPath(fileInfo.getPath().replace(prefix, ""));
            List<FileInfo> fileList = new ArrayList<>();
            // 取得代表目录中所有文件的File对象数组
            File[] list = file.listFiles();
            fileInfo.setList(fileList);
            // 遍历file数组
            for (File item : list) {
                item.getParent();
                FileInfo info = new FileInfo();
                info.setName(item.getName());
                info.setPath(item.getAbsolutePath());
                info.setParent(item.getParent());
                info.setDirectory(item.isDirectory());
                if (item.isDirectory()) {
                    //System.out.println(createPrintStr(item.getName(), fileLevel));
                    info.setFileLevel(fileLevel);
                    fileLevel++;
                    // 递归子目录
                    readFile(info, prefix);

                    item.getName();
                    fileLevel--;
                } else {
                    //System.out.println(createPrintStr(item.getName(), fileLevel));
                }
                info.setPath(item.getAbsolutePath().replace(prefix, ""));
                info.setPath(item.getParent().replace(prefix, ""));
                fileList.add(info);
            }
        }
    }

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean writeFile(String path, String content) {
        File file = new File(path);
        try {
            FileUtils.writeStringToFile(file, content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean createTemplate(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return false;
        } else {
            file.mkdir();
            file = new File(path + File.separator + "java");
            file.mkdir();
            file = new File(path + File.separator + "web");
            file.mkdir();
            return true;
        }
    }

    public static boolean createDirectory(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return false;
        } else {
            return file.mkdir();
        }
    }

    public static boolean deleteDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public static byte[] generateTemplateZip(String projectName, String templatePath, String fileName, String tableName, String path, String projectPath, String type) throws Exception {
        String zipUrl = projectPath + File.separator + projectName;
        File projectFile = new File(zipUrl);
        if (projectFile.exists()) {
            //删除之前生成的项目
            FileUtils.deleteDirectory(projectFile);
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath(path);
        fileInfo.setDirectory(true);
        readFile(fileInfo, path);
        System.out.println("zipUrl:" + zipUrl + ",path:" + path + ",templatePath:" + templatePath + ",projectPath:" + projectPath);
        Project project = Run.getProject(projectName);
        generateCode("", project, fileInfo, path, templatePath, tableName, false, projectPath, type);

        if (!new File(zipUrl).exists()) {
            throw new IOException("目录不存在!");
        }
        ByteArrayOutputStream outputStream = FileZipUtil.zipFileWithTier(zipUrl);

        return outputStream.toByteArray();
    }

    public static boolean generateTemplate(String projectName, String templatePath, String path, String tableName, String projectPath, String type) throws Exception {
        File projectFile = new File(projectPath + File.separator);
//        if (projectFile.exists()) {
//            //删除之前生成的项目
//            FileUtils.deleteDirectory(projectFile);
//        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath(path);
        fileInfo.setDirectory(true);
        //读取模版文件
        readFile(fileInfo, path);
        Project project = Run.getProject(projectName);
        generateCode("", project, fileInfo, path, templatePath, tableName, false, projectPath, type);
        return true;
    }

    /**
     * @param fileInfo
     * @param prefix
     * @param webFlag  是否根据模板路径存放代码(true:模板包含完成路径 false:路径为项目pakeage)
     * @throws Exception
     */

    public static void generateCode(String module, Project project, FileInfo fileInfo, String prefix, String templatePath, String tableName, boolean webFlag, String projectPath, String type) throws Exception {

        Boolean projectFlag = type.equals("project");
        ArrayList<String> tableList = new ArrayList<>();
        if (StringUtils.isNotBlank(tableName)) {
            String[] split = tableName.split(",");
            for (String s : split) {
                tableList.add(s.toLowerCase());
            }
        }

        String xd = "";
        for (FileInfo item : fileInfo.getList()) {

            if (!item.isDirectory()) {

//                if (!item.getName().endsWith(".ftl")) {
//
//                    Run.copyFile(item.getParent(), project.getCode() + File.separator + item.getPath(), item.getName());
//                    continue;
//                }
                String codeFileName = item.getName().replace(".ftl", "").replace("[project]", project.getCode());

                {
                    if (item.getName().indexOf("[project]") >= 0) {
                        xd = "";
                    } else if (item.getPath().indexOf("[project]") >= 0) {
                        String fullPath = item.getPath() + File.separator;
                        String substring = fullPath.substring(fullPath.indexOf("[project]") + 1);
                        substring = substring.substring(substring.indexOf(File.separator) + 1);
                        if (substring.equals("")) {
                            xd = "";
                        } else {
                            xd = substring.substring(0, substring.length() - 1);
                        }
                    }
                }

                //region Web生成
                if (webFlag) {
                    String dest = projectPath + File.separator + project.getCode() + item.getPath().replace("[project]", project.getCode()) + File.separator + item.getName().replace(".ftl", "").replace("[project]", project.getCode());
                    //非模板文件生成直接Copy
                    if (projectFlag && !item.getName().endsWith(".ftl")) {
                        Run.copyFile(item.getParent() + File.separator + item.getName(), dest);
                        continue;
                    }
                    if (item.getName().indexOf("[table") >= 0) {
                        for (TableModel tableModel : project.getTables()) {
                            if (!projectFlag && !tableList.contains(tableModel.getCode().toLowerCase())) {
                                continue;
                            }
                            String kebab = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tableModel.getTableAlias());
                            String tempDest = dest.replace("[table]", tableModel.getTableAlias()).replace("[tableKebab]", kebab).replace("[tablePath]", tableModel.getCode().toLowerCase() + File.separator).replace("[tableKebabPath]", kebab + File.separator).replace("[project]", project.getCode());
                            ;
                            System.out.println("dest:" + tempDest);
                            Run.generateFile(tableModel, item.getParent() + File.separator + item.getName(), tempDest);
                        }
                    } else if (projectFlag) {
                        Run.generateFile(project, item.getParent() + File.separator + item.getName(), dest);
                    }
                }
                //endregion Web生成

                //region Java生成
                else {
                    if (item.getName().indexOf("[table") >= 0) {
                        for (TableModel tableModel : project.getTables()) {
                            if (!projectFlag && !tableList.contains(tableModel.getCode().toLowerCase())) {
                                continue;
                            }

                            String tablepath = "";
                            if (item.getName().indexOf("[tablePath]") == 0) {
                                tablepath = "." + tableModel.getTableAlias();
                            }
                            String tempName = codeFileName;
                            tempName = tempName.replace("[table]", tableModel.getTableAlias()).replace("[tablePath]", "");
                            String fullPath = project.getCode() + ".java." + module + ".src.main.java." + project.getPackageName() + File.separator + xd;
                            Run.generateFile(tableModel, item.getParent(), item.getName(), fullPath, tempName);
                        }
                    } else if (projectFlag && item.getName().equals("pom.xml.ftl")) {
                        Run.generateFile(project, item.getParent(), item.getName(), project.getCode() + ".java." + module, codeFileName);
                    } else if (projectFlag && item.getPath().indexOf("resources") >= 0) {
                        if (!item.getName().endsWith(".ftl")) {
                            Run.copyFile(item.getParent() + File.separator + item.getName(), projectPath + File.separator + project.getCode() + File.separator + "java" + File.separator + module + File.separator + "src" + File.separator + "main" + File.separator + xd + File.separator + item.getName());
                        } else {
                            Run.generateFile(project, item.getParent(), item.getName(), project.getCode() + ".java." + module + ".src.main." + File.separator + xd, codeFileName);
                        }
                    } else if (projectFlag && item.getName().indexOf(".java") > 0) {
                        String fullPath = project.getCode() + ".java." + module + ".src.main.java." + project.getPackageName() + File.separator + xd;
                        if (item.getName().endsWith(".ftl")) {
                            Run.generateFile(project, item.getParent(), item.getName(), fullPath, codeFileName);

                        } else {
                            fullPath = fullPath.replaceAll("\\.", "/");
                            Run.copyFile(item.getParent() + File.separator + item.getName(), projectPath + File.separator + fullPath + File.separator + item.getName());

                        }

                    }
                }
                //endregion
            } else {
                //是否是Web项目生成
                boolean web_Flag = !((item.getParent().replace(templatePath, "") + (item.getName())).indexOf("java") >= 0);
                if (item.getName().indexOf("[project]") >= 0) {
                    module = item.getName().replace("[project]", project.getCode());
                }
                generateCode(module, project, item, prefix, templatePath, tableName, web_Flag, projectPath, type);
            }
        }
    }


    public static void main(String[] args) {

    }
}