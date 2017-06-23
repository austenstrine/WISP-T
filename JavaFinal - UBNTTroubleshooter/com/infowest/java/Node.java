package com.infowest.java;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
//import javax.swing.tree.DefaultMutableTreeNode.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.io.*;
import java.util.ArrayList;

public class Node extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4089171883331980678L;
	
	ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>(4);
	
	JTextArea titleTxt, 
			contentTxt,
			tip1,
			tip2,
			tip3,
			tip4,
			tip5,
			tip6;
	JButton acceptChanges,
			cancel,
			packer,
			addTip,
			removeTip;
	JPanel contentPane,
			center,
				centerTipBtns,
			south,
			north;
	JScrollPane centerScroll,
			southScroll;
	JTextArea[] tips = {null,null,null,null,null,null};
	private int switcher = 0;
	
	
	public Node()
	{
		super();
		Dimension d = new Dimension(800,400);
		this.setPreferredSize(d);
		try
		{
			File img = new File("wisp-t 20.png");
			BufferedImage bufferedImage = ImageIO.read(img);
			icons.add(bufferedImage);
			img = new File("wisp-t 250.png");
			bufferedImage = ImageIO.read(img);
			icons.add(bufferedImage);
			img = new File("wisp-t 500.png");
			bufferedImage = ImageIO.read(img);
			icons.add(bufferedImage);
			img = new File("wisp-t 750.png");
			bufferedImage = ImageIO.read(img);
			icons.add(bufferedImage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.setIconImages(icons);
		this.setTitle("Node");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			contentPane = new JPanel();
		this.setContentPane(contentPane);
		
			contentPane.setLayout(new BorderLayout());
			
			//north
				north = new JPanel(new FlowLayout());
			contentPane.add(north, BorderLayout.NORTH);
			
					packer = new JButton();
				north.add(packer);
				
					packer.addActionListener(this);
					Dimension packerD = new Dimension(10,10);
					packer.setPreferredSize(packerD);
					packer.setMaximumSize(packerD);
					packer.setMinimumSize(packerD);
					
					
			//center
					center = new JPanel();
				centerScroll = new JScrollPane(center);
			contentPane.add(centerScroll, BorderLayout.CENTER);
			
					center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
					
						titleTxt = new JTextArea("title");
					center.add(titleTxt);
					
						titleTxt.setBorder(BorderFactory.createTitledBorder("Node Title"));
						
						contentTxt = new JTextArea("content");
					center.add(contentTxt);
						
						contentTxt.setBorder(BorderFactory.createTitledBorder("Node Content"));
						

						centerTipBtns = new JPanel(new GridLayout(1,2));
					center.add(centerTipBtns);
					
							addTip = new JButton("Add Tip");
						centerTipBtns.add(addTip);
					
							addTip.addActionListener(this);
						
							removeTip = new JButton("Remove Tip");
						centerTipBtns.add(removeTip);
						
							removeTip.addActionListener(this);
						
						for(int i = 0; i < tips.length; ++i)
						{
							tips[i] = new JTextArea("Tip");
							tips[i].setRows(5);
							tips[i].setVisible(false);
							tips[i].setBorder(BorderFactory.createTitledBorder("Tip "+(i+1)));
						}
						tip1 = tips[0];
						tip2 = tips[1];
						tip3 = tips[2];
						tip4 = tips[3];
						tip5 = tips[4];
						tip6 = tips[5];
						
			//south
					south = new JPanel(new GridLayout(1,2));
				southScroll = new JScrollPane(south);
			contentPane.add(southScroll, BorderLayout.SOUTH);

						acceptChanges = new JButton("Accept Changes");
					south.add(acceptChanges);
					
						acceptChanges.addActionListener(this);
		
						cancel = new JButton("Cancel");
					south.add(cancel);
					
						cancel.addActionListener(this);
		pack();
		
	}
	


	public void setNodeTitle(String t)
	{
		titleTxt.setText(t);
	}
	public void setNodeContent(String c)
	{
		contentTxt.setText(c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source == acceptChanges)
		{
			Stringable stringable = new Stringable(titleTxt.getText(), contentTxt.getText());
			String[] stringTips = {null,null,null,null,null,null};
			if(tip1.isVisible())
				stringTips[0] = tip1.getText();
			else
				stringTips[0] = null;
			if(tip2.isVisible())
				stringTips[1] = tip2.getText();
			else
				stringTips[1] = null;
			if(tip3.isVisible())
				stringTips[2] = tip3.getText();
			else
				stringTips[2] = null;
			if(tip4.isVisible())
				stringTips[3] = tip4.getText();
			else
				stringTips[3] = null;
			if(tip5.isVisible())
				stringTips[4] = tip5.getText();
			else
				stringTips[4] = null;
			if(tip6.isVisible())
				stringTips[5] = tip6.getText();
			else
				stringTips[5] = null;
			
			stringable.setTips(stringTips);
			DefaultMutableTreeNode savedNode = new DefaultMutableTreeNode(stringable);
			
			try 
			{
		         FileOutputStream fileOut = new FileOutputStream("node.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(savedNode);
		         out.close();
		         fileOut.close();
		         System.out.println("Object saved in node.ser");
		    }
			catch(IOException i) 
			{
		         i.printStackTrace();
			}
			this.setVisible(false);
			this.dispose();
		}
		else if(source == cancel)
		{
			this.dispose();
		}
		else if (source == packer)
		{
			pack();
		}
		else if(source == addTip)
		{
			addTip(true);
		}
		else if(source == removeTip)
		{
			addTip(false);
		}
	}

	public static void main(String[] args) 
	{
		Node node = new Node();
		node.setModalityType(ModalityType.APPLICATION_MODAL);
		System.out.println(java.util.Arrays.toString(args));
		try 
		{
			node.setNodeTitle(args[0]);
			node.setNodeContent(args[1]);
			
			node.tip1.setText(args[2]);
			if(!(node.tip1.getText() == null))//I can't get this to detect when the string value is null or "null"
			{
				node.center.add(node.tip1);
				node.tip1.setVisible(true);
				node.switcher = 1;
			}
			
			node.tip2.setText(args[3]);
			if(!(node.tip2.getText() == null))
			{
				node.center.add(node.tip2);
				node.tip2.setVisible(true);
				node.switcher = 2;
			}
			
			node.tip3.setText(args[4]);
			if(!(node.tip3.getText() == null))
			{
				node.center.add(node.tip3);
				node.tip3.setVisible(true);
				node.switcher = 3;
			}
			
			node.tip4.setText(args[5]);
			if(!(node.tip4.getText() == null))
			{
				node.center.add(node.tip4);
				node.tip4.setVisible(true);
				node.switcher = 4;
			}
			
			node.tip5.setText(args[6]);
			if(!(node.tip5.getText() == null))
			{
				node.center.add(node.tip5);
				node.tip5.setVisible(true);
				node.switcher = 5;
			}
			
			node.tip6.setText(args[7]);
			if(!(node.tip6.getText() == null))
			{
				node.center.add(node.tip6);
				node.tip6.setVisible(true);
				node.switcher = 6;
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			/*node.setNodeTitle("Title");
			node.setNodeContent("Content");*/
		}
		node.setVisible(true);
	}
	
	public void addTip(boolean add)
	{ 
		if(add)
		{
			switch(switcher)
			{
			case 0:
				center.add(tip1);
				tip1.setVisible(true);
				break;
			case 1:
				center.add(tip2);
				tip2.setVisible(true);
				break;
			case 2:
				center.add(tip3);
				tip3.setVisible(true);
				break;
			case 3:
				center.add(tip4);
				tip4.setVisible(true);
				break;
			case 4:
				center.add(tip5);
				tip5.setVisible(true);
				break;
			case 5:
				center.add(tip6);
				tip6.setVisible(true);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Only six tips per branch/leaf are allowed!");	
				--switcher;
			}
			++switcher;
			System.out.println(switcher);
		}
		else
		{
			switch(switcher)
			{
			case 1:
				center.remove(tip1);
				tip1.setVisible(false);
				break;
			case 2:
				center.remove(tip2);
				tip2.setVisible(false);
				break;
			case 3:
				center.remove(tip3);
				tip3.setVisible(false);
				break;
			case 4:
				center.remove(tip4);
				tip4.setVisible(false);
				break;
			case 5:
				center.remove(tip5);
				tip5.setVisible(false);
				break;
			case 6:
				center.remove(tip6);
				tip6.setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(null, "There are no more tips to remove!");
				++switcher;
			}
			--switcher;
			System.out.println(switcher);
		}
		this.revalidate();
	}
}
