package com.zyn.storage.core.page.service;

import org.springframework.ui.Model;

public interface IPageService {
    /**
     * 首页跳转数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    String indexHandle();

    /**
     * 跳转到主页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    String homeHandle(Model model);

    /**
     * 跳转到分享管理页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    String shareHandle(Model model);

    /**
     * 查看分享页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    String sHandle(Model model, String shareId);
}
