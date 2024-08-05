package com.mall.member.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.member.biz.domain.dto.MemberLevelQuery;
import com.mall.member.biz.domain.vo.MemberLevelListVO;
import com.mall.member.biz.entity.MemberLevel;

/**
 * 会员等级
 *
 * @author naidelii
 */
public interface IMemberLevelService extends IService<MemberLevel> {

    IPage<MemberLevelListVO> selectListPage(Integer pageNo, Integer pageSize, MemberLevelQuery query);
}

