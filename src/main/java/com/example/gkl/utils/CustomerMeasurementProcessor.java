package com.example.gkl.utils;

import com.example.gkl.model.Customer;


public class CustomerMeasurementProcessor {
    private Customer currentCustomer;

    public CustomerMeasurementProcessor(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
    public String generateUpperBodyUniversalSize() {
        double chest = currentCustomer.getCustomerChestMeas();
        double shoulder = currentCustomer.getCustomerShoulderMeas();
        double waist = currentCustomer.getCustomerWaistMeas();
        double hip = currentCustomer.getCustomerHipMeas();

        // Define tolerance for each measurement
        double chestTolerance = 1.0;
        double shoulderTolerance = 1.0;
        double waistTolerance = 1.0;
        double hipTolerance = 1.0;

        // Determine the size based on measurements and tolerance ranges
        if (isWithinRange(chest, 88, 95, chestTolerance) &&
                isWithinRange(shoulder, 39, 44, shoulderTolerance) &&
                isWithinRange(waist, 75, 82, waistTolerance) &&
                isWithinRange(hip, 92, 97, hipTolerance)) { // Updated hip range for size S
            return "S";
        } else if (isWithinRange(chest, 95, 101, chestTolerance) &&
                isWithinRange(shoulder, 44, 48, shoulderTolerance) &&
                isWithinRange(waist, 82, 88, waistTolerance) &&
                isWithinRange(hip, 97, 104, hipTolerance)) { // Updated hip range for size M
            return "M";
        } else if (isWithinRange(chest, 101, 107, chestTolerance) &&
                isWithinRange(shoulder, 48, 52, shoulderTolerance) &&
                isWithinRange(waist, 88, 93, waistTolerance) &&
                isWithinRange(hip, 104, 107, hipTolerance)) { // Updated hip range for size L
            return "L";
        } else if (isWithinRange(chest, 107, 113, chestTolerance) &&
                isWithinRange(shoulder, 52, 56, shoulderTolerance) &&
                isWithinRange(waist, 93, 105, waistTolerance) &&
                isWithinRange(hip, 108, 115, hipTolerance)) { // Updated hip range for size XL
            return "XL";
        } else {
            // If no exact match, determine the closest size based on the measurements that do fall within the predefined ranges
            String closestSize = getClosestSize(chest, shoulder, waist, hip);
            return closestSize;
        }
    }

    private String getClosestSize(double chest, double shoulder, double waist, double hip) {
        // Calculate distances to each size's range
        double distanceS = calculateDistanceToSize(chest, 88, 95) +
                calculateDistanceToSize(shoulder, 39, 44) +
                calculateDistanceToSize(waist, 75, 82) +
                calculateDistanceToSize(hip, 92, 97);
        double distanceM = calculateDistanceToSize(chest, 95, 101) +
                calculateDistanceToSize(shoulder, 44, 48) +
                calculateDistanceToSize(waist, 82, 88) +
                calculateDistanceToSize(hip, 97, 104);
        double distanceL = calculateDistanceToSize(chest, 101, 107) +
                calculateDistanceToSize(shoulder, 48, 52) +
                calculateDistanceToSize(waist, 88, 93) +
                calculateDistanceToSize(hip, 104, 107);
        double distanceXL = calculateDistanceToSize(chest, 107, 113) +
                calculateDistanceToSize(shoulder, 52, 56) +
                calculateDistanceToSize(waist, 93, 105) +
                calculateDistanceToSize(hip, 108, 115);

        // Find the size with the minimum total distance
        double minDistance = Math.min(Math.min(distanceS, distanceM), Math.min(distanceL, distanceXL));

        // Return the closest size
        if (minDistance == distanceS) {
            return "S";
        } else if (minDistance == distanceM) {
            return "M";
        } else if (minDistance == distanceL) {
            return "L";
        } else {
            return "XL";
        }
    }


    private boolean isWithinRange(double measurement, double min, double max, double tolerance) {
        return measurement >= min - tolerance && measurement <= max + tolerance;
    }



    public String generateLowerBodyUniversalSize() {
        double waist = currentCustomer.getCustomerWaistMeas();
        double hip = currentCustomer.getCustomerHipMeas();
        double inseam = currentCustomer.getCustomerInseamMeas();

        // Define tolerance for each measurement
        double waistTolerance = 1.0;
        double hipTolerance = 1.0;
        double inseamTolerance = 1.0;

        // Determine the size based on measurements and tolerance ranges
        if (isWithinRange(waist, 73, 76.5, waistTolerance) &&
                isWithinRange(hip, 91, 94.5, hipTolerance) &&
                isWithinRange(inseam, 81, 81.75, inseamTolerance)) { // Updated ranges for size S
            return "S";
        } else if (isWithinRange(waist, 76.5, 83, waistTolerance) &&
                isWithinRange(hip, 95, 98, hipTolerance) &&
                isWithinRange(inseam, 81.75, 82.35, inseamTolerance)) { // Updated ranges for size M
            return "M";
        } else if (isWithinRange(waist, 83, 90, waistTolerance) &&
                isWithinRange(hip, 99, 101, hipTolerance) &&
                isWithinRange(inseam, 82.5, 82.8, inseamTolerance)) { // Updated ranges for size L
            return "L";
        } else if (isWithinRange(waist, 90, 95.8, waistTolerance) &&
                isWithinRange(hip, 101, 115, hipTolerance) &&
                isWithinRange(inseam, 83, 83.75, inseamTolerance)) { // Updated ranges for size XL
            return "XL";
        } else {
            // If no exact match, determine the closest size based on the measurements that do fall within the predefined ranges
            String closestSize = getClosestSize(waist, hip, inseam);
            return closestSize;
        }
    }

    private String getClosestSize(double waist, double hip, double inseam) {
        // Calculate distances to each size's range
        double distanceS = calculateDistanceToSize(waist, 73, 76.5) +
                calculateDistanceToSize(hip, 91, 94.5) +
                calculateDistanceToSize(inseam, 81, 81.75);
        double distanceM = calculateDistanceToSize(waist, 76.5, 83) +
                calculateDistanceToSize(hip, 95, 98) +
                calculateDistanceToSize(inseam, 81.75, 82.35);
        double distanceL = calculateDistanceToSize(waist, 83, 90) +
                calculateDistanceToSize(hip, 99, 101) +
                calculateDistanceToSize(inseam, 82.5, 82.8);
        double distanceXL = calculateDistanceToSize(waist, 90, 95.8) +
                calculateDistanceToSize(hip, 101, 115) +
                calculateDistanceToSize(inseam, 83, 83.75);

        // Find the size with the minimum total distance
        double minDistance = Math.min(Math.min(distanceS, distanceM), Math.min(distanceL, distanceXL));

        // Return the closest size
        if (minDistance == distanceS) {
            return "S";
        } else if (minDistance == distanceM) {
            return "M";
        } else if (minDistance == distanceL) {
            return "L";
        } else {
            return "XL";
        }
    }

    private double calculateDistanceToSize(double measurement, double min, double max) {
        if (measurement < min) {
            return min - measurement;
        } else if (measurement > max) {
            return measurement - max;
        } else {
            return 0;
        }
    }






}
