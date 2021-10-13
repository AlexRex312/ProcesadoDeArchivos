package com.mycompany.ProcesadoDeArchivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Alejandro Aguilar Otero
 */
public class HistogramaConVentana extends javax.swing.JFrame {


    private String nombreArchivo;
    private Map<String, Integer> mapa;
    
     //Crea una nueva ventana
    public HistogramaConVentana() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        textoArchivo = new javax.swing.JTextArea();
        labelArchivo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoHistograma = new javax.swing.JTextArea();
        labelHistograma = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        botonCargar = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        textoNombreArchivo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setLayout(new java.awt.BorderLayout());

        textoArchivo.setColumns(20);
        textoArchivo.setRows(5);
        textoArchivo.setEnabled(false);
        jScrollPane.setViewportView(textoArchivo);

        jPanel3.add(jScrollPane, java.awt.BorderLayout.CENTER);

        labelArchivo.setText("Contenido del Archivo");
        jPanel3.add(labelArchivo, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        textoHistograma.setColumns(20);
        textoHistograma.setRows(5);
        textoHistograma.setEnabled(false);
        jScrollPane2.setViewportView(textoHistograma);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        labelHistograma.setText("Histograma");
        jPanel4.add(labelHistograma, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        botonCargar.setText("Cargar txt");
        botonCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargarActionPerformed(evt);
            }
        });
        jPanel6.add(botonCargar);

        botonGuardar.setText("Guardar");
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(botonGuardar);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        textoNombreArchivo.setToolTipText("Escriba el nombre del archivo sin extension");
        jPanel5.add(textoNombreArchivo, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed

        //Array con la cabecera del excel
        String[] Cabecera = {"Palabra","Repeticiones"};

        //Creo el CSVPrinter
        try(CSVPrinter printer = new CSVPrinter(new FileWriter(nombreArchivo + "_histograma.csv"), CSVFormat.DEFAULT
            .withHeader(Cabecera))) {

        //Recorremos el mapa y escribimos en el excel
        mapa.forEach((Palabra, Repeticiones) -> {
            try {
                printer.printRecord(Palabra, Repeticiones);
            } catch (IOException ex) {
                Logger.getLogger(HistogramaConVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        } catch (IOException ex) {
            Logger.getLogger(HistogramaConVentana.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargarActionPerformed

        //Mapa con las claves y valores
        Map<String, Integer> palabras_histograma = new HashMap<>();

        //Leemos el texto en el cajetin
        nombreArchivo = textoNombreArchivo.getText();
        String nombreExtension = nombreArchivo + ".txt";

        //Leemos el archivo con un BufferedReader
        try(BufferedReader br = new BufferedReader(new FileReader(nombreExtension))) {

            //Recorremos cada linea
            Iterator<String> it = br.lines().iterator();
            while(it.hasNext()) {
                //Creo un string para cada linea
                String linea = it.next();
                //Lo muestro
                textoArchivo.append(linea+"\n");

                //Array de Strings con todas la palabras
                String[] palabras = linea.split("\\ |\\?|\\!|\\¡|\\¿|\\.|\\,|\\-|\\;|\\:|\\(|\\)|\\[|\\]|\\\n");
                for(String palabra : palabras) {
                    //Comprobamos que la palabra tenga mas de dos caracteres
                    if(palabra.length() > 2) {
                        //Paso la palabra a minusculas
                        String palabra_min = palabra.toLowerCase();
                        //Comprobamos si la palabra ya esta en el histograma
                        if(palabras_histograma.containsKey(palabra_min)) {
                            //Si esta sumamos uno
                            palabras_histograma.put(palabra_min, (palabras_histograma.get(palabra_min)+1));
                        } else {
                            //Si no le creamos una clave y la ponemos a valor 1
                            palabras_histograma.put(palabra_min, 1);
                        }
                    }
                }
            }

            //Al mapa global le asigno el mapa que he creado aqui
            mapa = palabras_histograma;

            //Muestro el resultado
            palabras_histograma.forEach((clave, valor) -> textoHistograma.append(clave + ": " + valor + "\n"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(HistogramaConVentana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HistogramaConVentana.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_botonCargarActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistogramaConVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new HistogramaConVentana().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCargar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelArchivo;
    private javax.swing.JLabel labelHistograma;
    private javax.swing.JTextArea textoArchivo;
    private javax.swing.JTextArea textoHistograma;
    private javax.swing.JTextField textoNombreArchivo;
    // End of variables declaration//GEN-END:variables
}
