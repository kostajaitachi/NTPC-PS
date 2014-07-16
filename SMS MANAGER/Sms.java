/*
*
author : prakhar
*
*/

package ntpc;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Choice;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.border.*;
import org.freixas.jcalendar.*;
import java.io.*;
import java.net.*;

public class Sms
{
// we will declare class variables here
// initialising jframes 
JFrame f1; // f1 - login frame;
JFrame f2; // f2 - features page;
JFrame f3; // f3 - create group page;
JFrame f4; // f4 - add persons to group;
JFrame f5; // f5 - use existing group;
JFrame f6; // f6 - modify groups frame;
JFrame f7; // f7 - modification frame - add members;
JFrame f8; // f8 - modification frame - delete members;
JFrame f9; // f9 - status of messages;
JFrame f10; //f10 - sending individual sms

//various labels used in the application
JLabel l1,img,marker,person,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14; 

//various buttons used in the application
JButton b1,logout,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25;

//various textfields and text areas used in the application
JTextField t1,t2,t3,t4;
JTextArea ta1,ta2;

// various choice elements
Choice c1,c2,c3,c4,c5;

// Database connection elements
Connection c;
Statement s;
ResultSet r1,r2,r3,r4,r5,r6,r7,r8;
String empname,currgrp;
int empid;

// conttrolling parameters
int flag = 0;

// date and time objects
Calendar cal;


