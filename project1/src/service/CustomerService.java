package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService instance = new CustomerService();


    private final Map<String, Customer> customers;


    private CustomerService() {
        customers = new HashMap<>();
    }


    public static CustomerService getInstance() {
        return instance;
    }


    public void addCustomer(String email, String firstName, String lastName) {
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Customer with this email is already registered.");
        }
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomer() {
        return customers.values();
    }
}
