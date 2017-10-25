
import java.awt.Color;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class intersectionModifyFrame extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private JTextField nameText;
	private JTextField coordinatesText;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					intersectionModifyFrame frame = new intersectionModifyFrame();
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
	public intersectionModifyFrame(Point p) {

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
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setForeground(Color.ORANGE);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Cancel is PRESSED
			}
		});
		cancelButton.setBounds(48, 227, 117, 29);
		contentPane.add(cancelButton);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setForeground(new Color(100, 149, 237));
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Acept is PRESSED
				
				
			}
		});
		acceptButton.setBounds(298, 227, 117, 29);
		contentPane.add(acceptButton);
		
		JLabel genLabel1 = new JLabel("¿Modificar intersección?");
		genLabel1.setBounds(153, 198, 159, 16);
		contentPane.add(genLabel1);
		
		coordinatesText = new JTextField();
		coordinatesText.setBounds(264, 121, 130, 26);
		contentPane.add(coordinatesText);
		coordinatesText.setColumns(10);
		
		JButton deleteBtn = new JButton("Eliminar");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELETE is PRESSED
				
				
				
			}
		});
		deleteBtn.setForeground(Color.RED);
		deleteBtn.setBounds(177, 227, 117, 29);
		contentPane.add(deleteBtn);
		
		JButton btnGirosPermitidos = new JButton("Giros Permitidos");
		btnGirosPermitidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGirosPermitidos.setBounds(153, 157, 159, 29);
		contentPane.add(btnGirosPermitidos);
	}
}
