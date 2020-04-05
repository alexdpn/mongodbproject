package com.project.mongodb.helper;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class EmbeddedMongoDbHelper {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private MongodExecutable executable;

    public IMongodConfig createConfiguration() throws Exception{
        return new MongodConfigBuilder()
                .version(Version.Main.V4_0)
                .net(new Net(HOST, PORT, Network.localhostIsIPv6()))
                .build();
    }

    public void startDatabase() {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        try {
            executable = starter.prepare(createConfiguration());
            MongodProcess process = executable.start();
        }catch (Exception e) {
            //do something
            e.printStackTrace();
        }
    }

    public void stopDatabase() {
        executable.stop();
    }
}
