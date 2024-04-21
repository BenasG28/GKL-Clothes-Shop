package com.example.gkl;

import com.example.gkl.model.Customer;
import com.example.gkl.utils.CustomerMeasurementProcessor;

public class LowerBodySizeTest {

    public static void main(String[] args) {
        // Create a mock Customer object with sample measurements
        Customer customer = new Customer();
        customer.setCustomerWaistMeas(); // Example waist measurement
        customer.setCustomerHipMeas(); // Example hip measurement
        customer.setCustomerInseamMeas(); // Example inseam measurement

        // Create a CustomerMeasurementProcessor object
        CustomerMeasurementProcessor processor = new CustomerMeasurementProcessor(customer);

        // Test the generateLowerBodyUniversalSize() method
        String size = processor.generateLowerBodyUniversalSize();

        // Print the result
        System.out.println("Lower body size: " + size);
    }
}
