package transformador;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.DecimalFormatSymbols;

public class Transformador {
    static NumberFormat numberFormat = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols());

    public static String transform(Double value){
        return numberFormat.format(value);
    }
}
