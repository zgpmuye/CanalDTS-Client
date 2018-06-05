package util;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import entity.Column;
import entity.MysqlInfo;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String getEntryType(EventType eventType){
        switch (eventType){
            case QUERY:
               return  "query";
            case DELETE:
                return "delete";
            case INSERT:
                return "insert";
            case UPDATE:
                return "update";
            default:
                return "";
        }
    }
    public static List<Column> getColumns(List<CanalEntry.Column> columns, MysqlInfo mysqlInfo) {
        List<Column> list = new ArrayList<Column>();
        Column c = null;
        int i = 0;
        String tableKeys = "";
        for (CanalEntry.Column column : columns) {
            if (column.getIsKey()) {
                if (tableKeys.length() > 0) {
                    tableKeys += "_" + column.getName();
                } else {
                    tableKeys += column.getName();
                }
            }
            c = new Column();
            c.setName(column.getName());
            c.setIndex(column.getIndex());
            c.setUpdate(column.getUpdated());
            c.setValue(column.getValue());
            c.setColumnType(column.getMysqlType());
            c.setKey(column.getIsKey());
            c.setNull(column.getIsNull());
            list.add(c);
        }
        mysqlInfo.setTablekeys(tableKeys);
        return list;
    }
}
