import com.project.mongodb.config.CompanyBinder;
import com.project.mongodb.controller.CompanyController;
import com.project.mongodb.helper.EmbeddedMongoDbHelper;
import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.repository.impl.CompanyRepositoryImpl;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

import java.net.URI;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;
import static org.junit.Assert.assertEquals;

public class CompanyControllerTest {

    private static URI uri;
    private static HttpServer server;
    private static Client client;
    private static WebTarget target;
    private static CompanyRepository companyRepository;

    @BeforeClass
    public static void startHttpServer(){
        uri = UriBuilder.fromUri("http://localhost/").port(4000).build();

        ResourceConfig config = new ResourceConfig(CompanyController.class);
        config.register(new CompanyBinder());
        config.register(JacksonFeature.class);
        server = GrizzlyHttpServerFactory.createHttpServer(uri, config);

        EmbeddedMongoDbHelper.startDatabase();

        companyRepository = new CompanyRepositoryImpl();
        companyRepository.init();

        client = ClientBuilder.newBuilder()
                              .register(JacksonFeature.class)
                              .build();

        target = client.target(uri).path("app/v1");
    }

    @Test
    public void testGet(){
        List<Company> list = companyRepository.getCompanies();
        list.forEach(company -> assertEquals(company.toString(), target.path("companies")
                                                                        .path(company.getId().toString())
                                                                        .request(MediaType.APPLICATION_JSON)
                                                                        .get(Company.class ).toString()));
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
        server.shutdown();
        EmbeddedMongoDbHelper.stopDatabase();
    }
}
