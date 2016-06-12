
/* This is the main class where the programming for creation of application window is written. An eclipse plugin called 
 * Application Window designer is used for this purpose. It provides with both Design and Source code view. Where one can easily select 
 * and drop the required components of the application window and perform actions on it by writing code in Source view.*/ 

package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Mars {
    
	static SecondJavaCC parser = null;
	
	public static StringBuffer buffer = new StringBuffer();// To add tokens into buffer as they are detected in MainGrammar.jj 
	public static StringBuffer buffer1 = new StringBuffer();
	public static ArrayList<String> line = new ArrayList<>();// To add the whole line from the buffer at the occurrence of new line
															 // in MainGrammar.jj to this ArrayList
	
	private JFrame frame;
	public String InputFile; // input file name will be saved here with .c extension
	public static File OutputFile;// File in which the converted code is to be saved
	public static String code; // Scilab code is saved in this string
	public static String outCode;
	public static int index;
	private JTextField txtBrowseYourScilab;// scilab file path will be stored here
	private JTextField txtBrowseDirectoryWhere;// file path of converted code is stored here
	
	private JPanel panelNull;// Panel where about the sotware in help is displayed 
	private JPanel panelMain;// Panel where selection of scilab file and directory is done 
	private JPanel panelOutput;// Panel where console is displayed, the converted code and the errors
	private JPanel panelRorNR;// Panel where selection of dsp kit and  real or non real time mode is to be done
	
	private ButtonGroup bg =new ButtonGroup();
    private JTextField txtInputVariable; // input variable for real time conversion
	private JTextField txtOutputVariable;// output variable for real time conversion
	private JLabel label;
	
	JFileChooser fileChooser= new JFileChooser();
	StringBuilder sb = new StringBuilder();
	public static JTextArea txtrOut;// text area at panelOutput (Console) where the converted code is stored

    
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mars window = new Mars();
					window.frame.setVisible(true);
					window.frame.setTitle("Scilab2CCS converter");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mars() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.setBounds(100, 100, 730, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu file = new JMenu ("File");
		menuBar.add(file);
		
		JMenuItem mntmConsole = new JMenuItem("Console");
		mntmConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelOutput.setVisible(true);
				panelMain.setVisible(false);
				panelRorNR.setVisible(false);
			}
		});
		file.add(mntmConsole);
		
		// Help
		JMenu help = new JMenu ("Help");
		menuBar.add(help);
		
		// About
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelNull.setVisible(true);
				panelMain.setVisible(false);
				panelRorNR.setVisible(false);
			}
		});
		help.add(about);
		
		

		panelRorNR = new JPanel();
		frame.getContentPane().add(panelRorNR, "name_60520985166603");
		
		//chckbxBrowseScilabFile.setVisible(false);
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("    Real-Time mode");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnNewRadioButton.setToolTipText("If input is to be taken from dsp kit and give processed output from dsp kit.");
		
		final JRadioButton rdbtnNonrealtimeMode = new JRadioButton("    Nonreal-Time mode");
		rdbtnNonrealtimeMode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNonrealtimeMode.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnNonrealtimeMode.setToolTipText("If no input is to be taken from dsp kit or give processed output from dsp kit.");
		
		
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNonrealtimeMode);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioButton.isSelected()){
					
					panelMain.add(txtInputVariable);
					panelMain.add(txtOutputVariable);
					txtInputVariable.setVisible(true);
					txtOutputVariable.setVisible(true);
					panelMain.setVisible(true);
					panelRorNR.setVisible(false);
					
				}
				
				if (rdbtnNonrealtimeMode.isSelected()){
				
					panelMain.remove(txtInputVariable);
					panelMain.remove(txtOutputVariable);
					panelMain.setVisible(true);
					panelRorNR.setVisible(false);
				}

			}
		});
		
//		************************************************************************************************* 		Combo Box
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println((String)comboBox.getSelectedItem());
			}
		});
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select the DSP Kit","TMS320 C5515", "TMS320 C5416"}));
		
		JLabel lblSelectTheProcessor = new JLabel("Select the DSP Kit for which the convertion is to be performed.");
