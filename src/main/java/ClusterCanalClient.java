import constant.Constant;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

/**
 * 集群模式的测试例子
 * 
 * @author jianghang 2013-4-15 下午04:19:20
 * @version 1.0.4
 */
public class ClusterCanalClient extends AbstractCanalClient {

    public ClusterCanalClient(String destination){
        super(destination);
    }

    public static void main(String args[]) {
        String destination = Constant.destination_master;
        if (args.length>0){
            destination=args[0];
        }
        System.out.println("destination=="+destination);
        CanalConnector connector = CanalConnectors.newClusterConnector(Constant.zk, destination, "root", "root"); //监听数据库到用户名密码
        final ClusterCanalClient clientTest = new ClusterCanalClient(destination);
        clientTest.setConnector(connector);
        clientTest.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                try {
                    logger.info("## stop the canal client");
                    clientTest.stop();
                } catch (Throwable e) {
                    logger.warn("##something goes wrong when stopping canal:", e);
                } finally {
                    logger.info("## canal client is down.");
                }
            }

        });
    }
}
