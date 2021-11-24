import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import java.awt.Font;

import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;


import java.util.*;
import java.util.stream.*;

public class Main extends JFrame {

	private static final long serialVersionUID = -6430646048324848364L;
	private static String save;
	//private static Window window;
	private JPanel contentPane;
	private JTextPane text_00;
	private JTextField textField_0;
	private JSpinner spinner_00;
	private JSpinner spinner_01;
	private JSpinner spinner_02;
	private JSpinner spinner_03;
	private JSpinner spinner_04;
	private JSpinner spinner_05;
	private JSpinner spinner_06;
	private JSpinner spinner_07;
	private JSpinner spinner_08;
	private JSpinner spinner_09;
	private JSpinner spinner_10;
	private JSpinner spinner_11;
	private JSpinner spinner_12;
	private JSpinner spinner_13;
	private JSpinner spinner_14;
	private JSpinner spinner_15;
	private JSpinner spinner_16;
	private JSpinner spinner_17;
	private JSpinner spinner_18;
	private JSpinner spinner_19;
	private JSpinner spinner_20;
	private JSpinner spinner_21;
	private JSpinner spinner_22;
	private JSpinner spinner_23;
	private JSpinner spinner_24;
	private JSpinner spinner_25;
	private JSpinner spinner_26;
	private JSpinner spinner_27;
	private JSpinner spinner_28;
	private JSpinner spinner_29;
	private JSpinner spinner_30;
	private JSpinner spinner_31;
	private JSpinner spinner_32;
	private JSpinner spinner_33;
	private JSpinner spinner_34;
	private JSpinner spinner_35;
	private JSpinner spinner_36;
	private JSpinner spinner_37;
	private JSpinner spinner_38;
	private JSpinner spinner_39;
	private JSpinner spinner_40;
	private JSpinner spinner_41;
	private JSpinner spinner_42;
	private JSpinner spinner_43;
	private JSpinner spinner_44;
	private JSpinner spinner_45;
	private JSpinner spinner_46;
	private JSpinner spinner_47;
	private JSpinner spinner_48;
	private JSpinner spinner_49;
	private JTextPane doppelt_0;
	private JTextPane doppelt_1;
	private JTextPane doppelt_2;
	private JToggleButton switch_00;
	private JToggleButton switch_01;
	private JCheckBox checkBox_00;
	private JCheckBox checkBox_01;
	private JCheckBox checkBox_02;
	private JCheckBox checkBox_03;
	private JCheckBox checkBox_05;
	private JCheckBox checkBox_06;
	private JCheckBox checkBox_07;

	private JComboBox<String> fach_00;
	private JComboBox<String> fach_01;
	private JComboBox<String> fach_02;
	private JComboBox<String> fach_03;
	private JComboBox<String> fach_04;
	private JComboBox<String> fach_05;
	private JComboBox<String> fach_06;
	private JComboBox<String> fach_07;
	private JComboBox<String> fach_08;
	private JComboBox<String> fach_09;
	private JComboBox<String> fach_10;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		save = readFile();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		text_00 = new JTextPane();
		text_00.setEditable(false);
		text_00.setForeground(Color.LIGHT_GRAY);
		text_00.setFont(new Font("Arial Black", Font.BOLD, 15));
		text_00.setBackground(Color.DARK_GRAY);
		text_00.setText("ABI Rechner MSS");
		text_00.setBounds(10, 11, 170, 28);
		contentPane.add(text_00);

		doppelt_0 = new JTextPane();
		doppelt_0.setFont(new Font("Arial Black", Font.BOLD, 13));
		doppelt_0.setBackground(Color.DARK_GRAY);
		doppelt_0.setText("2x");
		doppelt_0.setForeground(Color.lightGray);
		doppelt_0.setVisible(false);
		doppelt_0.setBounds(430, 60, 28, 20);
		contentPane.add(doppelt_0);

		doppelt_1 = new JTextPane();
		doppelt_1.setFont(new Font("Arial Black", Font.BOLD, 13));
		doppelt_1.setBackground(Color.DARK_GRAY);
		doppelt_1.setText("2x");
		doppelt_1.setForeground(Color.lightGray);
		doppelt_1.setVisible(false);
		doppelt_1.setBounds(430, 111, 28, 20);
		contentPane.add(doppelt_1);

		doppelt_2 = new JTextPane();
		doppelt_2.setFont(new Font("Arial Black", Font.BOLD, 13));
		doppelt_2.setBackground(Color.DARK_GRAY);
		doppelt_2.setText("2x");
		doppelt_2.setForeground(Color.lightGray);
		doppelt_2.setVisible(false);
		doppelt_2.setBounds(430, 162, 28, 20);
		contentPane.add(doppelt_2);

		// LK1
		spinner_00 = new JSpinner();
		spinner_00.setBackground(Color.WHITE);
		spinner_00.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_00.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_00.setBounds(190, 50, 50, 40);
		contentPane.add(spinner_00);

		spinner_01 = new JSpinner();
		spinner_01.setForeground(Color.WHITE);
		spinner_01.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_01.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_01.setBounds(250, 50, 50, 40);
		contentPane.add(spinner_01);

		spinner_02 = new JSpinner();
		spinner_02.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_02.setForeground(Color.WHITE);
		spinner_02.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_02.setBounds(310, 50, 50, 40);
		contentPane.add(spinner_02);

		spinner_03 = new JSpinner();
		spinner_03.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_03.setForeground(Color.WHITE);
		spinner_03.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_03.setBounds(370, 50, 50, 40);
		contentPane.add(spinner_03);

		// LK2
		spinner_04 = new JSpinner();
		spinner_04.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_04.setForeground(Color.WHITE);
		spinner_04.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_04.setBounds(190, 101, 50, 40);
		contentPane.add(spinner_04);

		spinner_05 = new JSpinner();
		spinner_05.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_05.setForeground(Color.WHITE);
		spinner_05.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_05.setBounds(250, 101, 50, 40);
		contentPane.add(spinner_05);

