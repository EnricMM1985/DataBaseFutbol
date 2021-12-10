/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Formulario extends JFrame {

    private final String[] titulos = {"Codigo", "Equipo", "Ciudad", "Estadio", "Nom.Quiniela"};
    private DefaultTableModel dtm = new DefaultTableModel();
    private DatabaseFutbol bdf = new DatabaseFutbol();
    private Container contentPane;
    private JTable jtb_Equipos;
    private JTextField txtEquipo;
    private JTextField jtDivision;
    private JComboBox jcTemporadas;

    public Formulario() {
        setResizable(false);
        setTitle("Equipos de Futbol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 628, 460);
        setLocationRelativeTo(null);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //-----NORTE-------------------------
        JPanel panelnorte = new JPanel();
        //panelcentro.setLayout(new GridLayout(1,6));
        panelnorte.setBorder(new TitledBorder(new TitledBorder(new LineBorder(
                new Color(192, 192, 192)), " Filtros", TitledBorder.LEADING,
                TitledBorder.TOP, null, null), " Filtros ",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelnorte.setBounds(10, 0, 582, 356);
        JLabel lblNombre = new JLabel("Equipo");
        txtEquipo = new JTextField(10);
        txtEquipo.setColumns(10);
        panelnorte.add(lblNombre);
        panelnorte.add(txtEquipo);
        JLabel lblDivision = new JLabel("Division");
        jtDivision = new JTextField(10);
        jtDivision.setColumns(10);
        panelnorte.add(lblDivision);
        panelnorte.add(jtDivision);
        JLabel lblTemporada = new JLabel("Temporadas");
        jcTemporadas = new JComboBox();
        jcTemporadas.setModel(new DefaultComboBoxModel(new String[]{"", "1997",
                "1998", "1999", "2000", "2001", "2002",
                "2003", "2004", "2005"}));
        panelnorte.add(lblTemporada);
        panelnorte.add(jcTemporadas);

        //-------CENTRO-----------------------
        JPanel panelcentro = new JPanel();
        panelcentro.setLayout(new GridLayout(1, 1));
        dtm.setColumnIdentifiers(titulos);
        jtb_Equipos = new JTable();
        jtb_Equipos.setRowSelectionAllowed(true);
        //jtb_Equipos.setEnabled(false);
        jtb_Equipos.setBounds(38, 95, 519, 124);
        jtb_Equipos.setBorder(null);
        jtb_Equipos.setModel(dtm);

        JScrollPane scroll = new JScrollPane(jtb_Equipos);
        scroll.setBounds(10, 67, 582, 294);
        panelcentro.add(scroll);

        //--------SUR-----------------------------------
        JPanel panelsur = new JPanel();
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnSalir = new JButton("Salir");
        JButton btnInsertar = new JButton("Insertar");
        JButton btnEliminar = new JButton("Eliminar");
        btnMostrar.setBounds(20, 378, 89, 23);
        btnSalir.setBounds(503, 377, 89, 23);
        panelsur.add(btnMostrar);
        panelsur.add(btnSalir);
        panelsur.add(btnInsertar);
        panelsur.add(btnEliminar);

        //----------------------------------------------     

        contentPane.add(panelcentro, BorderLayout.CENTER);
        contentPane.add(panelsur, BorderLayout.SOUTH);
        contentPane.add(panelnorte, BorderLayout.NORTH);

        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarConsulta();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                FrameInsertar f = new FrameInsertar(bdf);
                f.setAlwaysOnTop(true);
                f.setVisible(true);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int row = jtb_Equipos.getSelectedRow();
                String value = jtb_Equipos.getModel().getValueAt(row, 0).toString();
                if (bdf.elimina(value)) {
                    JOptionPane.showMessageDialog(null, "Equipo eliminado correctamente");
                    mostrarConsulta();
                }
            }
        });
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Formulario().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void mostrarConsulta() {
        dtm.setRowCount(0); // reinicia el DefaultTableModel
        String sql = "";
        try {

            if (jtDivision.getText().equals("") && jcTemporadas.getSelectedItem().equals("")) {
                sql = " Select `CodEq`,`Equipo`,`Ciudad`,`Estadio`,`NomQuinLFP` from EQUIPOS"
                        + " WHERE Equipo Like '%" + txtEquipo.getText() + "%'";
            } else if (!jtDivision.getText().equals("") && jcTemporadas.getSelectedItem().equals("")) {
                sql = "SELECT Distinct equipos.*, EqsTempo.Division " +
                        "FROM equipos INNER JOIN EqsTempo ON equipos.CodEq = EqsTempo.CodEq " +
                        "WHERE EqsTempo.Division=" + jtDivision.getText() + " AND " +
                        "Equipo Like '%" + txtEquipo.getText() + "%'";
            } else if (jtDivision.getText().equals("") && !jcTemporadas.getSelectedItem().equals("")) {
                sql = "SELECT Distinct equipos.*, EqsTempo.Division " +
                        "FROM equipos INNER JOIN EqsTempo ON equipos.CodEq = EqsTempo.CodEq " +
                        "WHERE EqsTempo.codTemp=" + jcTemporadas.getSelectedItem() + " AND " +
                        "Equipo Like '%" + txtEquipo.getText() + "%'";
            } else {
                sql = "SELECT Distinct equipos.*, EqsTempo.Division " +
                        "FROM equipos INNER JOIN EqsTempo ON equipos.CodEq = EqsTempo.CodEq " +
                        "WHERE EqsTempo.CodTemp=" + jcTemporadas.getSelectedItem() + " AND " +
                        "EqsTempo.Division=" + jtDivision.getText() + " AND " +
                        "Equipo Like '%" + txtEquipo.getText() + "%'";
            }

            ResultSet aux = bdf.consultaSelect(sql);
            while (aux.next()) {
                Object[] fila = {aux.getObject(1), aux.getObject(2), aux.getObject(3),
                        aux.getObject(4), aux.getObject(5)};
                dtm.addRow(fila);
            }
            jtb_Equipos.setModel(dtm);

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("error en acceso sql " + e);
        }
    }
}


    
    
        
      
      
    

   
                
   
     
       
   
       
      