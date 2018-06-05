import constant.Constant;
import util.KafkaAdapter;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        //        int num= Integer.parseInt(args[0]);
        int num = 1;
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < num; i++) {
            Thread test1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        KafkaAdapter adapter = KafkaAdapter.getInstance();
                        String msg = "json";//这里是压力测试
                        adapter.sendMsg(msg, "test");
                    }
                }
            });
            list.add(test1);
        }
        for (Thread thread : list) {
            thread.start();
        }

    }
}
