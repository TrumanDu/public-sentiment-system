package com.aibibang.dao.impl;

import java.lang.reflect.Field;

import com.aibibang.bean.BasicData;
import com.aibibang.common.Constant;
import com.aibibang.dao.BasicDataDao;
import com.aibibang.util.HbaseUtil;
import com.gargoylesoftware.htmlunit.javascript.host.file.File;

import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.internal.util.reflection.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by yanand on 2016/11/6.
 */
@Repository
public class HBaseBasicDataDao implements BasicDataDao {

    @Override
    public void saveToHbase(BasicData basicData)  {
    	Field[] fields = basicData.getClass().getDeclaredFields();
    	for(int i=1;i<fields.length;i++){
    		Field f = fields[i];
    		boolean flag = f.isAccessible();
    		if(!flag){
    			f.setAccessible(true);
    		}
    		String fieldName = f.getName();
    		try {
				Object o = f.get(basicData);
				if(o!=null){
			    	HbaseUtil.addData(Constant.BASICDATATABLE, Bytes.toBytes(basicData.getId()), Bytes.toBytes(Constant.BASICDATAFAMILY), Bytes.toBytes(fieldName), Bytes.toBytes(o.toString()));
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		f.setAccessible(false);
    		
    	}
    	 log.info(basicData == null ? null : basicData.toString());
    }
  
    public static void main(String[] args) {
    	HBaseBasicDataDao h =new HBaseBasicDataDao();
    	BasicData b=new BasicData();
    	b.setPublishTime("publishTime");
    	b.setSource("source");
    	b.setTopicTitle("topicTitle");
    	b.setUrl("wwwwwwwwwwwwww");
    	b.setId(1L);
    	h.saveToHbase(b);
	}
}
