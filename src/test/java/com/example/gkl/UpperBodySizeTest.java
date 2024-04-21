package com.example.gkl;

import com.example.gkl.model.Customer;
import com.example.gkl.utils.CustomerMeasurementProcessor;

public class UpperBodySizeTest {

    public static void main(String[] args) {
        // Create a mock Customer object with sample measurements
        Customer customer = new Customer();
        customer.setCustomerChestMeas(108.0); // Example chest measurement
        customer.setCustomerShoulderMeas(53.2); // Example shoulder measurement
        customer.setCustomerWaistMeas(100.0); // Example waist measurement
        customer.setCustomerHipMeas(109.0);

        // Create a CustomerMeasurementProcessor object
        CustomerMeasurementProcessor processor = new CustomerMeasurementProcessor(customer);

        // Test the generateUpperBodyUniversalSize() method
        String size = processor.generateUpperBodyUniversalSize();

        // Print the result
        System.out.println("Upper body size: " + size);
    }
}
