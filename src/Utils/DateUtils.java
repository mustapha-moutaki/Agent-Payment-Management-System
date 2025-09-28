package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DEFAULT_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);

    public  static String format(Date date){
        return sdf.format(date);
    }

    public static Date parse(String dateString){
        try{
            return sdf.parse(dateString);
        }catch(ParseException e){
            System.out.println("==> invalid date format, pls try use "+ DEFAULT_PATTERN);
            return null;
        }
    }
}

/**
 * usage DAte paymentDAte = DateUtils.parse("10/01/2023")
 */
