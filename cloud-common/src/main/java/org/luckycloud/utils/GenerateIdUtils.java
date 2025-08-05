package org.luckycloud.utils;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */

public class GenerateIdUtils {


    private static final IdGeneratorOptions options = new IdGeneratorOptions((short) 1);

    static {
        YitIdHelper.setIdGenerator(options);
    }

    public static String generateId() {
        return YitIdHelper.nextId() + "";
    }
}
