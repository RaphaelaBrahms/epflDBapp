package interfacepack;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * 
 * 
 * */

public class MainWindowFrame extends JFrame implements ActionListener, FocusListener{
	JTabbedPane mainTabbed;
	JLabel jLabel1; 
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu("File");
	JMenuItem jMenuFileSave = new JMenuItem("Save");
	JMenuItem jMenuFileExit = new JMenuItem("Exit");
	JMenu jMenuHelp = new JMenu("Info");
	JMenuItem jMenuMaker = new JMenuItem("Made By");
	
	JPanel secondtab = new JPanel();
	JPanel firsthalf = new JPanel();
	JPanel firstquarter = new JPanel();
	JPanel forcheckbox1 = new JPanel();
	JPanel forcheckbox2 = new JPanel();
	JPanel forcheckbox3 = new JPanel();
	JPanel secondquarter = new JPanel();    	//comboboxes
	JPanel secondhalf = new JPanel();
	
	SQLSearch s;
	
	JPanel searchPanel;
	
	//Deliverable 2 Predefined Queries
	JButton prd2[] = new JButton[8];

	//	Deliverable 3 Predefined Queries
	JButton prd3[] = new JButton[15];
	
	// Insert/Delete 
	JTextField jtItem[] = new JTextField[18];

	
	//	JTextField contents in Insert/Delete tab.
	String userInputItem[] = new String[18];	
	
	
	String predefArr1[] = new String[20];
	String predefArr2[] = new String[20];
	String predefArr3[] = new String[20];
	String predefArr4[] = new String[20];

	
	JComboBox<String> predefCombo1 = new JComboBox<String>(predefArr1);
	JComboBox<String> predefCombo2 = new JComboBox<String>(predefArr2);
	JComboBox<String> predefCombo3 = new JComboBox<String>(predefArr3);
	JComboBox<String> predefCombo4 = new JComboBox<String>(predefArr4);

	String comboName1;
	String comboName2;
	String comboName3;
	String comboName4;	//selected
	
	JTextArea predefJT[] = new JTextArea[5];
	String predefQ = "";
	JButton predefGo = new JButton("Go!");
	int predefNum = -1;	//predefined query number
	JButton predefClear = new JButton("Clear");
	
	
	//	Table Names shown in JComboBox in Insert/Delete tab.
	String []tables = new String[]{"SELECT A TABLE", "ARTIST", "BRAND_GROUP", "CHARA_IN_STORY", 
			"CHARACTER", "COLORBY", "COUNTRY", "FEATURE", 
			"INDICIA_PUBLISHER", "INKBY", "ISSUE", "ISSUEREPRINT", "LANG", 
			"PENCILBY", "PUBLISHER", "SCRIPTBY", 
			"SERIES", "SERIES_PUBLICATION_TYPE", "STORY", "STORY_TYPE", "STORYREPRINT"};
	
	int row = 20;
	int column = 18;
	String [][]TableColumns = new String[row][column];
	
	
	JComboBox<String> selectTable = new JComboBox<String>(tables); 
	JButton insert = new JButton("Insert");
	JButton delete = new JButton("Delete");
	
	
	//	DataProcess Object 'dp' basically does all the sql things, from connecting to DB to processing the queries.
	static DataProcess dp;	//set global
	String query;
	
