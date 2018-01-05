package com.sysco.utils;

import com.sysco.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Slf4j
public class ValidationUtil {

    private static final String HEADER_MSG = "Invalid Or Missing Header Authorization";
    private static final String DATE_MSG = "Invalid Query Param Date";

    public static boolean validateAuthHeader(String header) throws ValidationException {
        if(StringUtils.isEmpty(header)){
            log.error("validateAuthHeader() : Empty Or Missing Header Authorization = {}", header);
            throw new ValidationException(HttpStatus.UNAUTHORIZED, String.join(" = ",HEADER_MSG,header));
        }else {
            String values [] = header.split("\\|");
            if(values.length!=2){
                log.error("validateAuthHeader() : Invalid Authorization Header Value= {}", header);
                throw new ValidationException(HttpStatus.UNAUTHORIZED, String.join(" = ",HEADER_MSG,header));
            }
        }

        return true;
    }

    public static boolean validateDate(String date){
        String dateArray [] = date.split("-");
        boolean validDate = true;

        if(dateArray.length!=3){
            validDate =  false;
        }
        if(dateArray[0].length()!=4){
            validDate =  false;
        }
        if(dateArray[1].length()!=2){
            validDate =  false;
        }
        if (dateArray[2].length()!=2){
            validDate = false;
        }

        for(String s: dateArray){
            if(!isNumeric(s)){
                validDate = false;
                break;
            }
        }

        if(!validDate){
            log.error("validateDate() : Invalid Query Param Date= {}", date);
            throw new ValidationException(HttpStatus.BAD_REQUEST, String.join(" = ",DATE_MSG,date));
        }
        return validDate;
    }

    private static boolean isNumeric(String value){
        for(int i=0;i<value.length();i++){
            if(!Character.isDigit(value.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
