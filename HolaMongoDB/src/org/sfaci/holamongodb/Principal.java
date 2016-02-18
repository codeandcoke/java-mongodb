package org.sfaci.holamongodb;

/**
 * Clase principal de la aplicación
 *
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Principal {

    public static void main(String args[]) {

        Ventana view = new Ventana();
        VentanaModel model = new VentanaModel();
        VentanaController controller = new VentanaController(view, model);
    }
}