	//	For display in Predefined Queries tab.
	String queryResult;
	String defaultPredefMessage = "Result displayed here.";
	JTextArea show = new JTextArea(defaultPredefMessage);	
	JScrollPane scrollpane = new JScrollPane(show, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	// Insert/Delete Alert message panel
	String alertMessage = "Insert / Delete Message";
	JTextArea IDshow = new JTextArea(alertMessage);	
	JScrollPane IDscrollpane = new JScrollPane(IDshow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	
	public MainWindowFrame() {
		inits();
	}
	
	
	private void inits() {
		// Tabbed Design : 3 tabs (1:Basic Search, 2:Predefined Queries, 3:Insert/Delete)
		
		dp = new DataProcess();

		
	    mainTabbed = new JTabbedPane(); 	
	    mainTabbed.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	   	    
	    
	    Component panel1 = makeMainSearchPanel();
        mainTabbed.addTab("Search", panel1);
        
        //Component panel2 = makeButtonsOnPanel();
        Component panel2 = makeSecondPanel();
        mainTabbed.addTab("Predefined Queries", panel2);
        
        Component panel3 = makeInsertDeletePanel();
        mainTabbed.addTab("Insert/Delete", panel3);
        
	    this.setSize(new Dimension(1000, 800));//800 600	   
		setLayout(new BorderLayout());

		
        jLabel1 = new JLabel("Comics Database");
        Font titlefont = jLabel1.getFont();
        jLabel1.setFont(new Font(titlefont.getFontName(), Font.BOLD, 25));	// enlarge the title size
        
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(jLabel1, BorderLayout.PAGE_START);
		jLabel1.setBorder(new EmptyBorder(30,30,30,30));
		
		
        add(mainTabbed, BorderLayout.CENTER);
        
        // Make menu
		jMenuFile.add(jMenuFileSave);
		jMenuFile.add(jMenuFileExit); 
		jMenuHelp.add(jMenuMaker);	//	gives DB App makers' info 	
		
		// add the File and Help on MenuBar
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuHelp);

		
		
        
		this.setJMenuBar(jMenuBar1);
		this.setTitle("2017 DB_G27");
		this.setVisible(true);
		
		
		
		// default setting in the third tab (insert/delete).
		clearJTextFields();
		
		// set the tables' column info
		TableColumnInits();
		
		
		addActionListeners();
	}
	
	
	protected void TableColumnInits(){	
		// Stores the name of columns in each table.
		// Needed when designing DELETE SQL query. 
		
		//artist
		TableColumns[0][0] = "ARTIST_ID";
		TableColumns[0][1] = "ARTIST_NAME";
		
		//brand group
		TableColumns[1][0] = "B_ID";
		TableColumns[1][1] = "B_NAME";
		TableColumns[1][2] = "B_YR_B";
		TableColumns[1][3] = "B_YR_E";
		TableColumns[1][4] = "B_NOTES";
		TableColumns[1][5] = "B_URL";
		TableColumns[1][6] = "PUB_ID";
		
		//chara in story
		TableColumns[2][0] = "ST_ID";
		TableColumns[2][1] = "CHARACTER_ID";
		
		//character
		TableColumns[3][0] = "CHARACTER_ID";
		TableColumns[3][1] = "CHARACTER_NAME";
		
		//color by
		TableColumns[4][0] = "ST_ID";
		TableColumns[4][1] = "ARTIST_ID";
		
		//country
		TableColumns[5][0] = "C_ID";
		TableColumns[5][1] = "C_CODE";
		TableColumns[5][2] = "C_NAME";
		
		//feature
		TableColumns[6][0] = "ST_ID";
		TableColumns[6][1] = "CHARACTER_ID";
		
		//indicia publisher
		TableColumns[7][0] = "IND_ID";
		TableColumns[7][1] = "IND_NAME";
		TableColumns[7][2] = "PUB_ID";
		TableColumns[7][3] = "C_ID";
		TableColumns[7][4] = "IND_YR_B";
		TableColumns[7][5] = "IND_YR_E";
		TableColumns[7][6] = "SUR";
		TableColumns[7][7] = "IND_NOTES";
		TableColumns[7][8] = "IND_URL";
		
		//ink by
		TableColumns[8][0] = "ST_ID";
		TableColumns[8][1] = "ARTIST_ID";
		
		//issue
		TableColumns[9][0] = "ISS_ID";
		TableColumns[9][1] = "ISS_NUM";
		TableColumns[9][2] = "S_ID";
		TableColumns[9][3] = "IND_ID";
		TableColumns[9][4] = "PUB_DATE";
		TableColumns[9][5] = "PRICE";
		TableColumns[9][6] = "PAGE_COUNT";
		TableColumns[9][7] = "IND_FREQ";
		TableColumns[9][8] = "ISS_EDITING";
		TableColumns[9][9] = "ISS_NOTES";
		TableColumns[9][10] = "ISBN";
		TableColumns[9][11] = "VALID_ISBN";
		TableColumns[9][12] = "BCODE";
		TableColumns[9][13] = "ISS_TITLE";
		TableColumns[9][14] = "ON_SALE_DATE";
		TableColumns[9][15] = "RATING";
		
		//issue reprint
		TableColumns[10][0] = "ISS_ORIGIN_ID";
		TableColumns[10][1] = "ISS_TARGET_ID";
		
		//lang
		TableColumns[11][0] = "L_ID";
		TableColumns[11][1] = "LANG_CODE";
		TableColumns[11][2] = "LANG_NAME";
		
		//pencil by
		TableColumns[12][0] = "ST_ID";
		TableColumns[12][1] = "ARTIST_ID";
		
		//publisher
		TableColumns[13][0] = "PUB_ID";
		TableColumns[13][1] = "PUB_NAME";
		TableColumns[13][2] = "C_ID";
		TableColumns[13][3] = "PUB_YR_B";
		TableColumns[13][4] = "PUB_YR_E";
		TableColumns[13][5] = "PUB_NOTES";
		TableColumns[13][6] = "PUB_URL";
		
		//script by
		TableColumns[14][0] = "ST_ID";
		TableColumns[14][1] = "ARTIST_ID";
		
		//series
		TableColumns[15][0] = "S_ID";
		TableColumns[15][1] = "S_NAME";
		TableColumns[15][2] = "S_FORMAT";
		TableColumns[15][3] = "SERIES_YR_B";
		TableColumns[15][4] = "SERIES_YR_E";
		TableColumns[15][5] = "PUB_DATES";
		TableColumns[15][6] = "FISS_ID";
		TableColumns[15][7] = "LISS_ID";
		TableColumns[15][8] = "PUB_ID";
		TableColumns[15][9] = "C_ID";
		TableColumns[15][10] = "LANGID";
		TableColumns[15][11] = "SERIES_NOTES";
		TableColumns[15][12] = "COLOR";
		TableColumns[15][13] = "DIMENSIONS";
		TableColumns[15][14] = "PAPER_STOCK";
		TableColumns[15][15] = "S_BINDING";
		TableColumns[15][16] = "PUB_FORMAT";
		TableColumns[15][17] = "PUB_TYPE_ID";
		
		
		//series publication type
		TableColumns[16][0] = "SER_TYPE_ID";
		TableColumns[16][1] = "SER_NAME";
		
		//story
		TableColumns[17][0] = "ST_ID";
		TableColumns[17][1] = "ST_TITLE";
		TableColumns[17][2] = "FEATURE";
		TableColumns[17][3] = "SCRIPT";
		TableColumns[17][4] = "PENCILS";
		TableColumns[17][5] = "INKS";
		TableColumns[17][6] = "COLORS";
		TableColumns[17][7] = "LETTERS";
		TableColumns[17][8] = "GENRE";
		TableColumns[17][9] = "CHARACTERS";
		TableColumns[17][10] = "ISS_ID";
		TableColumns[17][11] = "ST_TYPE_ID";
		
		//story type
		TableColumns[18][0] = "STP_ID";
		TableColumns[18][1] = "STP_NAME";
		
		//story reprint
		TableColumns[19][0] = "ST_ORIGIN_ID";
		TableColumns[19][1] = "ST_TARGET_ID";
		
		dp.setTableColumns(TableColumns);
	}
	
	
    protected Component makeMainSearchPanel() {
        
    	setSearchPanel();
    	JPanel panel = searchPanel;
    	
        
        return panel;
    }
    
    
    protected void designButtons(){
    	//	Designs buttons on the second tab.
    	prd2[0] = new JButton("2 - A");
    	prd2[1] = new JButton("2 - B");
    	prd2[2] = new JButton("2 - C");
    	prd2[3] = new JButton("2 - D");
    	prd2[4] = new JButton("2 - E");
    	prd2[5] = new JButton("2 - F");
    	prd2[6] = new JButton("2 - G");
    	prd2[7] = new JButton("2 - H");
    	
        
        prd3[0] = new JButton("3 - A");
        prd3[1] = new JButton("3 - B");
        prd3[2] = new JButton("3 - C");
        prd3[3] = new JButton("3 - D");
        prd3[4] = new JButton("3 - E");
        prd3[5] = new JButton("3 - F");
        prd3[6] = new JButton("3 - G");
        prd3[7] = new JButton("3 - H");
        prd3[8] = new JButton("3 - I");
        prd3[9] = new JButton("3 - J");
        prd3[10] = new JButton("3 - K");
        prd3[11] = new JButton("3 - L");
        prd3[12] = new JButton("3 - M");
        prd3[13] = new JButton("3 - N");
        prd3[14] = new JButton("3 - O");
        
        for(int i = 0; i < 8; i++){
        	prd2[i].setBackground(Color.LIGHT_GRAY);
        	prd2[i].setForeground(Color.WHITE);
        }
        
        for(int i = 0; i < 15; i++){
        	prd3[i].setBackground(Color.PINK);
        }
    }
    
    
    
    
    
    protected Component makeSecondPanel(){
    	designButtons();
    	
    	GridLayout horizontalHalf = new GridLayout(2, 0);
		GridLayout verticalHalf = new GridLayout(0, 2);
		
		GridLayout row3 = new GridLayout(3, 0);
		GridLayout col8 = new GridLayout(0, 8);
		
		forcheckbox1.setLayout(col8);
		forcheckbox2.setLayout(col8);
		forcheckbox3.setLayout(col8);
		
		
    	secondtab.setLayout(horizontalHalf);
    	firsthalf.setLayout(horizontalHalf);
    	secondhalf.setLayout(new BorderLayout());
        show.setFont(new Font("Monospaced", Font.BOLD, 12));
        show.setText(defaultPredefMessage);
		show.setEditable(false);
		show.setMargin(new Insets(15, 15, 15, 15));
    	secondhalf.add(scrollpane, BorderLayout.CENTER);
    	
		firstquarter.setLayout(horizontalHalf);
		for(int i = 0; i < 8; i++){
			forcheckbox1.add(prd2[i]);
		}
		for(int i = 0; i < 8; i++){
			forcheckbox2.add(prd3[i]);
		}
		for(int i = 8; i < 15; i++){
			forcheckbox3.add(prd3[i]);
		}

		forcheckbox3.add(predefGo);
		
		
		
		secondquarter.setLayout(new GridLayout(3, 5));

		
		
		
		for(int i = 0; i<5; i++){
			predefJT[i] = new JTextArea("");
		}
		
		
		
		
		
		firstquarter.setLayout(row3);
		firstquarter.add(forcheckbox1);
		firstquarter.add(forcheckbox2);
		firstquarter.add(forcheckbox3);
		
		firsthalf.add(firstquarter);
		firsthalf.add(secondquarter);
		
		secondtab.add(firsthalf);
		secondtab.add(secondhalf);

    	return secondtab;
    }
    
    
    
    
    protected Component makeButtonsOnPanel(){
    	// Adding buttons to the second tab.
    	
        JPanel panel = new JPanel(false);
        
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        
        designButtons();               
        
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.CENTER;

        c.gridy = 0;
        for (int i = 0; i < 8; i++){
        	panel.add(prd2[i], c);
        }

        c.gridy = 1;
        for (int i = 0; i < 8; i++){
        	panel.add(prd3[i], c);
        }
        
        c.gridy = 2;
        for (int i = 8; i < 15; i++){
        	panel.add(prd3[i], c);
        }        
        
        c.gridy = 4;
        c.gridwidth = 10;
        c.gridheight = 7;
        c.fill = GridBagConstraints.BOTH;
        

        panel.add(scrollpane, c);

        c.fill = GridBagConstraints.NONE;
        
        return panel;
    }
    
    protected void clearJTextFields(){
    	//	Clears text fields on the third tab.
    	for (int i = 0; i < 18; i ++){
    		jtItem[i].setText("");
    		jtItem[i].setBackground(Color.GRAY);
    		jtItem[i].setEditable(false);
    	}
    }
    
    
    protected void fetchJTextFields(){
    	//	Stores user input values (from 18 jTextFields on the third tab) into a string array.

    	for(int i = 0; i < 18; i++){
    		userInputItem[i] = jtItem[i].getText();
    	}
    }
    
    protected Component makeInsertDeletePanel(){
    	//	Designs the third tab.
    	
    	JPanel IDpanel = new JPanel(false);
    	IDpanel.setLayout(new BorderLayout());
    	
    	
    	JPanel panel = new JPanel();
    	JPanel alertpanel = new JPanel();
    	
    	GridLayout g = new GridLayout(0, 2);
    	panel.setLayout(g);
    	
    	
    	alertpanel.setLayout(new BorderLayout());
    	IDshow.setMargin(new Insets(25, 20, 20, 20));
    	IDshow.setEditable(false);
    	Font alertfont = IDshow.getFont();
    	IDshow.setForeground(Color.BLUE);
    	IDshow.setFont(new Font(alertfont.getFontName(), Font.BOLD, 13));
    	alertpanel.add(IDscrollpane, BorderLayout.CENTER);
    	
    	panel.add(selectTable);
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	
    	for(int i = 0; i < 18; i++){
    		jtItem[i] = new JTextField(15);
    	}

    	for(int i = 0; i < 18; i++){
    		panel.add(jtItem[i]);
    	}
    	    	
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	
    	panel.add(insert);
    	panel.add(delete);
    	
    	panel.add(new JLabel(""));
    	panel.add(new JLabel(""));
    	
    	
    	IDpanel.add(panel, BorderLayout.CENTER);
    	IDpanel.add(alertpanel, BorderLayout.SOUTH);
        pack();
    	setVisible(true);
        
    	return IDpanel;
    }
    
    
    
    
    
    
    
    public void setAlertMessageI(){
    	alertMessage = dp.getInsertMessage();
    	IDshow.setText(alertMessage);
    }

    public void setAlertMessageD(){
    	alertMessage = dp.getDeleteMessage();
    	IDshow.setText(alertMessage);
    }

    
    
    
    
    
    
    
    public void addActionListeners(){
    	jMenuFileSave.addActionListener(this);
    	jMenuFileExit.addActionListener(this);
    	jMenuMaker.addActionListener(this);
    	
    	for (int i = 0; i < 8; i++){
    		prd2[i].addActionListener(this);
    	}

    	for (int i = 0; i < 15; i++){
    		prd3[i].addActionListener(this);
    	}

        predefGo.addActionListener(this);
        predefClear.addActionListener(this);
        selectTable.addActionListener(this);
        insert.addActionListener(this);
        delete.addActionListener(this);
       
        
        // Using FocusGained 
        // -> if you click on the JTextField (user input area) in Insert/Delete tab,
        // the prompt will disappear.
        
        for(int i = 0; i < 18; i ++){
        	jtItem[i].addFocusListener(this);
        }
        
    }
    	
    
    public void addPredefFocusListeners(){
    	for(int i = 0; i < 5; i ++){
        	predefJT[i].addFocusListener(this);
        }
    }
    
    public void addPredefActionListeners(){
    	predefCombo1.addActionListener(this);
    	predefCombo2.addActionListener(this);
    	predefCombo3.addActionListener(this);
    	predefCombo4.addActionListener(this);
    }
    
    
    public void setSearchPanel(){
    	// Needed in the first tab : basic search tab.
    	s = new SQLSearch(new JPanel());
    	searchPanel = s.getSearchPanel();
    	s.setTableNameArray(tables);
    	s.setAdditionalCheckBox();
    }
    

    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		if(e.getSource()==jMenuFileSave)
		{
			try {
				s.saveSearchHistory();
				dp.saveResultHistory();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==jMenuFileExit)
		{	// close the window
			this.dispose();
			System.exit(0);
		}
		else if(e.getSource()==jMenuMaker){
			JOptionPane.showMessageDialog(this, "2017 Introduction to Database System\n\nGroup 27\n\n277017 Hailing Zhang\n278834 Jessica Holland Zuber\n278949 Woojeong Park\n");
		}
		else if(e.getSource() == selectTable){	// When you select table to insert or delete items
	        // Controls user input areas & prompts the data name and format.
			
			String selectedTableName = (String) ((JComboBox)e.getSource()).getSelectedItem();
	        
	        clearJTextFields();	//always initializes when jcombobox clicked again.
	        
	        if (selectedTableName.equals(tables[1])) {//ARTIST
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("ARTIST_ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ARTIST_NAME");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	        }
	        else if(selectedTableName.equals(tables[2])){//brand group
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("BRAND ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("BRAND NAME");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("BRAND YEAR BEGAN: e.g. 1996");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("BRAND YEAR ENDED: e.g. 1997");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("BRAND NOTES");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("BRAND URL");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("PUBLISHER ID");
	        	
	        	for(int i = 0; i < 7; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
	        	
	        }
	        else if(selectedTableName.equals(tables[3])){//character in story
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("CHARACTER ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	        }
	        else if(selectedTableName.equals(tables[4])){//character
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("CHARACTER ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("CHARACTER BANE");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	        }
	        else if(selectedTableName.equals(tables[5])){//color by
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ARTIST ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
		        }
	        else if(selectedTableName.equals(tables[6])){//country
	        	if(jtItem[0].getText().isEmpty()) jtItem[0].setText("COUNTRY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("COUNTRY CODE");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("COUNTRY NAME");
	        	
	        	for(int i = 0; i < 3; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
	        }
			else if(selectedTableName.equals(tables[7])){//feature
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("CHARACTER ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);

			}	
			else if(selectedTableName.equals(tables[8])){//indicia publisher
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("INDICIA PUBLISHER ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("INDICIA PUBLISHER NAME");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("PUBLISHER ID");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("COUNTRY ID");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("IND. PUB. YEAR BEGAN: e.g. 1994");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("IND. PUB. YEAR ENDED: e.g. 2001");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("IS SURROGATE? If yes 1, If no 0");
	        	if(jtItem[7].getText().isEmpty()) jtItem[7].setText("INDICIA PUBLISHER NOTES");
	        	if(jtItem[8].getText().isEmpty()) jtItem[8].setText("INDICIA PUBLISHER URL");
	        	
	        	for(int i = 0; i < 9; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
			}
			else if(selectedTableName.equals(tables[9])){//ink by
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ARTIST ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);

			}
			else if(selectedTableName.equals(tables[10])){//issue
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("ISSUE ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ISSUE NUMBER");//?
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("SERIES ID");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("INDICIA PUBLISHER ID");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("PUBLICATION DATE : String type");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("PRICE");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("PAGE COUNT");
	        	if(jtItem[7].getText().isEmpty()) jtItem[7].setText("INDICIA FREQUENCY");//?
	        	if(jtItem[8].getText().isEmpty()) jtItem[8].setText("ISSUE EDITING");
	        	if(jtItem[9].getText().isEmpty()) jtItem[9].setText("ISSUE NOTES");
	        	if(jtItem[10].getText().isEmpty()) jtItem[10].setText("ISBN");
	        	if(jtItem[11].getText().isEmpty()) jtItem[11].setText("VALID ISBN");
	        	if(jtItem[12].getText().isEmpty()) jtItem[12].setText("BARCODE");
	        	if(jtItem[13].getText().isEmpty()) jtItem[13].setText("ISSUE TITLE");
	        	if(jtItem[14].getText().isEmpty()) jtItem[14].setText("ON SALE DATE");
	        	if(jtItem[15].getText().isEmpty()) jtItem[15].setText("RATING");
	        	
	        	for(int i = 0; i < 16; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
			}
			else if(selectedTableName.equals(tables[11])){//issue reprint
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("ISSUE ORIGIN ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ISSUE TARGET ID");
				
				for(int i = 0; i < 2; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}

			}
			else if(selectedTableName.equals(tables[12])){//lang
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("LANGUAGE ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("LANGUAGE CODE");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("LANGUAGE NAME");
	        	
	        	for(int i = 0; i < 3; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}

			}
			else if(selectedTableName.equals(tables[13])){//pencil by
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ARTIST ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	
			}
			else if(selectedTableName.equals(tables[14])){//publisher
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("PUBLISHER ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("PUBLISHER NAME");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("COUNTRY ID");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("PUBLISHER YEAR BEGAN: e.g. 2001");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("PUBLISHER YEAR ENDED: e.g. 2002");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("PUBLISHER NOTES");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("PUBLISHER URL");
	        	
	        	for(int i = 0; i < 7; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
			}
			else if(selectedTableName.equals(tables[15])){//script by
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("ARTIST ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	      
			}
			else if(selectedTableName.equals(tables[16])){//series
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("SERIES ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("SERIES NAME");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("SERIES FORMAT");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("SER. YEAR BEGAN : e.g. 1994-12-13");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("SER. YEAR ENDED : e.g. 2012-11-08");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("PUBLICATION DATES");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("FIRST ISSUE ID");
	        	if(jtItem[7].getText().isEmpty()) jtItem[7].setText("LAST ISSUE ID");
	        	if(jtItem[8].getText().isEmpty()) jtItem[8].setText("PUBLISHER ID");
	        	if(jtItem[9].getText().isEmpty()) jtItem[9].setText("COUNTRY ID");
	        	if(jtItem[10].getText().isEmpty()) jtItem[10].setText("LANGUAGE ID");
	        	if(jtItem[11].getText().isEmpty()) jtItem[11].setText("SERIES NOTES");
	        	if(jtItem[12].getText().isEmpty()) jtItem[12].setText("COLOR");
	        	if(jtItem[13].getText().isEmpty()) jtItem[13].setText("DIMENSIONS");
	        	if(jtItem[14].getText().isEmpty()) jtItem[14].setText("PAPER STOCK");
	        	if(jtItem[15].getText().isEmpty()) jtItem[15].setText("SERIES BINDING");
	        	if(jtItem[16].getText().isEmpty()) jtItem[16].setText("PUBLICATION FORMAT");
	        	if(jtItem[17].getText().isEmpty()) jtItem[17].setText("PUBLICATION TYPE ID");
	        	
	        	for(int i = 0; i < 18; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}
	        }
			else if(selectedTableName.equals(tables[17])){//series publication type
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("SERIES TYPE ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("SERIES TYPE NAME");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
			}
			else if(selectedTableName.equals(tables[18])){//story
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("STORY TITLE");
	        	if(jtItem[2].getText().isEmpty()) jtItem[2].setText("FEATURE");
	        	if(jtItem[3].getText().isEmpty()) jtItem[3].setText("SCRIPT");
	        	if(jtItem[4].getText().isEmpty()) jtItem[4].setText("PENCILS");
	        	if(jtItem[5].getText().isEmpty()) jtItem[5].setText("INKS");
	        	if(jtItem[6].getText().isEmpty()) jtItem[6].setText("COLOR");
	        	if(jtItem[7].getText().isEmpty()) jtItem[7].setText("LETTERS");
	        	if(jtItem[8].getText().isEmpty()) jtItem[8].setText("GENRE");
	        	if(jtItem[9].getText().isEmpty()) jtItem[9].setText("CHARACTERS");
	        	if(jtItem[10].getText().isEmpty()) jtItem[10].setText("ISSUE ID");
	        	if(jtItem[11].getText().isEmpty()) jtItem[11].setText("STORY TYPE ID");
	        	
	        	for(int i = 0; i < 12; i++){
	        		jtItem[i].setBackground(Color.WHITE);
	        		jtItem[i].setEditable(true);
	        	}	       
			}
			else if(selectedTableName.equals(tables[19])){//story type
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY TYPE ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("STORY TYPE NAME");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
	        	
			}
			else if(selectedTableName.equals(tables[20])){//story reprint
				if(jtItem[0].getText().isEmpty()) jtItem[0].setText("STORY ORIGIN ID");
	        	if(jtItem[1].getText().isEmpty()) jtItem[1].setText("STORY TARGET ID");
	        	jtItem[0].setBackground(Color.WHITE);
				jtItem[0].setEditable(true);
	        	jtItem[1].setBackground(Color.WHITE);
				jtItem[1].setEditable(true);
			}
			
		}
		// *** Both INSERT and DELETE use PreparedStatement in DataProcess ! ***

		else if (e.getSource() == insert){ //when Insert button pressed.
			int tableIndex = selectTable.getSelectedIndex();			
			query = "INSERT INTO " + tables[tableIndex] + " VALUES ";
			fetchJTextFields();
			
			
			int caseNumber = -1;
			
			// Sort the cases with parameters' data type & order -> 9 cases.
			// Process the insert query's "VALUES ( ? , ? , ... , ?)" part based on the case number, in DataProcess's function.
			
			if(tableIndex == 1 || tableIndex == 4 || tableIndex == 17 || tableIndex == 19){
				// num, varchar
				caseNumber = 0;
			}
			else if(tableIndex == 1 || tableIndex == 3 || tableIndex == 5 ||
					tableIndex == 7 || tableIndex == 9 || tableIndex == 11 || tableIndex == 13 ||
					tableIndex == 15 || tableIndex == 20){
				//num, num
				caseNumber = 1;
			}
			else if(tableIndex == 2){//brand group
				caseNumber = 2;
			}
			else if(tableIndex == 14){//publisher
				caseNumber = 3;
			}
			else if (tableIndex == 6 || tableIndex == 12){
				//country and lang
				caseNumber = 4;
			}
			else if (tableIndex == 8){//indicia publisher
				caseNumber = 5;
			}
			else if (tableIndex == 10){//issue
				caseNumber = 6;
			}
			else if (tableIndex == 16){
				//series => item 4, 5 : DATE ***************
				//something has to be done with date object. 
				caseNumber = 7;
			}
			else if (tableIndex == 18){//story
				caseNumber = 8;
			}
			

			dp.processInsertQuery(query, caseNumber, userInputItem);
			setAlertMessageI();

			System.out.println(query);
			System.out.println("----- Inserting data -----");
			

		}
		else if (e.getSource() == delete){	//if Delete button pressed

			int tableIndex = selectTable.getSelectedIndex();			
			query = "DELETE FROM " + tables[tableIndex] + " WHERE ";
			fetchJTextFields();
			
			String whereStatement = "";
			
			
			int maxJText = 0;
			int notNullCnt = 0;
			for(int i = 0; i < 18; i ++){
				if(!userInputItem[i].isEmpty()){
					maxJText = i;
					notNullCnt ++;
				}
			}
			
			// These two arrays take valid user inputs (not null). Sent as parameters to DataProcess's DeleteQuery function.
			String whereClause[] = new String[notNullCnt];
			int getJTPosition[] = new int[notNullCnt];	// gets the jtItem position and store in in an array
			
			
			int j = 0;
			
			for(int i = 0; i <= maxJText; i++){
				if(!userInputItem[i].isEmpty()){//process user-filled areas only
					whereClause[j] = userInputItem[i];
					getJTPosition[j] = i;
					j++;
					
					if(i == maxJText){
						whereStatement += (TableColumns[tableIndex - 1][i] + "= ? ");
					}
					else{
						whereStatement += (TableColumns[tableIndex - 1][i] + "= ? " + "AND ");
					}	
				}
			}
			
			
			query += whereStatement;
			System.out.println(query);
			System.out.println("----- Deleting data -----");
			
			dp.processDeleteQuery(query, tableIndex - 1, getJTPosition, whereClause);
			setAlertMessageD();
		}
		
		else if(e.getSource()==prd2[0]){
	
			secondquarterRemove();
			predefJT[0].setText("contry: e.g. Belgium");
			
			String predefArr1[] = new String[3];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "names";
			predefArr1[2] = "IDs";
			
			String predefArr2[] = new String[3];
			predefArr2[0] = "user-defined 2";
			predefArr2[1] = "highest";
			predefArr2[2] = "lowest";
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefCombo2 = new JComboBox<String>(predefArr2);
			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			
			
			secondquarter.add(new JLabel("Print the brand group "));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("with the "));
			secondquarter.add(predefCombo2);
			secondquarter.add(new JLabel(" number of"));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel(" indicia publishers."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 0;
		}
		else if(e.getSource()==prd2[1]){
			secondquarterRemove();
			
			predefJT[0].setText("contry: e.g. Denmark");
			
	
			addPredefActionListeners();
			addPredefFocusListeners();
			
			
			
			secondquarter.add(new JLabel("Print the ids and names"));
			secondquarter.add(new JLabel("of publishers of "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel(" book series."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 1;
		}
		else if(e.getSource()==prd2[2]){
			
			secondquarterRemove();
			
			predefJT[0].setText("country: e.g.Switzerland");
			
			String predefArr1[] = new String[4];
			predefArr1[0] = "user-defined 2";
			predefArr1[1] = "books";
			predefArr1[2] = "magazines";
			predefArr1[3] = "albums";
			
			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the names of all"));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel("series that have"));
			secondquarter.add(new JLabel("been published in"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarterRevalidate();
			predefNum = 2;
		}
		else if(e.getSource()==prd2[3]){	// too slow
			secondquarterRemove();
			
			String predefArr1[] = new String[30];
			predefArr1[0] = "Select year";
			for(int i = 1; i < 30; i++){
				int y = i+1987;
				String s = Integer.toString(y);
				System.out.println(s);
				predefArr1[i] = s;
			}
			predefCombo1 = new JComboBox<String>(predefArr1);

			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Starting from "));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel(", print the number"));
			secondquarter.add(new JLabel(" of issues published"));
			secondquarter.add(new JLabel(" each year"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());

			
			secondquarterRevalidate();
			predefNum = 3;
		}
		else if(e.getSource()==prd2[4]){

			secondquarterRemove();
			
			predefJT[0].setText("Indicia publisher name: e.g. DC Comics");
			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the number of "));
			secondquarter.add(new JLabel("series for each "));
			secondquarter.add(new JLabel("indicia publisher "));
			secondquarter.add(new JLabel("whose name resembles "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 4;
		}
		else if(e.getSource()==prd2[5]){	//error occurred because of null value in the story title.
			
			secondquarterRemove();
			
			predefJT[0].setText("number: e.g.10");
			
			String predefArr1[] = new String[3];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "titles";
			predefArr1[2] = "IDs";
			
			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the "));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel(" of the "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel(" most reprinted"));
			secondquarter.add(new JLabel(" stories"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarterRevalidate();
			
			secondquarterRevalidate();
			predefNum = 5;
		}
		else if(e.getSource()==prd2[6]){	//slow 

			secondquarterRemove();

			secondquarter.add(new JLabel("Print the artists that have scripted, drawn, and colored at least one of the stories they were involved in."));
			
			secondquarterRevalidate();
			
			predefNum = 6;
		}
		else if(e.getSource()==prd2[7]){ //slow : takes 2 minutes. with the query before version, it took 1 minute.
//			System.out.println("----- Deliverable 2 Query 8 -----");
//			dp.changeQuery("SELECT DISTINCT s.st_title FROM STORY s, (SELECT character_st_id FROM (SELECT s.st_id as character_st_id FROM STORY s, CHARA_IN_STORY cst,CHARACTER c, FEATURE f WHERE (c.character_name like \'%Batman%\' OR c.character_name LIKE \'%Bat-Man%\' OR c.character_name LIKE \'%Bruce Wayne%\')AND c.character_id=cst.character_id AND cst.st_id=s.st_id AND (NOT c.character_id=f.character_id) AND s.st_id=f.st_id) MINUS (SELECT re.st_origin_id as re_st_id FROM STORYREPRINT re ))WHERE s.st_id=character_st_id AND s.st_title is not null");	//						
//			//longer?
//			dp.processQuery();
//			queryResult = dp.exportResult();
//			show.setText(queryResult);
//			show.setEditable(false);
			secondquarterRemove();
			
			
			predefJT[0].setText("Character name: e.g. Batman");
			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print all non-reprinted "));
			secondquarter.add(new JLabel("stories involving "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel(" as a non-featured"));
			secondquarter.add(new JLabel("character. "));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 7;
		}
		/*
		 * prd3 to be appended
		 * */
		else if(e.getSource() == prd3[0]){
			secondquarterRemove();
			
			String predefArr1[] = new String[3];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "names";
			predefArr1[2] = "IDs";
			
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			
			addPredefActionListeners();
			addPredefFocusListeners();	
			
			secondquarter.add(new JLabel("Print the series"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("that have the highest"));
			secondquarter.add(new JLabel("number of issues which contain"));
			secondquarter.add(new JLabel("a story whose type"));
			secondquarter.add(new JLabel("(e.g. cartoon) is not the one"));
			secondquarter.add(new JLabel("occurring most frequently"));
			secondquarter.add(new JLabel("in the database (e.g. illustration)"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());


			
			secondquarterRevalidate();
			predefNum = 8;
		}
		else if(e.getSource() == prd3[1]){
			secondquarterRemove();
			
			String predefArr1[] = new String[3];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "names";
			predefArr1[2] = "IDs";
			
			String predefArr2[] = new String[3];
			predefArr2[0] = "user-defined 2";
			predefArr2[1] = "publishers";
			predefArr2[2] = "countries";
			
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefCombo2 = new JComboBox<String>(predefArr2);

			
			addPredefActionListeners();
			addPredefFocusListeners();	
			
			secondquarter.add(new JLabel("Print the "));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("of"));
			secondquarter.add(predefCombo2);
			secondquarter.add(new JLabel("who have series with"));
			secondquarter.add(new JLabel("all series types."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());


			
			secondquarterRevalidate();
			predefNum = 9;
		}
		else if(e.getSource() == prd3[2]){
			secondquarterRemove();
			predefJT[0] = new JTextArea("(number: e.g.10)");
			predefJT[1] = new JTextArea("(name: e.g.Alan Moore)");
			
			addPredefFocusListeners();			
			secondquarter.add(new JLabel("Print the "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel("most-reprinted"));
			secondquarter.add(new JLabel("characters from"));
			secondquarter.add(predefJT[1]);
			secondquarter.add(new JLabel("'s stories."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(predefClear);
			
			secondquarterRevalidate();
			predefNum = 10;
		}
		else if(e.getSource() == prd3[3]){
			secondquarterRemove();
			String predefArr1[] = new String[5];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "writers";
			predefArr1[2] = "pencil-workers";
			predefArr1[3] = "inkers";
			predefArr1[4] = "colorers";
			
			String predefArr2[] = new String[5];
			predefArr2[0] = "user-defined 3";
			predefArr2[1] = "pencil work";
			predefArr2[2] = "ink work";
			predefArr2[3] = "color work";
			predefArr2[4] = "script work";

			
			predefJT[0] = new JTextArea("(Genre: e.g. nature");
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefCombo2 = new JComboBox<String>(predefArr2);
			
			addPredefActionListeners();
			addPredefFocusListeners();	
			
			secondquarter.add(new JLabel("Print the "));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("of"));
			secondquarter.add(predefJT[0]);//genre
			secondquarter.add(new JLabel("-related stories"));
			secondquarter.add(new JLabel("that have also done the "));
			secondquarter.add(predefCombo2);
			secondquarter.add(new JLabel("in all their "));
			secondquarter.add(new JLabel("<what you chose-2>"));
			secondquarter.add(new JLabel("-related stories."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());

			
			secondquarterRevalidate();
			predefNum = 11;
		}
		else if(e.getSource() == prd3[4]){
			secondquarterRemove();
			//difficult
			
			String predefArr1[] = new String[13];
			predefArr1[0] = "user-defined 1";
			
			for (int i = 1; i<= 12; i++){
				predefArr1[i] = String.valueOf(i);
			}
			
			String predefArr2[] = new String[5];
			predefArr2[0] = "user-defined 2";
			for (int i = 1; i<= 4; i++){
				predefArr2[i] = String.valueOf(i);
			}
						
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefCombo2 = new JComboBox<String>(predefArr2);
			
			addPredefActionListeners();
			addPredefFocusListeners();	
			
			secondquarter.add(new JLabel("For each of the top-"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("publishers in terms of")); 
			secondquarter.add(new JLabel("published series, print the")); 
			secondquarter.add(predefCombo2);
			secondquarter.add(new JLabel(" most popular languages")); 
			secondquarter.add(new JLabel("of their series."));
			
			secondquarterRevalidate();
			predefNum = 12;
		}
		else if(e.getSource() == prd3[5]){
			secondquarterRemove();
			predefJT[0] = new JTextArea("(number: e.g.10000)");
			String predefArr1[] = new String[4];
			predefArr1[0] = "user-defined 2";
			predefArr1[1] = "books";
			predefArr1[2] = "magazines";
			predefArr1[3] = "albums";

			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();			
			secondquarter.add(new JLabel("Print the languages"));
			secondquarter.add(new JLabel("that have more than"));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel("original stories published in"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("along with the number"));
			secondquarter.add(new JLabel("of those stories."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 13;
		}
		else if(e.getSource() == prd3[6]){
			secondquarterRemove();
			String predefArr1[] = new String[8];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "Italian";
			predefArr1[2] = "Hebrew";
			predefArr1[3] = "Dutch";
			predefArr1[4] = "Polish";
			predefArr1[5] = "Malay";
			predefArr1[6] = "Esperanto";
			predefArr1[7] = "Swahili";
			
			String predefArr2[] = new String[4];
			predefArr2[0] = "user-defined 2";
			predefArr2[1] = "book";
			predefArr2[2] = "magazine";
			predefArr2[3] = "album";
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefCombo2 = new JComboBox<String>(predefArr2);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print all story types"));
			secondquarter.add(new JLabel("that have not been"));
			secondquarter.add(new JLabel("published as a part of"));
			secondquarter.add(predefCombo1);
			secondquarter.add(predefCombo2);
			secondquarter.add(new JLabel("series."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			
			predefNum = 14;
		}
		else if(e.getSource() == prd3[7]){
			secondquarterRemove();
			String predefArr1[] = new String[11];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "cartoon";
			predefArr1[2] = "activity";
			predefArr1[3] = "advertisement";
			predefArr1[4] = "biography";
			predefArr1[5] = "cover";
			predefArr1[6] = "filler";
			predefArr1[7] = "photo story";
			predefArr1[8] = "illustration";
			predefArr1[9] = "promo";
			predefArr1[10] = "recap";


			
			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the writers of"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("stories who have worked"));
			secondquarter.add(new JLabel("as writers for more than"));
			secondquarter.add(new JLabel("one indicia publisher"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarterRevalidate();
			predefNum = 15;
		}
		else if(e.getSource() == prd3[8]){
			secondquarterRemove();
			String predefArr1[] = new String[11];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "highest";
			predefArr1[2] = "lowest";
			
			predefJT[0] = new JTextArea("(number: e.g.10)");
			
			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the"));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel("brand groups with the"));			
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("number of"));
			secondquarter.add(new JLabel("indicia publishers."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
	
			secondquarterRevalidate();
			predefNum = 16;
		}
		else if(e.getSource() == prd3[9]){
			secondquarterRemove();
			
			secondquarter.add(new JLabel("Print the average series length (in terms of years) per indicia publisher"));
			secondquarterRevalidate();
			
			predefNum = 17;
		}
		else if(e.getSource() == prd3[10]){
			secondquarterRemove();
			
			String predefArr1[] = new String[11];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "single";
			predefArr1[2] = "double";
			
			
			predefCombo1 = new JComboBox<String>(predefArr1);

			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the top 10"));
			secondquarter.add(new JLabel("indicia publishers"));
			secondquarter.add(new JLabel("that have published"));
			secondquarter.add(new JLabel("the most"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("-issue series"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
	
			
			secondquarterRevalidate();
			
			predefNum = 18;
		}
		else if(e.getSource() == prd3[11]){
			secondquarterRemove();
			String predefArr1[] = new String[5];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "script writers";
			predefArr1[2] = "pencil-workers";
			predefArr1[3] = "inkers";
			predefArr1[4] = "colorers";
						
			predefJT[0] = new JTextArea("(number: e.g. 10");
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			
			addPredefActionListeners();
			addPredefFocusListeners();	
			
			secondquarter.add(new JLabel("Print the "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel("indicia publishers with"));
			secondquarter.add(new JLabel("the highest number of"));
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel("in a single story"));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());


			secondquarterRevalidate();
			
			predefNum = 19;
		}
		else if(e.getSource() == prd3[12]){
			secondquarterRemove();
			
			secondquarter.add(new JLabel("Print all Marvel heroes that appear in Marvel-DC story crossovers"));
			
			
			secondquarterRevalidate();
			predefNum = 20;
		}
		else if(e.getSource() == prd3[13]){
			secondquarterRemove();
			
			String predefArr1[] = new String[11];
			predefArr1[0] = "user-defined 1";
			predefArr1[1] = "series";
			predefArr1[2] = "indicia publishers";
			
			
			predefCombo1 = new JComboBox<String>(predefArr1);
			predefJT[0].setText("number: e.g. 5");
			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Print the top "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(predefCombo1);
			secondquarter.add(new JLabel(" with most issues."));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 21;
		}
		else if(e.getSource() == prd3[14]){
			secondquarterRemove();
			
			predefJT[0].setText("issue ID: e.g. 583477");
			
			addPredefActionListeners();
			addPredefFocusListeners();
			
			secondquarter.add(new JLabel("Given an issue "));
			secondquarter.add(new JLabel("with issue ID "));
			secondquarter.add(predefJT[0]);
			secondquarter.add(new JLabel(", print its most"));
			secondquarter.add(new JLabel("reprinted story. "));
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			secondquarter.add(new JLabel());
			
			secondquarterRevalidate();
			predefNum = 22;
		}
		else if(e.getSource() == predefCombo1){
			comboName1 = (String) ((JComboBox)e.getSource()).getSelectedItem();   
		}
		else if(e.getSource() == predefCombo2){
			comboName2 = (String) ((JComboBox)e.getSource()).getSelectedItem();   
		}
		else if(e.getSource() == predefCombo3){
			comboName3 = (String) ((JComboBox)e.getSource()).getSelectedItem();   
		}
		else if(e.getSource() == predefCombo4){
			comboName4 = (String) ((JComboBox)e.getSource()).getSelectedItem();   
		}
		else if(e.getSource() == predefGo){
			
			switch(predefNum){
			case 0:
				String countryname = predefJT[0].getText();
				String btype = "";
				String MaxMin = "";
				
				if(comboName1.equals("names")){
					btype = "b_name";
				}
				else if(comboName1.equals("IDs")){
					btype = "b_id";
				}
				
				if(comboName2.equals("highest")){
					MaxMin = "MAX";
				}
				else if(comboName2.equals("lowest")){
					MaxMin = "MIN";
				}
				predefQ = String.format("SELECT b.%s FROM BRAND_GROUP b,(SELECT b.b_id as brandgroup_id, count(*) FROM BRAND_GROUP b, INDICIA_PUBLISHER ip, COUNTRY c WHERE b.PUB_ID=ip.PUB_ID AND ip.c_id=c.c_id AND c.c_name='%s' GROUP BY b.B_ID HAVING COUNT(b.b_id) = (SELECT %s(indiacia_count)as max_count FROM (SELECT b.b_id as brand_id, count(*) as indiacia_count FROM BRAND_GROUP b, INDICIA_PUBLISHER ip, COUNTRY c WHERE b.PUB_ID=ip.PUB_ID AND ip.c_id=c.c_id AND c.c_name='%s' GROUP BY b.B_ID) b_id_2_ind_count)) where b.b_id=brandgroup_id", btype, countryname, MaxMin, countryname);
				break;
			case 1:	
				countryname = predefJT[0].getText();
				
				predefQ = String.format("SELECT DISTINCT P.pub_id, P.pub_name FROM PUBLISHER P, SERIES S, COUNTRY C WHERE (C.c_name = '%s' and S.c_id = C.c_id and P.pub_id = S.pub_id) ORDER BY (P.pub_id) ASC", countryname);
				
				break;
			case 2:
				countryname = predefJT[0].getText();
				String pubtype = "";
				
				if(comboName1.equals("books")){
					pubtype = "1";
				}
				else if(comboName1.equals("magazines")){
					pubtype = "2";
				}
				else if(comboName1.equals("albums")){
					pubtype = "3";
				}
				
				predefQ = String.format("SELECT S.s_name FROM SERIES S, COUNTRY C, SERIES_PUBLICATION_TYPE PT WHERE (C.c_name='%s' and S.c_id = C.c_id and PT.ser_type_id = %s and S.pub_type_id = PT.ser_type_id)", countryname, pubtype);
				
				break;
			case 3:
				String yearto = "2017";
				String yearfrom = comboName1;
				
				predefQ = String.format("SELECT years, count(*) FROM (SELECT i.iss_id,i.pub_date,years FROM ISSUE i CROSS JOIN (SELECT n AS years FROM (SELECT ROWNUM n FROM dual CONNECT BY LEVEL <= %s ) WHERE n >= %s)where regexp_like(i.pub_date,years)) GROUP BY years ORDER BY years", yearto, yearfrom);
				break;
			case 4:
				String indnamematch = predefJT[0].getText();
				
				predefQ = String.format("SELECT IP.ind_name, DCTABLE.cnt FROM INDICIA_PUBLISHER IP, (SELECT I.ind_id AS dc_ind_id, COUNT(I.S_ID) AS cnt FROM ISSUE I, (SELECT DISTINCT(IP.IND_ID) AS indicia_dc_id FROM INDICIA_PUBLISHER IP WHERE IP.IND_NAME LIKE '%%%s%%') DC WHERE I.ind_id = DC.indicia_dc_id GROUP BY (I.ind_id)) DCTABLE WHERE DCTABLE.dc_ind_id = IP.ind_id ORDER BY (DCTABLE.cnt) DESC", indnamematch);

				break;
			case 5:
				String col1 = "";
				String topN = predefJT[0].getText();
				
				if(comboName1.equals("titles")){
					col1 = "st_title";
				}
				else if(comboName1.equals("IDs")){
					col1 = "st_id";
				}
				
				predefQ = String.format("SELECT s.%s from STORY S, (SELECT SREP.ST_ORIGIN_ID as origin_id FROM STORYREPRINT SREP GROUP BY SREP.ST_ORIGIN_ID ORDER BY COUNT(SREP.ST_ORIGIN_ID) DESC) O where S.st_id = O.origin_id and s.st_title is not null and ROWNUM <= %s ORDER BY ROWNUM", col1, topN);
				break;
			case 6:
				predefQ = "SELECT a.artist_name FROM ARTIST a,(SELECT artist_id_join FROM (SELECT p.artist_id as artist_id_join, p.st_id as story_id FROM PENCILBY p INNER JOIN (SELECT s.artist_id as sc_artist_id, s.st_id as sc_st_id FROM SCRIPTBY s INNER JOIN COLORBY c ON s.artist_id=c.artist_id AND s.st_id=c.st_id ) ON p.artist_id=sc_artist_id AND p.st_id=sc_st_id) GROUP BY  artist_id_join ) WHERE  a.artist_id=artist_id_join";
				break;
			case 7:
				String characname = predefJT[0].getText();
				
				
				predefQ = String.format("SELECT DISTINCT s.st_title FROM STORY s, ( SELECT character_st_id FROM (SELECT s.st_id as character_st_id FROM STORY s, CHARA_IN_STORY cst,CHARACTER c, FEATURE f WHERE (c.character_name like '%%%s%%')AND c.character_id=cst.character_id AND cst.st_id=s.st_id AND (NOT c.character_id=f.character_id) AND s.st_id=f.st_id ) MINUS (SELECT re.st_origin_id as re_st_id FROM STORYREPRINT re))WHERE s.st_id=character_st_id AND s.st_title is not null", characname);

				break;
			case 8:
				String stype = "";
				if(comboName1.equals("names")){
					stype = "s_name";
				}
				else if(comboName1.equals("IDs")){
					stype = "s_id";
				}
				
				predefQ = String.format("SELECT ser1.%s FROM SERIES ser1 JOIN (SELECT series_id FROM (SELECT table_temp.series_id as series_id FROM(SELECT ser.s_name as series_name,ser.s_id as series_id,issue_id FROM (SERIES ser JOIN ISSUE iss ON ser.s_id=iss.s_id) JOIN(SELECT s.iss_id as issue_id FROM STORY s WHERE NOT s.st_type_id= (SELECT ST_IDS FROM (SELECT COUNT(s2.st_id) as COUNTS, s2.st_type_id as ST_IDS FROM STORY s2 GROUP BY s2.st_type_id) WHERE COUNTS=(SELECT MAX(type_count)as type_count_max FROM (SELECT count(s3.st_id) as type_count FROM STORY s3 GROUP BY s3.st_type_id))) GROUP BY s.iss_id )ON  issue_id=iss.iss_id ) table_temp GROUP BY table_temp.series_id ORDER BY COUNT(table_temp.issue_id) DESC) WHERE ROWNUM=1) table_temp2 ON table_temp2.series_id=ser1.s_id", stype);
				break;
			case 9:
				String ptype = "";
				String PorC = "";
				String sel = "";
				
				if(comboName2.equals("publishers")){
					PorC = "PUBLISHER";
					ptype = "pub_id";
					if(comboName1.equals("names")){
						sel = "pub_name";
					}
					else if(comboName1.equals("IDs")){
						sel = "pub_id";	
					}

				}
				else if(comboName2.equals("countries")){
					PorC = "COUNTRY";
					ptype = "c_id";
					if(comboName1.equals("names")){
						sel = "c_name";
					}
					else if(comboName1.equals("IDs")){
						sel = "c_id";	
					}
				}
				
				predefQ = String.format("SELECT pub.%s FROM %s pub,(SELECT distinct pub_id_1 FROM (((SELECT ser.%s as pub_id_1 FROM SERIES ser WHERE ser.pub_type_id =1) INNER JOIN (SELECT ser2.%s as pub_id_2 FROM SERIES ser2 WHERE ser2.pub_type_id =2) ON pub_id_2 = pub_id_1) INNER JOIN (SELECT ser3.%s as pub_id_3 FROM SERIES ser3 WHERE ser3.pub_type_id =3) ON pub_id_3 = pub_id_1)) WHERE pub.%s=pub_id_1", sel, PorC, ptype, ptype, ptype, ptype);

				break;
			case 10:
				String num = predefJT[0].getText();
				String name = predefJT[1].getText();
				
				predefQ = String.format("SELECT character_name FROM (SELECT character_name, count(character_name) as cnt FROM((((SELECT C.st_id FROM ARTIST A, COLORBY C WHERE A.artist_name='%s' AND C.artist_id=A.artist_id UNION SELECT I.st_id FROM ARTIST A, INKBY I WHERE A.artist_name='%s' AND I.artist_id=A.artist_id UNION SELECT P.st_id FROM ARTIST A, PENCILBY P WHERE A.artist_name='%s' AND P.artist_id=A.artist_id UNION SELECT B.st_id FROM ARTIST A, SCRIPTBY B WHERE A.artist_name='%s' AND B.artist_id=A.artist_id)) INTERSECT SELECT SR.st_origin_id FROM STORYREPRINT SR) T1 JOIN (SELECT T2.st_id, T2.character_id, T3.character_name FROM CHARA_IN_STORY T2, CHARACTER T3 WHERE T2.character_id=T3.character_id) T4 ON T1.st_id=T4.st_id) GROUP BY character_name ORDER BY cnt DESC) WHERE rownum<=%s",
						name, name, name, name, num);
				break;
			case 11:
				String genre = predefJT[0].getText();
				String table1 = "";
				String table2 = "";
				
				if(comboName1.equals("writers")){
					table1 = "SCRIPTBY";
				}
				else if(comboName1.equals("pencil-workers")){
					table1 = "PENCILBY";
				}
				else if(comboName1.equals("inkers")){
					table1 = "INKBY";
				}
				else if(comboName1.equals("colorers")){
					table1 = "COLORBY";
				}
				
				if(comboName2.equals("pencil work")){
					table2 = "PENCILBY";
				}
				else if(comboName2.equals("ink work")){
					table2 = "INKBY";
				}
				else if(comboName2.equals("color work")){
					table2 = "COLORBY";
				}
				else if(comboName2.equals("script work")){
					table2 = "SCRIPTBY";
				}
				
				predefQ = String.format("SELECT distinct artist.artist_name FROM ((STORY st JOIN %s script on st.st_id=script.st_id) JOIN %s color on st.st_id=color.st_id) JOIN ARTIST artist on script.artist_id=artist.artist_id WHERE (st.genre like lower('%%%s%%')) and script.artist_id=color.artist_id", table1, table2, genre);
				
				break;
			case 12:
				//HOW TO RUN THIS KIND OF QUERY IN JAVA?
				//predefQ = "CREATE TABLE TEMP (langid INTEGER,pub_id INTEGER, PRIMARY KEY(pub_id,langid)); BEGIN FOR rec IN (SELECT top_pub_id FROM (SELECT ser.pub_id as top_pub_id FROM SERIES ser GROUP BY ser.pub_id ORDER BY count(ser.pub_id) DESC)WHERE ROWNUM<=10) LOOP INSERT INTO TEMP (langid, pub_id) SELECT distinct ser6.langid as langid, ser6.pub_id as pub_id FROM (SELECT langid as langidFROM (SELECT ser5.langid as langid, count(*) FROM SERIES ser5 WHERE ser5.pub_id=rec.top_pub_id GROUP BY ser5.langid ORDER BY count(*) DESC) WHERE ROWNUM<=3)haha JOIN SERIES ser6 on ser6.langid=haha.langid and ser6.pub_id=rec.top_pub_id; END LOOP; END; SELECT pub_name,lang_name FROM (TEMP temp JOIN LANG lang on temp.langid=lang.l_id)JOIN PUBLISHER pub on pub.pub_id=temp.pub_id; DROP TABLE TEMP";
				
				break;
			case 13:
				String publishednum = predefJT[0].getText();
				pubtype = "";
				if(comboName1.equals("books")){
					pubtype = "1";
				}
				else if(comboName1.equals("magazines")){
					pubtype = "2";
				}
				else if(comboName1.equals("albums")){
					pubtype = "3";
				}
				
				predefQ = String.format("SELECT cnt, L.lang_name FROM (SELECT count(st_id) as cnt, langid FROM (SELECT ST.st_id, S.langid FROM STORY ST, ISSUE I, SERIES S WHERE ST.st_id NOT IN (SELECT SR.st_target_id FROM STORYREPRINT SR) AND ST.iss_id=I.iss_id AND I.s_id=S.s_id AND pub_type_id=%s) GROUP BY langid ORDER BY cnt DESC), LANG L WHERE cnt>=%s", pubtype, publishednum);
				break;
			case 14:
				
				
				String langid = "";
				
				if(comboName1.equals("Italian")){
					langid = "51";
				}
				else if(comboName1.equals("Hebrew")){
					langid = "52";
				}
				else if(comboName1.equals("Dutch")){
					langid = "82";
				}
				else if(comboName1.equals("Polish")){
					langid = "88";
				}
				else if(comboName1.equals("Malay")){
					langid = "77";
				}
				else if(comboName1.equals("Esperanto")){
					langid = "26";
				}
				else if(comboName1.equals("Swahili")){
					langid = "113";
				}
								
				
				pubtype = "";
				if(comboName2.equals("book")){
					pubtype = "1";
				}
				else if(comboName2.equals("magazine")){
					pubtype = "2";
				}
				else if(comboName2.equals("album")){
					pubtype = "3";
				}
				predefQ = String.format("SELECT STP.stp_name FROM STORY_TYPE STP WHERE STP.stp_id NOT IN (SELECT ST.st_type_id FROM STORY ST WHERE ST.iss_id IN(SELECT I.iss_id FROM SERIES S, ISSUE I WHERE S.pub_type_id=%s AND S.langid = %s AND S.s_id=I.s_id))", pubtype, langid);
				break;
			case 15:
				String storytypename = comboName1;

				predefQ = String.format("SELECT art.artist_name FROM (SELECT distinct script.artist_id as artist_id FROM STORY st JOIN SCRIPTBY script on script.st_id=st.st_id WHERE st.ST_TYPE_ID=(SELECT stp.stp_id FROM STORY_TYPE stp WHERE stp.stp_name like '%%%s%%') and st_title is not null) cartoon,(SELECT distinct artist_id as artist_id FROM(SELECT iss1.ind_id as ind_id,script1.artist_id as artist_id ,count (script1.artist_id)over (partition by script1.artist_id) as ind_count_for_art FROM (SCRIPTBY script1 JOIN STORY st1 on st1.st_id=script1.st_id) JOIN ISSUE iss1 on st1.iss_id=iss1.iss_id WHERE iss1.ind_id is not null GROUP BY iss1.ind_id,script1.artist_id ORDER BY script1.artist_id) WHERE IND_COUNT_FOR_ART>1) indpub,ARTIST art WHERE cartoon.artist_id=indpub.artist_id and art.artist_id=indpub.artist_id", storytypename);
				break;
			case 16:
				String AscOrDesc = "";
				if(comboName1.equals("highest")){
					AscOrDesc = "desc";
				}
				else if(comboName1.equals("lowest")){
					AscOrDesc = "asc";
				}
				topN = predefJT[0].getText();
				
				predefQ = String.format("SELECT brand1.b_name FROM (SELECT brand_id FROM(SELECT brand_id,count(*) as count_ind FROM(SELECT brand.b_id as brand_id,ind.ind_id as ind_id FROM (INDICIA_PUBLISHER ind JOIN PUBLISHER pub on ind.pub_id=pub.pub_id) JOIN BRAND_GROUP brand on brand.pub_id=pub.pub_id GROUP BY brand.b_id,ind.ind_id)GROUP BY brand_id ORDER BY count_ind %s, brand_id asc) WHERE ROWNUM<=%s),BRAND_GROUP brand1 WHERE brand1.b_id=brand_id", AscOrDesc, topN);
				
				break;
			case 17:
				//rounded the average.
				predefQ = "SELECT avg_ser_len,ind1.ind_name FROM (SELECT round(avg(ser_len), 2) as avg_ser_len,ind_id FROM (SELECT (EXTRACT(year FROM ser.series_yr_e)-EXTRACT(YEAR FROM ser.series_yr_b)) as ser_len, ser.s_id as ser_id,ind.ind_id as ind_id FROM (INDICIA_PUBLISHER ind JOIN ISSUE iss on iss.ind_id=ind.ind_id) JOIN SERIES ser on ser.s_id=iss.s_id WHERE (ser.series_yr_e is not null) and EXTRACT(year FROM ser.series_yr_e)-EXTRACT(YEAR FROM ser.series_yr_b)>0)GROUP BY ind_id) temp_table,INDICIA_PUBLISHER ind1 WHERE ind1.ind_id=temp_table.ind_id order by temp_table.ind_id";
				
				break;
			case 18:
				String numissues = "";
				if(comboName1.equals("single")){
					numissues = "1";
				}
				else if(comboName1.equals("double")){
					numissues = "2";
				}
				predefQ = String.format("SELECT ind1.ind_name FROM( SELECT ind_id FROM (SELECT ind_id,count(*) FROM(SELECT ser_id,iss_id,ind_id,count(*) over(partition by ser_id) as ser_count FROM (SELECT iss.iss_id as iss_id,ser.s_id as ser_id,ind.ind_id as ind_id FROM (INDICIA_PUBLISHER ind JOIN ISSUE iss on iss.ind_id=ind.ind_id) JOIN SERIES ser on ser.s_id=iss.s_id))WHERE ser_count=%s GROUP BY ind_id order by count(*) desc)WHERE ROWNUM<=10) top_ind_id,INDICIA_PUBLISHER ind1 WHERE top_ind_id.ind_id=ind1.ind_id", numissues);

				break;
			case 19:
				table1 = "";
				
				if(comboName1.equals("script writers")){
					table1 = "SCRIPTBY";
				}
				else if(comboName1.equals("pencil-workers")){
					table1 = "PENCILBY";
				}
				else if(comboName1.equals("inkers")){
					table1 = "INKBY";
				}
				else if(comboName1.equals("colorers")){
					table1 = "COLORBY";
				}
				
				topN = predefJT[0].getText();
				
				predefQ = String.format("SELECT ind.ind_name FROM(((SELECT *  FROM (SELECT script.st_id,count(*)as art_count FROM %s script GROUP BY script.st_id ORDER BY script.ST_ID) ORDER BY ART_COUNT DESC) temp JOIN STORY st on st.st_id=temp.ST_ID)JOIN ISSUE iss on iss.iss_id=st.iss_id) JOIN INDICIA_PUBLISHER ind on ind.ind_id=iss.IND_ID WHERE (iss.ind_id is not null) and ROWNUM<=%s", table1, topN);
				break;
			case 20:
				predefQ = "SELECT chara.character_name FROM (((INDICIA_PUBLISHER ind JOIN ISSUE iss on ind.ind_id=iss.ind_id) JOIN STORY st on st.iss_id=iss.iss_id)JOIN CHARA_IN_STORY chara_st on chara_st.st_id=st.st_id) JOIN CHARACTER chara on chara_st.character_id=chara.character_id WHERE ind.ind_name like '%DC Comics%' and ind.ind_name like '%Marvel%' GROUP BY chara.character_name ORDER BY count(*)";
				break;
			case 21:
				col1 = "";
				String col2 = "";
				table1 = "";
				topN = "";
				topN = predefJT[0].getText();
				
				if(comboName1.equals("series")){
					table1 = "SERIES";
					col1 = "s_id";
					col2 = "s_name";
				}
				else if(comboName1.equals("indicia publishers")){
					table1 = "INDICIA_PUBLISHER";
					col1 = "ind_id";
					col2 = "ind_name";
				}
				
				predefQ = String.format("SELECT ser.%s FROM(SELECT iss.%s as ser_id,count(*) as count_ser FROM ISSUE iss GROUP BY iss.%s ORDER BY count_ser desc),%s ser WHERE ROWNUM<=%s and ser.%s=ser_id", col2, col1, col1, table1, topN, col1);
				break;
			case 22:
				String givenissid = predefJT[0].getText();
				predefQ = String.format("SELECT st1.st_title FROM(SELECT stre.st_origin_id as story_id FROM (ISSUE iss JOIN STORY st on st.iss_id=iss.iss_id) JOIN STORYREPRINT stre on stre.st_origin_id=st.st_id WHERE iss.iss_id=%s GROUP BY stre.st_origin_id ORDER BY count(*)DESC),STORY st1 WHERE ROWNUM=1 and st1.st_id=story_id", givenissid);
				
				break;
			}
			
			secondquarter.removeAll();
			secondquarter.revalidate();
			secondquarter.repaint();
			
			
			if(predefNum == 12){
				dp.processSingleStmt("CREATE TABLE TEMP2 (langid INTEGER,pub_id INTEGER, PRIMARY KEY(pub_id,langid))");
				dp.processSingleStmt2(String.format("BEGIN FOR rec IN (SELECT top_pub_id FROM ( SELECT ser.pub_id as top_pub_id FROM SERIES ser GROUP BY ser.pub_id ORDER BY count(ser.pub_id) DESC) WHERE ROWNUM<=%s) LOOP INSERT INTO TEMP2 (langid, pub_id) SELECT distinct ser6.langid as langid, ser6.pub_id as pub_id FROM ( SELECT langid as langid FROM ( SELECT ser5.langid as langid, count(*) FROM SERIES ser5 WHERE ser5.pub_id=rec.top_pub_id GROUP BY ser5.langid ORDER BY count(*) DESC) WHERE ROWNUM<=%s)haha JOIN SERIES ser6 on ser6.langid=haha.langid and ser6.pub_id=rec.top_pub_id; END LOOP; END;", comboName1, comboName2));
				dp.changeQuery("SELECT pub_name,lang_name FROM (TEMP2 temp JOIN LANG lang on temp.langid=lang.l_id)JOIN PUBLISHER pub on pub.pub_id=temp.pub_id");
				dp.processQuery();
				
				//dp.processSingleStmt2("BEGIN FOR rec IN (SELECT top_pub_id FROM ( SELECT ser.pub_id as top_pub_id FROM SERIES ser GROUP BY ser.pub_id ORDER BY count(ser.pub_id) DESC) WHERE ROWNUM<=10) LOOP INSERT INTO TEMP (langid, pub_id) SELECT distinct ser6.langid as langid, ser6.pub_id as pub_id FROM ( SELECT langid as langid FROM ( SELECT ser5.langid as langid, count(*) FROM SERIES ser5 WHERE ser5.pub_id=rec.top_pub_id GROUP BY ser5.langid ORDER BY count(*) DESC) WHERE ROWNUM<=3)haha JOIN SERIES ser6 on ser6.langid=haha.langid and ser6.pub_id=rec.top_pub_id; END LOOP; END;");
				//dp.processSingleStmt("SELECT pub_name,lang_name FROM (TEMP temp JOIN LANG lang on temp.langid=lang.l_id)JOIN PUBLISHER pub on pub.pub_id=temp.pub_id");
				show.setText(dp.exportResult());
				dp.processSingleStmt("DROP TABLE TEMP2");
				//show.setFont(new Font("Monospaced", Font.BOLD, 12));
				//show.setEditable(false);
				
			}
			else{
				System.out.println(predefQ);
				dp.changeQuery(predefQ);
				dp.processQuery();
				queryResult = dp.exportResult();
				//show.setFont(new Font("Monospaced", Font.BOLD, 12));
				show.setText(queryResult);
				//show.setEditable(false);
			}
			show.setFont(new Font("Monospaced", Font.BOLD, 12));
			show.setEditable(false);

		}


		
	}
	
	public void secondquarterRemove(){
		secondquarter.removeAll();		
		secondquarter.revalidate();
		secondquarter.repaint();
	}
	
	public void secondquarterRevalidate(){
		secondquarter.revalidate();
		secondquarter.repaint();
		secondtab.revalidate();
		secondtab.repaint();
	}
	

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 18; i++){
			if(e.getSource() == jtItem[i]) jtItem[i].setText("");
		}
		for(int i = 0; i < 5; i++){
			if(e.getSource() == predefJT[i]) predefJT[i].setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// Not used
		// TODO Auto-generated method stub
		;
	}
}