package com.mall.member.biz.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.member.biz.domain.dto.MemberLevelQuery;
import com.mall.member.biz.domain.dto.MemberLevelSaveDTO;
import com.mall.member.biz.domain.dto.MemberLevelUpdateDTO;
import com.mall.member.biz.domain.vo.MemberLevelListVO;
import com.mall.member.biz.entity.MemberLevel;
import com.mall.member.biz.service.IMemberLevelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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

    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody MemberLevelSaveDTO saveDto) {
        MemberLevel data = new MemberLevel();
        BeanUtil.copyProperties(saveDto, data);
        levelService.save(data);
        return Result.success();

    }

    @PostMapping("/update")
    public Result<?> save(@Validated @RequestBody MemberLevelUpdateDTO updateDto) {
        MemberLevel data = new MemberLevel();
        BeanUtil.copyProperties(updateDto, data);
        levelService.updateById(data);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") String id) {
        MemberLevelListVO vo = levelService.getDetailsById(id);
        return Result.success(vo);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        // 执行删除逻辑
        levelService.removeById(id);
        return Result.success();
    }

}
