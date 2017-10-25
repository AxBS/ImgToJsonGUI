


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSegmentFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField segmentNameText;
	private JTextField intersectionOrigintextField;
	private JTextField intersectionDestinationtextField;
	private JTextField segmentLenthText;
	private JTextField segmentMaxSpeedText;
	private JTextField segmentCapacityText;
	private JTextField segmentDensityText;
	private JTextField segmentNumTracksText;
	private JTextField segmentDierection;
	private JTextField segmentPkIniText;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddSegmentFrame frame = new AddSegmentFrame();
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
	public AddSegmentFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel TitleLabel = new JLabel("Introduce los datos necesarios para la creación del segmento:");
		GridBagConstraints gbc_TitleLabel = new GridBagConstraints();
		gbc_TitleLabel.insets = new Insets(0, 0, 5, 0);
		gbc_TitleLabel.gridwidth = 5;
		gbc_TitleLabel.gridx = 1;
		gbc_TitleLabel.gridy = 0;
		contentPane.add(TitleLabel, gbc_TitleLabel);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		segmentNameText = new JTextField();
		segmentNameText.setHorizontalAlignment(SwingConstants.CENTER);
		segmentNameText.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_segmentNameText = new GridBagConstraints();
		gbc_segmentNameText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentNameText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentNameText.gridx = 3;
		gbc_segmentNameText.gridy = 2;
		contentPane.add(segmentNameText, gbc_segmentNameText);
		segmentNameText.setColumns(10);
		
		JLabel lblOrigen = new JLabel("Origen:");
		GridBagConstraints gbc_lblOrigen = new GridBagConstraints();
		gbc_lblOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrigen.gridx = 1;
		gbc_lblOrigen.gridy = 3;
		contentPane.add(lblOrigen, gbc_lblOrigen);
		
		intersectionOrigintextField = new JTextField();
		intersectionOrigintextField.setHorizontalAlignment(SwingConstants.CENTER);
		intersectionOrigintextField.setEditable(false);
		GridBagConstraints gbc_intersectionOrigintextField = new GridBagConstraints();
		gbc_intersectionOrigintextField.insets = new Insets(0, 0, 5, 5);
		gbc_intersectionOrigintextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_intersectionOrigintextField.gridx = 3;
		gbc_intersectionOrigintextField.gridy = 3;
		contentPane.add(intersectionOrigintextField, gbc_intersectionOrigintextField);
		intersectionOrigintextField.setColumns(10);
		
		JLabel lblDestino = new JLabel("Destino:");
		GridBagConstraints gbc_lblDestino = new GridBagConstraints();
		gbc_lblDestino.insets = new Insets(0, 0, 5, 5);
		gbc_lblDestino.gridx = 1;
		gbc_lblDestino.gridy = 4;
		contentPane.add(lblDestino, gbc_lblDestino);
		
		intersectionDestinationtextField = new JTextField();
		intersectionDestinationtextField.setHorizontalAlignment(SwingConstants.CENTER);
		intersectionDestinationtextField.setEditable(false);
		GridBagConstraints gbc_intersectionDestinationtextField = new GridBagConstraints();
		gbc_intersectionDestinationtextField.insets = new Insets(0, 0, 5, 5);
		gbc_intersectionDestinationtextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_intersectionDestinationtextField.gridx = 3;
		gbc_intersectionDestinationtextField.gridy = 4;
		contentPane.add(intersectionDestinationtextField, gbc_intersectionDestinationtextField);
		intersectionDestinationtextField.setColumns(10);
		
		JLabel lblLogitud = new JLabel("Logitud:");
		GridBagConstraints gbc_lblLogitud = new GridBagConstraints();
		gbc_lblLogitud.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogitud.gridx = 1;
		gbc_lblLogitud.gridy = 6;
		contentPane.add(lblLogitud, gbc_lblLogitud);
		
		segmentLenthText = new JTextField();
		segmentLenthText.setHorizontalAlignment(SwingConstants.CENTER);
		segmentLenthText.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_segmentLenthText = new GridBagConstraints();
		gbc_segmentLenthText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentLenthText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentLenthText.gridx = 3;
		gbc_segmentLenthText.gridy = 6;
		contentPane.add(segmentLenthText, gbc_segmentLenthText);
		segmentLenthText.setColumns(10);
		
		JLabel lblVmx = new JLabel("V.Máx:");
		GridBagConstraints gbc_lblVmx = new GridBagConstraints();
		gbc_lblVmx.insets = new Insets(0, 0, 5, 5);
		gbc_lblVmx.gridx = 1;
		gbc_lblVmx.gridy = 7;
		contentPane.add(lblVmx, gbc_lblVmx);
		
		segmentMaxSpeedText = new JTextField();
		segmentMaxSpeedText.setForeground(Color.LIGHT_GRAY);
		segmentMaxSpeedText.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentMaxSpeedText = new GridBagConstraints();
		gbc_segmentMaxSpeedText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentMaxSpeedText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentMaxSpeedText.gridx = 3;
		gbc_segmentMaxSpeedText.gridy = 7;
		contentPane.add(segmentMaxSpeedText, gbc_segmentMaxSpeedText);
		segmentMaxSpeedText.setColumns(10);
		
		JLabel lblCapacidad = new JLabel("Capacidad:");
		GridBagConstraints gbc_lblCapacidad = new GridBagConstraints();
		gbc_lblCapacidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCapacidad.gridx = 1;
		gbc_lblCapacidad.gridy = 8;
		contentPane.add(lblCapacidad, gbc_lblCapacidad);
		
		segmentCapacityText = new JTextField();
		segmentCapacityText.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentCapacityText = new GridBagConstraints();
		gbc_segmentCapacityText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentCapacityText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentCapacityText.gridx = 3;
		gbc_segmentCapacityText.gridy = 8;
		contentPane.add(segmentCapacityText, gbc_segmentCapacityText);
		segmentCapacityText.setColumns(10);
		
		JLabel lblDensidad = new JLabel("Densidad:");
		GridBagConstraints gbc_lblDensidad = new GridBagConstraints();
		gbc_lblDensidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblDensidad.gridx = 1;
		gbc_lblDensidad.gridy = 9;
		contentPane.add(lblDensidad, gbc_lblDensidad);
		
		segmentDensityText = new JTextField();
		segmentDensityText.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentDensityText = new GridBagConstraints();
		gbc_segmentDensityText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentDensityText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentDensityText.gridx = 3;
		gbc_segmentDensityText.gridy = 9;
		contentPane.add(segmentDensityText, gbc_segmentDensityText);
		segmentDensityText.setColumns(10);
		
		JLabel lblNcarriles = new JLabel("N.Carriles:");
		GridBagConstraints gbc_lblNcarriles = new GridBagConstraints();
		gbc_lblNcarriles.insets = new Insets(0, 0, 5, 5);
		gbc_lblNcarriles.gridx = 1;
		gbc_lblNcarriles.gridy = 10;
		contentPane.add(lblNcarriles, gbc_lblNcarriles);
		
		segmentNumTracksText = new JTextField();
		segmentNumTracksText.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentNumTracksText = new GridBagConstraints();
		gbc_segmentNumTracksText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentNumTracksText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentNumTracksText.gridx = 3;
		gbc_segmentNumTracksText.gridy = 10;
		contentPane.add(segmentNumTracksText, gbc_segmentNumTracksText);
		segmentNumTracksText.setColumns(10);
		
		JLabel lblGemelo = new JLabel("Gemelo:");
		GridBagConstraints gbc_lblGemelo = new GridBagConstraints();
		gbc_lblGemelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblGemelo.gridx = 1;
		gbc_lblGemelo.gridy = 12;
		contentPane.add(lblGemelo, gbc_lblGemelo);
		
		JToggleButton segmentTwinBtn = new JToggleButton("Crear gemelo");
		GridBagConstraints gbc_segmentTwinBtn = new GridBagConstraints();
		gbc_segmentTwinBtn.insets = new Insets(0, 0, 5, 5);
		gbc_segmentTwinBtn.gridx = 3;
		gbc_segmentTwinBtn.gridy = 12;
		contentPane.add(segmentTwinBtn, gbc_segmentTwinBtn);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		GridBagConstraints gbc_lblDireccin = new GridBagConstraints();
		gbc_lblDireccin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccin.gridx = 1;
		gbc_lblDireccin.gridy = 13;
		contentPane.add(lblDireccin, gbc_lblDireccin);
		
		segmentDierection = new JTextField();
		segmentDierection.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentDierection = new GridBagConstraints();
		gbc_segmentDierection.insets = new Insets(0, 0, 5, 5);
		gbc_segmentDierection.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentDierection.gridx = 3;
		gbc_segmentDierection.gridy = 13;
		contentPane.add(segmentDierection, gbc_segmentDierection);
		segmentDierection.setColumns(10);
		
		JLabel lblPkInicio = new JLabel("PK Inicio:");
		GridBagConstraints gbc_lblPkInicio = new GridBagConstraints();
		gbc_lblPkInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPkInicio.gridx = 1;
		gbc_lblPkInicio.gridy = 14;
		contentPane.add(lblPkInicio, gbc_lblPkInicio);
		
		segmentPkIniText = new JTextField();
		segmentPkIniText.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_segmentPkIniText = new GridBagConstraints();
		gbc_segmentPkIniText.insets = new Insets(0, 0, 5, 5);
		gbc_segmentPkIniText.fill = GridBagConstraints.HORIZONTAL;
		gbc_segmentPkIniText.gridx = 3;
		gbc_segmentPkIniText.gridy = 14;
		contentPane.add(segmentPkIniText, gbc_segmentPkIniText);
		segmentPkIniText.setColumns(10);
		
		JButton cancelSegmentBtn = new JButton("Cancelar");
		cancelSegmentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cancelSegmentBtn.setForeground(Color.RED);
		GridBagConstraints gbc_cancelSegmentBtn = new GridBagConstraints();
		gbc_cancelSegmentBtn.insets = new Insets(0, 0, 5, 5);
		gbc_cancelSegmentBtn.gridx = 3;
		gbc_cancelSegmentBtn.gridy = 15;
		contentPane.add(cancelSegmentBtn, gbc_cancelSegmentBtn);
		
		JButton acceptSegmentBtn = new JButton("Aceptar");
		acceptSegmentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		acceptSegmentBtn.setForeground(Color.BLUE);
		GridBagConstraints gbc_acceptSegmentBtn = new GridBagConstraints();
		gbc_acceptSegmentBtn.insets = new Insets(0, 0, 5, 0);
		gbc_acceptSegmentBtn.gridx = 5;
		gbc_acceptSegmentBtn.gridy = 15;
		contentPane.add(acceptSegmentBtn, gbc_acceptSegmentBtn);
	}

}
