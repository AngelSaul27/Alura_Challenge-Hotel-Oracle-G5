package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hotel.utils.AccionesVentana;

import javax.swing.JLabel;
import java.awt.Font;

public class Dashboard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel jpWelcome;
	private JPanel jpRegisterRsv = new RegisterReserva();
	private JPanel jpViewRsv = new ViewReservas();
	private JButton onRegistrarReserva;
	private JButton onViewReservas;
	
	/**
	 * Create the frame.
	 */
	public Dashboard() {
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 950, 600);
		setIconImage(getIcon("/com/hotel/imagenes/logo.png", 80, 80).getImage());
		    			
		jpWelcome = createBodyPanel();
		
		getContentPane().add(createHeaderPanel(), BorderLayout.NORTH);
		getContentPane().add(jpWelcome, BorderLayout.CENTER);
		getContentPane().add(createSlideMenuPanel(), BorderLayout.WEST);
		
		AccionesVentana listener = new AccionesVentana(this);
		this.addMouseListener(listener.mouseListener);
		this.addMouseMotionListener(listener.mouseMotionListener);
	}
	
	private JPanel createSlideMenuPanel() {
		JPanel slidePanel = new JPanel();
		JLabel icon = new JLabel(getIcon("/com/hotel/imagenes/logo.png", 80, 80));
		
		icon.setBounds(30, 11, 102, 102);
		slidePanel.setBackground(new Color(32, 63, 104));
		slidePanel.setPreferredSize(new Dimension(160, getHeight()));
		slidePanel.setLayout(null);
		slidePanel.add(icon);
		
		onRegistrarReserva = new JButton("Registrar reservación");		
		onRegistrarReserva.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		onRegistrarReserva.setBackground(new Color(73, 77, 199));
		onRegistrarReserva.setBounds(0, 150, 160, 40);
		onRegistrarReserva.setForeground(new Color(255, 255, 255));
		onRegistrarReserva.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		onRegistrarReserva.setFocusable(false);
		onRegistrarReserva.addActionListener(listenerRegisterReserva);
		
		onViewReservas = new JButton("Lista de reservaciones");
		onViewReservas.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		onViewReservas.setBounds(0, 190, 160, 40);
		onViewReservas.setBackground(new Color(32, 63, 104));
		onViewReservas.setForeground(new Color(255, 255, 255));
		onViewReservas.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	    onViewReservas.setFocusable(false);
	    onViewReservas.addActionListener(listenerViewReserva);
	    
	    slidePanel.add(onRegistrarReserva);
		slidePanel.add(onViewReservas);
	
		return slidePanel;
	}
	
	ActionListener listenerRegisterReserva = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { 
        	if(!jpRegisterRsv.isVisible() || jpWelcome.isVisible()) {
        		onRegistrarReserva.setBackground(new Color(73, 77, 199));
            	onViewReservas.setBackground(new Color(32, 63, 104));
            	
            	jpWelcome.setVisible(false);
            	jpViewRsv.setVisible(false);
            	jpRegisterRsv.setVisible(true);
                        		
            	add(jpRegisterRsv, BorderLayout.CENTER);
        	}
        }
    };
    
    ActionListener listenerViewReserva = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { 
        	if(!jpViewRsv.isVisible() || jpWelcome.isVisible()) {
        		onViewReservas.setBackground(new Color(73, 77, 199));
            	onRegistrarReserva.setBackground(new Color(32, 63, 104));
            	            	
            	jpWelcome.setVisible(false);
            	jpRegisterRsv.setVisible(false);
            	jpViewRsv.setVisible(true);
            	
            	((ViewReservas) jpViewRsv).updateTable();
            	
            	add(jpViewRsv, BorderLayout.CENTER);
        	}
        }
    };
	
	
    private JPanel createBodyPanel() {
    	JPanel body = new JPanel();
    	body.setBackground(new Color(192, 192, 192));		
    	
    	return body;
	}
	
	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		JButton bClose = new JButton(getIcon("/com/hotel/imagenes/cross.png", 20, 20));
		
		headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		headerPanel.setBackground(Color.decode("#3f3f3f"));
		bClose.setBorder(null);
		bClose.setFocusable(false);
		bClose.setBackground(Color.decode("#3f3f3f"));
		bClose.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) { 
	        	Dashboard.this.dispose();
	        }
		});
		
		headerPanel.add(bClose);
		
		return headerPanel;
	}
	
	private ImageIcon getIcon(String URL, Integer scaleW, Integer scaleH) {	
        Image imagen = new ImageIcon(Dashboard.class.getResource(URL)).getImage();
        Image scaled = imagen.getScaledInstance(scaleW, scaleH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
