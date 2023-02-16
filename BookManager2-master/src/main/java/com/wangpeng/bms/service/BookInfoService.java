package com.wangpeng.bms.service;

import com.wangpeng.bms.model.BookInfo;

import java.util.List;
import java.util.Map;

public interface BookInfoService {
    Integer getCount();

    List<BookInfo> queryBookInfos();

    BookInfo queryBookInfoById(Integer bookid);

    Integer getSearchCount(Map<String, Object> params);

    List<BookInfo> searchBookInfosByPage(Map<String, Object> params);

    Integer addBookInfo(BookInfo bookInfo);

    Integer deleteBookInfo(BookInfo bookInfo);

    Integer deleteBookInfos(List<BookInfo> bookInfos);

    Integer updateBookInfo(BookInfo bookInfo);
    BookInfo getById(Integer id);

    Integer getCount2();
    List<BookInfo> queryBookInfos2();
    List<BookInfo> searchBookInfosByPage2(Map<String, Object> params);
    Integer deleteBookInfo2(BookInfo bookInfo);
    Integer deleteBookInfos2(List<BookInfo> bookInfos);
    Integer getSearchCount2(Map<String, Object> params);
    Integer recoverBookInfo(BookInfo bookInfo);
    Integer recoverBookInfos(List<BookInfo> bookInfos);
}
