package org.luckycloud.ai.convert;

import org.luckycloud.ai.dto.EmojiGroupCreateCommand;
import org.luckycloud.ai.dto.EmojiGroupResponse;
import org.luckycloud.ai.dto.EmojiGroupUpdateCommand;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * 表情包系列对象转换器
 * @author lvyf
 * @date 2026/5/7
 */
@Mapper(componentModel = "spring")
public interface EmojiGroupConvert {

    EmojiGroupConvert INSTANCE = Mappers.getMapper(EmojiGroupConvert.class);

    /**
     * DO转Response
     */
    EmojiGroupResponse convert2Response(CloudEmojiGroupDO groupDO);

    List<EmojiGroupResponse> convert2ResponseList(List<CloudEmojiGroupDO> groupDOList);

    /**
     * Command转DO - 创建表情包系列
     */
    @Mapping(target = "emojiGroupId", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    CloudEmojiGroupDO convert2DO(EmojiGroupCreateCommand command);

    /**
     * Command转DO - 更新表情包系列
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    CloudEmojiGroupDO convert2DO(EmojiGroupUpdateCommand command);
}
