import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;
import rsc.Intersection;
import rsc.Segment;
import rsc.Step;

/**
 * Documentación siguiendeo la estructura explicada en https://en.wikipedia.org/wiki/Javadoc
 * @author Alex Beltran (sist@uji.es) y David Albalate (dalbalat@uji.es)
 * @version 1.0
 * */
public class GUI3 {
	/**
	 * Modo inicial del programa
	 * */
	String mode = "intersection"; // intersection - segment 
	
	//Components
	/**Messaging*/
	String message = new String();
	/**Background Map*/
	BufferedImage mapImage;
	BufferedImage mapImageINI;
	/**Panel containing the Map*/
	mapPanel map = new mapPanel();
	/**Frame containing the app*/
	JFrame mapFrame = new JFrame();
	/** App MenuBar*/
	JMenuBar menuBar = new JMenuBar();
	/** A menu contained in the MenuBar for App options*/
	menu menu = new menu();
	/** Menu that determines the MODE in which we operate */
	modeMenu modeMenu = new modeMenu();
	/** Global variables*/
	/**Area in which we consider we are clicking a user created object.*/
	int xMargin = 15;
	int yMargin = 15;
	/**IDs int used in step automating naming*/
	int stepIdCounter = 00;
	int stepTwinIdCounter = 01;

	/**
	 * Lista de intersecciones generales del sistema
	 * */
	ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	/**
	 * Lista de segmentos generales del sistema
	 * */
	ArrayList<Segment> segments = new ArrayList<Segment>();
	/**
	 * Lista de steps generales del sistema. Entendiendo step como parte de un segmento
	 * con una dirección fija permitiendo así crear segmentos no rectos
	 * */
	ArrayList<Step> steps = new ArrayList<Step>();

	/**
	 * Método principal del programa
	 * <p>
	 *     Este método carga el GUI que a su vez carga las
	 *     ventanas y demás elementos necesarios para la
	 *     ejecución del proyecto
	 * </p>
	 * */
	public static void main(String[] srgs) {
		new GUI3();
	}

