/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FrameInsertar extends JFrame {   
    
    private DatabaseFutbol bdf;
    private JLabel lbequipo;
    private JTextField tfequipo;
    private JLabel lbciudad;
    private JTextField tfciudad;
    private JLabel lbestadio;
    private JTextField tfestadio;
    private JLabel lbquiniela;
    private JTextField tfquiniela;
    private JButton btnInsertar;
    private JButton btnCancelar;    
    
    public FrameInsertar(DatabaseFutbol df) {
        this.bdf = df;
        lbequipo = new JLabel("Equipo:");
        tfequipo = new JTextField();
        lbciudad = new JLabel("Ciudad:");
        tfciudad = new JTextField();
        lbestadio = new JLabel("Estadio:");
        tfestadio = new JTextField();
        lbquiniela = new JLabel("Quiniela:");
        tfquiniela = new JTextField();
        btnInsertar = new JButton("Insertar");
        btnCancelar = new JButton("Cancelar");
        setResizable(true);
        setTitle("Insertar Equipo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize( new Dimension(400,200));
        setLocationRelativeTo(null);
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(5,2));
        cp.add(lbequipo);cp.add(tfequipo);
        cp.add(lbciudad);cp.add(tfciudad);
        cp.add(lbestadio);cp.add(tfestadio);
        cp.add(lbquiniela);cp.add(tfquiniela);
        cp.add(btnInsertar);cp.add(btnCancelar);        
        pack(); 
        
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bdf.inserta(tfequipo.getText(), tfciudad.getText(),
                        tfestadio.getText(), tfquiniela.getText())){
                    JOptionPane.showMessageDialog(null, "Equipo Introducido correctamente");
                    setVisible(false);
                }
            }
        });        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });        
    }    
}

