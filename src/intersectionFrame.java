


import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class intersectionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameText;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					intersectionFrame frame = new intersectionFrame(null); 
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public intersectionFrame(Point p) {
		
		setAlwaysOnTop(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(80, 70, 61, 16);
		contentPane.add(lblNombre);
		
		JLabel lblNewLabel = new JLabel("Coordenadas:");
		lblNewLabel.setBounds(77, 126, 98, 16);
		contentPane.add(lblNewLabel);
		
		nameText = new JTextField();
		nameText.setBounds(264, 65, 130, 26);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		JLabel intersectionXY = new JLabel("intersectionXY");
		intersectionXY.setText(p.toString());
		intersectionXY.setBounds(264, 126, 109, 16);
		contentPane.add(intersectionXY);
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setForeground(new Color(255, 0, 0));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Cancel is PRESSED
			}
		});
		cancelButton.setBounds(77, 227, 117, 29);
		contentPane.add(cancelButton);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setForeground(new Color(100, 149, 237));
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Acept is PRESSED
			}
		});
		acceptButton.setBounds(264, 227, 117, 29);
		contentPane.add(acceptButton);
		
		JLabel genLabel1 = new JLabel("¿Añadir intersección a la lista?");
		genLabel1.setBounds(130, 199, 203, 16);
		contentPane.add(genLabel1);
		
		JLabel titleLabel = new JLabel("Nueva Intersección");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		titleLabel.setBounds(139, 18, 170, 26);
		contentPane.add(titleLabel);
	}
}
