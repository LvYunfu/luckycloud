package org.luckycloud.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
@Component
public class TransactionalUtils {


    @Transactional(rollbackFor = Exception.class)
    public void executeInTransaction(List<Active> activeList) {
        for (Active active : activeList) {
            active.doActive();
        }
    }

    public interface Active {
        void doActive();
    }
}
