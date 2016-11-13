package com.aibibang.dao.impl;

import com.aibibang.bean.BasicData;
import com.aibibang.dao.BasicDataDao;
import org.springframework.stereotype.Repository;

/**
 * Created by yanand on 2016/11/6.
 */
@Repository
public class HBaseBasicDataDao implements BasicDataDao {

    @Override
    public void saveToHbase(BasicData basicData) {
        System.out.println(basicData.toString());
    }
}