		spinner_06 = new JSpinner();
		spinner_06.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_06.setForeground(Color.WHITE);
		spinner_06.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_06.setBounds(310, 101, 50, 40);
		contentPane.add(spinner_06);

		spinner_07 = new JSpinner();
		spinner_07.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_07.setForeground(Color.WHITE);
		spinner_07.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_07.setBounds(370, 101, 50, 40);
		contentPane.add(spinner_07);

		// LK3
		spinner_08 = new JSpinner();
		spinner_08.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_08.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_08.setBounds(190, 152, 50, 40);
		contentPane.add(spinner_08);

		spinner_09 = new JSpinner();
		spinner_09.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_09.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_09.setBounds(250, 152, 50, 40);
		contentPane.add(spinner_09);

		spinner_10 = new JSpinner();
		spinner_10.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_10.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_10.setBounds(310, 152, 50, 40);
		contentPane.add(spinner_10);

		spinner_11 = new JSpinner();
		spinner_11.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_11.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_11.setBounds(370, 152, 50, 40);
		contentPane.add(spinner_11);

		// gk1
		spinner_12 = new JSpinner();
		spinner_12.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_12.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_12.setBounds(190, 226, 50, 40);
		contentPane.add(spinner_12);

		spinner_13 = new JSpinner();
		spinner_13.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_13.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_13.setBounds(250, 226, 50, 40);
		contentPane.add(spinner_13);

		spinner_14 = new JSpinner();
		spinner_14.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_14.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_14.setBounds(310, 226, 50, 40);
		contentPane.add(spinner_14);

		spinner_15 = new JSpinner();
		spinner_15.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_15.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_15.setBounds(370, 226, 50, 40);
		contentPane.add(spinner_15);

		// gk2
		spinner_16 = new JSpinner();
		spinner_16.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_16.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_16.setBounds(190, 277, 50, 40);
		contentPane.add(spinner_16);

		spinner_17 = new JSpinner();
		spinner_17.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_17.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_17.setBounds(250, 277, 50, 40);
		contentPane.add(spinner_17);

		spinner_18 = new JSpinner();
		spinner_18.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_18.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_18.setBounds(310, 277, 50, 40);
		contentPane.add(spinner_18);

		spinner_19 = new JSpinner();
		spinner_19.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_19.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_19.setBounds(370, 277, 50, 40);
		contentPane.add(spinner_19);

		// gk3
		spinner_20 = new JSpinner();
		spinner_20.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_20.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_20.setBounds(190, 328, 50, 40);
		contentPane.add(spinner_20);

		spinner_21 = new JSpinner();
		spinner_21.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_21.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_21.setBounds(250, 328, 50, 40);
		contentPane.add(spinner_21);

		spinner_22 = new JSpinner();
		spinner_22.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_22.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_22.setBounds(310, 328, 50, 40);
		contentPane.add(spinner_22);

		spinner_23 = new JSpinner();
		spinner_23.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_23.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_23.setBounds(370, 328, 50, 40);
		contentPane.add(spinner_23);

		// gk4
		spinner_24 = new JSpinner();
		spinner_24.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_24.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_24.setBounds(190, 379, 50, 40);
		contentPane.add(spinner_24);

		spinner_25 = new JSpinner();
		spinner_25.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_25.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_25.setBounds(250, 379, 50, 40);
		contentPane.add(spinner_25);

		spinner_26 = new JSpinner();
		spinner_26.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_26.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_26.setBounds(310, 379, 50, 40);
		contentPane.add(spinner_26);

		spinner_27 = new JSpinner();
		spinner_27.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_27.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_27.setBounds(370, 379, 50, 40);
		contentPane.add(spinner_27);

		// gk5
		spinner_28 = new JSpinner();
		spinner_28.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_28.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_28.setBounds(190, 430, 50, 40);
		contentPane.add(spinner_28);

		spinner_29 = new JSpinner();
		spinner_29.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_29.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_29.setBounds(250, 430, 50, 40);
		contentPane.add(spinner_29);

		spinner_30 = new JSpinner();
		spinner_30.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_30.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_30.setBounds(310, 430, 50, 40);
		contentPane.add(spinner_30);

		spinner_31 = new JSpinner();
		spinner_31.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_31.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_31.setBounds(370, 430, 50, 40);
		contentPane.add(spinner_31);

		// gk6
		spinner_32 = new JSpinner();
		spinner_32.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_32.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_32.setBounds(190, 481, 50, 40);
		contentPane.add(spinner_32);

		spinner_33 = new JSpinner();
		spinner_33.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_33.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_33.setBounds(250, 481, 50, 40);
		contentPane.add(spinner_33);

		spinner_34 = new JSpinner();
		spinner_34.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_34.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_34.setBounds(310, 481, 50, 40);
		contentPane.add(spinner_34);

		spinner_35 = new JSpinner();
		spinner_35.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_35.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_35.setBounds(370, 481, 50, 40);
		contentPane.add(spinner_35);

		// gk7
		spinner_36 = new JSpinner();
		spinner_36.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_36.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_36.setBounds(190, 532, 50, 40);
		contentPane.add(spinner_36);

		spinner_37 = new JSpinner();
		spinner_37.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_37.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_37.setBounds(250, 532, 50, 40);
		contentPane.add(spinner_37);

		spinner_38 = new JSpinner();
		spinner_38.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_38.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_38.setBounds(310, 532, 50, 40);
		contentPane.add(spinner_38);

		spinner_39 = new JSpinner();
		spinner_39.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_39.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_39.setBounds(370, 532, 50, 40);
		contentPane.add(spinner_39);

		// ABI-Arbeiten
		spinner_40 = new JSpinner();
		spinner_40.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_40.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_40.setBounds(523, 50, 50, 40);
		contentPane.add(spinner_40);

		spinner_41 = new JSpinner();
		spinner_41.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_41.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_41.setBounds(523, 101, 50, 40);
		contentPane.add(spinner_41);

