package com.aibibang.dao;

import com.aibibang.bean.BasicData;

/**
 * Created by yanand on 2016/11/6.
 */
public interface BasicDataDao {
    void saveToHbase(BasicData basicData);
}
