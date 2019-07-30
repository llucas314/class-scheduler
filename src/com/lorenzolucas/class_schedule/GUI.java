/*
 * File: ExpressionStack.java
 * Name: Lorenzo Lucas
 * Date: 3/10/2019
 * Purpose: Creates a GUI for the class dependency program
 */
package com.lorenzolucas.class_schedule;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class GUI extends JFrame {
     private Graph<String> graph;

    private GUI(){
        //creates graphic user interface
        super("Class Dependency Graph");
        setSize(800,275);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,0));
        add(mainPanel);
        JLabel inputLabel = new JLabel("Input file name:");
        JTextField inputField = new JTextField();
        JButton buildButton = new JButton("Build Directed Graph");
        JLabel classLabel = new JLabel("Class to recompile:");
        JTextField classField = new JTextField();
        JButton orderButton = new JButton("Topological Order");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0,3));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(buildButton);
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new GridLayout(0,3));
        classPanel.add(classLabel);
        classPanel.add(classField);
        classPanel.add(orderButton);
        mainPanel.add(inputPanel);
        mainPanel.add(classPanel);
        JPanel recompPanel = new JPanel();
        JTextArea recompilationField = new JTextArea(3,50);
        recompilationField.setEditable(false);
        recompPanel.add(recompilationField);
        recompPanel.setLayout(new FlowLayout());
        recompPanel.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
        mainPanel.add(recompPanel);

        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = inputField.getText();
                try {
                    //builds graph from file
                    graph = new Graph<>(fileName);
                    JOptionPane.showMessageDialog(null,"Graph Built Successfully");
                } catch (FileNotFoundException fe){
                    JOptionPane.showMessageDialog(null, "File Did Not Open");
                }
            }
        });

        //provides action for topological order button
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String results = null;
                String className = classField.getText();
                try {
                    //resets boolean values to false and gets recompiled list
                    graph.setBooleans();
                    results = graph.getClassName(className);
                } catch (CycleException ce){
                    JOptionPane.showMessageDialog(null, "Cycle Found");
                } catch (NameFormatException ne){
                    JOptionPane.showMessageDialog(null, "Please Enter Correct Class Name\nExample: ClassA");
                }
                recompilationField.setText("");
                recompilationField.setText(results);
            }
        });
    }

    //main method
    public static void main(String[] args){
        GUI gui = new GUI();
        gui.setVisible(true);

    }
}
