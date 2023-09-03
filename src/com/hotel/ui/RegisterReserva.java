package com.hotel.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.hotel.controller.ReservasController;
import com.hotel.model.ReservaModel;
import com.toedter.calendar.JDateChooser;

public class RegisterReserva extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fldAmount;
	private JDateChooser checkOut;
	private JDateChooser checkIn;
	
	private JComboBox<Object> boxTypePay;
	
	private String strCheckOut;
	private String strCheckIn;
	
	private int rateDay = 300;
	private int intAmount;
	private int Width = 790, Height = 570;
	
	public RegisterReserva() {
		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, Width, Height);
		
		JPanel form_panel = new JPanel(null);
		form_panel.setBackground(SystemColor.control);
		form_panel.setPreferredSize(new Dimension(250, Height));
		
		JLabel lbRegistroTitulo = new JLabel("Registro de Reservaciones");
		lbRegistroTitulo.setBounds(10, 30, 230, 30);
		lbRegistroTitulo.setFont(new Font("Bodoni MT Condensed", Font.BOLD, 25));
		form_panel.add(lbRegistroTitulo);
		
		JPanel slide_bg = new JPanel(null);
		JLabel bgLabel = new JLabel(getIcon("/com/hotel/imagenes/real_room_bg.jpg", Width, Height));
		bgLabel.setBounds(0,0, Width-250, Height);
		bgLabel.setPreferredSize(new Dimension(Width-250, Height));
		slide_bg.setPreferredSize(new Dimension(Width-250, Height));
		slide_bg.add(bgLabel);
		
		add(slide_bg, BorderLayout.CENTER);
		
		JLabel lbCheckIn = new JLabel("FECHA DE CHECK IN");
		JLabel lbCheckOut = new JLabel("FECHA DE CHECK OUT");
		lbCheckIn.setBounds(10, 73, 230, 14);
		lbCheckOut.setBounds(10, 139, 230, 14);
		
		JLabel lbAmount = new JLabel("IMPORTE DE LA RESERVA");
		JLabel lbTypePay = new JLabel("FORMA DE PAGO");
		lbAmount.setBounds(10, 201, 230, 14);
		lbTypePay.setBounds(10, 267, 218, 14);
		
		fldAmount = new JTextField();
		fldAmount.setBounds(10, 226, 218, 30);
		fldAmount.setColumns(10);
		fldAmount.setEditable(false);
		fldAmount.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		fldAmount.setForeground(new Color(51,51,51));
		fldAmount.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
		
		boxTypePay = new JComboBox<Object>();
		boxTypePay.addItem("Tarjeta de Credito");
		boxTypePay.addItem("Tarjeta de Debito");
		boxTypePay.addItem("Efectivo");
		boxTypePay.setBounds(10, 292, 218, 30);
		boxTypePay.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
		
		checkOut = new JDateChooser();
		checkIn = new JDateChooser();
		checkOut.setBounds(10, 164, 218, 30);
		checkOut.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
		checkIn.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
		checkIn.setBounds(10, 98, 218, 30);
		
		checkOut.addPropertyChangeListener("date", calcularImporte);
		checkIn.addPropertyChangeListener("date", calcularImporte);
		
		JButton bSaveRsv = new JButton("SIGUIENTE");
		bSaveRsv.setBounds(10, 348, 218, 38);
		bSaveRsv.setBackground(new Color(32, 63, 104));
		bSaveRsv.setForeground(new Color(255, 255, 255));
		bSaveRsv.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		bSaveRsv.setFocusable(false);
		bSaveRsv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarReserva();
			}
		});
		
		form_panel.add(lbCheckIn);
		form_panel.add(lbCheckOut);
		form_panel.add(lbAmount);
		form_panel.add(lbTypePay);
		form_panel.add(fldAmount);
		form_panel.add(bSaveRsv);
		form_panel.add(boxTypePay);
		form_panel.add(checkOut);
		form_panel.add(checkIn);
		
		add(form_panel, BorderLayout.WEST);
	}
	
	private void registrarReserva() {
		if(!checkDate()) { return; }
		String typepay = boxTypePay.getSelectedItem().toString();
		ReservaModel model = new ReservaModel(strCheckIn, strCheckOut, intAmount, typepay);
		Boolean result = new ReservasController().registerReserva(model);
		
		if(result) {
			JOptionPane.showInternalMessageDialog(null, "Operación exitosa!");
			fldAmount.setText("");
			boxTypePay.setSelectedItem(0);
			checkOut.setDate(null);
			checkIn.setDate(null);
			intAmount = 0;
		}
	}
	
	PropertyChangeListener calcularImporte = new PropertyChangeListener() {
	    @Override
	    public void propertyChange(PropertyChangeEvent evt) {
	        if (checkDate()) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            strCheckOut = sdf.format(checkOut.getDate());
	            strCheckIn = sdf.format(checkIn.getDate());

	            LocalDate fecha1 = checkIn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            LocalDate fecha2 = checkOut.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            int resultDays = (int) ChronoUnit.DAYS.between(fecha1, fecha2) + 1;

	            intAmount = resultDays * rateDay;
	            fldAmount.setText("$ "+intAmount+" MXN");
	        }
	    }
	};
	
	private Boolean checkDate() {
		if(checkOut.getDate() == null || checkIn.getDate() == null) {
			return false;
		}
		
		if (checkOut.getDate().before(checkIn.getDate())) {
            JOptionPane.showInternalMessageDialog(null, "La segunda fecha no puede ser menor que la primera.");
            return false;
        }
		
		return true;
	}
	
	private ImageIcon getIcon(String URL, Integer scaleW, Integer scaleH) {	
		Image imagen = new ImageIcon(RegisterReserva.class.getResource(URL)).getImage();
        Image scaled = imagen.getScaledInstance(scaleW, scaleH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
