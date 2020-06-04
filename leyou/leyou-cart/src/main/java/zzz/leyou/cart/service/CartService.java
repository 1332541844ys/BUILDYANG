package zzz.leyou.cart.service;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.util.SecretKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zzz.leyou.cart.client.GoodsClient;
import zzz.leyou.cart.interceptor.LoginInterceptor;
import zzz.leyou.cart.pojo.Cart;
import zzz.leyou.common.pojo.UserInfo;
import zzz.leyou.common.utils.JsonUtils;
import zzz.leyou.item.api.GoodsApi;
import zzz.leyou.item.pojo.Sku;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "user:cart:";
    @Autowired
    private GoodsClient goodsClient;
    public void addCart(Cart cart) {
        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //查询购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();
        //判断当前商品是否在购物车中
        if (hashOperations.hasKey(key)) {
            //在，更新数量
            String cartJson = hashOperations.get(key).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum()+num);

        } else {
            //不在，新增购物车
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" :StringUtils.split(sku.getImages(),",")[0]);
            cart.setPrice(sku.getPrice());

        }
        hashOperations.put(key,JsonUtils.serialize(cart));


    }

    public List<Cart> queryCarts() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())){
            return null;
        }
        //获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        //获取购物车Map中的所有Cart值集合
        List<Object> cartsJson = hashOperations.values();
        //如果购物车集合为空，返回null
        if (CollectionUtils.isEmpty(cartsJson)){
            return null;
        }
        //把List<Object>转换为List<Cart>集合
        List<Cart> carts = cartsJson.stream().map(cartJson -> JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList());
        return carts;
    }

    public void updateNum(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())){
            return;
        }
        Integer num = cart.getNum();
        //获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        cart = JsonUtils.parse(cartJson, Cart.class);
        cart.setNum(num);
        hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));

    }
    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getUserInfo();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }
    public void deleteAllCart() {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getUserInfo();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(key);
    }
    //合并购物车
    public void addLocalCart(List<Cart> carts) {
        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //查询购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        carts.forEach(cart -> {
            String key = cart.getSkuId().toString();
            Integer num = cart.getNum();
            //判断当前商品是否在购物车中
            if (hashOperations.hasKey(key)){
                //在，更新数量
                String cartJson = hashOperations.get(key).toString();
                cart = JsonUtils.parse(cartJson, Cart.class);
                cart.setNum(cart.getNum()+num);
            }else {
                //不在，新增购物车
                cart.setUserId(userInfo.getId());

            }
            hashOperations.put(key,JsonUtils.serialize(cart));
        });

    }
}
