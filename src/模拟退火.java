import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class 模拟退火 {

    static double capacity=20;
    static double []demand=new double[]{0,3,1,4,5,2,1,2,3,4,1};
    static double []x=new double[]{30,10,32,26,40,54,37,48,25,41,35};
    static double []y=new double[]{30,20,31,44,51,27,33,15,36,29,35};
    static int []upTime=new int[]{0,10,200,10,65,73,300,150,90,85,150,140};
    static int []lowTime=new int[]{9999,100,325,120,240,450,260,180,220,290,280};
    static int []serveTime=new int[]{0,10,10,10,10,10,10,10,10,10,10};
    static double speed=1;

    static int early=1;
    static int late=3;
    static int Huge=9999999;
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
        path=insertDepot(path);
        return path;
        //return insertDepot(path);
    }

    public static ArrayList<Integer> insertDepot(ArrayList<Integer> path){
        double sum=0;
        for(int i=0;i<path.size();i++){
            sum+=demand[path.get(i)];
            if(sum>capacity) {
                path.add(i, 0);
                sum=demand[path.get(i)];
            }
        }
        return path;
    }

    public static double getCost(ArrayList<Integer> path){
        double sum=0;
        double timepunish=0;
        double load=0;
        double nowTime=0;
        for(int i=1;i<path.size();i++){
            sum+=getDistance(x[path.get(i-1)],y[path.get(i-1)],x[path.get(i)],y[path.get(i)]);
            System.out.println(nowTime);
            nowTime+=getDistance(x[path.get(i-1)],y[path.get(i-1)],x[path.get(i)],y[path.get(i)])/speed;
            if( nowTime<upTime[path.get(i)]){
                timepunish+=(upTime[path.get(i)]-nowTime)*early;
            } else if (nowTime>lowTime[path.get(i)]) {
                timepunish+=(nowTime-upTime[path.get(i)])*late;
            }
            nowTime+=serveTime[path.get(i)];
            load+=demand[path.get(i)];
            if(load>capacity){
                sum+=Huge;
            }
            if(path.get(i)==0){
                load=0;
                nowTime=0;
            }
        }
        System.out.println(sum);
        System.out.println(timepunish);
        return sum+timepunish;
    }

    public static double getDistance(double x1,double y1,double x2,double y2){
        return Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
    }


    public static ArrayList<Integer> localSearch(ArrayList<Integer> path){
        ArrayList<Integer> tempPath= (ArrayList<Integer>) path.clone();
        Random random=new Random();
        int pos1=random.nextInt(9)+1;
        int pos2=random.nextInt(9)+1;
        Collections.swap(tempPath,pos1,pos2);
        return tempPath;
    }

    public static ArrayList<Integer> inner(ArrayList<Integer> path){

        for(int i=0;i<20;i++){
            ArrayList<Integer> tempPath=localSearch(path);
            double c1=getCost(tempPath);
            double c2=getCost(path);
            if(c1<=c2){
                path=tempPath;
            }
            else {
                double delta=c1-c2;
                if(Math.exp(-delta/temperature)>=Math.random()){
                    path=tempPath;
                }
            }
        }

        return path;
    }

    public static void paint(ArrayList<Integer> path){
        JPanel panel_1=new JPanel(){
            public   void   paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.gray);
                for(int i =0;i<path.size()-1;i++){
                    g.drawArc((int) x[path.get(i)]*10-5, (int) y[path.get(i)]*10-5,10,10,0,360);
                    g.drawLine((int) x[path.get(i)]*10, (int) y[path.get(i)]*10, (int) x[path.get(i+1)]*10, (int) y[path.get(i+1)]*10);
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
        ArrayList<Integer> path;
        path=initCode(10);
        System.out.println(getCost(path));
        ArrayList<Double> cost=new ArrayList<>();
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
