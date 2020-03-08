/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.util.Timer;
import java.util.TimerTask;
import controlador.*;
import java.applet.AudioClip;

/**
 *
 * @author Lenovo-PC
 */
/**
 * Este método define las principales variables globales del pomodoro
 *
 */
public class Principal_Pomodoro extends javax.swing.JFrame {

    Timer temporizador;
    int NumPomodoro = 1;
    int cantidadP;
    int auxPom = 1;
    AudioClip alarma;

    /**
     * Creates new form Principal_Pomodoro
     */
    public Principal_Pomodoro() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Pomodoro");
        Tiempo_Ejecucion.setVisible(false);
        Tiempo_Descanso.setVisible(false);
    }

    /**
     * Este método fija el valor en el reloj del temporizador del pomodoro
     *
     * @param tiempo valor requerido para fijar el tiempo del reloj
     *
     */
    public void setTiempo(int tiempo) {
        long tiempoActual = 1000 * tiempo;
        long segundo = (tiempoActual / 1000) % 60;
        long minuto = (tiempoActual / (1000 * 60)) % 60;
        Reloj.setText(String.format("%02d:%02d", minuto, segundo));
    }

    /**
     * Este método obtienen el valor ingresado por el usuario para fijar el
     * tiempo que durara un pomodoro
     *
     * @param tiempo valor requerido para fijar en el campo de valores fijos el
     * valor de duración del pomodoro
     *
     */
    public void setPomodoro(int tiempo) {
        long tiempoActual = 1000 * tiempo;
        long segundo = (tiempoActual / 1000) % 60;
        long minuto = (tiempoActual / (1000 * 60)) % 60;
        PomodoroSET.setText(String.format("%02d:%02d", minuto, segundo));
    }

    /**
     * Este método obtienen el valor ingresado por el usuario para fijar el
     * tiempo que durara el descanso corto luego del pomodoro
     *
     * @param tiempo valor requerido para fijar en el campo de valores fijos el
     * valor de duración del descanso corto
     *
     */
    public void setDescansoC(int tiempo) {
        long tiempoActual = 1000 * tiempo;
        long segundo = (tiempoActual / 1000) % 60;
        long minuto = (tiempoActual / (1000 * 60)) % 60;
        DescansoCSET.setText(String.format("%02d:%02d", minuto, segundo));
    }

    /**
     * Este método obtienen el valor ingresado por el usuario para fijar el
     * tiempo que durara el descanso largo luego del cuarto pomodoro
     *
     * @param tiempo valor requerido para fijar en el campo de valores fijos el
     * valor de duración del descanso largo
     *
     */
    public void setDescansoL(int tiempo) {
        long tiempoActual = 1000 * tiempo;
        long segundo = (tiempoActual / 1000) % 60;
        long minuto = (tiempoActual / (1000 * 60)) % 60;
        DescansoLSET.setText(String.format("%02d:%02d", minuto, segundo));
    }

    /**
     * Este método obtiene los valores de duración de cada pomodoro para asignar
     * al temporizador
     *
     */
    public void pomodoroReloj() {
        cantidadP = Integer.parseInt(CantPomodoros.getText());
        temporizador = new Timer();
        temporizador.schedule(new TimerTask() {
            String[] TiempoTranscurrido = Pomodoro.getText().split(":");
            String[] Cero = ContCero.getText().split(":");
            String[] PomodoroV = Pomodoro.getText().split(":");
            String[] DCV = DescansoC.getText().split(":");
            String[] DLV = DescansoL.getText().split(":");
            int PomodoroVI = Integer.parseInt(PomodoroV[0]) * 60 + (Integer.parseInt(PomodoroV[1]));
            int DescansoCVI = Integer.parseInt(DCV[0]) * 60 + (Integer.parseInt(DCV[1]));
            int DescansoLVI = Integer.parseInt(DLV[0]) * 60 + (Integer.parseInt(DLV[1]));
            int contadorLimite = Integer.parseInt(TiempoTranscurrido[0]) * 60 + Integer.parseInt(TiempoTranscurrido[1]);
            int contadorCero = Integer.parseInt(Cero[0]) * 60 + (Integer.parseInt(Cero[1])) - 1;
            int contador = contadorLimite;

            /**
             * Este método corre los hilos y cuando el contador llega a cero
             * utilizando condicionales verifica que tipo de descanso se debe
             * ejecutar Cabe mencionar que el tiempo de los hilos se encuentra
             * en milisegundos
             *
             */
            @Override
            public void run() {
                setTiempo(contador);
                --contador;
                Pomodoro_enEjecucion.setText(String.valueOf(auxPom));
                setPomodoro(PomodoroVI);
                setDescansoC(DescansoCVI);
                setDescansoL(DescansoLVI);
                if (contador == contadorCero && NumPomodoro < 4) {
                    temporizador.cancel();
                    if (cantidadP > 1) {
                        if (auxPom == cantidadP) {
                            alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma4.wav"));
                            alarma.play();
                            try {
                                Thread.sleep(3000);
                            } catch (Exception e) {
                            }
                            PomodoroSET.setText("00:00");
                            DescansoCSET.setText("00:00");
                            DescansoLSET.setText("00:00");
                            Reiniciar.setEnabled(false);
                            Pomodoro_enEjecucion.setText("-");
                            Tiempo_Ejecucion.setVisible(false);
                            Iniciar.setEnabled(true);
                            auxPom = 1;
                            NumPomodoro = 1;
                        } else {
                            Tiempo_Ejecucion.setVisible(false);
                            Reiniciar.setEnabled(false);
                            alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma1.wav"));
                            alarma.play();
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                            }
                            descansoCorto();
                            NumPomodoro++;
                            auxPom++;
                        }
                    } else {
                        alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma4.wav"));
                        alarma.play();
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                        }
                        PomodoroSET.setText("00:00");
                        DescansoCSET.setText("00:00");
                        DescansoLSET.setText("00:00");
                        Reiniciar.setEnabled(false);
                        Pomodoro_enEjecucion.setText("-");
                        Tiempo_Ejecucion.setVisible(false);
                        Iniciar.setEnabled(true);
                        auxPom = 1;
                        NumPomodoro = 1;
                    }
                } else if (contador == contadorCero && NumPomodoro == 4) {
                    temporizador.cancel();
                    if (cantidadP > 1) {
                        if (auxPom == cantidadP) {
                            alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma4.wav"));
                            alarma.play();
                            try {
                                Thread.sleep(3000);
                            } catch (Exception e) {
                            }
                            PomodoroSET.setText("00:00");
                            DescansoCSET.setText("00:00");
                            DescansoLSET.setText("00:00");
                            Reiniciar.setEnabled(false);
                            Pomodoro_enEjecucion.setText("-");
                            Tiempo_Ejecucion.setVisible(false);
                            Iniciar.setEnabled(true);
                            auxPom = 1;
                            NumPomodoro = 1;
                        } else {
                            Tiempo_Ejecucion.setVisible(false);
                            Reiniciar.setEnabled(false);
                            alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma2.wav"));
                            alarma.play();
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                            }
                            descansoLargo();
                            auxPom++;
                        }
                    }
                }
            }
        }, 0, 1000);

    }

    /**
     * Este método obtiene los valores de duración del descanso corto para
     * asignar al temporizador
     *
     */
    public void descansoCorto() {
        Tiempo_Descanso.setVisible(true);
        temporizador = new Timer();
        temporizador.schedule(new TimerTask() {
            String[] TiempoDesc = DescansoC.getText().split(":");
            String[] CeroD = ContCero.getText().split(":");
            int contadorLimiteDC = Integer.parseInt(TiempoDesc[0]) * 60 + Integer.parseInt(TiempoDesc[1]);
            int contadorCeroD = Integer.parseInt(CeroD[0]) * 60 + (Integer.parseInt(CeroD[1])) - 1;
            int contDescansoC = contadorLimiteDC;

            /**
             * Este método corre los hilos del tiempo de descanso corto y cuando
             * el contador llega a cero utilizando condicionales verifica si
             * termino los pomodoros de la tarea asignada Cabe mencionar que el
             * tiempo de los hilos se encuentra en milisegundos
             *
             */
            @Override
            public void run() {
                setTiempo(contDescansoC);
                --contDescansoC;
                if (contDescansoC == contadorCeroD) {
                    temporizador.cancel();
                    Tiempo_Descanso.setVisible(false);
                    alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma3.wav"));
                    alarma.play();
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    Tiempo_Ejecucion.setVisible(true);
                    Reiniciar.setEnabled(true);
                    pomodoroReloj();
                }
            }
        }, 0, 1000);
    }

    /**
     * Este método obtiene los valores de duración del descanso largo para
     * asignar al temporizador
     *
     */
    public void descansoLargo() {
        Tiempo_Descanso.setVisible(true);
        temporizador = new Timer();
        temporizador.schedule(new TimerTask() {
            String[] TiempoDescL = DescansoL.getText().split(":");
            String[] CeroDL = ContCero.getText().split(":");
            int contadorLimiteDL = Integer.parseInt(TiempoDescL[0]) * 60 + Integer.parseInt(TiempoDescL[1]);
            int contadorCeroDL = Integer.parseInt(CeroDL[0]) * 60 + (Integer.parseInt(CeroDL[1])) - 1;
            int contDescansoL = contadorLimiteDL;

            /**
             * Este método corre los hilos del tiempo de descanso largo y cuando
             * el contador llega a cero utilizando condicionales verifica si
             * termino los pomodoros de la tarea asignada Cabe mencionar que el
             * tiempo de los hilos se encuentra en milisegundos
             *
             */
            @Override
            public void run() {
                setTiempo(contDescansoL);
                --contDescansoL;
                if (contDescansoL == contadorCeroDL) {
                    temporizador.cancel();
                    NumPomodoro = 1;
                    Tiempo_Descanso.setVisible(false);
                    alarma = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/sonido/alarma3.wav"));
                    alarma.play();
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    Tiempo_Ejecucion.setVisible(true);
                    Reiniciar.setEnabled(true);
                    pomodoroReloj();
                }
            }
        }, 0, 1000);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ContCero = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Reloj = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        PomodoroSET = new javax.swing.JLabel();
        DescansoCSET = new javax.swing.JLabel();
        DescansoLSET = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        JPCA = new javax.swing.JPanel();
        Pomodoro_enEjecucion = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        Tiempo_Descanso = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        Tiempo_Ejecucion = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Pomodoro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DescansoC = new javax.swing.JTextField();
        DescansoL = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        CantPomodoros = new javax.swing.JTextField();
        Iniciar = new javax.swing.JButton();
        Reiniciar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        ContCero.setText("00:00");
        ContCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContCeroActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 153, 255));
        setIconImage(getIconImage());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jPanel6.setBackground(new java.awt.Color(0, 0, 153));

        jPanel1.setBackground(new java.awt.Color(6, 63, 235));

        Reloj.setBackground(new java.awt.Color(0, 0, 0));
        Reloj.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        Reloj.setForeground(new java.awt.Color(255, 255, 255));
        Reloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Reloj.setText("00:00");

        jPanel5.setBackground(new java.awt.Color(6, 63, 235));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pomodoro");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Des. Corto");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Des. Largo");

        PomodoroSET.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        PomodoroSET.setForeground(new java.awt.Color(255, 255, 255));
        PomodoroSET.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PomodoroSET.setText("00:00");

        DescansoCSET.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        DescansoCSET.setForeground(new java.awt.Color(255, 255, 255));
        DescansoCSET.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DescansoCSET.setText("00:00");

        DescansoLSET.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        DescansoLSET.setForeground(new java.awt.Color(255, 255, 255));
        DescansoLSET.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DescansoLSET.setText("00:00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PomodoroSET, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(DescansoCSET, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DescansoLSET, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PomodoroSET, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DescansoCSET, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(DescansoLSET, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Minutos");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Segundos");

        jPanel12.setBackground(new java.awt.Color(6, 63, 235));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Pomodoro en Ejecución");

        JPCA.setBackground(new java.awt.Color(6, 63, 235));

        Pomodoro_enEjecucion.setBackground(new java.awt.Color(6, 63, 235));
        Pomodoro_enEjecucion.setFont(new java.awt.Font("Verdana", 1, 30)); // NOI18N
        Pomodoro_enEjecucion.setForeground(new java.awt.Color(255, 255, 255));
        Pomodoro_enEjecucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Pomodoro_enEjecucion.setText("-");

        jPanel13.setBackground(new java.awt.Color(6, 63, 235));

        Tiempo_Descanso.setBackground(new java.awt.Color(255, 0, 0));
        Tiempo_Descanso.setPreferredSize(new java.awt.Dimension(205, 20));

        javax.swing.GroupLayout Tiempo_DescansoLayout = new javax.swing.GroupLayout(Tiempo_Descanso);
        Tiempo_Descanso.setLayout(Tiempo_DescansoLayout);
        Tiempo_DescansoLayout.setHorizontalGroup(
            Tiempo_DescansoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        Tiempo_DescansoLayout.setVerticalGroup(
            Tiempo_DescansoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tiempo_Descanso, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(Tiempo_Descanso, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(6, 63, 235));

        Tiempo_Ejecucion.setBackground(new java.awt.Color(51, 255, 51));
        Tiempo_Ejecucion.setPreferredSize(new java.awt.Dimension(205, 20));

        javax.swing.GroupLayout Tiempo_EjecucionLayout = new javax.swing.GroupLayout(Tiempo_Ejecucion);
        Tiempo_Ejecucion.setLayout(Tiempo_EjecucionLayout);
        Tiempo_EjecucionLayout.setHorizontalGroup(
            Tiempo_EjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        Tiempo_EjecucionLayout.setVerticalGroup(
            Tiempo_EjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tiempo_Ejecucion, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(Tiempo_Ejecucion, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JPCALayout = new javax.swing.GroupLayout(JPCA);
        JPCA.setLayout(JPCALayout);
        JPCALayout.setHorizontalGroup(
            JPCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPCALayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(Pomodoro_enEjecucion, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JPCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        JPCALayout.setVerticalGroup(
            JPCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPCALayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(Pomodoro_enEjecucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPCA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JPCA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Reloj, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Reloj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(6, 63, 235));

        jPanel14.setBackground(new java.awt.Color(6, 63, 235));
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pomodoro");

        Pomodoro.setBackground(new java.awt.Color(6, 63, 235));
        Pomodoro.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        Pomodoro.setForeground(new java.awt.Color(255, 255, 255));
        Pomodoro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Pomodoro.setText("00:05");
        Pomodoro.setBorder(null);
        Pomodoro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PomodoroActionPerformed(evt);
            }
        });
        Pomodoro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PomodoroKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Des. Corto");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Des. Largo");

        DescansoC.setBackground(new java.awt.Color(6, 63, 235));
        DescansoC.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        DescansoC.setForeground(new java.awt.Color(255, 255, 255));
        DescansoC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DescansoC.setText("00:03");
        DescansoC.setBorder(null);
        DescansoC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescansoCActionPerformed(evt);
            }
        });
        DescansoC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DescansoCKeyTyped(evt);
            }
        });

        DescansoL.setBackground(new java.awt.Color(6, 63, 235));
        DescansoL.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        DescansoL.setForeground(new java.awt.Color(255, 255, 255));
        DescansoL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DescansoL.setText("00:05");
        DescansoL.setBorder(null);
        DescansoL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescansoLActionPerformed(evt);
            }
        });
        DescansoL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DescansoLKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pomodoro, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescansoC, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescansoL, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Pomodoro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescansoC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescansoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tiempos");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Número de Pomodoros");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Acciones");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ingrese Valor: ");

        CantPomodoros.setBackground(new java.awt.Color(6, 63, 235));
        CantPomodoros.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        CantPomodoros.setForeground(new java.awt.Color(255, 255, 255));
        CantPomodoros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CantPomodoros.setText("1");
        CantPomodoros.setBorder(null);
        CantPomodoros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CantPomodorosKeyTyped(evt);
            }
        });

        Iniciar.setBackground(new java.awt.Color(6, 63, 235));
        Iniciar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        Iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/play.png"))); // NOI18N
        Iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarActionPerformed(evt);
            }
        });
        Iniciar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IniciarKeyPressed(evt);
            }
        });

        Reiniciar.setBackground(new java.awt.Color(6, 63, 235));
        Reiniciar.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        Reiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/reiniciar.png"))); // NOI18N
        Reiniciar.setEnabled(false);
        Reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(CantPomodoros, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(Iniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Reiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Reiniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Iniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(CantPomodoros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Eras Medium ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("POMODORO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jMenu1.setText("Archivo");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });

        jMenuItem1.setText("Salir");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Información");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Que es Pomodoro?");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseClicked(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Autores");

        jMenuItem3.setText("Detalles");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Este método contiene las variables intervienen al momento que se presiona
     * el botón iniciar
     *
     */
    private void IniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarActionPerformed
        Iniciar.setEnabled(false);
        Reiniciar.setEnabled(true);
        pomodoroReloj();
        Tiempo_Ejecucion.setVisible(true);
    }//GEN-LAST:event_IniciarActionPerformed

    /**
     * Este método contiene las variables intervienen al momento que se presiona
     * el botón reiniciar
     *
     */
    private void ReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReiniciarActionPerformed
        Iniciar.setEnabled(true);
        temporizador.cancel();
        Reloj.setText("00:00");
        PomodoroSET.setText("00:00");
        DescansoCSET.setText("00:00");
        DescansoLSET.setText("00:00");
        Reiniciar.setEnabled(false);
        Pomodoro_enEjecucion.setText("-");
        Tiempo_Ejecucion.setVisible(false);
        Tiempo_Descanso.setVisible(false);
    }//GEN-LAST:event_ReiniciarActionPerformed

    private void ContCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContCeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ContCeroActionPerformed

    /**
     * Este método contiene el control de valores ingresados en la cantidad de
     * pomodoros que no acepte letras ni caracteres especiales
     *
     */
    private void CantPomodorosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CantPomodorosKeyTyped
        controlDatosIngreso CD = new controlDatosIngreso();
        CD.soloNumeros(evt);
    }//GEN-LAST:event_CantPomodorosKeyTyped

    private void PomodoroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PomodoroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PomodoroActionPerformed

    /**
     * Este método contiene el control de valores ingresados en la duración del
     * pomodoro que no acepte letras ni caracteres especiales
     *
     */
    private void PomodoroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PomodoroKeyTyped
        controlDatosIngreso CD = new controlDatosIngreso();
        CD.valores(evt);
    }//GEN-LAST:event_PomodoroKeyTyped

    private void DescansoCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescansoCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescansoCActionPerformed

    /**
     * Este método contiene el control de valores ingresados en la duración del
     * descanso corto que no acepte letras ni caracteres especiales
     *
     */
    private void DescansoCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DescansoCKeyTyped
        controlDatosIngreso CD = new controlDatosIngreso();
        CD.valores(evt);
    }//GEN-LAST:event_DescansoCKeyTyped

    private void DescansoLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescansoLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescansoLActionPerformed

    /**
     * Este método contiene el control de valores ingresados en la duración del
     * descanso largo que no acepte letras ni caracteres especiales
     *
     */
    private void DescansoLKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DescansoLKeyTyped
        controlDatosIngreso CD = new controlDatosIngreso();
        CD.valores(evt);
    }//GEN-LAST:event_DescansoLKeyTyped

    private void IniciarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IniciarKeyPressed
        // TODO add your handling code here:	
    }//GEN-LAST:event_IniciarKeyPressed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked

    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked

    }//GEN-LAST:event_jMenuItem1MouseClicked

    /**
     * Este método contiene el control para cuando se pulsa botón de salir del
     * sistema
     *
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Datos_Pomodoro DP = new Datos_Pomodoro();
        DP.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseClicked

    }//GEN-LAST:event_jMenuItem2MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Autores A = new Autores();
        A.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal_Pomodoro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal_Pomodoro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal_Pomodoro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal_Pomodoro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal_Pomodoro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CantPomodoros;
    private javax.swing.JTextField ContCero;
    private javax.swing.JTextField DescansoC;
    private javax.swing.JLabel DescansoCSET;
    private javax.swing.JTextField DescansoL;
    private javax.swing.JLabel DescansoLSET;
    private javax.swing.JButton Iniciar;
    private javax.swing.JPanel JPCA;
    private javax.swing.JTextField Pomodoro;
    private javax.swing.JLabel PomodoroSET;
    private javax.swing.JLabel Pomodoro_enEjecucion;
    private javax.swing.JButton Reiniciar;
    private javax.swing.JLabel Reloj;
    private javax.swing.JPanel Tiempo_Descanso;
    private javax.swing.JPanel Tiempo_Ejecucion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    // End of variables declaration//GEN-END:variables
}
