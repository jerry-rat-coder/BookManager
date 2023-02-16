package com.wangpeng.bms.web;

import com.wangpeng.bms.model.BookType;
import com.wangpeng.bms.service.BookTypeService;
import com.wangpeng.bms.utils.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private BookTypeService bookTypeService;
    @GetMapping("/example")
    public Map<String, Object> get(){
        List<BookType> alltypes =  bookTypeService.queryBookTypes();
        int[] data = new int[10];
        for (BookType it : alltypes) {
            String name = it.getBooktypename();
            switch (name) {
                case "计算机科学": data[0] += it.getStock(); break;
                case "历史": data[1] += it.getStock(); break;
                case "文学": data[2] += it.getStock(); break;
                case "科幻": data[3] += it.getStock(); break;
                case "小说": data[4] += it.getStock(); break;
                case "外语": data[5] += it.getStock(); break;
                default: break;
            }
        }
        return MyResult.getResultMap(0,"success",data);
    }
}
