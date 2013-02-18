package com.diycomputerscience.minesweeper.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.Square;

public class UITest extends BaseSwingTestCase {
	
	private FrameFixture window;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.window = new FrameFixture(robot(), createUI());
		this.window.show();
	}

	@After
	public void tearDown() throws Exception {
		this.window.cleanUp();
		super.tearDown();
	}

	@Test
	public void testUIVisibility() {
		
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				window.requireVisible();
			}			
		});
						
	}

	@Test
	public void testUIDefaultCloseOperation() {
		// Make Swing calls are run on the EDT
		int defaultCloseOperation = GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return ((JFrame)window.target).getDefaultCloseOperation();			
			}			
		});
		
		// Verify
		assertEquals(JFrame.EXIT_ON_CLOSE, defaultCloseOperation);
	}
	
	@Test
	public void testUITitle() {
		// Make Swing calls are run on the EDT
		String actualTitle = GuiActionRunner.execute( new GuiQuery<String>() {
			@Override
			protected String executeInEDT() throws Throwable {
				return window.target.getTitle();			
			}			
		});
		
		// Verify
		assertEquals("Minesweeper", actualTitle);
	}
	
	@Test
	public void testMainPanel() {
		// Make Swing calls are run on the EDT
		final JPanel mainPanel = GuiActionRunner.execute(new GuiQuery<JPanel>() {
			@Override
			protected JPanel executeInEDT() throws Throwable {
				return window.panel("MainPanel").target;
			}			
		});
		
		// verify that the contentPane contains a JPanel called "MainPanel"
		assertNotNull(mainPanel);
		
		// verify that the layoutManaget of the mainPanel is GridLayout		
		assertEquals(GridLayout.class, mainPanel.getLayout().getClass());
		
		// verify the dimensions of the GridLayout
		assertEquals(Board.MAX_ROWS, ((GridLayout)mainPanel.getLayout()).getRows());
		assertEquals(Board.MAX_COLS, ((GridLayout)mainPanel.getLayout()).getColumns());	
	}
	
	@Test
	public void testSquares() {
		
		Component components[] = GuiActionRunner.execute(new GuiQuery<Component[]>(){

			@Override
			protected Component[] executeInEDT() throws Throwable {
				JPanel mainPanel = window.panel("MainPanel").target;				
				return mainPanel.getComponents();
			}
			
		});
				
		// verify that the mainPanel has Board.MAX_ROWS x Board.MAX_COLS components
		assertEquals(Board.MAX_ROWS*Board.MAX_COLS, components.length);
		
		// verify that each component in the mainPanel is a JButton
		for(Component component : components) {
			assertEquals(JButton.class, component.getClass());
		}
	}
	
	@Test
	public void testMineIdentificationOnUI() throws Exception {		
		final Square squares[][] = this.board.getSquares();
		
		GuiActionRunner.execute(new GuiTask() {

			@Override
			protected void executeInEDT() throws Throwable {
				for(int row=0; row<squares.length; row++) {
					for(int col=0; col<squares.length; col++) {
						JButtonFixture squareUI = window.button(row+","+col);				
						if(squares[row][col].isMine()) {
							assertEquals(Color.RED, squareUI.background().target());
						} else {
							assertFalse(Color.red.equals(squareUI.background().target()));
						}
					}
				}				
			}
			
		});
	}
	
	@Test
	public void testCountOnSquares() throws Exception {	
		final Square squares[][] = this.board.getSquares();
		
		GuiActionRunner.execute(new GuiTask() {

			@Override
			protected void executeInEDT() throws Throwable {
				for(int row=0; row<squares.length; row++) {
					for(int col=0; col<squares.length; col++) {
						JButtonFixture squareUI = window.button(row+","+col);
						if(squares[row][col].isMine()) {
							squareUI.requireText("");					
						} else {
							squareUI.requireText(String.valueOf(squares[row][col].getCount()));
						}
					}
				}
			}
			
		});
	}
}
