package financiera.CCTIC.utils;

public class NumberToWordsConverter {
    private static final String[] UNIDADES = {
            "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"
    };

    private static final String[] DECENAS = {
            "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] DIEZ_A_VEINTE = {
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };

    private static final String[] CENTENAS = {
            "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    public static String convertir(int numero) {
        if (numero == 0) {
            return "cero";
        }
        if (numero < 0) {
            return "menos " + convertir(Math.abs(numero));
        }
        if (numero < 10) {
            return UNIDADES[numero];
        }
        if (numero < 20) {
            return DIEZ_A_VEINTE[numero - 10];
        }
        if (numero < 100) {
            return DECENAS[numero / 10] + ((numero % 10 != 0) ? " y " + UNIDADES[numero % 10] : "");
        }
        if (numero < 1000) {
            return CENTENAS[numero / 100] + ((numero % 100 != 0) ? " " + convertir(numero % 100) : "");
        }
        if (numero < 1000000) {
            return convertir(numero / 1000) + " mil" + ((numero % 1000 != 0) ? " " + convertir(numero % 1000) : "");
        }
        if (numero < 1000000000) {
            return convertir(numero / 1000000) + " millones" + ((numero % 1000000 != 0) ? " " + convertir(numero % 1000000) : "");
        }
        return convertir(numero / 1000000000) + " mil millones" + ((numero % 1000000000 != 0) ? " " + convertir(numero % 1000000000) : "");
    }

    public static void main(String[] args) {
        int numero = 123456789;
        System.out.println(convertir(numero));
    }
}
