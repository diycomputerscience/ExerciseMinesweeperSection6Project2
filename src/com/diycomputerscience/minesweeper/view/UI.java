package com.diycomputerscience.minesweeper.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.RandomMineInitializationStrategy;
import com.diycomputerscience.minesweeper.Square;

public class UI extends JFrame {
	
	private Board board;
	
	public UI(Board board) {
		// set this.board to the injected Board
		this.board = board;
		
		// Set the title to "Minesweeper"
		this.setTitle("Minesweeper");
				
		JPanel panel = new JPanel();
		
		// Set the name of the panel to "MainPanel" 
		panel.setName("MainPanel");
		
		// Set the layout of panel to GridLayout. Be sure to give it correct dimensions
		panel.setLayout(new GridLayout(Board.MAX_ROWS, Board.MAX_COLS));
		
		// add squares to the panel
		this.layoutSquares(panel);
		// add panel to the content pane
		this.getContentPane().add(panel);
		
		// validate components
		//this.validate();
	}
	
	private void layoutSquares(JPanel panel) {
		// TODO: Add code anywhere in this method to display the count on all squares that are not mines
		
		Square squares[][] = this.board.getSquares();
					
		for(int row=0; row<Board.MAX_ROWS; row++) {
			for(int col=0; col<Board.MAX_COLS; col++) {
				JButton squareUI = new JButton();
				squareUI.setName(row+","+col);
				if(squares[row][col].isMine()) {
					squareUI.setBackground(Color.RED);
				} else {
					squareUI.setText(String.valueOf(squares[row][col].getCount()));
				}
				panel.add(squareUI);
			}
		}
	}
	
	public static UI build(Board board) {
		UI ui = new UI(board);
		ui.setSize(300, 400);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		return ui;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				build(new Board());
			}
					
		});		
	}

}
