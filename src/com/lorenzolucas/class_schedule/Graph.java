/*
 * File: ExpressionStack.java
 * Name: Lorenzo Lucas
 * Date: 3/10/2019
 * Purpose: Builds a directed graph and performs topological sort of the graph's vertices
 */
package com.lorenzolucas.class_schedule;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph<T> {
    private ArrayList<LinkedList<Integer>> adjacencyList = new ArrayList<>();
    private static HashMap<String,Integer> findVertex = new HashMap<>();
    private Stack<String> stack = new Stack<>();
    private boolean[] discovered;
    private boolean[] finished;
    private T input;
    private StringBuilder results = new StringBuilder();

    //constructor for directed graph that takes user input to build graph
    public Graph(T input) throws FileNotFoundException{
        this.input = input;
        buildGraph(String.valueOf(input));
        setBooleans();
    }

    //method to build graph
    private void buildGraph(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        int index = 0;
        int adjIndex = 0;
        int linkedIndex = 0;
        while (scanner.hasNextLine()){
            //scans each line of the file and splits the line into a string array of classes
            String lineOfClasses = scanner.nextLine();
            String[] classes = lineOfClasses.split(" ");
            //this linked list is the list for the first class of each line. it allows for the dependant classes to be linked
            LinkedList<Integer> linkedList = new LinkedList<>();
            for (int i = 0; i < classes.length; i++){
                if ((!findVertex.containsKey(classes[i]))) {
                    //this separates the main class from the dependant classes
                    if (i == 0){
                        findVertex.put(classes[i], index);
                        adjacencyList.add(index,linkedList);
                        index++;
                    } else {
                        findVertex.put(classes[i],index);
                        //this adds dependant classes to the arraylist without adding anything to its linked list
                        LinkedList<Integer> temp = new LinkedList<>();
                        adjacencyList.add(index,temp);
                        linkedList.add(linkedIndex,index);
                        linkedIndex++;
                        index++;
                    }
                } else if (findVertex.containsKey(classes[i])){
                    //this allows you to modify a linked list of a vertex already added to the arraylist
                    int vertexIndex = findVertex.get(classes[i]);
                    if (i == 0){
                        adjIndex = findVertex.get(classes[i]);
                        adjacencyList.set(adjIndex,linkedList);
                    } else {
                        linkedList.add(linkedIndex,vertexIndex);
                        linkedIndex++;
                    }
                }
            }
            //resets the linked list index for each while repetition
            linkedIndex = 0;
        }
        scanner.close();
    }
    //creates arrays to assist depth first search in figuring out if a vertex has been discovered or finished
    public void setBooleans(){
        discovered = new boolean[findVertex.size()];
        finished = new boolean[findVertex.size()];
        for (int i = 0; i < findVertex.size(); i++){
            discovered[i] = false;
            finished[i] = false;
        }
    }
    //gets classname from user, performs DFS on that vertex, and returns recompilation
    public String getClassName(String className) throws CycleException, NameFormatException{
        if (!findVertex.containsKey(className)){
            throw new NameFormatException("Wrong name format");
        }
        int classIndex = findVertex.get(className);
        DFS(classIndex);
        //builds a string containing the results that pushed on the stack in reverse order
        while (!stack.empty()){
            results.append(stack.pop() + " ");
        }
        String finalResult = String.valueOf(results);
        results.setLength(0);
        return finalResult;
    }

    //performs the topological sort of a vertex
    private void DFS(int vertex) throws CycleException{
        if (discovered[vertex]){
            throw new CycleException("Cycle Found");
        }
        if (finished[vertex]){
            return;
        }
        discovered[vertex] = true;
        //checks if the linked list has indices and performs DFS
        if (adjacencyList.get(0).size() > 0){
            for (int i = 0; i < adjacencyList.get(vertex).size();i++){
                DFS(adjacencyList.get(vertex).get(i));
            }
        }
        finished[vertex] = true;
        stack.push(getKey(vertex));
    }

    //method created to retrieve string key from hashmap
    private String getKey(int value) {
        Object[] keys = findVertex.keySet().toArray();
        String className = null;
        for (Object key : keys) {
            if (findVertex.get(key.toString()) == value) {
                className = key.toString();
            }
        }
        return className;
    }

}
