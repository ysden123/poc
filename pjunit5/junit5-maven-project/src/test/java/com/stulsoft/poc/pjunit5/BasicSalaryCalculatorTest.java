/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yuriy Stul
 */
class BasicSalaryCalculatorTest {
    private BasicSalaryCalculator basicSalaryCalculator;

    @BeforeEach
    void init() {
        basicSalaryCalculator = new BasicSalaryCalculator();
    }

    @Test
    void testBasicSalaryWithValidSalary() {
        double basicSalary = 4000;
        basicSalaryCalculator.setBasicSalary(basicSalary);

        double expectedSocialInsurance = basicSalary * 0.25;
        assertEquals(expectedSocialInsurance, basicSalaryCalculator.getSocialInsurance());

        double expectedAddionalBonus = basicSalary * 0.1;
        assertEquals(expectedAddionalBonus, basicSalaryCalculator.getAdditionalBonus());

        double expectedGross = basicSalary + expectedSocialInsurance + expectedAddionalBonus;
        assertEquals(expectedGross, basicSalaryCalculator.getGrossSalary());

    }

    @DisplayName("Test BasicSalaryCalculator with invalid salary")
    @Test
    void testBasicSalaryWithInValidSalary() {

        double basicSalary = -100;
        assertThrows(IllegalArgumentException.class, () -> basicSalaryCalculator.setBasicSalary(basicSalary));

    }

    @Test
    void testImmutableCollections() {
        List<String> fruits = List.of("Mangosteen", "Durian fruit", "Longan");

        assertThrows(UnsupportedOperationException.class, () -> {
            fruits.add("Mango");
            fruits.remove(1);
        });

        assertEquals(3, fruits.size());
    }

    @Test
    @Disabled
    void testSomeMethod() {
    }

    @AfterEach
    void tearDown() {
        basicSalaryCalculator = null;
    }
}