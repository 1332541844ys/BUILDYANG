package zzz.leyou.item.api;


import org.springframework.web.bind.annotation.*;
import zzz.leyou.item.pojo.Brand;

@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}
