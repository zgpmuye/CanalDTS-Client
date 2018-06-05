package entity;

import java.util.List;

public class MysqlInfo {
    private long batchId;
    private String type;
    private String binlog;
    private long position;
    private String dbname;
    private String tablename;
    private String tablekeys;
    private List<Column> list;


    public MysqlInfo() {
    }

    @Override
    public String toString() {
        return "MysqlInfo{" +
                "batchId=" + batchId +
                ", type='" + type + '\'' +
                ", binlog='" + binlog + '\'' +
                ", position=" + position +
                ", dbname='" + dbname + '\'' +
                ", tablename='" + tablename + '\'' +
                ", tablekeys='" + tablekeys + '\'' +
                ", list=" + list +
                '}';
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBinlog() {
        return binlog;
    }

    public void setBinlog(String binlog) {
        this.binlog = binlog;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public List<Column> getList() {
        return list;
    }

    public void setList(List<Column> list) {
        this.list = list;
    }

    public String getTablekeys() {
        return tablekeys;
    }

    public void setTablekeys(String tablekeys) {
        this.tablekeys = tablekeys;
    }

    public MysqlInfo(long batchId, String type, String binlog, long position, String dbname, String tablename, String tablekeys, List<Column> list) {
        this.batchId = batchId;
        this.type = type;
        this.binlog = binlog;
        this.position = position;
        this.dbname = dbname;
        this.tablename = tablename;
        this.tablekeys = tablekeys;
        this.list = list;
    }
}
