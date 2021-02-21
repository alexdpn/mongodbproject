import com.project.mongodb.exception.AddressNotFoundException;
import com.project.mongodb.exception.CompanyNotFoundException;
import com.project.mongodb.exception.OfficeNotFoundException;
import com.project.mongodb.model.Address;
import com.project.mongodb.model.Company;
import com.project.mongodb.model.Office;
import com.project.mongodb.repository.CompanyRepository;
import com.project.mongodb.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company1;
    private Company company2;
    private List<Company> companyList;

    @Before
    public void init() {
        company1 = new Company("ABC", "John", "enterprise software",
                new Office(300,
                        new Address("France", "Paris", "Baguette Street Nr. 20")));

        company2 =  new Company("Maw", "Mike", "software automation",
                new Office(50,
                        new Address("France", "Lyon", "Lyon Street Nr. 20")));

        companyList = Arrays.asList(company1, company2);
    }

    @Test
    public void testGetCompanies() {
        when(companyRepository.getCompanies()).thenReturn(companyList);

        assertEquals(companyList, companyService.getCompanies());
        assertEquals(2, companyService.getCompanies().size());
    }

    @Test
    public void testGetCompanyById() {
        when(companyRepository.getCompanyById("1")).thenReturn(company1);

        assertEquals(company1, companyService.getCompanyById("1"));
    }

    @Test(expected = CompanyNotFoundException.class)
    public void testGetCompanyByIdWithCompanyNotFoundException() {
        lenient().when(companyRepository.getCompanyById("3")).thenReturn(null);

        companyService.getCompanyById("2");
    }

    @Test
    public void testGetOffice() {
        when(companyRepository.getOfficeByCompanyId("1")).thenReturn(company1.getOffice());

        assertEquals(company1.getOffice(), companyService.getOffice("1"));
    }

    @Test(expected = OfficeNotFoundException.class)
    public void testGetOfficeWithOfficeNotFoundException() {
        lenient().when(companyRepository.getOfficeByCompanyId("3")).thenReturn(null);

        companyService.getOffice("3");
    }

    @Test
    public void testGetAddress() {
        when(companyRepository.getAddressByCompanyId("1")).thenReturn(company1.getOffice().getAddress());

        assertEquals(company1.getOffice().getAddress(), companyService.getAddress("1"));
    }

    @Test(expected = AddressNotFoundException.class)
    public void testGetAddressWithAddressNotFoundException() {
        lenient().when(companyRepository.getAddressByCompanyId("3")).thenReturn(null);

        companyService.getAddress("3");
    }
}
