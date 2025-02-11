package top.quhailong.pan.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import top.quhailong.pan.request.ForgetPhoneSendRequest;
import top.quhailong.pan.request.ModifyPassRequest;
import top.quhailong.pan.user.service.PasswordService;
import top.quhailong.pan.request.base.RestAPIResultDTO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 密码服务
 *
 * @author: quhailong
 * @date: 2019/9/26
 */
@RestController
public class PasswordController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private HttpServletRequest httpServletRequest;
    @Autowired
    private PasswordService passwordService;

    /**
     * 忘记密码短信服务
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @RequestMapping(value = "forgetphonesend", method = RequestMethod.POST)
    public RestAPIResultDTO<String> forgetPhoneSend(@RequestBody ForgetPhoneSendRequest request) {
        logger.info("忘记密码短信服务请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("忘记密码短信服务数据处理开始,request:{}", request);
        RestAPIResultDTO<String> result = passwordService.forgetPhoneSendHandle(request);
        logger.info("忘记密码短信服务数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("忘记密码短信服务调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 手机号/用户名校验
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @RequestMapping(value = "checkphonesend", method = {RequestMethod.GET})
    public RestAPIResultDTO<String> checkPhoneSend(@RequestParam("username") String username) {
        logger.info("手机号/用户名校验服务请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("手机号/用户名校验数据处理开始,username:{}", username);
        RestAPIResultDTO<String> result = passwordService.checkPhoneSendHandle(username);
        logger.info("手机号/用户名校验数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("手机号/用户名校验调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 忘记密码的修改
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @RequestMapping(value = "modifypass", method = {RequestMethod.POST})
    public RestAPIResultDTO<String> modifyPass(@RequestBody ModifyPassRequest request) {
        logger.info("忘记密码的修改服务请求URL：{}", httpServletRequest.getRequestURL());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("忘记密码的修改数据处理开始,request:{}", request);
        RestAPIResultDTO<String> result = passwordService.modifyPassHandle(request);
        logger.info("忘记密码的修改数据处理结束,result:{}", result);
        stopWatch.stop();
        logger.info("忘记密码的修改调用时间,millies:{}", stopWatch.getTotalTimeMillis());
        return result;
    }
}
