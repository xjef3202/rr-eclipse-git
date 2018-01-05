package com.sysco.utils;

import com.sysco.exception.ValidationException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ValidationUtilTest {

    @Test
    public void validateDateTest(){
        String date = "2016-01-01";
        assertThat(ValidationUtil.validateDate(date)).isEqualTo(true);
    }

    @Test(expected = ValidationException.class)
    public void validateDateInvalidYearTest(){

        String date = "201-01-01";
        assertThat(ValidationUtil.validateDate(date)).isEqualTo(false);
    }

    @Test(expected = ValidationException.class)
    public void validateDateInvalidMonth(){
        String date = "2016-Jan-01";
        assertThat(ValidationUtil.validateDate(date)).isEqualTo(false);
    }

    @Test(expected = ValidationException.class)
    public void validateDateInvalidDay(){
        String date = "2016-Jan-01";
        assertThat(ValidationUtil.validateDate(date)).isEqualTo(false);
    }

    @Test
    public void validateAuthHeaderTest(){
        String authHeader = "067|000703";
        assertThat(ValidationUtil.validateAuthHeader(authHeader)).isEqualTo(true);
    }

    @Test(expected = ValidationException.class)
    public void validateAuthHeaderInvalidCustomerIdTest(){
        String authHeader = "067|000703|1";
        assertThat(ValidationUtil.validateAuthHeader(authHeader)).isEqualTo(false);
    }

    @Test(expected = ValidationException.class)
    public void validateAuthHeaderInvalidOpcoTest(){
        String authHeader = "06700703";
        assertThat(ValidationUtil.validateAuthHeader(authHeader)).isEqualTo(false);
    }
}
