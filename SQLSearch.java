package interfacepack;
import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class SQLSearch implements ActionListener, ItemListener{
	JPanel searchpanel;
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	

	JPanel thirdPanel;
	JPanel fourthPanel;
	JPanel SubmitOrClear;
	JPanel firstquarter;
	
	JPanel forcheckbox1;
	JPanel forcheckbox2;
	JPanel forcheckbox3;
	JPanel secondquarter;
	
	JPanel firsthalf;
	JPanel secondhalf;
	/** make a TextArea object **/

	
	JTextArea searchBox = new JTextArea();
	JScrollPane searchboxJP;

	
	JButton submit = new JButton("Submit");
	JButton clear = new JButton("Clear");
	JCheckBox advanced = new JCheckBox("Advanced Search");
	boolean isAdvanced = false;
	
	String tableName[] = new String[20];
	JCheckBox tableCheck[] = new JCheckBox[20];
	
	JLabel fillBelow[] = new JLabel[4];
	
	ArrayList<Integer> tableForAdvSearch = new ArrayList<Integer>();

	String keyword = ""; //user input search key word

	String queryResult;
	String promptMessage = "Depending on the amount of data, result loading might take some time.";
	JTextArea show = new JTextArea(promptMessage);	
	JScrollPane scrollpane = new JScrollPane(show, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	
	
	
	public SQLSearch(JPanel searchpanel) {
		this.searchpanel = searchpanel;
		this.secondhalf = new JPanel();
		this.thirdPanel = new JPanel();
		this.fourthPanel = new JPanel();
		this.SubmitOrClear = new JPanel();
		this.firstquarter = new JPanel();
		this.secondquarter = new JPanel();
		this.firsthalf = new JPanel();
		this.forcheckbox1 = new JPanel();
		this.forcheckbox2 = new JPanel();
		this.forcheckbox3 = new JPanel();
		
		advancedInits();
	}
	
	
	
	public void advancedInits(){
		GridLayout horizontalHalf = new GridLayout(2, 0);
		GridLayout verticalHalf = new GridLayout(0, 2);
		GridLayout row3 = new GridLayout(3, 0);
		GridLayout col7 = new GridLayout(0, 7);
		firstquarter.setLayout(horizontalHalf);
		secondquarter.setLayout(row3);
		firsthalf.setLayout(horizontalHalf);
		searchpanel.setLayout(horizontalHalf);
		SubmitOrClear.setLayout(verticalHalf);
		
		forcheckbox1.setLayout(col7);
		forcheckbox2.setLayout(col7);
		forcheckbox3.setLayout(col7);
		
		searchBox.setMargin(new Insets(25, 20, 20, 20));
		Font searchfont = searchBox.getFont();
		searchBox.setFont(new Font(searchfont.getFontName(), Font.BOLD, 15));
		show.setFont(new Font("Monospaced", Font.BOLD, 12));
		searchBox.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		searchboxJP = new JScrollPane(searchBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
		
		thirdPanel.setLayout(new GridLayout(1, 1));
		thirdPanel.add(searchboxJP);
		
		fourthPanel.setLayout(new GridLayout(0, 2));
		SubmitOrClear.add(submit);
		SubmitOrClear.add(clear);
		fourthPanel.add(SubmitOrClear);
		fourthPanel.add(advanced);
		
		
		for (int i = 0; i < 4; i++){
			fillBelow[i] = new JLabel();
		}
		
		secondhalf.setLayout(new BorderLayout());
		

		show.setMargin( new Insets(15, 15, 15, 15));
		secondhalf.add(scrollpane, BorderLayout.CENTER);
		
		advanced.setHorizontalAlignment(SwingConstants.CENTER);
	
		firstquarter.add(thirdPanel);
		firstquarter.add(fourthPanel);
		
		secondquarter.add(forcheckbox1);
		secondquarter.add(forcheckbox2);
		secondquarter.add(forcheckbox3);
		
		firsthalf.add(firstquarter);
		firsthalf.add(secondquarter);
		
		searchpanel.add(firsthalf);
		searchpanel.add(secondhalf);

		addActionListeners();
		addItemListeners();
	}

	
	public JPanel getSearchPanel() {
		return this.searchpanel;
	}
	
	public void setTableNameArray(String []arr){
		for(int i = 0; i < 20; i++){
			tableName[i] = arr[i+1];
		}
		MainWindowFrame.dp.setTableNameArray(tableName);
	}
	
	public void setAdditionalCheckBox(){
		for(int i = 0; i < 20; i++){
			tableCheck[i] = new JCheckBox(tableName[i]);	//discard "select table name"
		}

		Font titlefont = tableCheck[0].getFont();
		
		for(int i = 0; i< 20; i++){
	        tableCheck[i].setFont(new Font(titlefont.getFontName(), Font.ITALIC, 10));	// font size smaller
		}
		
		addCheckboxItemListeners();
	
	}
	
	public JButton getGoButton(){
		return this.submit;
	}
	

	public void saveSearchHistory() throws IOException {
		/** FileWriter save **/
		
		String saveString = "--Search Log--\n"+"search word: "+searchBox.getText()+"\n"+new Date().toString()+"\n";
		//System.out.println(saveString);
		
		
		//FileWriter fw = new FileWriter("/home/tigerlily/Desktop/DBresult.txt", true);
		FileWriter fw = new FileWriter("./DBresult.txt", true);
		fw.write(saveString);
		fw.close();
	}

	public void addItemListeners(){
		advanced.addItemListener(this);
	}
	
	public void addActionListeners(){
		submit.addActionListener(this);
		clear.addActionListener(this);		
	}
	
	public void addCheckboxItemListeners(){
		for(int i = 0; i < 20; i++){
			tableCheck[i].addItemListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == submit){//press search button
			keyword = searchBox.getText();
			if(keywordFilter(keyword)){
				show.setText("No nullstring allowed in search box!");
				show.setEditable(false);
			}
			else{
				System.out.println("Getting results for keyword "+ keyword);
				MainWindowFrame.dp.processMainSearchQuery(keyword, isAdvanced, tableForAdvSearch);
				queryResult = MainWindowFrame.dp.exportResult();
				show.setText(queryResult);
				show.setEditable(false);	// no result editing. it works!
			}
		}
		else if(e.getSource() == clear){
			searchBox.setText("");
			tableForAdvSearch.clear();
			for(JCheckBox b:tableCheck){
				//deselect the table checkboxes
				b.setSelected(false);
			}
			show.setText(promptMessage);
		}
	}

	
	public boolean keywordFilter(String kw){
		//check if user input keyword is blank.
		boolean isBlank = false;
		for(int i = 0; i < kw.length(); i++){
			if(kw.charAt(i)!=' ' && kw.charAt(i)!='\0' && kw.charAt(i)!='\n'){
				return isBlank;
			}
		}
		isBlank = true;
		return isBlank;
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getStateChange() == ItemEvent.SELECTED){
			//	Advanced search option
			if(e.getSource() == advanced){
				isAdvanced = true;
				
				System.out.println("Advanced Search Mode.");
				for(int i = 0; i < 7; i++){
					forcheckbox1.add(tableCheck[i]);
				}
				for(int i = 7; i < 14; i++){
					forcheckbox2.add(tableCheck[i]);
				}
				for(int i = 14; i < 20; i++){
					forcheckbox3.add(tableCheck[i]);
				}

			}
			else{
				// Each table check
				for(int i = 0; i < 20; i++){
					if(e.getSource() == tableCheck[i]){
						tableForAdvSearch.add(i);
					}
				}
				
				for(int x:tableForAdvSearch){
					System.out.print(tableName[x]+" ");
				}
				System.out.println("");
				
			}
		}
		else{//deselected
			if(e.getSource() == advanced){
				isAdvanced = false;
				System.out.println("Plain Search Mode.");
				
				//remove the table checkboxes
				for(int i = 0; i < 7; i++){
					forcheckbox1.remove(tableCheck[i]);
				}
				for(int i = 7; i < 14; i++){
					forcheckbox2.remove(tableCheck[i]);
				}
				for(int i = 14; i < 20; i++){
					forcheckbox3.remove(tableCheck[i]);
				}
			}
			else{
				for(int i = 0; i < 20; i++){
					if(e.getSource() == tableCheck[i]){
						for(String x:tableName){
							if(tableCheck[i].getText().equals(x)){
								tableForAdvSearch.remove(new Integer(i));								
							}
						}
					}
				}		
				
				// check total selected table names
				for(int x:tableForAdvSearch){
					System.out.print(tableName[x]+" ");
				}
				System.out.println("");
				
			}
		}

		searchpanel.revalidate();
		searchpanel.repaint();
	}


}
