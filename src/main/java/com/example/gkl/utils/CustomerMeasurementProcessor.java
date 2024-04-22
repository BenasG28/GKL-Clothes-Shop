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
                isWithinRange(waist, 93, 98, waistTolerance) &&
                isWithinRange(hip, 108, 112, hipTolerance)) { // Updated hip range for size XL
            return "XL";
        } else {
            // If no exact match, determine the size based on the closest measurements
            double closestChest = getClosestBoundary(chest, 88, 113);
            double closestShoulder = getClosestBoundary(shoulder, 39, 56);
            double closestWaist = getClosestBoundary(waist, 75, 98);
            double closestHip = getClosestBoundary(hip, 92, 112); // Updated hip range for closest calculation

            if (closestChest >= 88 && closestChest <= 95 &&
                    closestShoulder >= 39 && closestShoulder <= 44 &&
                    closestWaist >= 75 && closestWaist <= 82 &&
                    closestHip >= 92 && closestHip <= 97) { // Updated hip range for size S
                return "S";
            } else if (closestChest >= 95 && closestChest <= 101 &&
                    closestShoulder >= 44 && closestShoulder <= 48 &&
                    closestWaist >= 82 && closestWaist <= 88 &&
                    closestHip >= 97 && closestHip <= 104) { // Updated hip range for size M
                return "M";
            } else if (closestChest >= 101 && closestChest <= 107 &&
                    closestShoulder >= 48 && closestShoulder <= 52 &&
                    closestWaist >= 88 && closestWaist <= 93 &&
                    closestHip >= 104 && closestHip <= 107) { // Updated hip range for size L
                return "L";
            } else if (closestChest >= 107 && closestChest <= 113 &&
                    closestShoulder >= 52 && closestShoulder <= 56 &&
                    closestWaist >= 93 && closestWaist <= 98 &&
                    closestHip >= 108 && closestHip <= 112) { // Updated hip range for size XL
                return "XL";
            } else {
                return "No size matches";
            }
        }
    }

    private boolean isWithinRange(double measurement, double min, double max, double tolerance) {
        return measurement >= min - tolerance && measurement <= max + tolerance;
    }

    private double getClosestBoundary(double value, double min, double max) {
        return Math.abs(value - min) < Math.abs(value - max) ? min : max;
    }



    public String generateLowerBodyUniversalSize() {
        double waist = currentCustomer.getCustomerWaistMeas();
        double hip = currentCustomer.getCustomerHipMeas();
        double inseam = currentCustomer.getCustomerInseamMeas();

        // Define tolerance for each measurement
        double waistTolerance = 2.0;
        double hipTolerance = 2.0;
        double inseamTolerance = 2.0;

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
                isWithinRange(hip, 101, 107, hipTolerance) &&
                isWithinRange(inseam, 83, 83.75, inseamTolerance)) { // Updated ranges for size XL
            return "XL";
        } else {
            // If no exact match, determine the size based on the closest measurements
            double closestWaist = getClosestBoundary(waist, 73, 95.8);
            double closestHip = getClosestBoundary(hip, 91, 107);
            double closestInseam = getClosestBoundary(inseam, 81, 85);

            if (isWithinRange(closestWaist, 73, 76.5, waistTolerance) &&
                    isWithinRange(closestHip, 91, 94.5, hipTolerance) &&
                    isWithinRange(closestInseam, 81, 81.75, inseamTolerance)) { // Updated ranges for size S
                return "S";
            } else if (isWithinRange(closestWaist, 76.5, 83, waistTolerance) &&
                    isWithinRange(closestHip, 95, 98, hipTolerance) &&
                    isWithinRange(closestInseam, 82, 83, inseamTolerance)) { // Updated ranges for size M
                return "M";
            } else if (isWithinRange(closestWaist, 83, 90, waistTolerance) &&
                    isWithinRange(closestHip, 99, 101, hipTolerance) &&
                    isWithinRange(closestInseam, 83, 84, inseamTolerance)) { // Updated ranges for size L
                return "L";
            } else if (isWithinRange(closestWaist, 90, 95.8, waistTolerance) &&
                    isWithinRange(closestHip, 101, 107, hipTolerance) &&
                    isWithinRange(closestInseam, 84, 85, inseamTolerance)) { // Updated ranges for size XL
                return "XL";
            } else {
                return "No size matches";
            }
        }
    }





}