	/**
	 * Ejecuta el método init de la clase interna init utilizando un hilo
	 * */
	public GUI3() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new init();
			}

		});
	}

	public class init {

		public init() {
			
			menuBar.add(menu);
			menuBar.add(modeMenu);
			mapFrame.setJMenuBar(menuBar);
			mapFrame.add(map);
			

			//mapFrame.setSize(600, 400);
			mapFrame.setLocationRelativeTo(null);
			mapFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			mapFrame.setVisible(true);
			mapFrame.pack();
			
			InfoFrame infoFrame = new InfoFrame();
			infoFrame.setVisible(true);
			
			Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
			for (Path name: dirs) {
			    System.out.println(name);
			}
		}
	}

	public class mapPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		boolean activeSegment = false;		// Stores whether a segment has been initiated or not
		Segment seg = new Segment();			// Empty segment to store data until we save it on our array
		Step step = new Step();				// Empty step to store data until we save it on our array
		
		public mapPanel() {
			this.setPreferredSize(new Dimension(600, 400));
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (mapImage != null) {

						
						boolean exists = false; 									// Stores if clicked point is already a stored intersection
						Point p = e.getPoint();									// Point that has been clicked (X,Y)	
						Intersection clickedIntersection = new Intersection();	// Intersection that has been clicked (in case exists=true)

						
						// First we check if there is an intersection in a defined radius of the clicked point
						for (Intersection i : intersections) {
							if (Math.abs((int) p.getX() - i.getX()) < xMargin
									&& Math.abs((int) p.getY() - i.getY()) < yMargin) {
								exists = true;
								clickedIntersection = i;
								break;
							}
						}
						
						// Different options depending on the mode we have set
						switch (mode) {
						case "intersection":
							
							// There is an intersection in the clicked point
							if (exists) {
								
								// Modify intersection interface
								intersectionModifyFrame popUP = new intersectionModifyFrame(clickedIntersection.getId());
								popUP.setVisible(true);
							
							// There is NOT an intersection in the clicked point
							} else {
								
								// Create intersection interface
								intersectionFrame popUP = new intersectionFrame(p);
								popUP.setVisible(true);
								repaint();
							}
							break;
							
						case "segment":
							
							// There is not an initialized segment
							if (!activeSegment) {
								// Segment not initialized and no intersection selected
								if (!exists) {
									// Dialog info about how to use the application
									JOptionPane.showMessageDialog(null,
											"Haz clic de una Intersección para iniciar un segmento.");
								}
								// Segment not initialized but intersection selected
								else {

									// Initializing segment with PROVISIONAL ID and origin coordinates
									seg.setOrigin(clickedIntersection.getId());
									seg.setId("activeSegment");
									
									// Segment initialized
									activeSegment = true;

									//-------------------------------------------
									System.out.println(seg.toString());
									//-------------------------------------------
									
									// Setting origin coordinates,segment and name for the new step
									step.setId("ST-" + stepIdCounter);
									stepIdCounter += 2;
									step.setOriginX(clickedIntersection.getX());
									step.setOriginY(clickedIntersection.getY());
									step.setSegment(seg.getId());

									JOptionPane.showMessageDialog(null,
											"Iniciado Segmento en la intersección: " + clickedIntersection.getId());

								}
							}
							// There is an initialized segment
							else {
								if (!exists) {

									// Finalize ongoing step (and add it to the list) --> begin another step with
									// dest form previous step
									//paint(p, Color.GREEN);

									// Finalize ongoing step and adding it to the steps (segment still has
									// provisional name)
									step.setDestinationX(p.x);
									step.setDestinationY(p.y);
									step.setSegment("activeSegment");
									steps.add(step);
									//System.out.println("Added step: "+step.toString());
									repaint();

									// Setting NEW step origin values as previous destination
									step = new Step();
									step.setOriginX(p.x);
									step.setOriginY(p.y);
									step.setId("ST-" + stepIdCounter);
									stepIdCounter += 2;

								} else {

									// Time to finalize segment
									activeSegment = false;

									// Finalize ongoing step and adding it to the steps (segment still has
									// provisional name)
									step.setDestinationX(clickedIntersection.getX());
									step.setDestinationY(clickedIntersection.getY());
									step.setSegment("activeSegment");

									steps.add(step);
									System.out.println("Added step: "+step.toString());
									// Painting the step
									repaint();
									
									//Window to set the properties of the segment
									
									seg.setDestination(clickedIntersection.getId());
									
									AddSegmentFrame addSegment = new AddSegmentFrame(seg);
									addSegment.setVisible(true);
									
									System.out.println("Steps  --> " +steps);
									
									seg = new Segment();
									step = new Step();


								
								}
							}
						}
					}
				}
			});
		}


		public void paint(Point p,Color c) {
			Graphics2D g2d = mapImage.createGraphics();
			g2d.setColor(c);
			g2d.fillOval(p.x - 4, p.y - 4, 8, 8);
			g2d.dispose();
		}

		
		public void paint(Graphics gi) {
			
			Stroke step = new BasicStroke(2);
			
			Graphics2D g = (Graphics2D) gi;
			//Paint background
			g.drawImage(mapImage,0,0,this);
			//Paint the rest of the objects
			
			//Painting existing Intersections
			for(Intersection i: intersections) {
				g.setColor(Color.RED);
				g.fillOval(i.getX()-5, i.getY()-5, 10, 10);
				g.drawString(i.getId(),i.getX()-10, i.getY()-10);
				
			}
			
			//Painting existing Segments (steps)
			for(Step st: steps) {
				g.setColor(Color.GREEN);
				g.setStroke(step);
				Segment seg = null;
				for(Segment s: segments){
					if(s.getId().equals(st.getSegment()))
						seg = s;
				}
				if(seg != null && seg.getDirection().equals("down")){
					g.setColor(Color.ORANGE);
					g.drawLine(st.getOriginX()-4, st.getOriginY(), st.getDestinationX()-4, st.getDestinationY());
					g.drawString(st.getSegment(),st.getDestinationX()-13, st.getDestinationY()-13);
				} else {
					g.setColor(Color.GREEN);
					g.drawLine(st.getOriginX(), st.getOriginY(), st.getDestinationX(), st.getDestinationY());
					g.drawString(st.getSegment(),st.getDestinationX()+13, st.getDestinationY()+13);
				}
			}
		}	
	}

	/**
	 * Provides the user with a GUI to select a file
	 * @return BufferedImage Image which we have selected
	 */
	private BufferedImage getFile() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Escoge un mapa...");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
		fc.addChoosableFileFilter(filter);

		BufferedImage origImage = null;
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			try {
				origImage = ImageIO.read(selectedFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * If the user doesn't select any image (cancels the choice) the program
		 * terminates automatically.
		 */
		if (origImage == null)
			System.exit(0);
		return origImage;
	}

	public class menu extends JMenu{
		
		private static final long serialVersionUID = 1L;
		
		JMenuItem newFile;
		JMenuItem open;
		JMenuItem save ;
		JMenuItem export ;
		JMenuItem importar ;
		JMenuItem exit;

		
		public menu() {

			
			//Building the menu
			setText("Menu");
			
			
			newFile = new JMenuItem("Nuevo");
			open = new JMenuItem("Abrir");
			save = new JMenuItem("Guardar");
			importar = new JMenuItem("Importar");
			export = new JMenuItem("Exportar");
			exit = new JMenuItem("Salir");
			
			//Listener and action taken when NEW FILE is pressed
			newFile.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			          mapImage = getFile();
			          map.repaint();
			          mapFrame.setSize(new Dimension(mapImage.getWidth(),mapImage.getHeight()));
			        }
			});
			
			//Listener and action taken when EXIT is pressed
			exit.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			          System.exit(0);
			        }
			      });

			importar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					importValues();
				}
			});
			
			export.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			          
			          export();
 
			        }
			      });
			save.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			          
			    	  //Serialize object GUI3
			    	  		
			          
			          
			        }
			      });
			
			open.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			    	  	//FileChooser to browse the directory to load

			    	  	importValues();
			    	  
			        }
			      });
			
			add(newFile);
			add(open);
			add(save);
			add(importar);
			add(export);
			addSeparator();
			add(exit);
		}
		
		
		public Dimension getImageSize() {
			return mapImage == null ? new Dimension(600, 600) : new Dimension(mapImage.getWidth(), mapImage.getHeight());
		}
	}
	
	public class modeMenu extends JMenu{
		
		private static final long serialVersionUID = 1L;
		
		JCheckBoxMenuItem intersection;
		JCheckBoxMenuItem segment;

		
		public modeMenu() {

			
			//Building the menu
			setText("Modo");
			
			
			intersection = new JCheckBoxMenuItem("Intersección");
			segment = new JCheckBoxMenuItem("Segmento");
			intersection.setSelected(true);

			
			//Listener and action taken when NEW FILE is pressed
			intersection.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			          mode = "intersection";
			          segment.setSelected(false);
			        }
			});
			
			//Listener and action taken when EXIT is pressed
			segment.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent ev) {
			    	  	mode = "segment";
			    	  	intersection.setSelected(false);
			        }
			      });
			
			
			add(intersection);
			add(segment);


		}
		
	}
	
	public class intersectionFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField nameText;


		/**
		 * Create the frame.
		 */
		public intersectionFrame(Point p) {
			
			setAlwaysOnTop(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel titleLabel = new JLabel("Nueva Intersección");
			titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
			titleLabel.setBounds(139, 18, 250, 26);
			contentPane.add(titleLabel);
			
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
			intersectionXY.setText("X:"+p.getX() + " Y:"+p.getY());
			intersectionXY.setBounds(264, 126, 109, 16);
			contentPane.add(intersectionXY);
			
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setForeground(new Color(255, 0, 0));
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setBounds(77, 227, 117, 29);
			contentPane.add(cancelButton);
			
			JButton acceptButton = new JButton("Aceptar");
			acceptButton.setForeground(new Color(100, 149, 237));
			acceptButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String id = nameText.getText();
					int x = (int) p.getX();
					int y = (int) p.getY();
					Intersection intersec = new Intersection(id,x,y);
					
					intersections.add(intersec);
					System.out.println(intersections);
					map.repaint();
					dispose();
					
				}
			});
			acceptButton.setBounds(264, 227, 117, 29);
			contentPane.add(acceptButton);
			
			JLabel genLabel1 = new JLabel("¿Añadir intersección a la lista?");
			genLabel1.setBounds(130, 199, 203, 16);
			contentPane.add(genLabel1);
		}
	}
	
	public class intersectionModifyFrame extends JFrame {

		private JPanel contentPane;
		private static final long serialVersionUID = 1L;
		private JTextField nameText;
		private JTextField coordinatesText;
		private Intersection oldInter;

		/**
		 * Create the frame.
		 */
		public intersectionModifyFrame(String id) {
			
			
			Point p=new Point();
			
			for(Intersection i : intersections) {
				if(i.getId().equals(id)) {
					oldInter = i;

					p = new Point(oldInter.getX(),oldInter.getY());
				}
			}
			
			setAlwaysOnTop(true);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel titleLabel = new JLabel("Modificar Intersección");
			titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
			titleLabel.setBounds(139, 18, 250, 26);
			contentPane.add(titleLabel);
			
			JLabel lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(80, 70, 61, 16);
			contentPane.add(lblNombre);
			
			JLabel lblNewLabel = new JLabel("Coordenadas:");
			lblNewLabel.setBounds(77, 126, 98, 16);
			contentPane.add(lblNewLabel);
			
			nameText = new JTextField(id);
			nameText.setBounds(264, 65, 130, 26);
			contentPane.add(nameText);
			nameText.setColumns(10);
			
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setForeground(Color.ORANGE);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Cancel is PRESSED
					//NO actions taken
					dispose();
				}
			});
			cancelButton.setBounds(48, 227, 117, 29);
			contentPane.add(cancelButton);
			
			JButton acceptButton = new JButton("Aceptar");
			acceptButton.setForeground(new Color(100, 149, 237));
			acceptButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//Accept is PRESSED
					//Change preceding values for new ones.
					
					
					
					Point newPoint = new Point();
					String newId = nameText.getText();
					String pointStr = coordinatesText.getText();
					String[] strP = pointStr.split("Y");
					
					newPoint.setLocation(Integer.parseInt(strP[0].replaceAll("[\\D]", "")), Integer.parseInt(strP[1].replaceAll("[\\D]", "")));
					
					
					//Copying data from INPUTS/OUTPUTS in case we edit intersection after we have finished segment introduction
					Intersection newInter = new Intersection(newId,oldInter.getInSegments(),oldInter.getOutSegments(), oldInter.getProhibitions(),newPoint.x,newPoint.y);
					
					
					intersections.add(newInter);
					intersections.remove(oldInter);

					System.out.println(intersections.toString());
					map.repaint();
					dispose();
					
				}
			});
			acceptButton.setBounds(298, 227, 117, 29);
			contentPane.add(acceptButton);
			
			JLabel genLabel1 = new JLabel("¿Modificar intersección?");
			genLabel1.setBounds(153, 198, 159, 16);
			contentPane.add(genLabel1);
			
			coordinatesText = new JTextField("X:"+(int) p.getX()+" Y:"+(int) p.getY());
			coordinatesText.setBounds(264, 121, 130, 26);
			contentPane.add(coordinatesText);
			coordinatesText.setColumns(10);
			
			JButton deleteBtn = new JButton("Eliminar");
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//DELETE is PRESSED
					intersections.remove(oldInter);
					map.repaint();
					dispose();
				}
			});
			deleteBtn.setForeground(Color.RED);
			deleteBtn.setBounds(177, 227, 117, 29);
			contentPane.add(deleteBtn);
			
			JButton btnGirosPermitidos = new JButton("Giros Permitidos");
			btnGirosPermitidos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Show frame containing Input-Output for the intersection
					//HashMap<String, ArrayList<String>> prohibitions = oldInter.getProihibitions();
					addProhibitionsFrame prohibitionsFrame = new addProhibitionsFrame(oldInter.getId());
					prohibitionsFrame.setVisible(true);
					
				}
			});
			btnGirosPermitidos.setBounds(153, 157, 159, 29);
			//If there are no SEGMENTS we can't introduce prohibitions
			if(segments.size()==0)
				btnGirosPermitidos.setEnabled(false);
			contentPane.add(btnGirosPermitidos);
		}
	}
	
	public class InfoFrame extends JFrame {

	
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;

		/**
		 * Create the frame.
		 */
		public InfoFrame() {
			setBounds(100, 100, 579, 459);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPane.add(tabbedPane, BorderLayout.NORTH);
			
			JPanel IntersectionPanel = new JPanel();
			tabbedPane.addTab("Intersecciones", null, IntersectionPanel, null);
			
			JLabel IntersectionList = new JLabel();
			IntersectionPanel.add(IntersectionList);
			
			JPanel SegmentPanel = new JPanel();
			tabbedPane.addTab("Segmentos", null, SegmentPanel, null);
			
			JLabel SegmentList = new JLabel();
			SegmentPanel.add(SegmentList);
		
			JPanel StepPanel = new JPanel();
			tabbedPane.addTab("Steps", null, StepPanel, null);
			
			JLabel StepList = new JLabel();
			StepPanel.add(StepList);
			
			JButton btnNewButton = new JButton("Refresh");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					IntersectionList.setText(getIntersectionString(intersections));
					SegmentList.setText(getSegmentString(segments));
					StepList.setText(getStepString(steps));;
				}
			});
			contentPane.add(btnNewButton, BorderLayout.SOUTH);
		}

	}
	
	public class addProhibitionsFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		
		private Intersection intersection;
		private ArrayList<Segment> inputs;
		private ArrayList<Segment> outputs;
		private JPanel gridPanel;
		private JCheckBox box;
		private JCheckBox boxMatrix[][];
		

		public addProhibitionsFrame(String intersectionID){

			gridPanel = new JPanel();
			
			//Get the intersection we are working on
			for(Intersection i:intersections) {
				if(i.getId().equals(intersectionID)) {
					intersection=i;
					break;
				}
			}
			
			inputs = intersection.getInSegments();
			outputs = intersection.getOutSegments();
			String [] rows = new String[inputs.size()];
			String [] cols = new String[outputs.size()];
			System.out.println("Adding prohibitions to Intersection: " + intersection.getId());
			

			for(int i=0; i< inputs.size(); i++) {
				//Fill elements relative to the INputs
				rows[i] = inputs.get(i).getId();
			}	
			for(int i=0; i< outputs.size(); i++) {
				//Fill elements relative to the OUTputs
				cols[i] = outputs.get(i).getId(); 
			}
			
			boxMatrix = new JCheckBox[rows.length][cols.length];
			
			System.out.println("Rows: ");
			for(String r:rows)
				System.out.println(r);
			
			System.out.println("Cols: ");
			for(String c:cols)
				System.out.println(c);
			//Setting the Layout for the checkBox GRID
			gridPanel.setLayout(new GridLayout(rows.length+1,cols.length+1));
			
			//Building The INDEXES (Columns added, rows must be added at the beginning of each row)
			gridPanel.add(new JLabel(""));
			for(String col:cols) {
				gridPanel.add(new JLabel(col));
			}
			
			//Setting prohibitions for the first time
			//Filling up the GRID
			for(int i=0;i<rows.length;i++) {
				gridPanel.add(new JLabel(rows[i])); //Adding input name before all outputs
				for(int j=0;j<cols.length;j++) {
					box = new JCheckBox();
					box.putClientProperty("input", rows[i]);
					box.putClientProperty("output", cols[j]);
					gridPanel.add(box);
					boxMatrix[i][j] = box;
				}
			}
		
			//If prohibitions have already been set
			if(intersection.getProhibitions().size()>0) {
				//Prohibitions were  previously added
				HashMap<String, ArrayList<String>> prohibitions = intersection.getProhibitions();
				System.out.println(prohibitions.toString());
				//Getting the prohibitions for each INPUT
				for(String input: prohibitions.keySet()) {
					ArrayList<String> prohibedOutputs = prohibitions.get(input);
					//Going through the inputs
					for(int i=0;i<rows.length;i++) {
						//If an input is found to have prohibitions
						if(input.equals(rows[i])) {
							for(int j=0;j<cols.length;j++) {
								if(prohibedOutputs.contains(cols[j])) {
									boxMatrix[i][j].setSelected(true);	//Checking the prohibited outputs
								}	
							}
						}
					}
				}
			}
			JLabel titleTextLbl = new JLabel("Marca los movimientos prohibidos para "+intersectionID);
			JButton accept = new JButton("Aceptar");
			accept.setForeground(Color.BLUE);

			
			accept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//FILL prohibitions attribute in the intersection
					
					//Going through all rows, each row represents an INPUT
					for(int i=0;i<rows.length;i++) {
						//Creating an List of Prohibitions to be inserted for each INPUT
						ArrayList<String> prohib = new ArrayList<String>();
						for(int j=0;j<cols.length;j++) {
							//If the move is prohibited, insert it in the list of prohibitions for the INPUT
							if(boxMatrix[i][j].isSelected())
								prohib.add((String) boxMatrix[i][j].getClientProperty("output"));
						}
						//Check if there's anything prohibited. If not, do not insert a blank list (input does not appear in prohibitions)
						if(prohib.size()>0)
							intersection.insertProhibition(rows[i], prohib);
					}

					//Close JFrame
					dispose();
				}
			});
			
			
			this.setLayout(new BorderLayout());
			this.setAlwaysOnTop(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.add(titleTextLbl, BorderLayout.NORTH);
			this.add(gridPanel,BorderLayout.CENTER);
			this.add(accept, BorderLayout.SOUTH);
			this.pack();
			this.setVisible(true);

		}
	}
	
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
		private JTextField segmentDirection;
		private JTextField segmentPkIniText;

		/**
		 * Create the frame.
		 */
		public AddSegmentFrame(Segment seg) {
			
			
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
			
			//Setting the origin from the passed segment
			intersectionOrigintextField.setText(seg.getOrigin());
			
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
			
			//Setting the destination from the passed segment
			intersectionDestinationtextField.setText(seg.getDestination());
			
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
			
			segmentDirection = new JTextField();
			segmentDirection.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_segmentDierection = new GridBagConstraints();
			gbc_segmentDierection.insets = new Insets(0, 0, 5, 5);
			gbc_segmentDierection.fill = GridBagConstraints.HORIZONTAL;
			gbc_segmentDierection.gridx = 3;
			gbc_segmentDierection.gridy = 13;
			contentPane.add(segmentDirection, gbc_segmentDierection);
			segmentDirection.setColumns(10);
			
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
					
					
					//Delete all steps added related to this segment ("activeSegment")
					ArrayList<Step> stepsToRemove = new ArrayList<Step>();
					for(Step s : steps) {
						if(s.getSegment().equals("activeSegment"))
							stepsToRemove.add(s);
					}
					for(Step s : stepsToRemove)
						steps.remove(s);
					
					
					
					//Close JFrame
					map.repaint();
					dispose();
					
					
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
					
					//Set all new Properties of the Segment 

					seg.setId(segmentNameText.getText());
					seg.setLength(segmentLenthText.getText());
					seg.setMaxSpeed(segmentMaxSpeedText.getText());
					seg.setCapacity(segmentCapacityText.getText());
					seg.setDensity(segmentDensityText.getText());
					seg.setNumberTracks(segmentNumTracksText.getText());
					seg.setDirection(segmentDirection.getText());
					seg.setPkIni(segmentPkIniText.getText());
					
					//Create Twin segment and its steps if necessary
					if(segmentTwinBtn.isSelected()) {
						
						seg.addTwinSegment(seg.getId()+"-T");
						
						Segment twin = new Segment();
						twin.setId(seg.getId()+"-T");
						twin.addTwinSegment(seg.getId());
						twin.setOrigin(seg.getDestination());
						twin.setDestination(seg.getOrigin());
						twin.setLength(seg.getLength());
						twin.setMaxSpeed(seg.getMaxSpeed());
						twin.setCapacity(seg.getCapacity());
						twin.setDensity(seg.getDensity());
						twin.setNumberTracks(seg.getNumberTracks());
						
						if(seg.getDirection().equals("up"))
							twin.setDirection("down");
						else twin.setDirection("up");
						
						float start = Float.parseFloat(seg.getPkIni());
						float length = Float.parseFloat(seg.getLength());
						String pkIniTwin = Float.toString(start+length);
						
						twin.setPkIni(pkIniTwin);
						
						//Include the the new segments in the segment list
						segments.add(seg);
						segments.add(twin);
						
						//Adding the segment to inputs/outputs of the respective intersections
						for(Intersection i:intersections) {
							if( i.getId().equals(seg.getOrigin())) {
								if(!i.getOutSegments().contains(seg))
									i.addOutSegment(seg);
							}
							if( i.getId().equals(seg.getDestination())) {
								if(!i.getInSegments().contains(seg))	
									i.addInSegment(seg);
							}
						}
						
						//Adding the segment to inputs/outputs of the respective intersections
						for(Intersection i:intersections) {
							if( i.getId().equals(twin.getOrigin())) {
								if(!i.getOutSegments().contains(twin))
									i.addOutSegment(twin);
							}
							if( i.getId().equals(twin.getDestination())) {
								if(!i.getInSegments().contains(twin))	
									i.addInSegment(twin);
							}
						}
						
						//Include the steps of the twin segment
						ArrayList<Step> twinSteps = new ArrayList<Step>();
						
						for(Step s : steps) {
							if(s.getSegment().equals("activeSegment")) {
								//Create new step to fill
								Step twinStep = new Step();
								
								//Set general twin step ID
								twinStep.setId("ST-"+stepTwinIdCounter);
								stepTwinIdCounter+=2;
								
								//Set inverse coordinates from its twin step
								twinStep.setOriginX(s.getDestinationX());
								twinStep.setOriginY(s.getDestinationY());
								twinStep.setDestinationX(s.getOriginX());
								twinStep.setDestinationY(s.getOriginY());
								
								//Set its segment (twin segment) and adding the step
								twinStep.setSegment(twin.getId());
								twinSteps.add(twinStep);
								
								
							}
						}
						/*TODO: Aquí se hace el reverso del twin. Hay que asegurarse de que esto funciona*/
						Collections.reverse(twinSteps);
						for(Step t : twinSteps) {
							steps.add(t);
						}
						
					}else {
						
						//Include the the new segment in the segment list
						segments.add(seg);

						//Adding the segment to inputs/outputs of the respective intersections
						for(Intersection i:intersections) {
							if( i.getId().equals(seg.getOrigin())) {
								if(!i.getOutSegments().contains(seg))
									i.addOutSegment(seg);
							}
							if( i.getId().equals(seg.getDestination())) {
								if(!i.getInSegments().contains(seg))	
									i.addInSegment(seg);
							}
						}
						
					}
					//Change the related steps in the Step list to add the correct name ( activeSegment -> RealName )
					for(Step s : steps) {
						if(s.getSegment().equals("activeSegment"))
							s.setSegment(seg.getId());
					}
					
					//Close JFrame
					map.repaint();
					dispose();
					
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
	

	/**
	 * Used in the infroFrame in order to display the contents of the Intersection Array
	 * @param intersections
	 * @return Formatted String 
	 */
	public String getIntersectionString(ArrayList<Intersection> intersections) {
		String res = "<html>";
		for(Intersection i : intersections)
			res += i.toString() + "<br>";
		res+="</html>";
		return res;
	}
	
	/**
	 * Used in the infroFrame in order to display the contents of the Segment Array
	 * @param segments
	 * @return Formatted String 
	 */
	public String getSegmentString(ArrayList<Segment> segments) {
		String res = "<html>";
		for(Segment s : segments)
			res += s.toString() + "<br>";
		res+="</html>";
		return res;
	}
	
	/**
	 * Used in the infroFrame in order to display the contents of the Step Array
	 * @param steps
	 * @return Formatted String 
	 */
	public String getStepString(ArrayList<Step> steps) {
		String res = "<html>";
		for(Step s : steps)
			res += s.toString() + "<br>";
		res+="</html>";
		return res;
	}
	
	
	/**
	 * Exports all introduced values into a folder choosed by the user, including the map
	 */
	public void export() {
		
		String directory;
		
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setDialogTitle("Escoge un directorio...");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    // disable the "All files" option.
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " 
	         +  chooser.getSelectedFile());
	      
	      directory = chooser.getSelectedFile().getAbsolutePath();
	      exportIntersections(directory);
		  exportSegments(directory);
		  exportSteps(directory);
		  exportProhibitions(directory);
		  
		  //Saving image
		  BufferedImage bi = mapImage;
		  File outputfile = new File(directory+"/ESCENARIO_SIN_NOMBRE.png");
		  try {
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
	      }
	    else {
	      System.out.println("No Selection ");
	      }
	}
	
	/**
	 * Imports all values contained in a folder choosed by the user, including the map
	 */
	public void importValues() {
		
		String directory;
		
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setDialogTitle("Escoge el mapa dentro del directorio a cargar...");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
		chooser.addChoosableFileFilter(filter);
	    // disable the "All files" option.
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " 
	         +  chooser.getSelectedFile());
	      
	      directory = chooser.getCurrentDirectory().getAbsolutePath();
	      importIntersections(directory);
		  importSegments(directory);
		  importSteps(directory);
		  
		  //Importing image
		  File selectedFile = chooser.getSelectedFile();
			try {
				mapImage = ImageIO.read(selectedFile);
				mapFrame.setSize(new Dimension(mapImage.getWidth(),mapImage.getHeight()));
				map.repaint();
			} catch (IOException ex) {
				ex.printStackTrace();
			} 
	      
	      }
	    else {
	      System.out.println("No Selection ");
	      }
	}
	
	
	public void exportProhibitions(String path) {
		File intersectionFile = new File(path+"/prohibitions.json");
		PrintWriter writer;
		try {
			writer = new PrintWriter(intersectionFile.getAbsolutePath());
			if(intersections.size()>0) {
				for(Intersection i:intersections) {
					writer.write(i.prohibitionstoJSon());
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void importProhibitions(String path){
		File prohibitionsFile = new File(path+"/prohibitions.json");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(prohibitionsFile));
			String line = reader.readLine();
			while (line != null){
				JSONObject objAux = new JSONObject(line);
				String intersectionId = objAux.getString("intersectionId");
				for(Intersection i: intersections){
					if(i.getId().compareTo(intersectionId) == 0){
						i.prohibitionsFromJSon(line);
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	   		
	public void exportIntersections(String path) {
		File intersectionFile = new File(path+"/intersections.json");
		PrintWriter writer;
		try {
			writer = new PrintWriter(intersectionFile.getAbsolutePath());
			if(intersections.size()>0) {
				for(Intersection i:intersections) {
					writer.write(i.toJSon()+"\n");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void importIntersections(String path) {
		File intersectionFile = new File(path+"/intersections.json");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(intersectionFile));
			String line = reader.readLine();
			while (line != null){
				Intersection inter = new Intersection();
				inter.fromJSon(line);
				intersections.add(inter);

				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportSteps(String path) {
		File stepsFile = new File(path+"/steps.json");
		PrintWriter writer;
		try {
			writer = new PrintWriter(stepsFile.getAbsolutePath());
			if(steps.size()>0) {
				for(Step s : steps) {
					writer.write(s.toJSon()+"\n");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void importSteps(String path) {
		File stepsFile = new File(path+"/steps.json");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(stepsFile));
			String line = reader.readLine();
			while (line != null){
				Step step = new Step();
				step.fromJSon(line);
				steps.add(step);

				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void exportSegments(String path) {
		File intersectionFile = new File(path+"/segments.json");
		PrintWriter writer;
		try {
			writer = new PrintWriter(intersectionFile.getAbsolutePath());
			if(intersections.size()>0) {
				for(Segment s: segments) {
					writer.write(s.toJSon()+"\n");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path - El path de la carpeta de donde cogera los segmentos
	 * */
	public void importSegments(String path) {
		File segmentsFile = new File(path+"/segments.json");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(segmentsFile));
			String line = reader.readLine();
			while (line != null){
				Segment segment = new Segment();
				segment.fromJSon(line);
				String intersectionOriginId = segment.getOrigin();
				String intersectionDestinationId = segment.getDestination();
				for(Intersection i: intersections){
					if(i.getId().equals(intersectionOriginId)){
						i.addOutSegment(segment);
					}
					if(i.getId().equals(intersectionDestinationId)){
						i.addInSegment(segment);
					}
				}
				segments.add(segment);

				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
