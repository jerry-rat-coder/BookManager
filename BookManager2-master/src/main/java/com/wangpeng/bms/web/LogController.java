package com.wangpeng.bms.web;

import com.wangpeng.bms.model.Log;
import com.wangpeng.bms.service.LogService;
import com.wangpeng.bms.utils.MyResult;
import com.wangpeng.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class LogController{
    @Autowired
    LogService logService;
    @GetMapping(value = "/getCount")
    public Integer getCount(){
//        System.out.println(123);
        return logService.getCount();
    }
    @GetMapping(value = "/queryAdminsByPage")
    public Map<String, Object> queryAdminsByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
//        System.out.println(params);
//        System.out.println(logService);
//        int count = logService.getSearchCount(params);  // 获得总数
        int count = logService.getCount();
        List<Log> logInfos = logService.searchLogsByPage(params);  // 分页查询
        return MyResult.getListResultMap(0, "success", count, logInfos);
    }
    @GetMapping(value = "/queryAdmins")
    public List<Log> queryAdmins(){
        return logService.searchAll();
    }
    @PostMapping(value = "/addadmin")
    public Integer addAdmin(Integer id,String content,String note){
        Log log = new Log();
        log.setOperateTime(new Date(System.currentTimeMillis()));
        log.setOperatorId(id);
        log.setNote(note);
        log.setOperateContent(content);
        return logService.addLog(log);
    }
    @DeleteMapping(value = "/deleteAdmin")
    public Integer deleteLog(@RequestBody Log log){
        log.setIsdel(1);
        return logService.updateLog(log);
    }

}
