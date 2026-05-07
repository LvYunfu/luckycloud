package org.luckycloud.ai.service.impl;

import jakarta.annotation.Resource;
import org.luckycloud.ai.convert.EmojiGroupConvert;
import org.luckycloud.ai.convert.EmojiInfoConvert;
import org.luckycloud.ai.convert.EmojiIpConvert;
import org.luckycloud.ai.dto.*;
import org.luckycloud.ai.service.EmojiService;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;
import org.luckycloud.mapper.emoji.CloudEmojiGroupMapper;
import org.luckycloud.mapper.emoji.CloudEmojiInfoMapper;
import org.luckycloud.mapper.emoji.CloudEmojiIpInfoMapper;
import org.luckycloud.utils.GenerateIdUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.luckycloud.constant.SystemConstant.DISABLE;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
@Service
public class EmojiServiceImpl implements EmojiService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private CloudEmojiGroupMapper emojiGroupMapper;

    @Resource
    private EmojiGroupConvert emojiGroupConvert;

    @Resource
    private CloudEmojiInfoMapper emojiInfoMapper;

    @Resource
    private EmojiInfoConvert emojiInfoConvert;

    @Resource
    private CloudEmojiIpInfoMapper emojiIpInfoMapper;

    @Resource
    private EmojiIpConvert emojiIpConvert;


    @Override
    public String uploadIp(EmojiIpCreateCommand request) {
        return null;
    }

    @Override
    public String createEmojiGroup(EmojiGroupCreateCommand command) {
        CloudEmojiGroupDO groupDO = emojiGroupConvert.convert2DO(command);
        // 生成唯一ID
        groupDO.setEmojiGroupId(GenerateIdUtils.generateId());
        emojiGroupMapper.insert(groupDO);
        return groupDO.getEmojiGroupId();
    }

    @Override
    public void updateEmojiGroup(EmojiGroupUpdateCommand command) {
        CloudEmojiGroupDO groupDO = emojiGroupConvert.convert2DO(command);
        emojiGroupMapper.updateByPrimaryKeySelective(groupDO);
    }

    @Override
    public void deleteEmojiGroup(String emojiGroupId) {
        // 逻辑删除，将状态设置为无效
        CloudEmojiGroupDO groupDO = new CloudEmojiGroupDO();
        groupDO.setEmojiGroupId(emojiGroupId);
        groupDO.setStatus(DISABLE);
        groupDO.setUpdateTime(LocalDateTime.now());
        emojiGroupMapper.updateByPrimaryKeySelective(groupDO);
    }

    @Override
    public EmojiGroupResponse getEmojiGroup(String emojiGroupId) {
        CloudEmojiGroupDO groupDO = emojiGroupMapper.selectByPrimaryKey(emojiGroupId);
        if (groupDO == null) {
            return null;
        }
        return emojiGroupConvert.convert2Response(groupDO);
    }

    @Override
    public List<EmojiGroupResponse> getEmojiGroupList() {
        // 查询所有有效的表情包系列
        List<CloudEmojiGroupDO> groupDOList = emojiGroupMapper.selectAll();

        return emojiGroupConvert.convert2ResponseList(groupDOList);
    }

    @Override
    public String createEmojiInfo(EmojiInfoCreateCommand command) {
        CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
        // 生成唯一ID
        infoDO.setEmojiId(GenerateIdUtils.generateId());
        emojiInfoMapper.insert(infoDO);
        return infoDO.getEmojiId();
    }

    @Override
    public int batchCreateEmojiInfo(List<EmojiInfoCreateCommand> commands) {
        List<CloudEmojiInfoDO> infoDOList = commands.stream()
                .map(command -> {
                    CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
                    infoDO.setEmojiId(GenerateIdUtils.generateId());
                    return infoDO;
                })
                .collect(Collectors.toList());

        return emojiInfoMapper.batchInsert(infoDOList);
    }

    @Override
    public void updateEmojiInfo(EmojiInfoUpdateCommand command) {
        CloudEmojiInfoDO infoDO = emojiInfoConvert.convert2DO(command);
        emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
    }

    @Override
    public void deleteEmojiInfo(String emojiId) {
        // 逻辑删除，将状态设置为无效
        CloudEmojiInfoDO infoDO = new CloudEmojiInfoDO();
        infoDO.setEmojiId(emojiId);
        infoDO.setStatus(DISABLE);
        infoDO.setUpdateTime(LocalDateTime.now());
        emojiInfoMapper.updateByPrimaryKeySelective(infoDO);
    }

    @Override
    public EmojiInfoResponse getEmojiInfo(String emojiId) {
        CloudEmojiInfoDO infoDO = emojiInfoMapper.selectByPrimaryKey(emojiId);
        if (infoDO == null) {
            return null;
        }
        return emojiInfoConvert.convert2Response(infoDO);
    }

    @Override
    public List<EmojiInfoResponse> getEmojiInfoList(String emojiGroupId) {
        // 查询指定系列的有效表情包
        List<CloudEmojiInfoDO> infoDOList = emojiInfoMapper.selectByCondition("1", emojiGroupId);
        return emojiInfoConvert.convert2ResponseList(infoDOList);
    }

    @Override
    public String createEmojiIp(EmojiIpCreateCommand command) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpConvert.convert2DO(command);
        // 生成唯一ID
        ipInfoDO.setIpId(GenerateIdUtils.generateId());
        emojiIpInfoMapper.insert(ipInfoDO);
        return ipInfoDO.getIpId();
    }

    @Override
    public void updateEmojiIp(EmojiIpUpdateCommand command) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpConvert.convert2DO(command);
        emojiIpInfoMapper.updateByPrimaryKeySelective(ipInfoDO);
    }

    @Override
    public void deleteEmojiIp(String ipId) {
        // 逻辑删除，将状态设置为无效
        CloudEmojiIpInfoDO ipInfoDO = new CloudEmojiIpInfoDO();
        ipInfoDO.setIpId(ipId);
        ipInfoDO.setStatus(DISABLE);
        ipInfoDO.setUpdateTime(LocalDateTime.now());
        emojiIpInfoMapper.updateByPrimaryKeySelective(ipInfoDO);
    }

    @Override
    public EmojiIpResponse getEmojiIp(String ipId) {
        CloudEmojiIpInfoDO ipInfoDO = emojiIpInfoMapper.selectByPrimaryKey(ipId);
        if (ipInfoDO == null) {
            return null;
        }
        return emojiIpConvert.convert2Response(ipInfoDO);
    }

    @Override
    public List<EmojiIpResponse> getEmojiIpList() {
        // 查询所有有效的表情IP
        List<CloudEmojiIpInfoDO> ipInfoDOList = emojiIpInfoMapper.selectByCondition("1");
        return emojiIpConvert.convert2ResponseList(ipInfoDOList);
    }

//    @Override
//    public Flux<String> chat(EmojiIpCommand request) {
//        return chatClient.prompt()
//                .stream()
//                 .content();
//    }
}
