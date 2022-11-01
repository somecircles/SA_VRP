package test;

import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class test {
    static int customerNum=11;
    static int depotNum=1;
    static double capacity=20;
    static Integer Huge=99999999;
    static double [][]node={{20,30},{40,10},{15,21},{41,36},{33,29},{27,21},{43,32},{25,18},{16,17},{37,31},{43,40},{22,21}};
    static double []demand={0,2,6,3,5,1,4,3,5,4,4,3};
    static Random random=new Random();
    static double temperature=1000;
    static double alpha=0.99;
    /*public static void paint(ArrayList<Integer> path){
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

    }*/
    @Test
    public void p(){
        Random random=new Random();
        ArrayList<Integer> path=new ArrayList<>();
        for (int i=0;i<10;i++){
            path.add(11-i);
        }
        ArrayList<Integer> tempPath= (ArrayList<Integer>) path.clone();
        System.out.println(tempPath);
        int pos1= random.nextInt(path.size()-2)+1;
        int pos2= random.nextInt(path.size()-2)+1;
        System.out.print(pos1);
        System.out.print(",");
        System.out.println(pos2);
        Collections.swap(tempPath,pos1,pos2);
        System.out.println(tempPath);
    }

    @Test
    public void test(){
        int a=1;
        int b=2;
        int c=3;
        int d=4;
        System.out.println(d);
        if (a>b){
            d=0;
        }
        else {
            d=1;
        }
        System.out.println(d);
        if (a>b){
            d=0;
        } else if (a>c) {
            d=1;
        }
        else if(b<c){
            d=3;
        }

        else {
            d=2;
        }
        System.out.println(d);
        if(a<b){
            d=0;
        }
        if(a<c){
            d=1;
        }
        System.out.println(d);

        int i=0;
        while (true){
            if(i%2==1) {
                i++;
                continue;
            }
            System.out.println(i);
            i++;
            if(i>=1000){
                break;
            }
        }

    }

}
