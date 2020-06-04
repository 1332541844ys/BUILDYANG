package zzz.leyou.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import zzz.leyou.goods.service.GoodsHtmlService;
import zzz.leyou.goods.service.GoodsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {
    @Autowired
    private TemplateEngine engine; //静态页面模板引擎
    @Autowired
    private GoodsService goodsService;
    @Override
    public void createHtml(Long spuId) {
        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));
        PrintWriter printWriter = null;
        File file = new File("D:\\IdeaProject\\springboot\\tools\\nginx-1.14.0\\html\\item\\" + spuId +".html" );
        try {
            //把静态文件生成到本地硬盘
            printWriter = new PrintWriter(file);

            this.engine.process("item",context,printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null){
                printWriter.close();
            }
        }


    }

    @Override
    public void deleteHtml(Long id) {
        File file = new File("D:\\IdeaProject\\springboot\\tools\\nginx-1.14.0\\html\\item\\" + id +".html" );
        file.deleteOnExit();//方法：存在就删掉
    }
}
