package com.ilroberts.modulith.customer;

import com.ilroberts.modulith.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Application.class)
@ActiveProfiles(value = "integration")
public class CustomerIntegrationTests {

    private static final String BASE_URL = "http://localhost:8080/api/";

    @Test
    public void when_NoCustomersCreated_Then_ResponseIsEmpty() {

        TestRestTemplate template = new TestRestTemplate();
        ResponseEntity<Customer[]> response = template.getForEntity(BASE_URL + "/customer", Customer[].class);

        var statusCode = response.getStatusCode();
        assertThat(statusCode.value()).isEqualTo(200);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void when_CustomerCreated_Then_ResponseContainsCustomer() {

        var createdCustomer = createCustomer( "Bilbo Baggins", "b.baggins@theshire.com");

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getName()).isEqualTo("Bilbo Baggins");
        assertThat(createdCustomer.getEmail()).isEqualTo("b.baggins@theshire.com");
    }

    @Test
    public void when_CustomerUpdated_Then_ResponseContainsUpdatedCustomer() {

        TestRestTemplate template = new TestRestTemplate();

        var initialCustomer = createCustomer( "Bilbo Baggins", "b.baggins@theshire.com");

        Customer updatedCustomer = new Customer(initialCustomer.getId(), initialCustomer.getName(), "b.baggins@theshire.org");
        HttpEntity<Customer> httpEntity = new HttpEntity<>(updatedCustomer);

        String updatedCustomerId = String.valueOf(initialCustomer.getId());
        ResponseEntity<Customer> response = template.exchange(BASE_URL + "/customer/" + updatedCustomerId,
                HttpMethod.PUT,
                httpEntity,
                Customer.class);

        var statusCode = response.getStatusCode();
        assertThat(statusCode.value()).isEqualTo(200);

        var responseCustomer = response.getBody();
        assertThat(responseCustomer).isNotNull();
        assertThat(responseCustomer.getName()).isEqualTo("Bilbo Baggins");
        assertThat(responseCustomer.getEmail()).isEqualTo("b.baggins@theshire.org");
    }

    @Test
    public void when_CustomerDeleted_Then_ResponseIsEmpty() {

        TestRestTemplate template = new TestRestTemplate();
        var newCustomer = createCustomer("Frodo Baggins", "f.baggins@theshire.com");

        String newCustomerId = String.valueOf(newCustomer.getId());
        ResponseEntity<Void> response = template.exchange(BASE_URL + "/customer/" + newCustomerId,
                HttpMethod.DELETE,
                null,
                Void.class);

        var statusCode = response.getStatusCode();
        assertThat(statusCode.value()).isEqualTo(200);
    }

    private Customer createCustomer(String name, String email) {
        TestRestTemplate template = new TestRestTemplate();

        Customer customer = new Customer(null, name, email);
        ResponseEntity<Customer> response = template.postForEntity(BASE_URL + "/customer", customer, Customer.class);
        return response.getBody();
    }
}
