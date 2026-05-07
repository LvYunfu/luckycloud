package org.luckycloud.ai.convert;

import org.luckycloud.ai.dto.EmojiIpCreateCommand;
import org.luckycloud.ai.dto.EmojiIpResponse;
import org.luckycloud.ai.dto.EmojiIpUpdateCommand;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;
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
public interface EmojiIpConvert {

    EmojiIpConvert INSTANCE = Mappers.getMapper(EmojiIpConvert.class);

    /**
     * DO转Response
     */
    EmojiIpResponse convert2Response(CloudEmojiIpInfoDO ipInfoDO);

    List<EmojiIpResponse> convert2ResponseList(List<CloudEmojiIpInfoDO> ipInfoDOList);

    /**
     * Command转DO - 创建表情IP
     */
    @Mapping(target = "ipId", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    CloudEmojiIpInfoDO convert2DO(EmojiIpCreateCommand command);

    /**
     * Command转DO - 更新表情IP
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    CloudEmojiIpInfoDO convert2DO(EmojiIpUpdateCommand command);
}
