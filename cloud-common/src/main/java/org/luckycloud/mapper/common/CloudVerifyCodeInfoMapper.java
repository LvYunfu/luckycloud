package org.luckycloud.mapper.common;

import org.luckycloud.domain.common.CloudVerifyCodeInfoDO;

/**
* @author lvyf
* @description 针对表【cloud_verify_code_info】的数据库操作Mapper
* @createDate 2025-06-07 23:44:24
* @Entity org.luckycloud.domain.common.CloudVerifyCodeInfoDO
*/
public interface CloudVerifyCodeInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudVerifyCodeInfoDO record);

    int insertSelective(CloudVerifyCodeInfoDO record);

    CloudVerifyCodeInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudVerifyCodeInfoDO record);

    int updateByPrimaryKey(CloudVerifyCodeInfoDO record);

}
