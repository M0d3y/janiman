package org.janiman.gui.addFiles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.janiman.parser.anidb.AnidbApi;

import net.iharder.dnd.FileDrop;
import net.iharder.dnd.FileDrop.Listener;
import net.iharder.dnd.FileDropEvent;
import net.iharder.dnd.FileDropListener;

public class AddFilesView extends JPanel {
	
	FileDrop dropper;
	SwingWorker worker;
	Controller controller;
	File[] files;
	JButton buttonStart;
	JProgressBar progressBar;
	
	
	
	JList list;
	
	public AddFilesView()
	{
		super();

		initComponents();
		setUp();
	}
	private void initComponents()
	{
		controller = new Controller();
		dropper = new FileDrop(this,controller);
		
		list=new JList();
		buttonStart = new JButton("Start");
		buttonStart.setActionCommand("start");
		buttonStart.addActionListener(controller);
		
		progressBar = new JProgressBar();

	}
	private void setUp()
	{
		super.setLayout(new BorderLayout());
		super.add(list,BorderLayout.WEST);
		super.add(buttonStart,BorderLayout.EAST);
		super.add(progressBar,BorderLayout.SOUTH);
	}
	
	
	class Controller implements FileDropListener, Listener, ActionListener
	{

		@Override
		public void filesDropped(FileDropEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void filesDropped(File[] arg0) {
			list.setListData(arg0);
			files=arg0;
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("start"))
			{
				worker = new SwingWorker()
				{
					
					@Override
					protected Object doInBackground() throws Exception {
						progressBar.setIndeterminate(true);
						AnidbApi.getInstance().hashAndAddFiles(new ArrayList<File>(Arrays.asList(files)));
						
						progressBar.setIndeterminate(false);
						return null;
					}
					
				};
				worker.execute();
			}
			
		}
		
	}

}
