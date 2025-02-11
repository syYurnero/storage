package com.zyn.storage.edge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import com.zyn.storage.edge.service.IEdgeService;
import com.zyn.storage.request.base.RestAPIResultDTO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class EdgeServiceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IEdgeService edgeService;

    /**
     * 生成公钥
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    //@RequestMapping(value = "getPublickKey", method = {RequestMethod.GET})
    @RequestMapping(value = "getpublickey", method = RequestMethod.GET)
    public RestAPIResultDTO<String> getPublicKey() throws Exception {
        logger.info("生成公钥请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("生成公钥数据处理开始");
        RestAPIResultDTO<String> result = edgeService.getPublicKeyHandle();
        logger.info("生成公钥数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("生成公钥调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 生成验证码
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    @RequestMapping(value = "getverfyimg/{vcodestr}", method = RequestMethod.GET)
    public void getVerfyImg(@PathVariable(value = "vcodestr") String vcodestr, HttpServletResponse response) {
        logger.info("生成验证码请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("生成验证码数据处理开始");
        edgeService.getVerfyImgHandle(vcodestr, response);
        logger.info("生成验证码数据处理结束");
        stopWatch.stop();
        logger.info("生成验证码调用时间,millies:{}", stopWatch.getTotalTimeMillis());
    }


    /**
     * 检查密码格式
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    @RequestMapping(value = "regcheckpwd", method = RequestMethod.POST)
    public RestAPIResultDTO<String> regCheckPwd(@RequestParam("password") String password, @RequestParam("publicKey") String publicKey) {
        logger.info("检查密码格式请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("检查密码格式数据处理开始");
        RestAPIResultDTO<String> result = edgeService.regCheckPwdHandle(password, publicKey);
        logger.info("检查密码格式数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("检查密码格式调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 变换图片的UUID
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    //@RequestMapping("/regsmscodestr")
    @RequestMapping(value = "/regsmscodestr", method = RequestMethod.GET)
    public RestAPIResultDTO<String> regsmscodestr() {
        RestAPIResultDTO<String> panResult = new RestAPIResultDTO<>();
        logger.info("变换图片的UUID请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        panResult.setRespCode(1);
        panResult.setRespData(UUID.randomUUID().toString().replaceAll("-", ""));
        stopWatch.stop();
        logger.info("变换图片的UUID调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return panResult;
    }
}
