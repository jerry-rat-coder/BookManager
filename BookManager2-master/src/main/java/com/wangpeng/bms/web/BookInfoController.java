package com.wangpeng.bms.web;

import com.wangpeng.bms.model.BookInfo;
import com.wangpeng.bms.model.BookType;
import com.wangpeng.bms.service.BookInfoService;
import com.wangpeng.bms.service.BookTypeService;
import com.wangpeng.bms.utils.MyResult;
import com.wangpeng.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bookInfo")
public class BookInfoController {

    @Autowired
    BookInfoService bookInfoService;
    @Autowired
    BookTypeService bookTypeService;

    // 获取图书数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return bookInfoService.getCount();
    }

    // 查询所有图书信息
    @GetMapping(value = "/queryBookInfos")
    public List<BookInfo> queryBookInfos(){
        return bookInfoService.queryBookInfos();
    }

    // 分页搜索查询图书信息 params: {page, limit, bookname, bookauthor, booktypeid}
    @GetMapping(value = "/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
//        MyUtils.parsePageParams(params);
//        System.out.println(params);
        int count = bookInfoService.getSearchCount(params);  // 获得总数
//        System.out.println(count);
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);  // 分页查询
//        System.out.println(bookInfos);
        return MyResult.getListResultMap(0, "success", count, bookInfos);
    }

    // 添加图书信息
    @PostMapping(value = "/addBookInfo")
    public Integer addBookInfo(@RequestBody BookInfo bookInfo){
        bookInfo.setIsdel(0);
        return bookInfoService.addBookInfo(bookInfo);
    }

    // 删除图书信息
    @DeleteMapping(value = "/deleteBookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.deleteBookInfo(bookInfo);
    }

    // 删除一些图书信息
    @DeleteMapping(value = "/deleteBookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.deleteBookInfos(bookInfos);
    }

    // 更新图书信息
    @PutMapping(value = "/updateBookInfo")
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo){
        int bookid = bookInfo.getBookid();
        //如果修改了类型 旧类型减1 新类型加1
        BookInfo pro = bookInfoService.getById(bookid);
        BookType protype = bookTypeService.getById(pro.getBooktypeid());
        BookType newtype = bookTypeService.getById(bookInfo.getBooktypeid());
        System.out.println(protype.getStock());
        System.out.println(newtype.getStock());
        protype.setStock(protype.getStock()-1);
        newtype.setStock(newtype.getStock()+1);
        bookTypeService.updateBookType(protype);
        bookTypeService.updateBookType(newtype);
        return bookInfoService.updateBookInfo(bookInfo);
    }
}
