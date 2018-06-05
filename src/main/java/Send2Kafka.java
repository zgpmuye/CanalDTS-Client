import constant.Constant;
import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.google.protobuf.InvalidProtocolBufferException;
import entity.Column;
import entity.MysqlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.KafkaAdapter;
import util.Utils;

import java.util.List;

public class Send2Kafka {
    protected final static Logger logger             = LoggerFactory.getLogger(Send2Kafka.class);

    public static void getMysqlInfo(long batchId, List<Entry> entries,String destination){
//        System.out.println(entries);
        KafkaAdapter producer = KafkaAdapter.getInstance();
        for (Entry entry :entries){
            if (entry.getEntryType()== CanalEntry.EntryType.ROWDATA){
                MysqlInfo mysqlInfo=new MysqlInfo();
                CanalEntry.RowChange rowChage = null;
                try {
                    rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }
                CanalEntry.EventType eventType = rowChage.getEventType();
                if (eventType== CanalEntry.EventType.DELETE||eventType== CanalEntry.EventType.INSERT||eventType== CanalEntry.EventType.UPDATE) {
//                    System.out.println(entry.toString());
                    mysqlInfo.setBatchId(batchId);
                    mysqlInfo.setType(Utils.getEntryType(eventType));
                    mysqlInfo.setBinlog(entry.getHeader().getLogfileName());
                    mysqlInfo.setPosition(entry.getHeader().getLogfileOffset());
                    mysqlInfo.setDbname(entry.getHeader().getSchemaName());
                    mysqlInfo.setTablename(entry.getHeader().getTableName());
                    List<Column> columns=null;
                    for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                        if (eventType == CanalEntry.EventType.DELETE) {
                            columns = Utils.getColumns(rowData.getBeforeColumnsList(),mysqlInfo);
                        } else {
                            columns= Utils.getColumns(rowData.getAfterColumnsList(),mysqlInfo);
                        }
                    }
                    mysqlInfo.setList(columns);
                    String json = JSON.toJSONString(mysqlInfo);
//                    logger.info("json=="+json);
                    logger.info("json=="+mysqlInfo.getDbname()+":"+mysqlInfo.getTablename()+":"+mysqlInfo.getBinlog()+":"+mysqlInfo.getPosition());
                    producer.sendMsg(json,destination);
//                    producer.send(new KeyedMessage<Integer,byte[]>(topic,json.getBytes()));
                }
            }else {
                logger.info("select ");
            }
        }
    }




}
