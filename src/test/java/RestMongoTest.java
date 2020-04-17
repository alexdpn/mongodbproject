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
import org.junit.runners.MethodSorters;

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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void testPost(){

        List<Company> list = Arrays.asList(
                new Company("Good Technology Inc", "Tom", "android applications",
                        new Office(500,
                                new Address("Ireland", "Dublin", "Green Street, Nr. 100A"))),
                new Company("Beer Ltd.", "Mary", "beer",
                        new Office(400,
                                new Address("Canada", "Toronto", "Queen's Street, Nr.25"))),
                new Company("Red Wine Inc.", "Louis",  "production of wine",
                        new Office(300,
                                new Address("Germany", "Berlin", "Beer Street, Nr. 200B"))),
                new Company("Surf Ltd", "Mike", "production of surfboards",
                        new Office(170,
                                new Address("USA", "Los Angeles", "Ocean Street, Nr. 200C"))),
                new Company("Sound of Artists", "Mark", "music production",
                        new Office(200,
                                new Address("Australia", "Sydney", "Kangaroo Street, Nr. 60D")))
        );




        List<Integer> listOfStatusCodes = new ArrayList<>(5);

        list.forEach(company -> listOfStatusCodes.add(target.path("companies")
                                                            .request()
                                                            .post(Entity.entity(company, MediaType.APPLICATION_JSON))
                                                            .getStatus()));

        listOfStatusCodes.forEach(status -> assertEquals(201, status.longValue()));
    }


    @Test
    public void testQGet(){
        List<Company> list = companyRepository.getCompanies();
        list.forEach(company -> assertEquals(company.toString(), target.path("companies")
                                                                        .path(company.getId().toString())
                                                                        .request(MediaType.APPLICATION_JSON)
                                                                        .get(Company.class ).toString()));
    }

    @Test
    public void testQPut(){
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
    public void testQDelete() {
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
