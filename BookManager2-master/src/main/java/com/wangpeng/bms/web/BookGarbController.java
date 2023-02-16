package com.wangpeng.bms.web;

import com.wangpeng.bms.model.BookInfo;
import com.wangpeng.bms.service.BookInfoService;
import com.wangpeng.bms.utils.MyResult;
import com.wangpeng.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/bookGarb")
public class BookGarbController {
    @Autowired
    BookInfoService bookInfoService;

    // 获取图书数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return bookInfoService.getCount2();
    }

    // 查询所有图书信息
    @GetMapping(value = "/queryBookInfos")
    public List<BookInfo> queryBookInfos(){
        return bookInfoService.queryBookInfos2();
    }

    // 分页搜索查询图书信息 params: {page, limit, bookname, bookauthor, booktypeid}
    @GetMapping(value = "/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
//        MyUtils.parsePageParams(params);
//        System.out.println(params);
        int count = bookInfoService.getSearchCount2(params);  // 获得总数
//        System.out.println(count);
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage2(params);  // 分页查询
//        System.out.println(bookInfos);
        return MyResult.getListResultMap(0, "success", count, bookInfos);
    }

    // 删除图书信息
    @DeleteMapping(value = "/deleteBookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.deleteBookInfo2(bookInfo);
    }

    // 删除一些图书信息
    @DeleteMapping(value = "/deleteBookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.deleteBookInfos2(bookInfos);
    }
    @PostMapping(value = "/recoverBookInfo")
    public Integer recoverBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.recoverBookInfo(bookInfo);
    }
    @PostMapping(value = "/recoverBookInfos")
    public Integer recoverBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.recoverBookInfos(bookInfos);
    }
}
