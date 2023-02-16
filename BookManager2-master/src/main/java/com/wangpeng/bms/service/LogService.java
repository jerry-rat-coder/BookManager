package com.wangpeng.bms.service;

import com.wangpeng.bms.model.Log;

import java.util.List;
import java.util.*;

public interface LogService {
    int getCount();
    List<Log> searchLogsByPage(Map<String, Object> params);
    Integer getSearchCount(Map<String, Object> params);
    List<Log> searchAll();
    Integer addLog(Log log);
    Integer deletLog(Log log);
    Integer updateLog(Log log);
}
