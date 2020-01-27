package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    //点的个数
    //站点名字
    //站点边的权值
    private int pointNum;
    private String[] point;
    public int graph[][];
    private String id;
    private int sum = 0;


    //利用构造函数初始化
    public Graph(int pointNum) {
        this.pointNum = pointNum;
        point = new String[pointNum];
        graph = new int[pointNum][pointNum];
        //初始化
        for (int i = 0; i < pointNum; i++) {
            for (int j = 0; j < pointNum; j++) {
                graph[i][j] = -1;
            }
        }
    }

    //给矩阵添加值
    public void add(int x, int y, int num) {
        if (graph[x - 1][y - 1] == -1) {
            graph[x - 1][y - 1] = num;
//            graph[y-1][x-1] = num;
        }
    }

    //清空矩阵
    public void clear(){
        if (graph!=null){
            for (int i = 0; i < pointNum; i++) {
                for (int j = 0; j < pointNum; j++) {
                    graph[i][j] = -1;
                }
            }
        }
        if (point!=null){
            for (int i = 0;i<point.length;i++){
                point[i] = null;
            }
        }
    }

    //打印稀疏矩阵
    public void show() {
        for (int i = 0; i < pointNum; i++) {
            for (int j = 0; j < pointNum; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < pointNum; i++) {
            point[i] = null;
        }
    }


    //path记录路径，返回提交,path第一位用来储存整个路径的长度，其他pointNum位来储存路径，要注意，存储顺序是从数组末位开始的
    //front记录某点的最短路径由哪点推出
    //dis记录最短路径
    //isUsed表示受否用到
    public int[] findPath(int start, int end) {
        int path[] = new int[pointNum + 1];
        int front[] = new int[pointNum];
        int dis[] = new int[pointNum];
        boolean isUsed[] = new boolean[pointNum];
        //初始化
        for (int i = 0; i < pointNum; i++) {
            path[i] = -1;
            front[i] = -1;
            dis[i] = -1;
            isUsed[i] = false;
        }
        //第一次键入值初始化
        int startx = start - 1;
        for (int i = 0; i < pointNum; i++) {
            if (graph[startx][i] != -1) {
                isUsed[startx] = true;
                front[i] = startx;
                dis[i] = graph[startx][i];
            }
        }

        //循环体把点遍历一遍
        for (int num = 1; num < pointNum; num++) {

            //找到路径
            if (dis[end - 1] != -1) {
                //把路径存到数组里
                int endx = end - 1;
                for (int k = 0; k < pointNum; k++) {
                    if (endx != -1) {
                        path[pointNum - k] = front[endx];
                        endx = front[endx];
                    }
                }
                path[0] = dis[end - 1];
                return path;
            }

            //寻找最小值
            int min = 100000000;
            for (int j = 0; j < pointNum; j++) {
                if (!isUsed[j] && dis[j] <= min && dis[j] != -1) {
                    min = dis[j];
                    startx = j;
                }
            }
            //把值置为用过
            isUsed[startx] = true;

            //替换路径
            for (int i = 0; i < pointNum; i++) {
                //如果图的这个位置可以走通
                if (graph[startx][i] != -1) {
                    //这个位置没有达到起点或者路径长
                    if ((graph[startx][i] + dis[startx]) <= dis[i] || dis[i] == -1) {
                        front[i] = startx;
                        dis[i] = graph[startx][i] + dis[startx];
                    }
                }
            }

//            System.out.println("dis:");
//            for (int s = 0; s < pointNum; s++) {
//                System.out.print(dis[s] + " ");
//            }
//            System.out.println();
//            System.out.println("path:");
//            for (int s = 0; s < pointNum; s++) {
//                System.out.print(front[s] + " ");
//            }
//            System.out.println();
        }
        return null;
    }

    //总路程增加
    public void incSum(int num) {
        this.sum += num;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public String[] getPoint() {
        return point;
    }

    public void setPoint(String[] point) {
        this.point = point;
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

}
