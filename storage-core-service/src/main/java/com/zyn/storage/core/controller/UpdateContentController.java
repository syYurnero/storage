package com.zyn.storage.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.zyn.storage.core.service.IUpdateContentService;
import com.zyn.storage.request.CopyOrMoveFileRequest;
import com.zyn.storage.request.CreateDirRequest;
import com.zyn.storage.request.CreateVirtualAddressRequest;
import com.zyn.storage.request.RenameFileOrDirRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 更新服务
 *
 * @author: zhouyongnan
 * @date: 2024/9/24
 */
@RestController
public class UpdateContentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IUpdateContentService updateContentService;

    /**
     * 文件或文件夹重命名
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    //@RequestMapping(value = "rename", method = { RequestMethod.POST })
    @RequestMapping(value = "renamefileordir", method = RequestMethod.PUT)
    public RestAPIResultDTO<String> renameFileOrDir(@RequestBody RenameFileOrDirRequest request) {
        logger.info("文件或文件夹重命名请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("文件或文件夹重命名数据处理开始,request:{}", request);
        RestAPIResultDTO<String> result = updateContentService.renameFileOrDirHandle(request);
        logger.info("文件或文件夹重命名数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("文件或文件夹重命名调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 删除文件
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    //@RequestMapping(value = "del", method = { RequestMethod.POST })
    @RequestMapping(value = "deletefile", method = RequestMethod.DELETE)
    public RestAPIResultDTO<String> deleteFile(String uid, String vids) throws Exception {
        logger.info("删除文件请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("删除文件数据处理开始,vids:{}", vids);
        RestAPIResultDTO<String> result = updateContentService.deleteFileHandle(vids);
        logger.info("删除文件数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("删除文件调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 创建文件夹
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    //@RequestMapping("/createDir")
    @RequestMapping(value = "createdir", method = RequestMethod.POST)
    public RestAPIResultDTO<Map<String, Object>> createDir(@RequestBody CreateDirRequest request) {
        logger.info("创建文件夹请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("创建文件夹数据处理开始,request:{}", request);
        RestAPIResultDTO<Map<String, Object>> result = updateContentService.createDirHandle(request);
        logger.info("创建文件夹数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("创建文件夹调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 文件复制或移动
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    //@RequestMapping(value = "filemanager", method = { RequestMethod.POST })
    @RequestMapping(value = "copyormovefile", method = RequestMethod.PUT)
    public RestAPIResultDTO<String> copyOrMoveFile(@RequestBody CopyOrMoveFileRequest request) {
        logger.info("文件复制或移动请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("文件复制或移动数据处理开始,request:{}", request);
        RestAPIResultDTO<String> result = updateContentService.copyOrMoveFileHandle(request);
        logger.info("文件复制或移动数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("文件复制或移动调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 创建文件(调用)
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    @RequestMapping(value = "createvirtualaddress", method = RequestMethod.POST)
    public RestAPIResultDTO<Integer> createVirtualAddress(@RequestBody CreateVirtualAddressRequest request) {
        logger.info("创建文件请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("创建文件数据处理开始,request:{}", request);
        RestAPIResultDTO<Integer> result = updateContentService.createVirtualAddressHandle(request);
        logger.info("创建文件数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("创建文件调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

}
