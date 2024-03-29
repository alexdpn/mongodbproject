package com.project.mongodb.helper;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;

import static com.project.mongodb.util.Constants.HOST;
import static com.project.mongodb.util.Constants.MONGODB_PORT;

@Slf4j
public class EmbeddedMongoDbHelper {


    private static MongodExecutable executable;

    private EmbeddedMongoDbHelper () {}

    public static IMongodConfig createConfiguration() throws Exception{
        return new MongodConfigBuilder()
                .version(Version.Main.V4_0)
                .net(new Net(HOST, MONGODB_PORT, Network.localhostIsIPv6()))
                .build();
    }

    public static void startDatabase() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        try {
            executable = starter.prepare(createConfiguration());
            MongodProcess process = executable.start();
        }catch (Exception e) {
            log.error("Error during database initialization", e);
            throw e;
        }
    }

    public static void stopDatabase() {
        executable.stop();
    }
}