		spinner_42 = new JSpinner();
		spinner_42.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_42.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_42.setBounds(523, 152, 50, 40);
		contentPane.add(spinner_42);

		spinner_43 = new JSpinner();
		spinner_43.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_43.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_43.setBounds(523, 226, 50, 40);
		contentPane.add(spinner_43);

		spinner_44 = new JSpinner();
		spinner_44.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_44.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_44.setBounds(523, 277, 50, 40);
		contentPane.add(spinner_44);

		// Facharbeit
		spinner_45 = new JSpinner();
		spinner_45.setModel(new SpinnerNumberModel(5, 5, 15, 1));
		spinner_45.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_45.setBounds(654, 101, 50, 40);
		contentPane.add(spinner_45);

		// gk8
		spinner_46 = new JSpinner();
		spinner_46.setVisible(false);
		spinner_46.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_46.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_46.setBounds(190, 583, 50, 40);
		contentPane.add(spinner_46);

		spinner_47 = new JSpinner();
		spinner_47.setVisible(false);
		spinner_47.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_47.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_47.setBounds(250, 583, 50, 40);
		contentPane.add(spinner_47);

		spinner_48 = new JSpinner();
		spinner_48.setVisible(false);
		spinner_48.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_48.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_48.setBounds(310, 583, 50, 40);
		contentPane.add(spinner_48);

