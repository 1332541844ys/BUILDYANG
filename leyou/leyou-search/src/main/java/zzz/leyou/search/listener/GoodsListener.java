package zzz.leyou.search.listener;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zzz.leyou.search.service.SearchService;

import java.io.IOException;

@Component
public class GoodsListener {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private SearchService searchService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.SAVE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    //接收保存消息
    public void save(Long id) throws IOException {//异常要抛，不能处理，抛出异常，spring帮你ack确认
        if (id == null){
            return;
        }
        this.searchService.save(id);
    }
    //接收删除消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.DELETE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    //接收保存消息
    public void delete(Long id) throws IOException {//异常要抛，不能处理，抛出异常，spring帮你ack确认
        if (id == null){
            return;
        }
        this.searchService.delete(id);
    }
}
