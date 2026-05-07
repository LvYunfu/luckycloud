package org.luckycloud.ai.convert;

import org.luckycloud.ai.dto.EmojiInfoCreateCommand;
import org.luckycloud.ai.dto.EmojiInfoResponse;
import org.luckycloud.ai.dto.EmojiInfoUpdateCommand;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * 表情包信息对象转换器
 * @author lvyf
 * @date 2026/5/7
 */
@Mapper(componentModel = "spring")
public interface EmojiInfoConvert {

    EmojiInfoConvert INSTANCE = Mappers.getMapper(EmojiInfoConvert.class);

    /**
     * DO转Response
     */
    EmojiInfoResponse convert2Response(CloudEmojiInfoDO infoDO);

    List<EmojiInfoResponse> convert2ResponseList(List<CloudEmojiInfoDO> infoDOList);

    /**
     * Command转DO - 创建表情包
     */
    @Mapping(target = "emojiId", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    CloudEmojiInfoDO convert2DO(EmojiInfoCreateCommand command);

    /**
     * Command转DO - 更新表情包
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    CloudEmojiInfoDO convert2DO(EmojiInfoUpdateCommand command);
}
