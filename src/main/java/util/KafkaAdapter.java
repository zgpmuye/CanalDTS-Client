package util;

import constant.Constant;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaAdapter {
    private static KafkaAdapter adapter = null;
    private static Producer<String, byte[]> producer = null;
    protected final static Logger logger = LoggerFactory.getLogger(KafkaAdapter.class);

    public static KafkaAdapter getInstance() {
        if (adapter == null) {
            synchronized (KafkaAdapter.class) {
                if (adapter == null) {
                    adapter = new KafkaAdapter();
                    init();
                }
            }
        }
        return adapter;
    }

    public static void init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constant.kafka);
        props.put("acks", "1");
        props.put("producer.type", "async");
//        props.put("batch.num.messages", "100");
//        props.put("retries", 0);
//        props.put("compression.type", "gzip");
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);

//        props.put("retries", 0);  //重试次数
//        props.put("batch.size", 10);
//        props.put("buffer.memory", 1000000000);  //1G
//        props.put("send.buffer.bytes", 100000000);  //100M
//        props.put("max.request.size", 100000000);  //100M
//        props.put("compression.type", "gzip");
//        props.put("linger.ms", 0);

        props.put("batch.num.messages", "500");
        props.put("retries", 0);  //重试次数
        props.put("batch.size", 33554432);//这个设置大点是每批次到内存
        props.put("buffer.memory", 200000000);  //200M
        props.put("send.buffer.bytes", 100000000);  //100M
        props.put("max.request.size", 100000000);  //100M
        props.put("linger.ms", 0);
        props.put("compression.type", "gzip");


        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        producer = new KafkaProducer<String, byte[]>(props);
    }

    public void sendMsg(String msg,String destination) {
        String topic = destination;
        ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<String, byte[]>(topic, msg.getBytes());
        producer.send(producerRecord, new SendCallback(producerRecord, 0));
    }

    static class SendCallback implements Callback {
        ProducerRecord<String, byte[]> record;
        int sendSeq = 0;

        public SendCallback(ProducerRecord record, int sendSeq) {
            this.record = record;
            this.sendSeq = sendSeq;
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            //send success
            if (null == e) {
//                String meta = "topic:" + recordMetadata.topic() + ", partition:"
//                        + recordMetadata.topic() + ", offset:" + recordMetadata.offset();
                logger.info("send message success, " +  "  offset:" + recordMetadata.offset());
//                logger.info("send message success, record:" + record.toString() + ", meta:" + meta);
                return;
            }
            //send failed
//            logger.error("send message failed, seq:" + sendSeq + ", record:" + record.toString() + ", errmsg:" + e.getMessage());
            logger.error("send message failed,errmsg:" + e.getMessage());
            if (sendSeq < 1) {
                producer.send(record, new SendCallback(record, ++sendSeq));
            }
        }
    }

}
