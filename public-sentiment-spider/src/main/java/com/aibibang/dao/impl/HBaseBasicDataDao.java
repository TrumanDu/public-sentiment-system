package com.aibibang.dao.impl;

import com.aibibang.bean.BasicData;
import com.aibibang.dao.BasicDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by yanand on 2016/11/6.
 */
@Repository
public class HBaseBasicDataDao implements BasicDataDao {
    private static final Logger log = LoggerFactory.getLogger(HBaseBasicDataDao.class);

    @Override
    public void saveToHbase(BasicData basicData) {
        log.info(basicData == null ? null : basicData.toString());
    }
}
