package com.wangpeng.bms.service.impl;

import com.wangpeng.bms.mapper.LogMapper;
import com.wangpeng.bms.model.Log;
import com.wangpeng.bms.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;
    //分页查询记录信息
    @Override
    public List<Log> searchLogsByPage(Map<String, Object> params){
//        System.out.println(params);
        List<Log> ans = logMapper.selectBySearch(params);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ans);
        for(Log log : ans) {
//            System.out.println(log.getOperatorName())
            if(log.getOperateTime() != null) log.setOperateTimestr(simpleDateFormat.format(log.getOperateTime()));
            if(log.getOperateTime() != null) log.setOperateTimestr(simpleDateFormat.format(log.getOperateTime()));
        }
//        System.out.println(ans);
        return ans;
//        return logMapper.selectBySearch(params);
    }
    //分页查询记录数量
    @Override
    public Integer getSearchCount(Map<String, Object> params){
        return logMapper.selectCount(params);
    }
    @Override
    public int getCount(){
        return logMapper.selectCounts();
    }
    @Override
    public List<Log> searchAll(){return logMapper.selectAll();}
    @Override
    public Integer addLog(Log log){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
////            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
////            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
////            log.set
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        log.setLogId(logMapper.selectCounts()+1);
        System.out.println(log);
        return logMapper.insert(log); //这里只要插入date即可，不需要存储正确格式的str timestr不存入
    }
    @Override
    public Integer deletLog(Log log){
        return logMapper.deleteByPrimaryKey(log.getLogId());
    }
    @Override
    public Integer updateLog(Log log){
        return logMapper.updateByPrimaryKeySelective(log);
    }

}
