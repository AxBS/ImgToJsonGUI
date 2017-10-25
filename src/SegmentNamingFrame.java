
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SegmentNamingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField segmentNamingFrameText;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SegmentNamingFrame frame = new SegmentNamingFrame();
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
	public SegmentNamingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel SegmentNamingInfoLabel = new JLabel("\"\" ");
		SegmentNamingInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(SegmentNamingInfoLabel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Nombre del Segmento: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNewLabel, BorderLayout.WEST);
		
		segmentNamingFrameText = new JTextField();
		segmentNamingFrameText.setHorizontalAlignment(SwingConstants.CENTER);
		segmentNamingFrameText.setToolTipText("Introduce el nombre del segmento a iniciar.");
		contentPane.add(segmentNamingFrameText, BorderLayout.CENTER);
		segmentNamingFrameText.setColumns(5);
		
		JButton segmentNamingFrameAcceptBtn = new JButton("Aceptar");
		segmentNamingFrameAcceptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(segmentNamingFrameAcceptBtn, BorderLayout.SOUTH);
	}
}
