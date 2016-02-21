package org.sfaci.holamongodb.util;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidades
 *
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Util {

    public static int mensajeConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }

    public static void mensajeError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public static String formatFecha(Date fecha) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        return format.format(fecha);
    }

    public static Date parseFecha(String fecha) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        return format.parse(fecha);
    }
}
