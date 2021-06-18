package com.shangyitong.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shangyitong.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    void importDictData(MultipartFile multipartFile);


    List<Dict> findChildData(long id);

    void exportData(HttpServletResponse httpServletResponse);
}
