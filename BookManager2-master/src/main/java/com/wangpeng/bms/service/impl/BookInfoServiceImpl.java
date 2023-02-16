package com.wangpeng.bms.service.impl;

import com.wangpeng.bms.mapper.BookInfoMapper;
import com.wangpeng.bms.mapper.BookTypeMapper;
import com.wangpeng.bms.model.BookInfo;
import com.wangpeng.bms.model.BookType;
import com.wangpeng.bms.service.BookInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BookInfoServiceImpl implements BookInfoService {

    @Resource
    private BookInfoMapper bookInfoMapper;
    @Resource
    private BookTypeMapper bookTypeMapper;

    @Override
    public Integer getCount() {
        return bookInfoMapper.selectCount();
    }

    @Override
    public List<BookInfo> queryBookInfos() {
        return bookInfoMapper.selectAll();
    }

    @Override
    public BookInfo queryBookInfoById(Integer bookid) {
        return bookInfoMapper.selectByPrimaryKey(bookid);
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookInfoMapper.selectCountBySearch(params);
    }
    @Override
    public Integer getSearchCount2(Map<String, Object> params){
        return bookInfoMapper.selectCountBySearch2(params);
    }

    @Override
    public List<BookInfo> searchBookInfosByPage(Map<String, Object> params) {
//        System.out.println(params);
        return bookInfoMapper.selectBySearch(params);
    }

    @Override
    public Integer addBookInfo(BookInfo bookInfo) {
        BookType bookType = bookTypeMapper.selectByPrimaryKey(bookInfo.getBooktypeid());
        bookType.setStock(bookType.getStock() + 1);
        bookTypeMapper.updateByPrimaryKeySelective(bookType);
        return bookInfoMapper.insertSelective(bookInfo);
    }

    @Override
    public Integer deleteBookInfo(BookInfo bookInfo) {
        BookType bookType = bookTypeMapper.selectByPrimaryKey(bookInfo.getBooktypeid());
//        System.out.println("傻逼类型测试开始");
//        System.out.println(bookType);
//        System.out.println(bookType.getBooktypename());
        bookType.setStock(bookType.getStock() - 1);
        bookTypeMapper.updateByPrimaryKeySelective(bookType);
        bookInfo.setIsdel(1);
        return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }

    @Override
    public Integer deleteBookInfos(List<BookInfo> bookInfos) {
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            BookType bookType = bookTypeMapper.selectByPrimaryKey(bookInfo.getBooktypeid());
            bookType.setStock(bookType.getStock() - 1);
            bookTypeMapper.updateByPrimaryKeySelective(bookType);
            bookInfo.setIsdel(1);
            count += bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
        }
        return count;
    }

    @Override
    public Integer updateBookInfo(BookInfo bookInfo) {
        return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }
    @Override
    public BookInfo getById(Integer id){
        return bookInfoMapper.selectByPrimaryKey(id);
    }
    @Override
    public Integer getCount2(){
        return bookInfoMapper.selectCount2();
    }


    @Override
    public Integer deleteBookInfos2(List<BookInfo> bookInfos){
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            bookInfo.setIsdel(1);
            count += bookInfoMapper.deleteByPrimaryKey(bookInfo.getBookid());
        }
        return count;
    }
    @Override
    public List<BookInfo> queryBookInfos2(){
        return bookInfoMapper.selectAll2();
    }
    @Override
    public List<BookInfo> searchBookInfosByPage2(Map<String, Object> params){
        return bookInfoMapper.selectBySearch2(params);
    }
    @Override
    public Integer deleteBookInfo2(BookInfo bookInfo){
        return bookInfoMapper.deleteByPrimaryKey(bookInfo.getBookid());
    }
    @Override
    public Integer recoverBookInfo(BookInfo bookInfo){
        BookType bookType = bookTypeMapper.selectByPrimaryKey(bookInfo.getBooktypeid());
        bookType.setStock(bookType.getStock() + 1);
        bookTypeMapper.updateByPrimaryKeySelective(bookType);
        bookInfo.setIsdel(0);
        return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }
    @Override
    public Integer recoverBookInfos(List<BookInfo> bookInfos){
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            BookType bookType = bookTypeMapper.selectByPrimaryKey(bookInfo.getBooktypeid());
            bookType.setStock(bookType.getStock() + 1);
            bookTypeMapper.updateByPrimaryKeySelective(bookType);
            bookInfo.setIsdel(0);
            count += bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
        }
        return count;
    }

}
