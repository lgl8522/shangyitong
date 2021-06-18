package com.shangyitong.yygh.cmn.controller;

import com.shangyitong.yygh.cmn.service.DictService;
import com.shangyitong.yygh.common.result.Result;
import com.shangyitong.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable long id) {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    @PostMapping(value = "importData")
    public Result importDict(MultipartFile multipartFile) {
        dictService.importDictData(multipartFile);
        return Result.ok();
    }


    @ApiOperation(value = "导出数据字典")
    @GetMapping("exportData")
    public Result exportData(HttpServletResponse httpServletResponse) {
        dictService.exportData(httpServletResponse);
        return Result.ok();
    }

}
