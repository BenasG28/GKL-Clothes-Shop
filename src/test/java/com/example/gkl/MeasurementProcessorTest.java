package com.example.gkl;

import com.example.gkl.model.Customer;
import com.example.gkl.utils.CustomerMeasurementProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MeasurementProcessorTest {
    private CustomerMeasurementProcessor customerMeasurementProcessor;
    private Customer mockCustomer;

    @BeforeEach
    public void setUp(){
        mockCustomer = mock(Customer.class);
        customerMeasurementProcessor = new CustomerMeasurementProcessor(mockCustomer);
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_S(){
        when(mockCustomer.getCustomerChestMeas()).thenReturn(90.0);
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(40.0);
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(78.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(95.0);
        assertEquals("S", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_M(){
        when(mockCustomer.getCustomerChestMeas()).thenReturn(98.0);
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(45.0);
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(84.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(101.0);
        assertEquals("M", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_L() {
        when(mockCustomer.getCustomerChestMeas()).thenReturn(105.0);
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(50.0);
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(90.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(107.0);
        assertEquals("L", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_XL() {
        when(mockCustomer.getCustomerChestMeas()).thenReturn(112.0);
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(55.0);
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(96.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(115.0);
        assertEquals("XL", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateLowerBodyUniversalSize_S() {
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(75.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(93.0);
        when(mockCustomer.getCustomerInseamMeas()).thenReturn(81.5);
        assertEquals("S", customerMeasurementProcessor.generateLowerBodyUniversalSize());
    }

    @Test
    public void testGenerateLowerBodyUniversalSize_M() {
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(80.0);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(96.5);
        when(mockCustomer.getCustomerInseamMeas()).thenReturn(82.0);
        assertEquals("M", customerMeasurementProcessor.generateLowerBodyUniversalSize());
    }

    @Test
    public void testGenerateLowerBodyUniversalSize_L() {
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(86.5);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(100.0);
        when(mockCustomer.getCustomerInseamMeas()).thenReturn(82.65);
        assertEquals("L", customerMeasurementProcessor.generateLowerBodyUniversalSize());
    }

    @Test
    public void testGenerateLowerBodyUniversalSize_XL() {
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(93.5);
        when(mockCustomer.getCustomerHipMeas()).thenReturn(108.0);
        when(mockCustomer.getCustomerInseamMeas()).thenReturn(83.25);
        assertEquals("XL", customerMeasurementProcessor.generateLowerBodyUniversalSize());
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_ClosestSize() {
        when(mockCustomer.getCustomerChestMeas()).thenReturn(85.0); // Outside S and M range
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(42.0); // Within S and M range
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(80.0); // Within M range
        when(mockCustomer.getCustomerHipMeas()).thenReturn(100.0); // Outside M and L range
        assertEquals("S", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateUpperBodyUniversalSize_MultipleClosestSizes() {
        when(mockCustomer.getCustomerChestMeas()).thenReturn(92.0); // Outside S range, closer to S than M
        when(mockCustomer.getCustomerShoulderMeas()).thenReturn(47.0); // Outside S and M range, closer to M
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(86.0); // Within M range
        when(mockCustomer.getCustomerHipMeas()).thenReturn(102.0); // Outside M and L range, closer to M
        assertEquals("M", customerMeasurementProcessor.generateUpperBodyUniversalSize());
    }

    @Test
    public void testGenerateLowerBodyUniversalSize_ClosestSize() {
        when(mockCustomer.getCustomerWaistMeas()).thenReturn(74.0); // Outside S range
        when(mockCustomer.getCustomerHipMeas()).thenReturn(80.0); // Within S and M range
        when(mockCustomer.getCustomerInseamMeas()).thenReturn(80.5); // Outside S range
        assertEquals("S", customerMeasurementProcessor.generateLowerBodyUniversalSize());
    }


}
