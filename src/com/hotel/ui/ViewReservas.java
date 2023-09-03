package com.hotel.ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.hotel.controller.ReservasController;
import com.hotel.model.ReservaModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewReservas extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTable reservasTable;
	private DefaultTableModel model;
	private ReservasController controller = new ReservasController();
	private int Width = 790;
	
	public ViewReservas() {
		setBackground(SystemColor.control);
		setLayout(null);
		setBounds(0,0, 790, 570);
		
		model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("F. Entrada");
        model.addColumn("F. Salida");
        model.addColumn("IMPORTE");
        model.addColumn("TIPO PAGO");
        reservasTable = new JTable(model);
        reservasTable.setBounds(40, 55, 710, 456);
        reservasTable.setDefaultEditor(Object.class, null);

		JLabel lbTitulo = new JLabel("RESERVACIONES");
		lbTitulo.setForeground(new Color(0, 11, 21));
		lbTitulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lbTitulo.setBounds(40, 15, 235, 25);
			
		JButton bSearch = new JButton("Buscar");
		JTextField idSearch = new JTextField();
		bSearch.setBounds(Width-130, 20, 89, 20);
		idSearch.setBounds(Width-240, 20, 100, 20);
		bSearch.setBackground(new Color(32, 63, 104));
		bSearch.setForeground(new Color(255, 255, 255));
		bSearch.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		bSearch.setFocusable(false);
		bSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarEnTabla(idSearch.getText());
			}
		});
		
		JButton bEdit = new JButton("Editar");
		bEdit.setForeground(new Color(255, 255, 255));
		bEdit.setBackground(new Color(0, 128, 64));
		bEdit.setBounds(520, 522, 111, 37);
		bEdit.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		bEdit.setFocusable(false);
		bEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = reservasTable.getSelectedRow();
				
				int id = (int) model.getValueAt(selectedRow, 0);
				String entrada = model.getValueAt(selectedRow, 1).toString();
				String salida = model.getValueAt(selectedRow, 2).toString();
				String pago = model.getValueAt(selectedRow, 4).toString();

				double amountDouble = Double.parseDouble(model.getValueAt(selectedRow, 3).toString());
				int amount = (int) Math.round(amountDouble);
				
				UpdateTable tableUpdate = () -> { updateTable(); };
				
				ReservaModel dataModel = new ReservaModel(entrada, salida, amount, pago);
				EditReserva jfReserva = new EditReserva(id, dataModel, tableUpdate);
				jfReserva.setVisible(true);
			}
		});
		
		
		JButton bDeleted = new JButton("Eliminar");
		bDeleted.setBackground(new Color(128, 0, 0));
		bDeleted.setForeground(SystemColor.text);
		bDeleted.setBounds(641, 522, 108, 37);
		bDeleted.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		bDeleted.setFocusable(false);
		bDeleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = reservasTable.getSelectedRow();
				int id = (int) model.getValueAt(selectedRow, 0);
				
				if(controller.eliminarReserva(id)) {
					updateTable();
				}
			}
		});
		
		add(bSearch);
		add(idSearch);
		add(lbTitulo);
		add(reservasTable);
		add(bEdit);
		add(bDeleted);
	}
	
	private void buscarEnTabla(String id) {
		DefaultTableModel model = (DefaultTableModel) reservasTable.getModel();
        int rowCount = model.getRowCount();
        boolean isExist = false;
        
        for (int row = 0; row < rowCount; row++) {
            String idEnTabla = model.getValueAt(row, 0).toString();
            if (id.equals(idEnTabla)) {
            	reservasTable.setRowSelectionInterval(row, row);
            	isExist = true;
                break; 
            }
        }
        
        if(!isExist) {
        	JOptionPane.showInternalMessageDialog(null, "Sin resultados!");
        }
	}
	
	public void updateTable() {
		model.setRowCount(0);
		controller.fillTable(model);
	}
}
