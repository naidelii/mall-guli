package com.mall.member.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.member.biz.domain.dto.MemberLevelQuery;
import com.mall.member.biz.domain.vo.MemberLevelListVO;
import com.mall.member.biz.service.IMemberLevelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 会员等级
 *
 * @author naidelii
 */
@Api(tags = "会员等级")
@Slf4j
@RequestMapping("/member/level")
@RestController
@RequiredArgsConstructor
public class MemberLevelController {

    private final IMemberLevelService levelService;

    @GetMapping("/listPage")
    public Result<IPage<MemberLevelListVO>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                     @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                     MemberLevelQuery query) {
        IPage<MemberLevelListVO> pageList = levelService.selectListPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }
}