//		lblSelectTheProcessor.
		lblSelectTheProcessor.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		
		GroupLayout gl_panelRorNR = new GroupLayout(panelRorNR);
		gl_panelRorNR.setHorizontalGroup(
			gl_panelRorNR.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelRorNR.createSequentialGroup()
					.addContainerGap(322, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addGap(306))
				.addGroup(gl_panelRorNR.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_panelRorNR.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectTheProcessor)
						.addGroup(gl_panelRorNR.createParallelGroup(Alignment.LEADING, false)
							.addComponent(comboBox, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panelRorNR.createSequentialGroup()
								.addComponent(rdbtnNewRadioButton, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
								.addGap(64)
								.addComponent(rdbtnNonrealtimeMode, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		gl_panelRorNR.setVerticalGroup(
			gl_panelRorNR.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRorNR.createSequentialGroup()
					.addGap(63)
					.addComponent(lblSelectTheProcessor)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(81)
					.addGroup(gl_panelRorNR.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnNewRadioButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnNonrealtimeMode, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(51))
		);
		
		panelRorNR.setLayout(gl_panelRorNR);
		
		
		// Main panel
		
		panelMain = new JPanel();
		frame.getContentPane().add(panelMain, "name_393388455414476");
		panelMain.setLayout(null);
		
		txtBrowseYourScilab = new JTextField();
		txtBrowseYourScilab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBrowseYourScilab.setText("Browse your scilab code");
		txtBrowseYourScilab.setBounds(12, 13, 566, 36);
		panelMain.add(txtBrowseYourScilab);
		txtBrowseYourScilab.setColumns(10);
		
		//******************************************************************************************      First Browse
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*File file =   new File("E:\\TestCode.sci");
				FileReader fileReader;
				try {
					fileReader = new FileReader(file);
				
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
				fileReader.close();
				txtBrowseYourScilab.setText(file.getPath());
				
				InputFile = file;
				code = stringBuffer.toString();
				} catch (IOException e) {
					
					e.printStackTrace();
				}*/
				if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					java.io.File file = fileChooser.getSelectedFile();
					
					try {
						
						FileReader fileReader = new FileReader(file); // Creating a file reader reading text in file
						BufferedReader bufferedReader = new BufferedReader(fileReader);
						StringBuffer stringBuffer = new StringBuffer(); // Buffer to store text in file
						String line;
						while ((line = bufferedReader.readLine()) != null) {// Reading file line by line and storing in buffer 
							stringBuffer.append(line);     // append line in buffer 
							stringBuffer.append("\n");     // append next line at end of line 
						}
						fileReader.close();
						txtBrowseYourScilab.setText(file.getAbsolutePath());// getting the full path of file selected
						
						String InputName = file.getName(); // get the name of file 
						String IN=InputName.replaceAll("(.*)(\\.sce|\\.sci)", "$1.c");// replace the scilab file extension to .c 
						InputFile = IN;												  // to save as output file name	
						code = stringBuffer.toString();// save the contents of buffer to String code
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					System.err.println("File not selected");
				}
			}
		});
		btnBrowse.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBrowse.setBounds(615, 12, 97, 36);
		panelMain.add(btnBrowse);
		
//********************************************************************************************************** 	Convert Code 
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Changing.InputVariable=Changing.InputVariable+txtInputVariable.getText()+"$";// storing values of input and output 
				Changing.InputVariable=Changing.InputVariable+txtOutputVariable.getText()+"$";// variable in case of real time
				Changing.InputOutputVariable=Changing.InputVariable+"&"; 
				String code1 = code.replaceAll("((?m)^[ \\t]*\\r?\\n)","");// clearing out all empty lines
				
				
				InputStream is = new ByteArrayInputStream(code1.getBytes());// sending the code to JavaCC for scanning and parsing
				 if(parser == null) parser = new SecondJavaCC(is);
	                else SecondJavaCC.ReInit(is);    
	                
	                try
	                {
	                  switch (SecondJavaCC.scilab())
	                  {
	                  	  case 0 :                  // code was read successfully by JavaCC (MainGrammar.jj)
	                  	  txtrOut.setText(null);  
	                  	  
	                  	  for (String lines : line)
	                  	  {
	                  		buffer1.append(lines+"\n");
	                  		String conCode =Changing.MainProgram; // saving the converted code to String conCode 
	                  		conCode=Changing.HeaderFile + "\n" +Changing.GlobleVariable + "\n" + Changing.Function + "\n" + Changing.Initializevariable + "\n" + conCode + "\n" + Changing.Ending;
	                  		
	                  		BufferedReader reader = new BufferedReader(new StringReader(conCode)); // so as to read the string conCode line by line
	                  		String output = "";
	            			int a=0;
	            			while((conCode=reader.readLine()) != null){ // to add appropriate spacing to the converted code 
	            	//			System.out.println(stringLine);
	            				/*Adding and removing appropriate amount of spaces at beginning of each line.
	            				 * a is incremented or decremented based on presence of 
	            				 * opening and closing brackets respectively. Spaces are added based on the value of a */
	            				//If } not {
	            				if(conCode.contains("}") && !conCode.contains("{")) {
	            					a--;
	            				}
	            				
	            				for(int i=0;i<a;i++) {
	            					output=output+"	";
	            					
	            				}
	            				
	            				//If { not }
	            				if(conCode.contains("{") && !conCode.contains("}")) {
	            					a++;
	            					
	            				}
	            				output=output+conCode+"\n";
	            			}
	                  		txtrOut.setText(output);
	                  		txtrOut.setForeground(Color.BLUE);
	                  		
	                  		if(OutputFile!=null){   // Saving the converted code into the OutputFile created
	                  		FileWriter fileWriter = new FileWriter(OutputFile);			
	            			fileWriter.write(txtrOut.getText());
	            			fileWriter.flush();
	            			fileWriter.close();
	                  		}
	            			label.setText("Converted code is saved at : " + txtBrowseDirectoryWhere.getText());
	      //            		System.out.println(lines);
	                  	  }
	                  	  
	                      panelOutput.setVisible(true);
	                      panelMain.setVisible(false);
	                      
	                      break;
	                      case 1 : 
	                      txtrOut.setText("Goodbye");
	                      panelOutput.setVisible(true);
	                      panelMain.setVisible(false);
	                      break;
	                      default : 
	                      break;
	                  }
	                }
	                catch (Exception e)
	                {
	                	txtrOut.setText("exception in expression.\n"+
	                		  				e.getMessage());
	                	txtrOut.setForeground(Color.RED);
	                	 panelOutput.setVisible(true);
	                      panelMain.setVisible(false);
	                }
	                catch (Error e)
	                {
	                	txtrOut.setText("error in expression.\n"+
	    		  						   e.getMessage());
	                	txtrOut.setForeground(Color.RED);
	                	 panelOutput.setVisible(true);
	                      panelMain.setVisible(false);
	                }	
	                

			}
		});
		btnConvert.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnConvert.setBounds(293, 296, 136, 47);
		panelMain.add(btnConvert);
		// ************************************************************************************************  END CONVERT CODE
		
		
		txtBrowseDirectoryWhere = new JTextField();
		txtBrowseDirectoryWhere.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBrowseDirectoryWhere.setText("Browse directory where .c file saves");
		txtBrowseDirectoryWhere.setColumns(10);
		txtBrowseDirectoryWhere.setBounds(12, 79, 566, 36);
		panelMain.add(txtBrowseDirectoryWhere);
		
		//**************************************************************************************************   Second Browse
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser(); // to select the directory where converted code is to be saved
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Select a Directory");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      File dirName = chooser.getSelectedFile();
			    
			  
			try{					// creating an output file with same file name as scilab input file with .c extension 
				
		        Writer output = null;
		        String fileName = InputFile;
		        File file = new File (dirName, fileName);
		        output = new BufferedWriter(new FileWriter(file));
		        output.close();
		        txtBrowseDirectoryWhere.setText(file.getAbsolutePath());
		        
		        OutputFile = file;
		        
				}catch(IOException e){
					e.printStackTrace();
				}
			    }
		    else {
			      System.err.println("No Selection ");
			    }
				
				
			
			}	

			
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBounds(615, 78, 97, 36);
		panelMain.add(button);
		
