package game.ui;

import game.GameofLifeRules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class GameofLifeFrame extends JFrame{
    
    private static final long serialVersionUID = 1L;
	
    private static Timer timer;
    
    JPanel gridPanel;
	
	JPanel controlPanel;
	
	private static final String UNDERSCORE = "_";
	
	private static final int[] gridSizeArray = {5, 10, 15, 20};
    
    private int gridSize;
    
    protected void frameInit(){
    	
    	    	 
        try {
		
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
        } catch(Exception e){
            e.printStackTrace();
        }
        
        setSize(500, 500);
		
        timer = new Timer(1000, this::nextGeneration);  
        super.frameInit();
       
        getContentPane().setLayout(new BorderLayout());
        
        gridPanel = new JPanel();
        controlPanel = new JPanel();
        gridSize = 5;
        
        gridPanel.setLayout(new GridLayout(gridSize,gridSize));
        gridPanel.setSize(500, 1000);
                
        for(int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++){
                JButton button = new JButton();
                button.setName(i + UNDERSCORE + j);
                button.addActionListener(this::toggleCell);
                button.setBackground(Color.WHITE);
                gridPanel.add(button);
            }
        }
        
        controlPanel.setLayout(new GridLayout(1,4));
        controlPanel.setSize(500, 0);
        
                
        JLabel comboLabel = new JLabel();
        comboLabel.setText("Choose Grid Size: ");
        controlPanel.add(comboLabel);
        
        String[] sizeArray = {"5x5", "10x10", "15x15", "20x20"};
        JComboBox<String> gridSizeCombo = new JComboBox<String>(sizeArray);
        gridSizeCombo.setName("GridSizeCombo");
        gridSizeCombo.setSelectedIndex(0);
        gridSizeCombo.setEnabled(true);
        gridSizeCombo.addActionListener(this::resizeGrid);
        controlPanel.add(gridSizeCombo);
        
        JButton startButton = new JButton();
        setButtonProperties(startButton, "Start", Color.GREEN, controlPanel);
        
        JButton stopButton = new JButton();
        setButtonProperties(stopButton, "Stop", Color.RED, controlPanel);
        
      
        add(gridPanel,BorderLayout.CENTER);
        add(controlPanel,BorderLayout.SOUTH);
                
    }
    
    @SuppressWarnings("unchecked")
	public void resizeGrid(ActionEvent e){
    	
    	JComboBox<String> theSize = (JComboBox<String>)e.getSource();
    	
    	gridSize = gridSizeArray[theSize.getSelectedIndex()];
    	
    	gridPanel.removeAll();
    	
    	gridPanel.setLayout(new GridLayout(gridSize,gridSize));
        gridPanel.setBounds(0, 500, 500, 400);
                
        for(int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++){
                JButton button = new JButton();
                button.setName(i + UNDERSCORE + j);
                button.addActionListener(this::toggleCell);
                button.setBackground(Color.WHITE);
                gridPanel.add(button);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    	
    }
    
    public void setButtonProperties(JButton button, String name, Color color, JPanel controlPanel){
    	
    	button.setName(name);
    	button.setText(name);
    	button.setBackground(color);
    	button.addActionListener(this::handleTimer);
    	controlPanel.add(button);
    	
    }    
   

    public void handleTimer (ActionEvent e){ 
       
    	JButton theButton = (JButton) e.getSource();
        String name = theButton.getName();
       
        if(name.equalsIgnoreCase("Start")){
             timer.setInitialDelay(0);
             timer.start();
         } else if (name.equalsIgnoreCase("Stop")){
             timer.stop();
         }
         
     }
    
    
    public void toggleCell (ActionEvent e){
        
    	JButton theButton = (JButton) e.getSource();
         
        if (theButton.getBackground().equals(Color.WHITE)) {
             theButton.setBackground(Color.BLACK);
        } else {
             theButton.setBackground(Color.WHITE);
        }
        
     }
    
    
    
    public void nextGeneration (ActionEvent e){
        
    	boolean[][] nextGrid = new boolean[gridSize][gridSize];
        
        nextGrid = GameofLifeRules.nextGeneration(getCurrentGrid());
        paintNextGeneration(nextGrid);
        
    }
    
    public boolean[][] getCurrentGrid(){
        boolean[][] currentGrid = new boolean[gridSize][gridSize];
        Component[] components = gridPanel.getComponents();
        for(int i = 0; i < components.length; i++){
            if(components[i] instanceof JButton){
                JButton cell = (JButton) components[i];
                if(cell.getName().contains(UNDERSCORE) && cell.getBackground().equals(Color.BLACK)){
                    int x = Integer.parseInt(cell.getName().substring(0, cell.getName().indexOf(UNDERSCORE)));
                    int y = Integer.parseInt(cell.getName().substring(cell.getName().indexOf(UNDERSCORE) + 1, cell.getName().length()));;
                    currentGrid[x][y] = true;
                }
            }
        }
        
        return currentGrid;
    }
    
    
    public void paintNextGeneration(boolean[][] nextGrid){
    	
    	 Component[] components = gridPanel.getComponents();
                
        for(int i = 0; i < components.length; i++){
        	if(components[i] instanceof JButton){
                JButton cell = (JButton) components[i];
                
                if(cell.getName().contains(UNDERSCORE)){
                    int x = Integer.parseInt(cell.getName().substring(0, cell.getName().indexOf(UNDERSCORE)));
                    int y = Integer.parseInt(cell.getName().substring(cell.getName().indexOf(UNDERSCORE) + 1, cell.getName().length()));
                    
                    if(nextGrid[x][y]){
                        cell.setBackground(Color.BLACK);
                    } else {
                        cell.setBackground(Color.WHITE);
                    }					
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        GameofLifeFrame frame = new GameofLifeFrame();
        frame.setSize(500, 500);
        frame.setTitle("Conway's Game of Life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