	public Sms()
	{
		//making connections with the ODBC-JDBC Bridge
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			c= DriverManager.getConnection("jdbc:odbc:DATA");
			s=c.createStatement();
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		// login frame initialised
		f1 = new JFrame("Login Page");
		f1.setSize(1200,600);
		f1.setLayout(null);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//adding components to the login frame
		img = new JLabel(new ImageIcon("ntpc.png"));
		img.setBounds(20,20,150,90);
		f1.add(img);
		
		marker = new JLabel("NTPC Sms Messenger System");
		marker.setForeground(Color.BLACK);
		marker.setFont(new Font("Tw Cen MT",Font.BOLD,40));
		marker.setBounds(100,140,700,60);
		f1.add(marker);
		
		//help on frame
		JTextArea help = new JTextArea(10,20);
		help.setBounds(700,200,400,200);
		help.setFont(new Font("Consolas",Font.PLAIN,20));
		help.setLineWrap(true);
		help.setWrapStyleWord(true);
		help.setText("Enter your employee id as provided by BTPS to use this service");
		help.setEditable(false);
		help.setForeground(Color.RED);
		f1.add(help);
		
		logout = new JButton("Sign Out");
		logout.setBounds(620,70,160,30);
		
		l1 = new JLabel("Enter Employee ID");
		l1.setForeground(Color.BLUE);
		l1.setFont(new Font("Arial",Font.PLAIN,28));
		l1.setBounds(100,200,600,40);
		f1.add(l1);
		
		t1 = new JTextField(100);
		t1.setBounds(100,240,400,40);
		f1.add(t1);
		
		b1 = new JButton("Enter");
		b1.setBounds(100,300,160,80);
		f1.add(b1);
		
		f1.setVisible(true);
		
		// adding listener to buttons
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				final int temp = Integer.parseInt(t1.getText());
				try
				{
					flag = -1;
					PreparedStatement p = c.prepareStatement("select EmpName,EmpID from members");
					r1 = p.executeQuery();
					while(r1.next())
					{
						int a = Integer.parseInt(r1.getString(2));
						if(a == temp)
						{
							empid = a;
							empname = r1.getString(1);
							flag = 1;
							break;
						}
					}
					p.close();
					
				}
				
				catch(Exception e)
				{
					flag = -1;
					System.out.println("Exception Occured");
					e.printStackTrace();
				}
				
				if(flag == -1)
				{
					t1.setText(null);
					JOptionPane.showMessageDialog(null,"Invalid Login");
				}
				else
				{
					// features frame initialised
					f2 = new JFrame("Features Page");
					f2.setSize(1200,600);
					f2.setLayout(null);
					f1.setVisible(false);
					f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f2.add(img);
					f2.add(marker);
					f2.add(logout);
					
					person = new JLabel(empname);
					person.setBounds(620,40,180,25);
					f2.add(person);
					
					// adding components to the features page
					l2 = new JLabel("Choose from below:");
					l2.setForeground(Color.BLUE);
					l2.setFont(new Font("Arial",Font.PLAIN,28));
					l2.setBounds(100,200,600,40);
					f2.add(l2);
					
					//help on frame
					JTextArea help = new JTextArea(10,20);
					help.setBounds(700,200,400,300);
					help.setFont(new Font("Consolas",Font.PLAIN,20));
					help.setLineWrap(true);
					help.setWrapStyleWord(true);
					help.setText("Following are the features - \nCreate Group - Make your own group and add contacts\nUse Existing Group - Schedule a message for an existing group\nModify Group - Modify your groups\nSend Message - Send message instantly\nStatus - status of messages you have sent");
					help.setEditable(false);
					help.setForeground(Color.RED);
					f2.add(help);
					
					b2 = new JButton("Create Group");
					b2.setBounds(100,250,160,80);
					f2.add(b2);
					
					b3 = new JButton("Use Existing Group");
					b3.setBounds(270,250,160,80);
					f2.add(b3);
					
					b4 = new JButton("Modify your Group");
					b4.setBounds(440,250,160,80);
					f2.add(b4);
					
					b5 = new JButton("Status of Messages");
					b5.setBounds(100,340,160,80);
					f2.add(b5);
					
					b6 = new JButton("Send a Message");
					b6.setBounds(270,340,160,80);
					f2.add(b6);
					
					b11 = new JButton("Exit");
					b11.setBounds(440,340,160,80);
					f2.add(b11);
					
					f2.setVisible(true);
					
					logout.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							t1.setText(null);
							f2.setVisible(false);
							f1.setVisible(true);
							f1.add(img);
							f1.add(marker);
						}
					});
					
					// create group button
					b2.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							// create group frame initialised
							f3 = new JFrame("Create Group Page");
							f3.setSize(1200,600);
							f3.setLayout(null);
							f2.setVisible(false);
							f3.setVisible(true);
							f3.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent we)
								{
									f3.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f3.add(img);
							f3.add(marker);
							f3.add(logout);
							f3.add(person);
							
							// adding components to the features page
							l3 = new JLabel("Enter Group Name:");
							l3.setForeground(Color.BLUE);
							l3.setFont(new Font("Arial",Font.PLAIN,28));
							l3.setBounds(100,200,600,40);
							f3.add(l3);
							
							//help on frame
							JTextArea help = new JTextArea(10,20);
							help.setBounds(700,200,400,200);
							help.setFont(new Font("Consolas",Font.PLAIN,20));
							help.setLineWrap(true);
							help.setWrapStyleWord(true);
							help.setText("Enter a suitable group name for your group and enter its description. Description is however not required. Having Done that, Click Create Group to proceed");
							help.setEditable(false);
							help.setForeground(Color.RED);
							f3.add(help);
							
							t2 = new JTextField(100);
							t2.setBounds(100,250,400,40);
							f3.add(t2);
							
							l4 = new JLabel("Enter Group Desc. :");
							l4.setForeground(Color.BLUE);
							l4.setFont(new Font("Arial",Font.PLAIN,28));
							l4.setBounds(100,300,600,40);
							f3.add(l4);
							
							t3 = new JTextField(100);
							t3.setBounds(100,350,400,40);
							f3.add(t3);
							
							b7 = new JButton("Create Group");
							b7.setBounds(100,400,160,80);
							f3.add(b7);
							
							b8 = new JButton("Back");
							b8.setBounds(270,400,160,80);
							f3.add(b8); 
							
							b7.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									try
									{
										currgrp = t2.getText();
										PreparedStatement p = c.prepareStatement("insert into groups values(? ,?, ?)");
										p.setString(1,t2.getText());
										p.setString(2,t3.getText());
										p.setInt(3,empid);
										p.execute();
										p.close();
									}

									catch(Exception e)
									{	
										e.printStackTrace();
									}
									
									// initialising add persons frame 
									f4 = new JFrame("Add Persons Page");
									f4.setSize(1200,600);
									f4.setLayout(null);
									f3.setVisible(false);
									
									f4.addWindowListener(new WindowAdapter(){
										public void windowClosing(WindowEvent we)
										{
											f4.setVisible(false);
											f2.setVisible(true);
											f2.add(img);
											f2.add(marker);
											f2.add(logout);
											f2.add(person);
										}
									});
									
									f4.add(img);
									f4.add(marker);
									f4.add(logout);
									f4.add(person);
									
									// adding components to the frame
									
									//help on frame
									JTextArea help = new JTextArea(10,20);
									help.setBounds(700,200,400,200);
									help.setFont(new Font("Consolas",Font.PLAIN,20));
									help.setLineWrap(true);
									help.setWrapStyleWord(true);
									help.setText("Congratulations on making a new Group. Enter as many employees as you want to be in this group. If by mistake you have entered a wrong employee, Go to Modify Groups,You can make modifications there.");
									help.setEditable(false);
									help.setForeground(Color.RED);
									f4.add(help);
									
									l5 = new JLabel("Add Members:");
									l5.setBounds(100,200,600,40);
									f4.add(l5);
									
									try
									{
										// queries to enter members to just made group
										PreparedStatement p = c.prepareStatement("select EmpName from members");
										r2 = p.executeQuery();
										c1 = new Choice();
										c1.setBounds(100,250,400,40);
										f4.add(c1);
										String s[] = new String[1000];
										int k = 0;
										while(r2.next())
										{
											s[k++] = r2.getString(1);
											c1.add(s[k-1]);
										}
										p.close();
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
									
									b9 = new JButton("Add Member");
									b9.setBounds(100,320,160,80);
									f4.add(b9);
									
									b10 = new JButton("Back");
									b10.setBounds(270,320,160,80);
									f4.add(b10);
									
									f4.setVisible(true);
									
									b9.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											try
											{
												String mem = c1.getSelectedItem();
												PreparedStatement p = c.prepareStatement("insert into group_data values(?, ?, ?)");
												p.setString(1,currgrp);
												p.setString(2,mem);
												p.setInt(3,empid);
												p.execute();
												p.close();
												
												JOptionPane.showMessageDialog(null,"Member is Added");
											}
											catch(Exception e)
											{
												e.printStackTrace();
											}
										}
									});
									
									b10.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											f4.setVisible(false);
											f2.setVisible(true);
											f2.add(img);
											f2.add(marker);
											f2.add(logout);
											f2.add(person);
										}
									});
								}
							});
							
							b8.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									f3.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
						}
					});
					
					// use existing group button listener
					b3.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							f5 = new JFrame("Use Existing Group Page");
							f5.setSize(1200,600);
							f5.setLayout(null);
							f2.setVisible(false);
							f5.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent we)
								{
									f5.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f5.add(img);
							f5.add(marker);
							f5.add(logout);
							f5.add(person);
							
							// add components 
							l6 = new JLabel("Select a Group:");
							l6.setForeground(Color.BLUE);
							l6.setFont(new Font("Arial",Font.PLAIN,28));
							l6.setBounds(100,200,600,40);
							f5.add(l6);
							
							try
							{
								PreparedStatement p = c.prepareStatement("select distinct GroupName from groups");
								r3 = p.executeQuery();
								int k = 0;
								String s[] = new String[1000];
								c2 = new Choice();
								c2.setBounds(100,250,400,40);
								f5.add(c2);
								while(r3.next())
								{
									s[k++] = r3.getString(1);
									c2.add(s[k-1]);
								}
								p.close();
							}
							
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							l7 = new JLabel("Enter a Message:");
							l7.setForeground(Color.BLUE);
							l7.setFont(new Font("Arial",Font.PLAIN,28));
							l7.setBounds(100,300,600,40);
							f5.add(l7);
							
							ta1 = new JTextArea(400,90);
							ta1.setBounds(100,350,400,40);
							f5.add(ta1);
							
							l8 = new JLabel("Pick Date and Time");
							l8.setForeground(Color.BLUE);
							l8.setFont(new Font("Arial",Font.PLAIN,28));
							l8.setBounds(650,150,500,40);
							f5.add(l8);
							
							// for time 
							
							Border etchedBorder = BorderFactory.createEtchedBorder();
							Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
							Border compoundBorder = BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);

							MyDateListener listener = new MyDateListener();

							final JCalendar calendar4 = new JCalendar( Calendar.getInstance(Locale.ENGLISH),Locale.ENGLISH, JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, false);
							calendar4.addDateListener(listener);
							calendar4.setBorder(compoundBorder);
							calendar4.setBounds(650,200,400,320);
							f5.add(calendar4);
							
							b12 = new JButton("Save Message");
							b12.setBounds(100,450,160,80);
							f5.add(b12);
							
							b13 = new JButton("Back");
							b13.setBounds(270,450,160,80);
							f5.add(b13);
							
							f5.setVisible(true);
							
							b12.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									try
									{
										String gsel = c2.getSelectedItem();
										String message = ta1.getText();
										int status = 0;
										PreparedStatement p = c.prepareStatement("insert into mesaages12 values(?, ?, ?, ?, ?, ?)");
										p.setString(1,gsel);
										p.setString(2,message);
										p.setInt(3,status);
										p.setInt(4,empid);
										p.setString(5,empname);
										p.setTimestamp(6,new Timestamp(cal.getTimeInMillis()));
										p.execute();
										p.close();
										
										ta1.setText(null);
										JOptionPane.showMessageDialog(null,"Message Successfully Entered");
									}
									
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							});
							
							b13.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									f5.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
						}
					});
					
					// modify group button listener on features page
					b4.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							f6 = new JFrame("Modify your Groups");
							f6.setSize(1200,600);
							f6.setLayout(null);
							f2.setVisible(false);
							f6.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent we)
								{
									f6.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f6.add(img);
							f6.add(marker);
							f6.add(logout);
							f6.add(person);
							
							// add components 
							
							//help on frame
							JTextArea help = new JTextArea(10,20);
							help.setBounds(700,200,400,200);
							help.setFont(new Font("Consolas",Font.PLAIN,20));
							help.setLineWrap(true);
							help.setWrapStyleWord(true);
							help.setText("Many Options are there to modify your groups here.\nP.S. Only the GroupOwner can make changes to his/her group.");
							help.setEditable(false);
							help.setForeground(Color.RED);
							f6.add(help);
							
							l9 = new JLabel("Select a Group to modify:");
							l9.setForeground(Color.BLUE);
							l9.setFont(new Font("Arial",Font.PLAIN,28));
							l9.setBounds(100,200,600,40);
							f6.add(l9);
							
							c3 = new Choice();
							c3.setBounds(100,250,400,40);
							f6.add(c3);
							
							b14 = new JButton("Add Members");
							b14.setBounds(100,320,160,80);
							f6.add(b14);
							
							b15 = new JButton("Delete Members");
							b15.setBounds(270,320,160,80);
							f6.add(b15);
							
							b16 = new JButton("Delete Group");
							b16.setBounds(100,410,160,80);
							f6.add(b16);
							
							b17 = new JButton("Done");
							b17.setBounds(270,410,160,80);
							f6.add(b17);
							
							f6.setVisible(true);
							
							try
							{
								PreparedStatement p = c.prepareStatement("select GroupName from Groups where GroupOwnerID = ?");
								p.setInt(1,empid);
								r4 = p.executeQuery();
								String s[] = new String[1000];
								int k = 0;
								while(r4.next())
								{
									s[k++] = r4.getString(1);
									c3.add(s[k-1]);
								}
								p.close();
							}
							
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							
							//  add members modification listener
							b14.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									final String gsel = c3.getSelectedItem();
									f7 = new JFrame("Add Members Modification");
									f7.setSize(1200,600);
									f7.setLayout(null);
									f6.setVisible(false);
									f7.addWindowListener(new WindowAdapter(){
										public void windowClosing(WindowEvent we)
										{
											f7.setVisible(false);
											f6.setVisible(true);
											f6.add(img);
											f6.add(marker);
											f6.add(logout);
											f6.add(person);
										}
									});
									
									f7.add(img);
									f7.add(marker);
									f7.add(logout);
									f7.add(person);
									
									
									l10 = new JLabel("Add More members to " + gsel + ":");
									l10.setForeground(Color.BLUE);
									l10.setFont(new Font("Arial",Font.PLAIN,28));
									l10.setBounds(100,200,600,40);
									f7.add(l10);
									
									//help on frame
									JTextArea help = new JTextArea(10,20);
									help.setBounds(700,200,400,200);
									help.setFont(new Font("Consolas",Font.PLAIN,20));
									help.setLineWrap(true);
									help.setWrapStyleWord(true);
									help.setText("Add more members to already existing group. Adding new members will not affect members already existing in the group.");
									help.setEditable(false);
									help.setForeground(Color.RED);
									f7.add(help);
									
									c4 = new Choice();
									c4.setBounds(100,250,400,40);
									f7.add(c4);
							
									try
									{
										
										PreparedStatement p = c.prepareStatement("select EmpName from members");
										r5 = p.executeQuery();
										String s[] = new String[1000];
										int k = 0;
										while(r5.next())
										{
											s[k++] = r5.getString(1);
											c4.add(s[k-1]);
										}
										p.close();
									}
									
									catch(Exception e)
									{
										e.printStackTrace();
									}
									
									b18 = new JButton("Add");
									b18.setBounds(100,300,160,80);
									f7.add(b18);
									
									b19 = new JButton("Back");
									b19.setBounds(270,300,160,80);
									f7.add(b19);
									
									f7.setVisible(true);
									
									b18.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											String sel = c4.getSelectedItem();
											try
											{
												PreparedStatement p = c.prepareStatement("insert into group_data values(?, ?, ?)");
												p.setString(1,gsel);
												p.setString(2,sel);
												p.setInt(3,empid);
												p.execute();
												p.close();
												JOptionPane.showMessageDialog(null,"New Member Added");
											}
											
											catch(Exception e)
											{
												e.printStackTrace();
											}
											
										}
									});
									
									b19.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											f7.setVisible(false);
											f6.setVisible(true);
											f6.add(img);
											f6.add(marker);
											f6.add(logout);
											f6.add(person);
										}
									});
								}
							});
							
							// delete modification frame listener
							b15.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									final String gsel = c3.getSelectedItem();
									f8 = new JFrame("Delete Members Modification");
									f8.setSize(1200,600);
									f8.setLayout(null);
									f6.setVisible(false);
									f8.addWindowListener(new WindowAdapter(){
										public void windowClosing(WindowEvent we)
										{
											f8.setVisible(false);
											f6.setVisible(true);
											f6.add(img);
											f6.add(marker);
											f6.add(logout);
											f6.add(person);
										}
									});
									
									f8.add(img);
									f8.add(marker);
									f8.add(logout);
									f8.add(person);
									
									//help on frame
									JTextArea help = new JTextArea(10,20);
									help.setBounds(700,200,400,200);
									help.setFont(new Font("Consolas",Font.PLAIN,20));
									help.setLineWrap(true);
									help.setWrapStyleWord(true);
									help.setText("Delete members from the group which are no more required or were accudently added while creating the group.");
									help.setEditable(false);
									help.setForeground(Color.RED);
									f8.add(help);
									
									l11 = new JLabel("Delete members from " + gsel + ":");
									l11.setForeground(Color.BLUE);
									l11.setFont(new Font("Arial",Font.PLAIN,28));
									l11.setBounds(100,200,600,40);
									f8.add(l11);
									
									c5 = new Choice();
									c5.setBounds(100,250,400,40);
									f8.add(c5);
							
									try
									{
										
										PreparedStatement p = c.prepareStatement("select GroupMembers from group_data where GroupName = ?");
										p.setString(1,gsel);
										r6 = p.executeQuery();
										String s[] = new String[1000];
										int k = 0;
										while(r6.next())
										{
											s[k++] = r6.getString(1);
											c5.add(s[k-1]);
										}
										p.close();
									}
									
									catch(Exception e)
									{
										e.printStackTrace();
									}
									
									b20 = new JButton("Delete");
									b20.setBounds(100,300,160,80);
									f8.add(b20);
									
									b21 = new JButton("Back");
									b21.setBounds(270,300,160,80);
									f8.add(b21);
									
									f8.setVisible(true);
									
									b20.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											String sel = c5.getSelectedItem();
											try
											{
												PreparedStatement p = c.prepareStatement("delete from group_data where GroupMembers = ? and GroupName = ? and GroupOwner = ?");
												p.setString(1,sel);
												p.setString(2,gsel);
												p.setInt(3,empid);
												p.execute();
												p.close();
												JOptionPane.showMessageDialog(null,"Member removed from group");
												f8.setVisible(false);
												f6.setVisible(true);
												f6.add(img);
												f6.add(marker);
												f6.add(logout);
												f6.add(person);
											}
											
											catch(Exception e)
											{
												e.printStackTrace();
											}
											
										}
									});
									
									b21.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent ae)
										{
											f8.setVisible(false);
											f6.setVisible(true);
											f6.add(img);
											f6.add(marker);
											f6.add(logout);
											f6.add(person);
										}
									});
								}
							});
							
							// delete group listener
							b16.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									final String gsel = c3.getSelectedItem();
									try
									{
										PreparedStatement p = c.prepareStatement("delete from groups where GroupName = ? and GroupOwnerID = ?");
										p.setString(1,gsel);
										p.setInt(2,empid);
										p.execute();
										p.close();
										
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
									
									try
									{	
										c3.removeAll();
										PreparedStatement p = c.prepareStatement("select distinct GroupName from groups where GroupOwnerID = ?");
										p.setInt(1,empid);
										r7 = p.executeQuery();
										String s[] = new String[1000];
										int k = 0;
										while(r7.next())
										{
											s[k++] = r7.getString(1);
											c3.add(s[k-1]);
										}
										p.close();
									}
									
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							});
							
							b17.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									f6.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
						}
					});
					
					// status of messages sent by the logged in user
					b5.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							f9 = new JFrame("Status of Your Messages");
							f9.setSize(1200,600);
							f9.setLayout(null);
							f2.setVisible(false);
							f9.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent we)
								{
									f9.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f9.add(img);
							f9.add(marker);
							f9.add(logout);
							f9.add(person);
							
							
							final String[] columns = {"Group","Messages","Status"};
							final List<Object[]> data = new ArrayList<Object[]>();
							
							try
							{
								
								PreparedStatement p = c.prepareStatement("select GroupName,Message,status from mesaages12 where Sender = ?");
								p.setInt(1,empid);
								r8 = p.executeQuery();
								int k = 0;
								while(r8.next())
								{
										Object[] array = new Object[3];
										
										array[0] = r8.getString(1);
										array[1] = r8.getString(2);
										array[2] = r8.getString(3);
										//System.out.println("yes");
										data.add(array);
										
								
								}
								
								p.close();
								
							}
							
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							final JTable table = new JTable(data.toArray(new Object[][] {}),columns);
							table.setPreferredScrollableViewportSize(new Dimension(500,50));
							table.setFillsViewportHeight(true);
							
							JScrollPane scroll = new JScrollPane(table);
							scroll.setBounds(100,250,500,300);
							f9.add(scroll);
							
							l12 = new JLabel("Status of Messages:");
							l12.setForeground(Color.BLUE);
							l12.setFont(new Font("Arial",Font.PLAIN,28));
							l12.setBounds(100,200,600,40);
							f9.add(l12);
							
							b22 = new JButton("Resend");
							b22.setBounds(650,330,160,80);
							f9.add(b22);
							
							b23 = new JButton("Back");
							b23.setBounds(650,420,160,80);
							f9.add(b23);
							
							b22.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									int row_sel = table.getSelectedRow();
									Object mes1 = (Object) table.getModel().getValueAt(row_sel, 0);
									// extracted message from the selected row in the table
									String message = mes1.toString();
									Object stat1 = (Object) table.getModel().getValueAt(row_sel, 1);
									// extracted status for the selected row in the table
									int status = Integer.parseInt(stat1.toString());
								}
							});
							
							b23.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									f9.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f9.setVisible(true);
						}
					});
					
					// Sending individual sms to number listener
					b6.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							f10 = new JFrame("Send Sms");
							f10.setSize(1200,600);
							f10.setLayout(null);
							f2.setVisible(false);
							f10.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent we)
								{
									f10.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(logout);
									f2.add(person);
								}
							});
							
							f10.add(img);
							f10.add(marker);
							f10.add(logout);
							f10.add(person);
							
							// adding components to the sms page
							
							//help on frame
							JTextArea help = new JTextArea(10,20);
							help.setBounds(700,200,400,200);
							help.setFont(new Font("Consolas",Font.PLAIN,20));
							help.setLineWrap(true);
							help.setWrapStyleWord(true);
							help.setText("Send SMS to people who are not in group. Note that these Messages will not be in queue and can not be scheduled for later. You can enter multiple messages too for e.g.\n+919542514041;+919639010352");
							help.setEditable(false);
							help.setForeground(Color.RED);
							f10.add(help);
							
							l13 = new JLabel("Enter Phone No:");
							l13.setForeground(Color.BLUE);
							l13.setFont(new Font("Arial",Font.PLAIN,28));
							l13.setBounds(100,200,600,40);
							f10.add(l13);
							
							t4 = new JTextField(100);
							t4.setBounds(100,250,400,40);
							f10.add(t4);
							
							l14 = new JLabel("Enter a Message:");
							l14.setForeground(Color.BLUE);
							l14.setFont(new Font("Arial",Font.PLAIN,28));
							l14.setBounds(100,300,600,40);
							f10.add(l14);
							
							ta2 = new JTextArea(400,90);
							ta2.setBounds(100,350,400,40);
							f10.add(ta2);
							
							b24 = new JButton("Send");
							b24.setBounds(100,400,160,80);
							f10.add(b24);
							
							b25 = new JButton("Back");
							b25.setBounds(270,400,160,80);
							f10.add(b25);
							
							f10.setVisible(true);
							
							b24.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									String mobile = "+91" + t4.getText();
									String message = ta2.getText();
									System.out.println(mobile);
									System.out.println(message);
									String ret = new sendSMS().sendSms(mobile,message);
									System.out.println(ret);
								}
							});
							
							b25.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent ae)
								{
									f10.setVisible(false);
									f2.setVisible(true);
									f2.add(img);
									f2.add(marker);
									f2.add(person);
									f2.add(logout);
								}
							});
						}
					});
					
					b11.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							System.exit(0);
						}
					});
				}
			}
		});
	}
	
	private class MyDateListener implements DateListener
	{

		public void dateChanged(DateEvent e)
		{
			cal = e.getSelectedDate();
			if (cal != null) {
				System.out.println(cal.getTime());
			}
			else {
				System.out.println("No time selected.");
			}
		}

	}
	
	public static void main(String args[])
	{
		new Sms();
	}
}

class sendSMS 
{
	public String sendSms(String no,String mes) 
	{
		try 
		{
			// Construct data
			String user = "username=" + "prakhar.bits059@gmail.com";
			String hash = "&hash=" + "fe8c327ad641df6d64087028b30e6fbcec937be6";
			String message = "&message=" + mes;
			String sender = "&sender=" + "prakhar";
			String numbers = "&numbers=" + no;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) 
			{
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} 
		catch (Exception e) 
		{
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
}