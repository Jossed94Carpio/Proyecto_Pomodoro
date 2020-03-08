/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Lenovo-PC
 */

/**
 * Este método contiene los métodos el control de valores ingresados no acepte caracteres especiales
 **/
public class controlDatosIngreso {
    /**
     * Este método controla que los valores ingresados no admite letras ni caracteres especiales a escepción de el simbolo (:)
     **/
    public void valores(java.awt.event.KeyEvent evt) {
        char[] p = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':'};
        int b = 0;
        for (int i = 0; i <= 10; i++) {
            if (p[i] == evt.getKeyChar()) {
                b = 1;
            }

        }
        if (b == 0) {
            evt.consume();
        }
    }

    /**
     * Este método controla que los valores ingresados no admite letras ni caracteres especiales
     **/
    public void soloNumeros(java.awt.event.KeyEvent evt) {
        char[] p = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        int b = 0;
        for (int i = 0; i <= 9; i++) {
            if (p[i] == evt.getKeyChar()) {
                b = 1;
            }
        }
        if (b == 0) {
            evt.consume();
        }
    }
}
