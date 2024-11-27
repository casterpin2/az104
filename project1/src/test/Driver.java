package test;

import model.Customer;

public class Driver {
    public static void main(String[] args){
        Customer customer = new Customer("first","second","test@email.com");
        System.out.println(customer);
        Customer customerFailed = new Customer("first","second","test@email");
        System.out.println(customer);
    }
}
