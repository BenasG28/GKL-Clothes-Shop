package com.example.gkl;

import com.example.gkl.model.Customer;
import com.example.gkl.utils.CustomerMeasurementProcessor;

public class LowerBodySizeTest {

    public static void main(String[] args) {
        // Create a mock Customer object with sample measurements
        Customer customer = new Customer();
        customer.setCustomerWaistMeas(23.3); // Example waist measurement
        customer.setCustomerHipMeas(23.3); // Example hip measurement
        customer.setCustomerInseamMeas(31.3); // Example inseam measurement

        // Create a CustomerMeasurementProcessor object
        CustomerMeasurementProcessor processor = new CustomerMeasurementProcessor(customer);

        // Test the generateLowerBodyUniversalSize() method
        String size = processor.generateLowerBodyUniversalSize();

        // Print the result
        System.out.println("Lower body size: " + size);
    }
}
