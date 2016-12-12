package com.patrickmcdonald.advent.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part2 {
    
    static ArrayList<Bot> bots = new ArrayList<>();
    static int[] output = new int[25];

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("input10.txt"));
        ArrayList<String> instructions = new ArrayList<>();
        
        for(int i=0;i<300;i++) { //fill bots initially
            Bot b = new Bot(i);
            bots.add(b);
        }
       
        while(scan.hasNext()) { //add lines as instructions
            instructions.add(scan.nextLine());
        }
        
        while(instructions.size() > 0) { //while we have stuff to do
            for(int i=0;i<instructions.size();i++) {
            String[] line = instructions.get(i).split(" "); //too lazy to indent sry
            if(line[0].equals("value")) { //if value
                Bot a = findBot(Integer.parseInt(line[5])); //get that bot and set its value
                a.values.add(Integer.parseInt(line[1]));
                instructions.remove(i--);
            }
            else {
                Bot b = findBot(Integer.parseInt(line[1])); //get the bot
                if(b.values.size() >= 2) { //if it has at least 2 chips
                    instructions.remove(i--);
                    int high = b.findHigh();
                    int low = b.findLow();
                    if(high == 61 || low == 61) //puzzle input here
                        if(low == 17 || high == 17) //puzzle input here
                            System.out.println(b.id); //print its id
                    if(line[5].equals("bot")) {
                        Bot c = findBot(Integer.parseInt(line[6]));
                        c.values.add(low);
                    }
                    else {
                        output[Integer.parseInt(line[6])] = low;
                    }
                    if(line[10].equals("bot")) {
                        Bot c = findBot(Integer.parseInt(line[11]));
                        c.values.add(high);
                    }
                    else {
                        output[Integer.parseInt(line[11])] = high;
                    }
                }
                if(output[0] != 0 && output[1] != 0 && output[2] != 0) //print out the outputs whenever ready
                    System.out.println("A:" + output[0]*output[1]*output[2]);
            }
        }
    }
        for(Bot b : bots) { //print out everything jsut because
            System.out.println("ID: " + b.id);
            for(Integer i : b.values) {
                System.out.println(i);
            }
        }
        System.out.println("Output");
        for(Integer i : output) {
            System.out.println(i);
        }
    }
        
    
    public static class Bot {
        ArrayList<Integer> values = new ArrayList<>();
        int id = 0;
        
        public Bot(int id) {
            this.id = id;
        }
        
        public void add(int i) {
            values.add(i);
        }
        
        public int findLow() {
            int lowest = 9999;
            for(int i=0;i<values.size();i++) {
                if(values.get(i) < lowest)
                    lowest = values.get(i);
            }
            values.remove(values.indexOf(lowest));
            return lowest;
        }
        
        public int findHigh() {
            int highest = 0;
            for(int i=0;i<values.size();i++) {
                if(values.get(i) > highest)
                    highest = values.get(i);
            }
            values.remove(values.indexOf(highest));
            return highest;
        }
    }
    
    public static Bot findBot(int id) {
        for(Bot b : bots) {
            if(b.id == id)
                return b;
        }
        return null;
    }
    
    public static boolean hasBot(int id) {
        for(Bot b : bots) {
            if(b.id == id) 
                return true;
        }
        return false;
    }
}
