package test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class SA {
    static int customerNum=11;
    static int depotNum=1;
    static double capacity=20;
    static Integer Huge=99999999;
    static double [][]node={{20,30},{40,10},{15,21},{41,36},{33,29},{27,21},{43,32},{25,18},{16,17},{37,31},{43,40},{22,21}};
    static double []demand={0,2,6,3,5,1,4,3,5,4,4,3};
    static Random random=new Random();
    static double temperature=1000;
    static double alpha=0.99;



    public static ArrayList<Integer> initCode(int length){
        ArrayList<Integer> path=new ArrayList<>();
        for(int i=1;i<=length;i++){
            path.add(i);
        }
        Collections.shuffle(path);
        path.add(0,0);
        path.add(0);
        return insertDepot(path);
    }

    public static ArrayList<Integer> insertDepot(ArrayList<Integer> path){
        double sum=0;
        for(int i=0;i<path.size();i++){
            sum+=demand[path.get(i)];
            if(sum>capacity){
                path.add(i,0);
                sum=demand[path.get(i)];
            }
        }
        return path;
    }

    public static double getCost(ArrayList<Integer> path){
        double cost=0;
        double sum=0;
        for(int i=0;i<path.size()-1;i++){
            cost+=getDistance(path.get(i),path.get(i+1));
            sum+=demand[path.get(i)];
            if(sum>capacity){
                cost+=Huge;
            }
            if(path.get(i)==0){
                sum=0;
            }
        }
        return cost;
    }

    public static double getDistance(Integer from, Integer to) {
        return Math.sqrt(Math.pow((node[from][0]-node[to][0]),2)+Math.pow((node[from][1]-node[to][1]),2));
    }

    public static ArrayList<Integer> exchange(ArrayList<Integer> path){
        ArrayList<Integer> tempPath= (ArrayList<Integer>) path.clone();
        int pos1= random.nextInt(path.size()-2)+1;
        int pos2= random.nextInt(path.size()-2)+1;
        Collections.swap(tempPath,pos1,pos2);
        return tempPath;
    }

    public static ArrayList<Integer> inner(ArrayList<Integer> path){
        ArrayList<Integer> tempPath=exchange(path);
        double delta=getCost(tempPath)-getCost(path);
        if(delta<=0){
            return tempPath;
        }
        else {
            if(Math.exp(-delta/temperature)>=Math.random()){
                return tempPath;
            }
            else {
                return path;
            }
        }
    }

    public static void paint(ArrayList<Integer> path){


        JPanel panel_1=new JPanel(){
            public   void   paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.gray);
                for(int i =0;i<path.size()-1;i++){
                    g.drawArc((int) node[path.get(i)][0]*10, (int) node[path.get(i)][1]*10,10,10,0,360);
                    g.drawLine((int) node[path.get(i)][0]*10, (int) node[path.get(i)][1]*10, (int) node[path.get(i+1)][0]*10, (int) node[path.get(i+1)][1]*10);
                }
            }
        };

        //创建一个类DrawUI
        JFrame jf = new JFrame("图形图像");
        jf.add(panel_1);

        //设置界面大小
        jf.setSize(1000,800);

        //设置关闭页面时的操作
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //注意是用JFrame来调用退出方法

        //将页面可视化（一般在设置好界面之后）
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        ArrayList<Integer> path=initCode(customerNum);
        ArrayList<Double> cost = new ArrayList<>();
        System.out.println(path);
        getCost(path);
        for(int i=0;i<1000;i++){
            path=inner(path);
            cost.add(getCost(path));
            temperature*=alpha;
        }
        System.out.println(path);
        System.out.println(cost);
        paint(path);
    }
}