		spinner_49 = new JSpinner();
		spinner_49.setVisible(false);
		spinner_49.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		spinner_49.setFont(new Font("Arial Black", Font.PLAIN, 11));
		spinner_49.setBounds(370, 583, 50, 40);
		contentPane.add(spinner_49);

		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setBackground(Color.GRAY);
		btnCalculate.setFont(new Font("Arial Black", Font.BOLD, 13));
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				calc();
			}
		});
		btnCalculate.setBounds(653, 464, 175, 108);
		btnCalculate.setFocusPainted(false);
		contentPane.add(btnCalculate);

		// 47 //Switch
		switch_00 = new JToggleButton("2.M\u00FCndliches");
		switch_00.setBackground(Color.LIGHT_GRAY);
		switch_00.setFont(new Font("Arial Black", Font.PLAIN, 14));
		switch_00.setSelected(true);
		switch_00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkboxChange();
				if (!switch_00.isSelected()) {
					fach_04.setModel(new DefaultComboBoxModel<String>(new String[] { "          gk2", "Deutsch",
							"Englisch", "Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik",
							"Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion",
							"Ethik", "Philosophie", "Musik", "Kunst", "Darstell. Spiel" }));
				} else {
					fach_04.setModel(new DefaultComboBoxModel<String>(new String[] { "     M\u00FCndlich-2", "Deutsch",
							"Englisch", "Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik",
							"Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion",
							"Ethik", "Philosophie", "Musik", "Kunst", "Darstell. Spiel" }));
				}
			}
		});
		switch_00.setBounds(583, 277, 150, 40);
		switch_00.setFocusPainted(false);
		switch_00.setToolTipText("Aktivieren wenn ein zweites Prüfungsfach benötigt wird");
		contentPane.add(switch_00);

		switch_01 = new JToggleButton("BLL");
		switch_01.setBackground(Color.LIGHT_GRAY);
		switch_01.setSelected(true);
		switch_01.setFont(new Font("Arial Black", Font.PLAIN, 14));
		switch_01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkboxChange();
			}
		});
		switch_01.setBounds(743, 277, 85, 40);
		switch_01.setFocusPainted(false);
		switch_01.setToolTipText("Aktivieren wenn eine Besondere-Lern-Leistung erarbeitet wurde");
		contentPane.add(switch_01);

		// Facharbeit
		checkBox_00 = new JCheckBox("Facharbeit");
		checkBox_00.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_00.setForeground(Color.LIGHT_GRAY);
		checkBox_00.setBackground(Color.DARK_GRAY);
		checkBox_00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_00.setBounds(710, 110, 100, 23);
		checkBox_00.setFocusPainted(false);
		checkBox_00.setToolTipText("Häckchen wenn eine Facharbeit mit mindestens 5 Punkten erarbeitet wurde");
		contentPane.add(checkBox_00);

		// Checkboxen
		checkBox_01 = new JCheckBox("Voll");
		checkBox_01.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_01.setForeground(Color.LIGHT_GRAY);
		checkBox_01.setBackground(Color.DARK_GRAY);
		checkBox_01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_01.setBounds(426, 286, 50, 23);
		checkBox_01.setVisible(false);
		checkBox_01.setFocusPainted(false);
		checkBox_01.setToolTipText("Häckchen wenn dieser Kurs vollständig eingebracht werden muss");
		contentPane.add(checkBox_01);

		checkBox_02 = new JCheckBox("Voll");
		checkBox_02.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_02.setForeground(Color.LIGHT_GRAY);
		checkBox_02.setBackground(Color.DARK_GRAY);
		checkBox_02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_02.setBounds(426, 337, 75, 23);
		checkBox_02.setFocusPainted(false);
		checkBox_02.setToolTipText("Häckchen wenn dieser Kurs vollständig eingebracht werden muss");
		contentPane.add(checkBox_02);
		
		checkBox_03 = new JCheckBox("Voll");
		checkBox_03.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_03.setForeground(Color.LIGHT_GRAY);
		checkBox_03.setBackground(Color.DARK_GRAY);
		checkBox_03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_03.setBounds(426, 388, 75, 23);
		checkBox_03.setFocusPainted(false);
		checkBox_03.setToolTipText("Häckchen wenn dieser Kurs vollständig eingebracht werden muss");
		contentPane.add(checkBox_03);

		// 3/4
		checkBox_05 = new JCheckBox("Sport");
		checkBox_05.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_05.setForeground(Color.LIGHT_GRAY);
		checkBox_05.setBackground(Color.DARK_GRAY);
		checkBox_05.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_05.setBounds(426, 490, 85, 23);
		checkBox_05.setFocusPainted(false);
		checkBox_05.setToolTipText("Häckchen wenn an einem Grundkurs Sport teilgenommen wurde (gk6=Sport)");
		contentPane.add(checkBox_05);

		// Halb
		checkBox_06 = new JCheckBox("K\u00FCnst.Fach");
		checkBox_06.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_06.setForeground(Color.LIGHT_GRAY);
		checkBox_06.setBackground(Color.DARK_GRAY);
		checkBox_06.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_06.setBounds(426, 541, 89, 23);
		checkBox_06.setFocusPainted(false);
		checkBox_06.setToolTipText(
				"Häckchen wenn an einem Grundkurs Kunst, Musik oder darstellendes Spiel teilgenommen wurde (gk7=künstl. Fach)");
		contentPane.add(checkBox_06);

		checkBox_07 = new JCheckBox("nur in 12");
		checkBox_07.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkBox_07.setForeground(Color.LIGHT_GRAY);
		checkBox_07.setBackground(Color.DARK_GRAY);
		checkBox_07.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxChange();
			}
		});
		checkBox_07.setBounds(426, 592, 75, 23);
		checkBox_07.setFocusPainted(false);
		checkBox_07.setToolTipText("Häckchen wenn nur in der 12 ein künstl. Fach besucht wurde");
		contentPane.add(checkBox_07);

		textField_0 = new JTextField();
		textField_0.setBackground(Color.LIGHT_GRAY);
		textField_0.setFont(new Font("Arial Black", Font.BOLD, 14));
		textField_0.setEditable(false);
		textField_0.setBounds(653, 396, 175, 58);
		contentPane.add(textField_0);
		textField_0.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setToolTipText("Ben\u00F6tigte Punkte zum Verbessern des Schnitts");
		textField_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		textField_1.setBackground(Color.GRAY);
		textField_1.setEditable(false);
		textField_1.setBounds(613, 396, 40, 29);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setToolTipText("\u00DCbersch\u00FCssige Punkte zum halten des Schnitts");
		textField_2.setFont(new Font("Arial Black", Font.BOLD, 12));
		textField_2.setColumns(10);
		textField_2.setBackground(Color.GRAY);
		textField_2.setEditable(false);
		textField_2.setBounds(613, 425, 40, 29);
		contentPane.add(textField_2);

		JButton btnSave = new JButton("Save");
		btnSave.setBackground(Color.LIGHT_GRAY);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setBounds(696, 583, 90, 23);
		btnSave.setFocusPainted(false);
		btnSave.setToolTipText("Speichert Noten und Fächer");
		contentPane.add(btnSave);

		fach_00 = new JComboBox<String>();
		fach_00.setForeground(UIManager.getColor("textText"));
		fach_00.setBackground(Color.GRAY);
		fach_00.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_00.setModel(new DefaultComboBoxModel<String>(
				new String[] { "          LK1", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Sport", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_00.setBounds(10, 50, 170, 40);
		contentPane.add(fach_00);

		fach_01 = new JComboBox<String>();
		fach_01.setForeground(UIManager.getColor("textText"));
		fach_01.setBackground(Color.GRAY);
		fach_01.setModel(new DefaultComboBoxModel<String>(
				new String[] { "          LK2", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Sport", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_01.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_01.setBounds(10, 101, 170, 40);
		contentPane.add(fach_01);

		fach_02 = new JComboBox<String>();
		fach_02.setForeground(UIManager.getColor("textText"));
		fach_02.setBackground(Color.GRAY);
		fach_02.setModel(new DefaultComboBoxModel<String>(
				new String[] { "          LK3", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Sport", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_02.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_02.setBounds(10, 152, 170, 40);
		contentPane.add(fach_02);

		fach_03 = new JComboBox<String>();
		fach_03.setForeground(UIManager.getColor("textText"));
		fach_03.setBackground(Color.GRAY);
		fach_03.setModel(new DefaultComboBoxModel<String>(
				new String[] { "     M\u00FCndlich-1", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Philosophie", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_03.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_03.setBounds(10, 226, 170, 40);
		contentPane.add(fach_03);

		fach_04 = new JComboBox<String>();
		fach_04.setForeground(UIManager.getColor("textText"));
		fach_04.setBackground(Color.GRAY);
		fach_04.setModel(new DefaultComboBoxModel<String>(
				new String[] { "          gk2", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Philosophie", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_04.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_04.setBounds(10, 277, 170, 40);
		contentPane.add(fach_04);

		fach_05 = new JComboBox<String>();
		fach_05.setForeground(UIManager.getColor("textText"));
		fach_05.setBackground(Color.GRAY);
		fach_05.setModel(new DefaultComboBoxModel<String>(new String[] { "          gk3", "Deutsch", "Englisch",
				"Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik", "Chemie", "Informatik",
				"Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion", "Ethik", "Philosophie" }));
		fach_05.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_05.setBounds(10, 328, 170, 40);
		contentPane.add(fach_05);

		fach_06 = new JComboBox<String>();
		fach_06.setForeground(UIManager.getColor("textText"));
		fach_06.setBackground(Color.GRAY);
		fach_06.setModel(new DefaultComboBoxModel<String>(new String[] { "          gk4", "Deutsch", "Englisch",
				"Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik", "Chemie", "Informatik",
				"Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion", "Ethik", "Philosophie" }));
		fach_06.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_06.setBounds(10, 379, 170, 40);
		contentPane.add(fach_06);

		fach_07 = new JComboBox<String>();
		fach_07.setForeground(UIManager.getColor("textText"));
		fach_07.setBackground(Color.GRAY);
		fach_07.setModel(new DefaultComboBoxModel<String>(new String[] { "          gk5", "Deutsch", "Englisch",
				"Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik", "Chemie", "Informatik",
				"Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion", "Ethik", "Philosophie" }));
		fach_07.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_07.setBounds(10, 430, 170, 40);
		contentPane.add(fach_07);

		fach_08 = new JComboBox<String>();
		fach_08.setForeground(UIManager.getColor("textText"));
		fach_08.setBackground(Color.GRAY);
		fach_08.setModel(new DefaultComboBoxModel<String>(new String[] { "          gk6", "Deutsch", "Englisch",
				"Franz\u00F6sisch", "Spanisch", "Latein", "Mathe", "Biologie", "Physik", "Chemie", "Informatik",
				"Geschichte", "Sozialkunde", "Erkunde", "Sozi./Erdk.", "Religion", "Ethik", "Philosophie", "Sport" }));
		fach_08.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_08.setBounds(10, 481, 170, 40);
		contentPane.add(fach_08);

		fach_09 = new JComboBox<String>();
		fach_09.setForeground(UIManager.getColor("textText"));
		fach_09.setBackground(Color.GRAY);
		fach_09.setModel(new DefaultComboBoxModel<String>(
				new String[] { "          gk7", "Deutsch", "Englisch", "Franz\u00F6sisch", "Spanisch", "Latein",
						"Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Geschichte", "Sozialkunde", "Erkunde",
						"Sozi./Erdk.", "Religion", "Ethik", "Philosophie", "Musik", "Kunst", "Darstell. Spiel" }));
		fach_09.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_09.setBounds(10, 532, 170, 40);
		contentPane.add(fach_09);

		fach_10 = new JComboBox<String>();
		fach_10.setForeground(UIManager.getColor("textText"));
		fach_10.setBackground(Color.GRAY);
		fach_10.setModel(
				new DefaultComboBoxModel<String>(new String[] { "          gk8", "Englisch", "Franz\u00F6sisch",
						"Spanisch", "Latein", "Biologie", "Physik", "Chemie", "Informatik", "Philosophie" }));
		fach_10.setFont(new Font("Arial Black", Font.BOLD, 14));
		fach_10.setBounds(10, 583, 170, 40);
		contentPane.add(fach_10);

		if (!new File("save.txt").exists()) {
			try {
				String text = "0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;5;0;0;0;0;/000000000/0;0;0;0;0;0;0;0;0;0;0;/";
				BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));
				writer.write(text);
				writer.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}

		load();
		colorReset();
		checkboxChange();
	}

	public void checkboxChange() {
		colorReset();
		// 2 Mündliches | BLL
		if (switch_00.isSelected() || switch_01.isSelected()) {
			if (switch_00.isSelected()) {
				checkBox_01.setVisible(false);
			} else {
				checkBox_01.setVisible(true);
			}
			spinner_44.setVisible(true);
		} else {
			checkBox_01.setVisible(true);
			spinner_44.setVisible(false);
		}

		if (checkBox_06.isSelected()) {
			checkBox_07.setVisible(true);
		} else {
			spinner_36.setVisible(true);
			spinner_39.setVisible(true);
			// gk 8
			fach_10.setVisible(false);
			// nurIn12
			checkBox_07.setSelected(false);
			checkBox_07.setVisible(false);
		}

		if (checkBox_06.isSelected() && checkBox_07.isSelected()) {
			// Kunst 11|13 weg
			spinner_36.setVisible(false);
			spinner_39.setVisible(false);

			// gk8
			fach_10.setVisible(true);
			spinner_46.setVisible(true);
			spinner_47.setVisible(true);
			spinner_48.setVisible(true);
			spinner_49.setVisible(true);

		} else {
			// Kunst 11|13 weg
			spinner_36.setVisible(true);
			spinner_39.setVisible(true);
			// gk8
			fach_10.setVisible(false);
			spinner_46.setVisible(false);
			spinner_47.setVisible(false);
			spinner_48.setVisible(false);
			spinner_49.setVisible(false);
		}

	}

	public void calc() {

		// Alle möglichen Kombinationen von Kursen die nicht Eingebracht werden
		ArrayList<Data> data = new ArrayList<Data>();

		data.add(kommbinationBerechnen("null"));
		data.add(kommbinationBerechnen("gk3"));
		data.add(kommbinationBerechnen("gk4"));
		data.add(kommbinationBerechnen("gk5"));
		data.add(kommbinationBerechnen("gk6"));
		data.add(kommbinationBerechnen("gk8"));

		System.out.println(data.get(0).getPunkte() + " null");
		System.out.println(data.get(1).getPunkte() + " gk3");
		System.out.println(data.get(2).getPunkte() + " gk4");
		System.out.println(data.get(3).getPunkte() + " gk5");
		System.out.println(data.get(4).getPunkte() + " gk6");

		// Beste Kombination ermitteln
		Collections.sort(data, new Comparator<Data>() {
			public int compare(Data d1, Data d2) {
				return Integer.valueOf((Integer) d2.getPunkte()).compareTo((Integer) d1.getPunkte());
			}
		});

		Data fin = data.get(0);
		ArrayList<JSpinner> kurse = fin.getKurse();
		int punkteBlock1 = fin.getBlock1();
		int punkteBlock2 = fin.getBlock2();
		int punkte = fin.getPunkte();
		String nEK = fin.getNEK();
		System.out.println("Nicht Einbringen Kurs: " + nEK);
		String schnitt = schnitt(punkte, punkteBlock1, punkteBlock2);

		changeColor(fin, kurse, schnitt);

		// Ausgabe Konsole | Window
		System.out.println("Block1=" + punkteBlock1);
		System.out.println("Block2=" + punkteBlock2);
		System.out.println("Erreichte Punkte=" + punkte);
		System.out.println("ABI-Schnitt=" + schnitt);

		if (punkte == 1) {
			textField_0.setText(" " + punkte + " Punkt | Ø" + schnitt);
		}
		if (schnitt.charAt(0) == 'F') {
			textField_0.setText(" " + punkte + " Punkte | " + schnitt);
		} else {
			textField_0.setText(" " + punkte + " Punkte | Ø" + schnitt);
		}

		System.out.println("---------------------------");
	}

	public boolean isABIPruefung(JSpinner s) {
		boolean isABIPruefung = false;
		if (s.equals(spinner_40) || s.equals(spinner_41) || s.equals(spinner_42) || s.equals(spinner_43)
				|| s.equals(spinner_44)) {
			isABIPruefung = true;
		}
		return isABIPruefung;
	}

	public class Data {
		private ArrayList<JSpinner> kurse;
		private int punkte;
		private int block1;
		private int block2;
		private String nEK;

		public Data(ArrayList<JSpinner> k, int p, int b1, int b2, String nEK) {
			this.kurse = k;
			this.punkte = p;
			this.block1 = b1;
			this.block2 = b2;
			this.nEK = nEK;
		}

		public ArrayList<JSpinner> getKurse() {
			return kurse;
		}

		public int getPunkte() {
			return punkte;
		}

		public int getBlock1() {
			return block1;
		}

		public int getBlock2() {
			return block2;
		}

		public String getNEK() {
			return nEK;
		}
	}

	public Data kommbinationBerechnen(String nE) {
		// nE=gk3 | nE=gk4 | nE=gk5 | nE=gk6 | nE=gk8
		// Block1
		ArrayList<JSpinner> kurse = new ArrayList<JSpinner>();
		ArrayList<JSpinner> restKurse = new ArrayList<JSpinner>();
		int anzahlKurse = 35;

		// Leistungskurse
		kurse.add(spinner_00);
		kurse.add(spinner_01);
		kurse.add(spinner_02);
		kurse.add(spinner_03);
		kurse.add(spinner_04);
		kurse.add(spinner_05);
		kurse.add(spinner_06);
		kurse.add(spinner_07);
		kurse.add(spinner_08);
		kurse.add(spinner_09);
		kurse.add(spinner_10);
		kurse.add(spinner_11);
		anzahlKurse = anzahlKurse - 12;

		// Mündlich_1 | gk1
		kurse.add(spinner_12);
		kurse.add(spinner_13);
		kurse.add(spinner_14);
		kurse.add(spinner_15);
		anzahlKurse = anzahlKurse - 4;

		// Mündlich_2 | gk2
		if (switch_00.isSelected() || checkBox_01.isSelected()) {
			kurse.add(spinner_16);
			kurse.add(spinner_17);
			kurse.add(spinner_18);
			kurse.add(spinner_19);
			anzahlKurse = anzahlKurse - 4;
		} else {
			restKurse.add(spinner_16);
			restKurse.add(spinner_17);
			restKurse.add(spinner_18);

			kurse.add(spinner_19);
			anzahlKurse--;
		}

		// gk3 
		if (checkBox_02.isSelected()) {
			kurse.add(spinner_20);
			kurse.add(spinner_21);
			kurse.add(spinner_22);
			kurse.add(spinner_23);
			anzahlKurse = anzahlKurse - 4;
		} else {
			if (nE != "gk3") {
				restKurse.add(spinner_20);
				restKurse.add(spinner_21);
				restKurse.add(spinner_22);

				kurse.add(spinner_23);
				anzahlKurse--;
			}

		}

		// gk4
		if (checkBox_03.isSelected()) {
			kurse.add(spinner_24);
			kurse.add(spinner_25);
			kurse.add(spinner_26);
			kurse.add(spinner_27);
			anzahlKurse = anzahlKurse - 4;
		} else {
			if (nE != "gk4") {
				restKurse.add(spinner_24);
				restKurse.add(spinner_25);
				restKurse.add(spinner_26);

				kurse.add(spinner_27);
				anzahlKurse--;
			}
		}

		// gk5
		if (nE != "gk5") {
			restKurse.add(spinner_28);
			restKurse.add(spinner_29);
			restKurse.add(spinner_30);

			kurse.add(spinner_31);
			anzahlKurse--;
		}

		// gk6 | Sport
		if (checkBox_05.isSelected() && nE != "gk6") {
			ArrayList<JSpinner> help = new ArrayList<JSpinner>();
			help.add((JSpinner) spinner_32);
			help.add((JSpinner) spinner_33);
			help.add((JSpinner) spinner_34);

			kurse.add(spinner_35);
			anzahlKurse--;

			Collections.sort(help, (a, b) -> (Integer) a.getValue() < (Integer) b.getValue() ? -1
					: a.getValue() == b.getValue() ? 0 : 1);

			restKurse.add(help.get(1));
			restKurse.add(help.get(2));
		}
		if (!checkBox_05.isSelected() && nE != "gk6") {
			restKurse.add(spinner_32);
			restKurse.add(spinner_33);
			restKurse.add(spinner_34);

			kurse.add(spinner_35);
			anzahlKurse--;
		}

		// gk7 | Kunst
		if (!checkBox_06.isSelected() && !checkBox_07.isSelected()) {
			restKurse.add(spinner_36);
			restKurse.add(spinner_37);
			restKurse.add(spinner_38);

			kurse.add(spinner_39);
			anzahlKurse--;
		}
		if (checkBox_06.isSelected() && !checkBox_07.isSelected()) {
			ArrayList<JSpinner> help = new ArrayList<JSpinner>();
			help.add(spinner_36);
			help.add(spinner_37);
			help.add(spinner_38);
			kurse.add(spinner_39);
			anzahlKurse--;

			Collections.sort(help, new Comparator<JSpinner>() {
				public int compare(JSpinner s1, JSpinner s2) {
					return Integer.valueOf((Integer) s2.getValue()).compareTo((Integer) s1.getValue());
				}
			});

			kurse.add(help.get(0));
			anzahlKurse--;

			restKurse.add(help.get(1));
			restKurse.add(help.get(2));

		}
		if (checkBox_06.isSelected() && checkBox_07.isSelected()) {
			// Kunst in 12(1/2)
			kurse.add(spinner_37);
			kurse.add(spinner_38);
			anzahlKurse = anzahlKurse - 2;

			// gk8 | Nur wenn Kunst eingeschoben wurde
			if (nE != "gk8") {
				restKurse.add(spinner_46);
				restKurse.add(spinner_47);
				restKurse.add(spinner_48);

				kurse.add(spinner_49);
				anzahlKurse--;
			}
		}

		// restKurse werden ausgewertet
		restKurse = restKurseBerechnen(restKurse, anzahlKurse);
		for (JSpinner s : restKurse) {
			kurse.add(s);
		}

		// Berechnung
		// BLOCK_1
		// LKs
		int lk1 = 0;
		int lk2 = 0;
		int lk3 = 0;

		lk1 += (Integer) kurse.get(0).getValue();
		lk1 += (Integer) kurse.get(1).getValue();
		lk1 += (Integer) kurse.get(2).getValue();
		lk1 += (Integer) kurse.get(3).getValue();

		lk2 += (Integer) kurse.get(4).getValue();
		lk2 += (Integer) kurse.get(5).getValue();
		lk2 += (Integer) kurse.get(6).getValue();
		lk2 += (Integer) kurse.get(7).getValue();

		lk3 += (Integer) kurse.get(8).getValue();
		lk3 += (Integer) kurse.get(9).getValue();
		lk3 += (Integer) kurse.get(10).getValue();
		lk3 += (Integer) kurse.get(11).getValue();

		ArrayList<JSpinner> helpLK = new ArrayList<JSpinner>();
		for (int i = 0; i < 12; i++) {
			helpLK.add(kurse.get(0));
			kurse.remove(0);
		}

		ArrayList<Integer> lk = new ArrayList<Integer>();
		lk.add(lk1);
		lk.add(lk2);
		lk.add(lk3);
		Collections.sort(lk, Collections.reverseOrder());

		// "2x" anzeigen
		doppelt_0.setVisible(false);
		doppelt_1.setVisible(false);
		doppelt_2.setVisible(false);
		if (lk1 > lk2 || lk1 > lk3) {
			doppelt_0.setVisible(true);
		}
		if (lk2 > lk1 || lk2 > lk3) {
			doppelt_1.setVisible(true);
		}
		if (lk3 > lk1 || lk3 > lk2) {
			doppelt_2.setVisible(true);
		}
		if (lk1 == lk2) {
			doppelt_0.setVisible(true);
		}
		if (lk2 == lk3) {
			doppelt_1.setVisible(true);
		}
		if (lk1 == lk3) {
			doppelt_0.setVisible(true);
		}

		int punkteLKs = ((lk.get(0) + lk.get(1)) * 2) + lk.get(2);

		// GKs
		int punkteGKs = 0;
		for (JSpinner sp : kurse) {
			punkteGKs += (Integer) sp.getValue();
		}
		int punkteBlock1 = 0;
		if (checkBox_00.isSelected()) {
			punkteBlock1 = punkteLKs + punkteGKs + (Integer) spinner_45.getValue();
		} else {
			punkteBlock1 = punkteLKs + punkteGKs;
		}
		double d = punkteBlock1;
		d = d * 40 / 44;
		punkteBlock1 = (int) Math.round(d);

		// BLOCK_2
		int punkteBlock2 = 0;
		if (switch_00.isSelected() || switch_01.isSelected()) {
			punkteBlock2 += (Integer) spinner_40.getValue();
			punkteBlock2 += (Integer) spinner_41.getValue();
			punkteBlock2 += (Integer) spinner_42.getValue();
			punkteBlock2 += (Integer) spinner_43.getValue();
			punkteBlock2 += (Integer) spinner_44.getValue();
			punkteBlock2 = punkteBlock2 * 4;
		} else {
			punkteBlock2 += (Integer) spinner_40.getValue();
			punkteBlock2 += (Integer) spinner_41.getValue();
			punkteBlock2 += (Integer) spinner_42.getValue();
			punkteBlock2 += (Integer) spinner_43.getValue();
			punkteBlock2 = punkteBlock2 * 5;
		}
		Collections.reverse(helpLK);
		for (JSpinner sp : helpLK) {
			kurse.add(0, sp);
		}

		int punkte = punkteBlock1 + punkteBlock2;
		return new Data(kurse, punkte, punkteBlock1, punkteBlock2, nE);
	}

	public ArrayList<JSpinner> restKurseBerechnen(ArrayList<JSpinner> restKurse, int fehlendeKurse) {
		ArrayList<JSpinner> kurse = restKurse;
		Collections.sort(kurse, new Comparator<JSpinner>() {
			public int compare(JSpinner s1, JSpinner s2) {
				return Integer.valueOf((Integer) s2.getValue()).compareTo((Integer) s1.getValue());
			}
		});

		for (int i = kurse.size(); i > fehlendeKurse; i--) {
			kurse.remove(kurse.size() - 1);
		}
		return kurse;
	}

	public String schnitt(int p, int b1, int b2) {
		String out = "";
		double count = 0.0;
		int zahl = 300;
		int help = 18;
		int pUnten = help - 1;
		int pOben = 1;
		if (b1 >= 200 && b2 >= 100) {
			if (p == 400) {
				out = "4.0";
			} else {
				while (zahl < p) {
					if (help == 18) {
						help = 0;
						count++;
					}
					help++;
					zahl++;
				}
				pUnten = help - 1;
				pOben = 1;

				while (help != 18) {
					pOben++;
					help++;
				}
				textField_1.setText(" " + Character.toString((char) 0x2191) + pOben);
				textField_2.setText(" " + Character.toString((char) 0x2193) + pUnten);
				count = count * 0.1;
				count = 4.0 - count;
				count = count * 100;
				count = Math.round(count);
				count = count / 100;
				out = Double.toString(count);
			}
		} else {
			out = "FAIL";
		}

		return new String(out);
	}

	public void changeColor(Data dat, ArrayList<JSpinner> kurse, String schnitt) {
		colorReset();
		// Farbliche Markierung der Kurse
		for (Component c : contentPane.getComponents()) {
			if (c instanceof JSpinner && ((kurse.contains(((JSpinner) c))) || isABIPruefung((JSpinner) c))) {
				((JSpinner) c).getEditor().getComponent(0).setBackground(Color.decode("#339900"));
			} else {
				// Wenn nicht ABIPrüfung -> rot
				if (c instanceof JSpinner && !isABIPruefung((JSpinner) c)) {
					((JSpinner) c).getEditor().getComponent(0).setBackground(Color.decode("#9b111e"));
					((JSpinner) c).getEditor().getComponent(0).setForeground(Color.LIGHT_GRAY);
				}
			}
		}
		if (checkBox_00.isSelected()) {
			spinner_45.getEditor().getComponent(0).setBackground(Color.decode("#339900"));
			spinner_45.getEditor().getComponent(0).setForeground(UIManager.getColor("textText"));
		} else {
			spinner_45.getEditor().getComponent(0).setBackground(Color.decode("#9b111e"));
			spinner_45.getEditor().getComponent(0).setForeground(Color.LIGHT_GRAY);
		}
		if (schnitt.charAt(0) == 'F') {
			textField_0.setBackground(Color.decode("#000000"));
			textField_0.setForeground(Color.LIGHT_GRAY);
		}
		if (schnitt.charAt(0) == '4') {
			textField_0.setBackground(Color.decode("#9b111e"));
			textField_0.setForeground(Color.LIGHT_GRAY);
		}
		if (schnitt.charAt(0) == '3') {
			textField_0.setBackground(Color.decode("#FFBF00"));
			textField_0.setForeground(UIManager.getColor("textText"));
		}
		if (schnitt.charAt(0) == '2') {
			textField_0.setBackground(Color.decode("#339900"));
			textField_0.setForeground(UIManager.getColor("textText"));
		}
		if (schnitt.charAt(0) == '1' || schnitt.charAt(0) == '0') {
			textField_0.setBackground(Color.decode("#00BFFF"));
			textField_0.setForeground(UIManager.getColor("textText"));
		}
	}

	public void colorReset() {
		// Reset der Spinner Hintergrund-Farben
		for (Component c : contentPane.getComponents()) {
			if (c instanceof JSpinner) {
				((JSpinner) c).getEditor().getComponent(0).setBackground(Color.gray);
				((JSpinner) c).getEditor().getComponent(0).setForeground(UIManager.getColor("textText"));
			}
		}
	}

	private void load() {
		ArrayList<Integer> spinner = new ArrayList<Integer>();
		ArrayList<Integer> check = new ArrayList<Integer>();
		ArrayList<Integer> faecher = new ArrayList<Integer>();

		// Werte der Spinner werden erfasst
		while (save.charAt(0) != '/') {
			String zahl = "";
			while (save.charAt(0) != ';') {
				zahl = zahl + save.charAt(0);
				save = save.substring(1);
			}
			spinner.add(Integer.parseInt(zahl));
			save = save.substring(1);
		}
		save = save.substring(1);

		// Werte der Switch/Checkboxen werden erfasst
		while (save.charAt(0) != '/') {
			if (save.charAt(0) == '0') {
				check.add(0);
			} else {
				check.add(1);
			}
			save = save.substring(1);
		}
		save = save.substring(1);

		// Namen der Fächer werden erfasst
		while (save.charAt(0) != '/') {
			String fach = "";
			while (save.charAt(0) != ';') {
				fach += save.charAt(0);
				save = save.substring(1);
			}
			faecher.add(Integer.parseInt(fach));
			save = save.substring(1);
		}

		// Werte der Datei werden für Componets gesetzt
		int tb = 0;
		for (Component c : contentPane.getComponents()) {

			// Spinner werden gesetzt
			if (c instanceof JSpinner) {
				((JSpinner) c).setValue(spinner.get(0));
				spinner.remove(0);
			}

			// Switch wird gesetzt
			if (c instanceof JToggleButton && tb < 2) {
				if (check.get(0) == 1 && !((JToggleButton) c).isSelected()) {
					((JToggleButton) c).doClick();
				}
				if (check.get(0) == 0 && ((JToggleButton) c).isSelected()) {
					((JToggleButton) c).doClick();
				}
				check.remove(0);
				tb++;
			}

			// Check Boxen werden gesetzt
			if (c instanceof JCheckBox) {
				if (check.get(0) == 1) {
					((JCheckBox) c).setSelected(true);
				} else {
					((JCheckBox) c).setSelected(false);
				}
				check.remove(0);
			}

			// ComboBoxen Fach werden gesetzt
			if (c instanceof JComboBox) {
				((JComboBox<?>) c).setSelectedIndex(faecher.get(0));
				faecher.remove(0);
			}
		}
	}

	private void save() {
		String spinner = "";
		String check = "";
		String fach = "";
		for (Component c : contentPane.getComponents()) {
			boolean set = false;

			// Spinner
			if (c instanceof JSpinner) {
				spinner += ((JSpinner) c).getValue() + ";";
				set = true;
			}
			// ToggleButton
			if (c instanceof JToggleButton && set == false) {

				if (((JToggleButton) c).isSelected()) {
					check += "1";
				} else {
					check += "0";
				}
				set = true;
			}
			// Checkboxen
			if (c instanceof JCheckBox && set == false) {
				if (((JCheckBox) c).isSelected()) {
					check += "1";
				} else {
					check += "0";
				}
				set = true;
			}
			// Fächer
			if (c instanceof JComboBox && set == false) {
				fach += ((JComboBox<?>) c).getSelectedIndex() + ";";
			}
		}
		String text = spinner + "/" + check + "/" + fach + "/";
		System.out.println(text);
		System.out.println("-------------------------------------------");
		try {
			Files.delete(Paths.get("save.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	private static String readFile() {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get("save.txt"), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}
}
