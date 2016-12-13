package com.aibibang.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import com.aibibang.common.Constant;

/** 
* @author: Truman.P.Du 
* @since: 2016年6月7日 下午4:47:34 
* @version: v1.0
* @description:
*/
public class HbaseUtil {
	private static Configuration conf = null;
	private static Admin admin = null;
	private static Connection connection = null;
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "***.***.***.***");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			connection = ConnectionFactory.createConnection(conf);
			admin = connection.getAdmin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 创建表
	 * @param tableName
	 * @param columnsName
	 * @throws IOException
	 */
	public static void createTable(String tableName, String[] columnsName) throws IOException {
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
		// Adding column families to table descriptor
		if (columnsName != null) {
			for (String name : columnsName) {
				tableDescriptor.addFamily(new HColumnDescriptor(name));
			}
		}
		admin.createTable(tableDescriptor);
		System.out.println(" Table created ");
	}

	/**
	 * 获取所有的表
	 * @param tableName
	 * @param columnsName
	 * @throws IOException
	 */
	public static HTableDescriptor[] listTable() throws IOException {
		HTableDescriptor[] tables = admin.listTables();
		return tables;
	}

	/**
	 * 删除表
	 * @param tableName
	 * @throws IOException
	 */
	public static void deleteTable(String tableName) throws IOException {
		admin.disableTable(TableName.valueOf(tableName));
		admin.deleteTable(TableName.valueOf(tableName));

	}

	/**
	 * 删除 columns
	 * @param tableName
	 * @param columnsName
	 * @throws IOException
	 */
	public static void deleteColumn(String tableName, String[] columnsName) throws IOException {
		if (columnsName != null) {
			for (String name : columnsName) {
				admin.deleteColumn(TableName.valueOf(tableName), name.getBytes());
			}
		}
	}

	/**
	 * 查看表是否存在
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public static boolean tableIsExists(String tableName) throws IOException {
		return admin.tableExists(TableName.valueOf(tableName));
	}
	/**
	 * 增加数据
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public static void addData(String tableName, byte[] row, byte[] family, byte[] column, byte[] data) {
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Put p = new Put(row);
			p.addColumn(family, column, data);
			table.put(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (table != null)
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	/**
	 * 读取数据
	 * @param tableName
	 * @param row
	 * @param family
	 * @param column
	 * @return
	 */
	public static String getValue(String tableName, byte[] row, byte[] family, byte[] column) {
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Get g = new Get(row);
			Result reasult;
			reasult = table.get(g);
			byte[] value = reasult.getValue(family, column);
			if (value != null) {
				return new String(value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (table != null)
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return null;
	}

	/**
	 * 删除数据
	 * @param tableName
	 * @param row
	 * @param family
	 * @param column
	 */
	public static void remove(String tableName, byte[] row, byte[] family, byte[] column) {
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Delete delete = new Delete(row);
			if (column != null) {
				delete.addColumn(family, column);
			}
			if (family != null) {
				delete.addFamily(family);
			}
			table.delete(delete);
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取hbase集群状态
	 * @return
	 * @throws IOException
	 */
	public static ClusterStatus getClusterStatus() throws IOException {
		ClusterStatus clusterStatus = admin.getClusterStatus();
		return clusterStatus;
	}
	
	public static void main(String[] args) {
		String[]columnsName =new String[]{"info"};
		try {
			HbaseUtil.createTable(Constant.BASICDATATABLE, columnsName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