//		*******************************************************************************************************
		JButton button_1 = new JButton("Back");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(false);
				panelRorNR.setVisible(true);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button_1.setBounds(615, 408, 97, 36);
		panelMain.add(button_1);
		
		txtInputVariable = new JTextField();
		txtInputVariable.setVisible(false);
		txtInputVariable.setText("Input Variable");
		txtInputVariable.setBounds(12, 133, 116, 22);
		txtInputVariable.setColumns(10);
		
//		txtInputVariable.addMouseListener(new MouseListener());
		
		
		txtOutputVariable = new JTextField();
		txtOutputVariable.setVisible(false);
		txtOutputVariable.setText("Output Variable");
		txtOutputVariable.setColumns(10);
		txtOutputVariable.setBounds(12, 168, 116, 22);
		
		
		
		panelNull = new JPanel();
		frame.getContentPane().add(panelNull, "name_393394668956405");
		panelNull.setLayout(null);
		
		JTextArea txtrThisSoftwareDoes = new JTextArea();
		txtrThisSoftwareDoes.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtrThisSoftwareDoes.setText("S2CCS does convertion of Scilab to CCS code.\nIt works only for CCS code for real time processing\nin C5515 DSP Kit.\n\nFirst write code for real time processing in Scilab \nand browse the '.sci' file written by user.\nThen browse the directory where you want to save \nyour converted code with name CCScode.c \nFinally click Convert to finish convertion.\n\n");
		txtrThisSoftwareDoes.setBounds(0, 0, 712, 389);
		txtrThisSoftwareDoes.setEditable(false);
		panelNull.add(txtrThisSoftwareDoes);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelRorNR.setVisible(true);
				panelNull.setVisible(false);
			}
		});
		btnBack.setBounds(615, 419, 97, 25);
		panelNull.add(btnBack);
		
		//************************************************************************************************  Console
		panelOutput = new JPanel();
		frame.getContentPane().add(panelOutput, "name_183684713734473");
		//panelOutput.add(scroll);
		JButton btnBack1 = new JButton("Back");
		btnBack1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelRorNR.setVisible(true);
				panelOutput.setVisible(false);
			}
		});
		
		JLabel lblResultantOutput = new JLabel("  Console");
		lblResultantOutput.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panelOutput = new GroupLayout(panelOutput);
		gl_panelOutput.setHorizontalGroup(
			gl_panelOutput.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelOutput.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelOutput.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelOutput.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panelOutput.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBack1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addComponent(lblResultantOutput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)))
		);
		gl_panelOutput.setVerticalGroup(
			gl_panelOutput.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelOutput.createSequentialGroup()
					.addComponent(lblResultantOutput, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panelOutput.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBack1)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		txtrOut = new JTextArea();
		txtrOut.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtrOut.setText("Out");
		txtrOut.setEditable(false);
		scrollPane.setViewportView(txtrOut);
		panelOutput.setLayout(gl_panelOutput);
		
	}
	}


