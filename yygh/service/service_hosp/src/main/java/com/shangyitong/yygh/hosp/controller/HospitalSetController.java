package com.shangyitong.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shangyitong.yygh.common.hander.YyghException;
import com.shangyitong.yygh.common.result.Result;
import com.shangyitong.yygh.common.util.MD5;
import com.shangyitong.yygh.hosp.service.HospitalSetService;
import com.shangyitong.yygh.model.hosp.HospitalSet;
import com.shangyitong.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags ="医院设置管理" )
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
   private   HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospital(){
    List<HospitalSet> list = hospitalSetService.list ();
    return Result.ok(list);
}
    @ApiOperation(value = "根据id删除医院")
    @DeleteMapping("delete"+"{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
        final boolean isremove = hospitalSetService.removeById(id);
        if (isremove) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //条件查询带分页
    @ApiOperation("分页条件查询")
        @PostMapping("findpagehospset/{current}/{limit}")
            public Result   findpagehospset(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
            Page<HospitalSet> page=new Page<>(current,limit) ;
           QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
           String hosname = hospitalSetQueryVo.getHosname();
           String hoscode = hospitalSetQueryVo.getHoscode();
           if (!StringUtils.isEmpty(hosname)){
               wrapper.like("hosname",hospitalSetQueryVo.getHosname());
           }
            if (!StringUtils.isEmpty(hoscode)) {
                wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
            }
            Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);
            return Result.ok(hospitalSetPage);
        }
    @ApiOperation("添加医院设置")
    @PostMapping("saveHospitalSet")
    public  Result saveHopitalSet (@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);
        Random random=new Random();
        hospitalSet.setSignKey( MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean saveflag = hospitalSetService.save(hospitalSet);
        return saveflag? Result.ok():Result.fail();
    }


    @ApiOperation("根据id获取医院设置")
    @GetMapping("getHospSetById")
    public Result getHosHos(@PathVariable long id){
        HospitalSet byId = hospitalSetService.getById(id);
        return  Result.ok(byId);
    }
    @ApiOperation("修改医院设置")
    @PostMapping("updateHospSet")
    public  Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean updateflag = hospitalSetService.updateById(hospitalSet);
        return updateflag? Result.ok():Result.fail();
    }


    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemove")
    public  Result batchMoveHospSet(@RequestBody  List<Long> idList){
        boolean removeflag = hospitalSetService.removeByIds(idList);
        return Result.ok();
    }
    @ApiOperation("解锁与锁定")
    @PostMapping("lockHospSet/{id}/{status}")
    public  Result  lockHospSet(@PathVariable long id,@PathVariable Integer status){
         HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        return  Result.ok();
    }

    @ApiOperation("发送签名")
    @PutMapping("sendKey/{id}")
    public  Result sendKey(@PathVariable long id){
        try{

        }catch (Exception e) {
            throw new YyghException("失败",201);
        }
        HospitalSet hospitalSet = hospitalSetService.getById(id);
         String signKey = hospitalSet.getSignKey();
         String hoscode = hospitalSet.getHoscode();
         //TODO 发送短信
         return Result.ok();
    }

}