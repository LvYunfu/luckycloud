package org.luckycloud.ai.convert;

import jakarta.annotation.Resource;
import org.luckycloud.ai.dto.*;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;
import org.luckycloud.dto.emoji.request.EmojiIpListQueryDTO;
import org.luckycloud.dto.emoji.response.EmojiIpStatistic;
import org.luckycloud.utils.UploadUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * 表情IP对象转换器
 * @author lvyf
 * @date 2026/5/7
 */
@Mapper(componentModel = "spring")
public abstract class EmojiIpConvert {

    @Resource
    protected UploadUtils uploadUtils;

    /**
     * DO转Response
     */
    @Mapping(target = "imageUrl", expression = "java(uploadUtils.getFileUrl(ipInfoDO.getUserId(),\"emoji_ip\",ipInfoDO.getImageUrl()))")
    public abstract EmojiIpResponse convert2Response(CloudEmojiIpInfoDO ipInfoDO);


    public abstract  EmojiIpListQueryDTO convert2QueryDto(EmojiIpListQuery ipInfoDO);

    public abstract  List<EmojiIpResponse> convert2ResponseList(List<CloudEmojiIpInfoDO> ipInfoDOList);

    /**
     * Command转DO - 创建表情IP
     */
    @Mapping(target = "ipId", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    public abstract CloudEmojiIpInfoDO convert2DO(EmojiIpCreateCommand command);

    /**
     * Command转DO - 更新表情IP
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    public abstract CloudEmojiIpInfoDO convert2DO(EmojiIpUpdateCommand command);

    public abstract EmojiIpStatisticResponse convert2StatisticResponse(EmojiIpStatistic statistic);
}
