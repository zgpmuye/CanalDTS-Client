### 官方地址
Simple客户端例子：[SimpleCanalClientTest](https://github.com/alibaba/canal/blob/master/example/src/main/java/com/alibaba/otter/canal/example/SimpleCanalClientTest.java)  
Cluster客户端例子：[ClusterCanalClientTest](https://github.com/alibaba/canal/blob/master/example/src/main/java/com/alibaba/otter/canal/example/ClusterCanalClientTest.java)

### canal client
ClusterCanalClient 是canal client的高可用的类，主要是消费canal server的数据

AbstractCanalClient 是canal client的直接连接canal server的类

Send2Kafka  这是将canal client消费的数据进行格式化成MysqlInfo
并生成json字符串发送到kafka

上面的canal client的启动方式可以是

nohup java -classpath CanalDTS-Client-2.0.jar  ClusterCanalClient topic> cluster1.log &







//下面是以前版本的新版本在CanalDTS-Consumer

### consumer.HbaseConsumer
是将Kafka的数据消费并存进hbase中

nohup java -classpath CanalClientAndKafkaConsumer2Hbase-1.0-SNAPSHOT.jar  consumer.HbaseConsumer > consumer.log &

### compare.CompareMain 
这是读取mysql中的数据并与hbase进行对比的类

nohup java -classpath CanalClientAndKafkaConsumer2Hbase-1.0-SNAPSHOT.jar  consumer.HbaseConsumer mysql-bin.000259 0 > consumer.log &

### canal server相关

canal.properties  是多ip配置的配置文件

customer-instance.xml 是多ip高可用的一个xml文件，需要放到canal server的conf下的spring目录下。

instance.properties  里面有监控库下面表的正则配置，可以监控单张表