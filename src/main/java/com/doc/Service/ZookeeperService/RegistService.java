package com.doc.Service.ZookeeperService;//package com.doc.Service.ZookeeperService;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.ChildData;
//import org.apache.curator.framework.recipes.cache.PathChildrenCache;
//import org.apache.curator.retry.RetryNTimes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * com.doc.Service.ZookeeperService 于2017/9/1 由Administrator 创建 .
// */
//@Component
//public class RegistService implements ApplicationRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(RegistService.class);
//    @Value("${zookeeper.address}")
//    private  String zookeeperAddress;
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        String ZK_PATH = "/zktest";
//        CuratorFramework client= CuratorFrameworkFactory.
//                newClient(zookeeperAddress,
//                        new RetryNTimes(10, 5000));
//        client.start();
//
//        PathChildrenCache watcher = new PathChildrenCache(
//                client,
//                ZK_PATH,
//                true    // if cache data
//        );
//
//        watcher.getListenable().addListener((client1, event) -> {
//            ChildData data = event.getData();
//            if (data == null) {
//                System.out.println("No data in event[" + event + "]");
//            } else {
//                System.out.println("Receive event: "
//                        + "type=[" + event.getType() + "]"
//                        + ", path=[" + data.getPath() + "]"
//                        + ", data=[" + new String(data.getData()) + "]"
//                        + ", stat=[" + data.getStat() + "]");
//            }
//        });
//        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
//        System.out.println("Register zk watcher successfully!");
//
//        Thread.sleep(Integer.MAX_VALUE);
//    }
//}
