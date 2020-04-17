import com.project.mongodb.config.CompanyBinder;
import com.project.mongodb.controller.CompanyController;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;
import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.weld.environment.se.Weld;
import org.junit.*;

import static org.junit.Assert.*;
import static com.mongodb.client.model.Sorts.*;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RestMongoTest {

    private static URI uri;
    private static Weld weld;
    private static HttpServer server;
    private static CompanyRepository companyRepository;
    private static Client client;
    private static WebTarget target;

    @BeforeClass
    public static void startHttpServer(){
        uri = UriBuilder.fromUri("http://localhost/").port(4000).build();
        ResourceConfig config = new ResourceConfig(CompanyController.class);
        config.register(new CompanyBinder());
        config.register(JacksonFeature.class);
        server = JdkHttpServerFactory.createHttpServer(uri, config);

        EmbeddedMongoDbHelper.startDatabase();

        companyRepository = new CompanyRepository();

        weld = new Weld();

        client = ClientBuilder.newBuilder()
                              .register(JacksonFeature.class)
                              .build();

        target = client.target(uri).path("app");

    }

    @Test
    public void testGet(){
        List<Company> list = companyRepository.getCompanies();
        List<Company> list2 = new ArrayList<>(5);
        list.forEach(company -> list2.add(target.path("companies")
                                                                        .path(company.getId().toString())
                                                                        .request(MediaType.APPLICATION_JSON)
                                                                        .get(Company.class )));

        System.out.print(list2);
    }

    @Test
    public void testPut(){
        String lastId = companyRepository.getMongoCollection()
                                .find().sort(descending("_id"))
                                .limit(1).first()
                                .getId().toString();

        Company company  = new Company("Sound of Artists", "Mark", "production of music",
                                new Office(250,
                                    new Address("Australia", "Sydney", "Kangaroo Street 60D")));

        int status = target.path("companies").path(lastId)
                                            .request().put(Entity.entity(company, MediaType.APPLICATION_JSON))
                                            .getStatus();

        assertEquals(200, status);
    }

    @Test
    public void testDelete() {
        String firstId = companyRepository.getMongoCollection().find().first().getId().toString();
        int status = target.path("companies").path(firstId).request().delete().getStatus();

        assertEquals(204, status);
    }

    @AfterClass
    public static void stopServer(){
        server.stop(0);
        EmbeddedMongoDbHelper.stopDatabase();
        weld.shutdown();
    }
}
