package ${model.packageName}.controller;

import cn.xtits.xtf.common.web.AjaxResult;
import org.apache.hadoop.hbase.client.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @fileName: TestController.java
 * @author: Dan
 * @createDate: 2018/2/6 15:20
 * @description: 测试
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("testJson")
    @ResponseBody
    public AjaxResult testJson() {

        return new AjaxResult("index_ok,成功!" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }

}
