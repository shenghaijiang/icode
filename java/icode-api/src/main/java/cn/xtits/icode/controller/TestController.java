package cn.xtits.icode.controller;

import cn.xtits.xtf.common.web.AjaxResult;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @fileName: TestController
 * @author: Dan
 * @createDate: 2018-08-09 10:37.
 * @description:
 */
@RestController
@RequestMapping("dan")
public class TestController {

    @RequestMapping("test")
    public AjaxResult test01() {

        return new AjaxResult(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }

}
