package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.hotel.controller.LoginController;
import com.hotel.utils.AccionesVentana;

import java.awt.Font;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 780, 530);
		setIconImage(getIcon("/com/hotel/imagenes/logo.png", 80, 80).getImage());
		
		JPanel headerPanel = new JPanel();
		JButton bClose = new JButton(getIcon("/com/hotel/imagenes/cross.png", 20, 20));
		
		headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		headerPanel.setBackground(Color.decode("#3f3f3f"));
		
		bClose.setBorder(null);
		bClose.setFocusable(false);
		bClose.setBackground(Color.decode("#3f3f3f"));
		bClose.addActionListener(closeListener);
		
		headerPanel.add(bClose);
			
		JPanel bodyPanel = new JPanel();
		JPasswordField fldPassword = new JPasswordField("123456");//temporal
		JTextField fldEmail = new JTextField("admin@gmail.com");//temporal
		JLabel lbEmail = new JLabel("Correo electronico:");
		lbEmail.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		JLabel lbPassword = new JLabel("Contraseña: ");
		lbPassword.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		JLabel lbWelcomeBack = new JLabel("¡Bienvenido de Nuevo!");
		JButton submit = new JButton("Iniciar Sesión");
		submit.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		JLabel icSession = new JLabel(getIcon("/com/hotel/imagenes/hotel-2.png", 260, 270));

		bodyPanel.setLayout(null);
		bodyPanel.setPreferredSize(new Dimension(250, 530));
		
		lbWelcomeBack.setFont(new Font("Lucida Calligraphy", Font.BOLD, 18));
	
		fldEmail.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		fldEmail.setForeground(new Color(51,51,51));
        fldEmail.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
        
        fldPassword.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        fldPassword.setForeground(new Color(51,51,51));
        fldPassword.setBorder(new LineBorder(new Color(204, 204, 204), 1, true));
        
        submit.setBackground(new Color(32, 63, 104));
        submit.setForeground(new Color(255, 255, 255));
        submit.setText("Iniciar Sessión");
        submit.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        submit.setFocusable(false);
        
        submit.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) { 
        		LoginController login = new LoginController();
        		
        		String email = fldEmail.getText().trim();
        		@SuppressWarnings("deprecation")
				String password = fldPassword.getText().trim();
        		        		
        		if(password != "" && email != "") {
        			Boolean result = login.validarCredenciales(email, password);
        			
        			if(result) {
        				Dashboard frame = new Dashboard();
        				frame.setVisible(true);
        				setVisible(false);
        				dispose();
        			}else {
        				JOptionPane.showInternalMessageDialog(null, "Credenciales incorrectas!");
        			}
        			
        		}else {
        			JOptionPane.showInternalMessageDialog(null, "Complete todos los campos para continuar!");
        		}
 
        	}
        });
        
        lbEmail.setBounds(30, 297, 190, 30);
        fldEmail.setBounds(30, 329, 190, 30);
        lbPassword.setBounds(30, 368, 190, 30);
        fldPassword.setBounds(30, 397, 190, 30);
        icSession.setBounds(10, 11, 230, 239);
        submit.setBounds(30, 448, 190, 40);
        lbWelcomeBack.setBounds(10, 261, 230, 25);
		
		bodyPanel.add(lbWelcomeBack);
		bodyPanel.add(icSession);
        bodyPanel.add(lbEmail);
        bodyPanel.add(lbPassword);
        bodyPanel.add(fldEmail);
        bodyPanel.add(fldPassword);
        bodyPanel.add(submit);
	   
		JPanel centerPanel = new JPanel(null);
		JLabel icShape = new JLabel(getIcon("/com/hotel/imagenes/real_bg.jpg", 530, 519));
		
		centerPanel.setBackground(new Color(32, 63, 104));
		icShape.setBounds(0,0,530,519);
	
		centerPanel.add(icShape);
		
		getContentPane().add(bodyPanel, BorderLayout.EAST);	
		getContentPane().add(centerPanel, BorderLayout.CENTER);		
		getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		WindowController();
	}
	
	public void WindowController() {
		AccionesVentana listener = new AccionesVentana(this);
		this.addMouseListener(listener.mouseListener);
		this.addMouseMotionListener(listener.mouseMotionListener);
	}
	
	
	private ImageIcon getIcon(String URL, Integer scaleW, Integer scaleH) {	
		Image imagen = new ImageIcon(Login.class.getResource(URL)).getImage();
        Image scaled = imagen.getScaledInstance(scaleW, scaleH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
	
	ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { Login.this.dispose(); }
    };
}
