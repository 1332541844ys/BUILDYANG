package zzz.leyou.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import zzz.leyou.item.pojo.SpecGroup;
import zzz.leyou.item.pojo.SpecParam;
import zzz.leyou.item.service.SpecificationService;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamsByGid(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "generic",required = false)Boolean generic,
            @RequestParam(value = "searching",required = false)Boolean searching
    ){
        List<SpecParam> params = this.specificationService.queryParamsByGid(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 新增
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveParams(@RequestBody SpecParam specParam){
        this.specificationService.saveParams(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    //更新
    @PutMapping("param")
    public ResponseEntity<Void> updateParams(@RequestBody SpecParam specParam){
        this.specificationService.updateParams(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //删除
    @DeleteMapping("param/{gid}")
    public ResponseEntity<Void> deleteParams(@PathVariable("gid")Long gid){
        this.specificationService.deleteParams(gid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id查询规格参数组和下面的规格参数
     * @param cid
     * @return
     */
    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsWithParam(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroups = this.specificationService.queryGroupsWithParam(cid);
        if (CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }

}
