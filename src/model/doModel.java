package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import model.*;

import static java.lang.Thread.sleep;

public class doModel {
    private static Graph g;
    private static int pointNum = -1;
    private static List<Graph> graphs = new ArrayList<>();
    private static List<stationModel> stationModels = new ArrayList<>();
    private static List<busModel> busModels = new ArrayList<>();
    private static List<routesModel> routesModels = new ArrayList<>();


    //增删改公交信息
    //type为1为增加条目，type为2为修改站点，type为3为删除条目
    private static void changeBus(int type, String id, String name, String direct, int newsite1, int newsite2) {
        String path = "src/data/buses.txt";
        List<String[]> data = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(path));
            System.out.print("");
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                data.add(str.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // list转化为数组
        String[][] result = data.toArray(new String[][]{});
        //增加条目
        if (type == 1) {
            writeIn(path, id + " " + name + " " + direct + " " + String.valueOf(newsite1) + " " + String.valueOf(newsite2));
        } else if (type == 2) {
            for (int i = 1; i < result.length; i++) {
                if (result[i][0].equals(id)) {
                    autoReplace(path, result[i][1], name);
                }
            }
        } else if (type == 3) {
            for (int i = 1; i < result.length; i++) {
                if (result[i][0].equals(id)) {
                    autoReplace(path, "\r\n" + result[i][0] + " " + result[i][1] + " " + result[i][2] + " " + result[i][3] + " " + result[i][4], "");
                }
            }
        } else {
            System.out.println("输入有误");
        }

        clear();
        Initial();
    }

    //增删改站点
    public static void changeStation(int type, String content, String oldStr, String newStr) {
        //type等于1 代表追加写入  type等于2代表修改 等于3代表删除
        String path = "src/data/stations.txt";
        //读入station
        List<String[]> stationdata = new ArrayList<>();
        try {
            Scanner scanner2 = new Scanner(new File(path));

            while (scanner2.hasNextLine()) {
                String str2 = scanner2.nextLine();
                stationdata.add(str2.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // list转化为数组
        String[][] result2 = stationdata.toArray(new String[][]{});

        if (type == 1) {
            writeIn(path, content);
            //更改站点数目
            String num = result2[0][0];
            String num2 = String.valueOf(Integer.parseInt(num) + 1);
            autoReplace(path, num, num2);
            autoReplace(path, num2 + " " + result2[Integer.parseInt(num)][1], num + " " + result2[Integer.parseInt(num)][1]);

        } else if (type == 2) {
            autoReplace(path, oldStr, newStr);
        } else if (type == 3) {
            //改变路线里站点
            //
            //删除整行数据
            autoReplace(path, "\r\n" + findStationInStations(oldStr), "");
            //更改站点数目
            String num = result2[0][0];
            String num2 = String.valueOf(Integer.parseInt(num) - 1);
            autoReplace(path, num, num2);
            autoReplace(path, num2 + " " + result2[Integer.parseInt(num)][1], num + " " + result2[Integer.parseInt(num)][1]);
        } else {
            System.out.println("输入错误");
        }
        clear();
        Initial();

    }

    //增删改路径
    //type为操作类型，type为1为修改站点，type为2为修改长度，type为3为删除中间站，并连接前后两个站 type = 4 为添加路线
    public static void changeRoutes(int type, String id, int oldPoint1, int oldPoint2, int newPoint1, int newPoint2, int length) {
        String path = "src/data/routes.txt";
        String path2 = "src/data/buses.txt";
        //打开routes
        List<String[]> routes = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNextLine()) {
                String str1 = sc.nextLine();
                //可删下面那行
                System.out.print("");
                routes.add(str1.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[][] result1 = routes.toArray(new String[][]{});

        //打开bus文件
        List<String[]> busdata = new ArrayList<>();
        try {
            Scanner scanner3 = new Scanner(new File(path2));
            //下面那行没有用
            int dsfdsfdfs = 1;
            while (scanner3.hasNextLine()) {
                String str3 = scanner3.nextLine();
                busdata.add(str3.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // list转化为数组
        String[][] result3 = busdata.toArray(new String[][]{});

        //更改站点 传值为全部的
        if (type == 1) {
            String olds = null;
            for (int i = 1; i < result1.length; i++) {
                if (result1[i][1].equals(String.valueOf(oldPoint1)) && result1[i][2].equals(String.valueOf(oldPoint2)) && result1[i][0].equals(id)) {
                    olds = result1[i][1] + " " + result1[i][2] + " " + result1[i][3];
                    //找是否更改的站点是起始点
                    for (int j = 1; j < result3.length; j++) {
                        if (id.equals(result3[j][0])) {                              //找到对应的id
                            if (String.valueOf(oldPoint1).equals(result3[j][3])) {   //如果起始点是要被更改的点
                                //把第一个点替换掉
                                autoReplace(path2, result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result3[j][3] + " " + result3[j][4],
                                        result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + String.valueOf(newPoint1) + " " + result3[j][4]);
                            } else if (String.valueOf(oldPoint2).equals(result3[j][4])) {      //如果终点是要被更改的点
                                autoReplace(path2, result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result3[j][3] + " " + result3[j][4],
                                        result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + String.valueOf(newPoint2) + " " + result3[j][4]);
                            }
                        }
                    }
                }
            }
            if (olds == null) {
                System.out.println("输入错误");
                return;
            }
            autoReplace(path, olds, newPoint1 + " " + newPoint2 + " " + length);
        }
        //传值有length和oldpoint
        else if (type == 2) {
            String oldLength = null;

            for (int i = 1; i < result1.length; i++) {
                if (result1[i][1].equals(String.valueOf(oldPoint1)) && result1[i][2].equals(String.valueOf(oldPoint2)) && result1[i][0].equals(id)) {
                    oldLength = result1[i][3];
                }
            }
            if (oldLength == null) {
                System.out.println("输入有误");
                return;
            }
            String s1 = oldPoint1 + " " + oldPoint2 + " " + oldLength;
            String s2 = oldPoint1 + " " + oldPoint2 + " " + length;
            autoReplace(path, s1, s2);
        }
        //传参只有id和第一个站点
        else if (type == 3) {
            boolean flag = false;
            //point 是用来计数route里被删除的数的前后点，这样子可以连接起来 pointnum用来看是不是删除了两行
            String firstpoint1 = null;
            String sepoint2 = null;
            int pointcount = 0;
            for (int i = 1; i < result1.length; i++) {
                //如果前点或者后点包含要删除的点
                if ((result1[i][2].equals(String.valueOf(oldPoint1)) || result1[i][1].equals(String.valueOf(oldPoint1))) && result1[i][0].equals(id)) {
                    flag = true;
                    if (result1[i][2].equals(String.valueOf(oldPoint1))) {
                        firstpoint1 = result1[i][1];
                        pointcount++;
                    }
                    if (result1[i][1].equals(String.valueOf(oldPoint1))) {
                        sepoint2 = result1[i][2];
                        pointcount++;
                    }
                    if (pointcount != 2) {
                        String s1 = result1[i][0] + " " + result1[i][1] + " " + result1[i][2] + " " + result1[i][3];
                        autoReplace(path, "\r\n" + s1, "");
                    } else {
                        //相当于删除两行，不过第二行修改成其他的
                        String s1 = result1[i][0] + " " + result1[i][1] + " " + result1[i][2] + " " + result1[i][3];

                        String s2 = result1[i][0] + " " + firstpoint1 + " " + sepoint2 + " " + result1[i][3];
                        autoReplace(path, s1, s2);
                    }


                    //删除如果是起点和终点则操作
                    for (int j = 1; j < result3.length; j++) {
                        if (result3[j][0].equals(id)) {
                            //找到对应的id
                            if (String.valueOf(oldPoint1).equals(result3[j][3])) {   //如果起始点是要被更改的点
                                //把第一个点改成同路径后点
                                autoReplace(path2, result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result3[j][3] + " " + result3[j][4],
                                        result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result1[i][2] + " " + result3[j][4]);
                            } else if (String.valueOf(oldPoint1).equals(result3[j][4])) {      //如果终点是要被更改的点
                                autoReplace(path2, result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result3[j][3] + " " + result3[j][4],
                                        result3[j][0] + " " + result3[j][1] + " " + result3[j][2] + " " + result1[i][1] + " " + result3[j][4]);
                            }
                        }
                    }

                }
            }
            if (!flag) {
                System.out.println("输入有误");
            }
        }
        //添加，追加写入
        else if (type == 4) {
            writeIn(path, id + " " + String.valueOf(newPoint1) + " " + String.valueOf(newPoint2) + " " + String.valueOf(length));
        } else {
            System.out.println("输入有误");
        }
        clear();
        Initial();
    }


    //文件追加写入
    private static void writeIn(String file, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write("\r\n" + content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //修改文件
    private static void autoReplace(String filePath, String oldstr, String newStr) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] fileContext = new byte[fileLength.intValue()];
        FileInputStream in = null;
        PrintWriter out = null;
        try {
            in = new FileInputStream(filePath);
            in.read(fileContext);
            // 避免出现中文乱码
            String str = new String(fileContext, "utf-8");
            str = str.replace(oldstr, newStr);
            out = new PrintWriter(filePath);
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //找到路线名并输出路径
    public static void findIdInRouteANDPrint(String s) {
        for (int i = 0; i < routesModels.size(); i++) {
            if (routesModels.get(i).getRouteId().equals(s)) {
                for (int k = 0; k < routesModels.get(i).Site.size(); k++) {
                    if (k == 0) {
                        System.out.print(routesModels.get(i).Site.get(k));
                    } else {
                        System.out.print(" --> " + routesModels.get(i).Site.get(k));
                    }
                }
                System.out.println();
            }
        }
    }

    //找出在路径中的站点并输出
    public static void findSiteInRouteANDPrint(String s) {
        for (int i = 0; i < routesModels.size(); i++) {
            for (int j = 0; j < routesModels.get(i).Site.size(); j++) {
                if (routesModels.get(i).Site.get(j).equals(s)) {
                    //输出
                    for (int k = 0; k < routesModels.get(i).Site.size(); k++) {
                        if (k == 0) {
                            System.out.print(routesModels.get(i).Site.get(k));
                        } else {
                            System.out.print(" -> " + routesModels.get(i).Site.get(k));
                        }
                    }
                    System.out.println();
                    break;
                }
            }
        }
    }

    //找到站点名字，返回model
    private static String findStationInStations(String station) {
        for (int i = 0; i < stationModels.size(); i++) {
            if (station.equals(stationModels.get(i).getName())) {
                String ans = stationModels.get(i).getId() + " " + station;
                return ans;
            }
        }
        return null;
    }

    // type 是所找图的位置，-1代表是主图，其他是代表在graphs里的位置
    public static void findPathInGraph(int type, int start, int end) {
        //查找图上两点路径经过的点
        int temp[];
        if (type == -1) {
            temp = g.findPath(start, end);
        } else {
            temp = graphs.get(type).findPath(start, end);
        }
        if (temp != null) {
            for (int i = 1; i < temp.length; i++) {
                if (temp[i] != -1)
                    System.out.print("站点" + (temp[i] + 1) + " ->");
            }
            System.out.println("目的地");

            System.out.println("长度为：" + temp[0]);
        } else {
            System.out.println("未找到");
        }
    }

    //判断主图是否有路线
    private static boolean isHasPath(int start, int end) {
        return g.findPath(start, end) != null;
//        if (g.findPath(start, end) != null) {
//            return true;
//        }
//        return false;
    }

    public static int[] isHaveBuses(int start, int end) {
        int temp[] = new int[2];
        temp[0] = -1;
        temp[1] = -1;
        if (isHasPath(start, end)) {
            //是否有单条线路满足
            for (int i = 0; i < routesModels.size(); i++) {
                if (routesModels.get(i).isHasPath(start, end)) {
                    temp[0] = i + 1;
                    return temp;
                }
            }
            //计算两条线
            //route1用于存储包含起点站台的路线
            //route2用于存储包含终点站台的路线
            int[] route1 = new int[routesModels.size()];
            int[] route2 = new int[routesModels.size()];
            for (int i = 0; i < routesModels.size(); i++) {
                route1[i] = -1;
                route2[i] = -1;
            }
            int tempRoute1 = 0;
            int tempRoute2 = 0;
            //找包含起点终点的路线号
            for (int i = 0; i < routesModels.size(); i++) {
                int temp1 = routesModels.get(i).Site.indexOf(String.valueOf(start));
                int temp2 = routesModels.get(i).Site.indexOf(String.valueOf(end));
                if (temp1 != -1) {
                    route1[tempRoute1++] = i;
                }
                if (temp2 != -1) {
                    route2[tempRoute2++] = i;
                }
            }

            //通过找他们是否有中转站和中转站是否在起点之后，终点之后来判断
            for (int i = 0; i < routesModels.size(); i++) {
                for (int j = 0; j < routesModels.size(); j++) {
                    if (route1[i] != -1 && route2[j] != -1 && route1[i] != route2[j]) {
                        for (int m = 0; m < routesModels.get(route1[i]).Site.size(); m++) {
                            for (int n = 0; n < routesModels.get(route2[j]).Site.size(); n++) {
                                if (routesModels.get(route1[i]).Site.get(m).equals(routesModels.get(route2[j]).Site.get(n))
                                        && m > routesModels.get(route1[i]).Site.indexOf(String.valueOf(start))
                                        && n < routesModels.get(route2[j]).Site.indexOf(String.valueOf(end))) {
                                    temp[0] = route1[i] + 1;
                                    temp[1] = route2[j] + 1;
                                    return temp;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return null;
        }
        return null;
    }


    //在图里找相应的公交编号
    private static int findIdInGraphReturnIndex(String s) {
        for (int j = 0; j < graphs.size(); j++) {
            //注意，java字符串相等不可以用等于号
            if (graphs.get(j).getId().equals(s)) {
                return j;
            }
        }
        return -1;
    }

    //在公交路线里找相应的公交编号
    private static int findIdInRouteReturnIndex(String s) {
        for (int j = 0; j < routesModels.size(); j++) {
            //注意，java字符串相等不可以用等于号
            if (routesModels.get(j).getRouteId().equals(s)) {
                return j;
            }
        }
        return -1;
    }

    public static void showRoutes() {
        for (int i = 0; i < routesModels.size(); i++) {
            routesModels.get(i).show();
        }
    }

    public static void showGraphs() {
        for (int i = 0; i < graphs.size(); i++) {
            graphs.get(i).show();
            System.out.println("_________________________________________");
        }
    }

    public static void showStation() {
        for (int i = 0; i < stationModels.size(); i++) {
            stationModels.get(i).show();
        }
    }

    public static void showBus() {
        for (int i = 0; i < busModels.size(); i++) {
            busModels.get(i).show();
        }
    }

    //初始化站台信息
    private static void InitialStation() {
        //利用文件读入
        //站台信息
        List<String[]> stationdata = new ArrayList<>();
        try {
            Scanner scanner2 = new Scanner(new File("src/data/stations.txt"));

            while (scanner2.hasNextLine()) {
                String str2 = scanner2.nextLine();
                stationdata.add(str2.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // list转化为数组
        String[][] result2 = stationdata.toArray(new String[][]{});
        //初始化图
        g = new Graph(Integer.parseInt(result2[0][0]));
        pointNum = Integer.parseInt(result2[0][0]);
        int x2 = result2.length;
//        int y2 = result2[1].length;

        //记录站点情况
        for (int i = 1; i < x2; i++) {
            stationModel stationModel = new stationModel();
            stationModels.add(stationModel.add(result2[i][0], result2[i][1]));
        }

    }

    //初始化公交信息
    private static void InitialBus() {
        List<String[]> busdata = new ArrayList<>();
        try {
            Scanner scanner3 = new Scanner(new File("src/data/buses.txt"));

            while (scanner3.hasNextLine()) {
                String str3 = scanner3.nextLine();
                busdata.add(str3.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // list转化为数组
        String[][] result3 = busdata.toArray(new String[][]{});

        int x3 = result3.length;
        for (int i = 1; i < x3; i++) {
            busModel bus = new busModel();
            busModels.add(bus.add(result3[i][0], result3[i][1], result3[i][2], result3[i][3], result3[i][4]));
        }
    }

    //初始化路线信息
    private static void InitialRoutesANDGraphs() {
        //利用文件读入
        //路径信息
        List<String[]> routedata = new ArrayList<>();
        try {
            Scanner scanner1 = new Scanner(new File("src/data/routes.txt"));

            while (scanner1.hasNextLine()) {
                String str1 = scanner1.nextLine();

                routedata.add(str1.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // list转化为数组
        String[][] result1 = routedata.toArray(new String[][]{});

        int x1 = result1.length;
        int y1 = result1[1].length;
//        System.out.println(y1);

        //把信息存入图里

        for (int i = 1; i < x1; i++) {
            int place = findIdInGraphReturnIndex(result1[i][0]);
            int place1 = findIdInRouteReturnIndex(result1[i][0]);
            if (place1 != -1) {

                routesModels.get(place1).Site.add(result1[i][2]);
                routesModels.get(place1).incDis(Integer.parseInt(result1[i][3]));
            } else {
                routesModel route = new routesModel();
                route.setRouteId(result1[i][0]);
                route.Site.add(result1[i][1]);
                route.Site.add(result1[i][2]);
                route.incDis(Integer.parseInt(result1[i][3]));
                routesModels.add(route);
            }
            if (place != -1) {
                graphs.get(place).add(Integer.parseInt(result1[i][1]), Integer.parseInt(result1[i][2]), Integer.parseInt(result1[i][3]));
                graphs.get(place).incSum(Integer.parseInt(result1[i][3]));
            } else {
//                System.out.println(pointNum);
                Graph graph = new Graph(pointNum);
                graph.setId(result1[i][0]);
                graph.incSum(Integer.parseInt(result1[i][3]));
                graph.add(Integer.parseInt(result1[i][1]), Integer.parseInt(result1[i][2]), Integer.parseInt(result1[i][3]));
                graphs.add(graph);
            }
        }

        //把信息存到sum图里
        //sum图用来记录总的连通情况
        for (int i = 1; i < x1; i++) {
            g.setId("Sum");
            g.add(Integer.parseInt(result1[i][1]), Integer.parseInt(result1[i][2]), 0);
        }
    }

    private static void clear() {
        stationModels.clear();
        routesModels.clear();
        busModels.clear();
        graphs.clear();
        g.clear();
    }

    private static void Initial() {
        //clear
        InitialStation();
        InitialBus();
        InitialRoutesANDGraphs();
    }

    //分用户设计更显得人性化和合理，因为你不可能让用户随便更改公交数据
    //用户菜单，仅仅用于打印
    private static void CommonMenu() {
        System.out.println("请输入您的选择：");
        System.out.println("1.---查询公交站台----");
        System.out.println("2.---查看公交路线----");
        System.out.println("3.-------导航--------");
        System.out.println("4.--切换管理员模式---");
        System.out.println("5.-------退出--------");
    }

    //管理员菜单，用与打印
    private static void masterMenu() {
        System.out.println("请选择您要操作的种类：");
        System.out.println("1.----站台信息----");
        System.out.println("2.----路线信息----");
        System.out.println("3.----公交信息----");
        System.out.println("4.------退出------");
    }

    //普通用户界面
    public void CommonView() {
        int choose = -1;
        Initial();
        while (choose != 5) {
            CommonMenu();
            Scanner ch = new Scanner(System.in);
            choose = ch.nextInt();

            if (choose == 1) {
                System.out.println("请输入查询的站台名：");
                Scanner sc1 = new Scanner(System.in);
                String station = sc1.next();
                String ans = findStationInStations(station);
                if (ans == null) {
                    System.out.println("输入有误,即将返回主菜单");
                    System.out.println();
                    System.out.println();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                String[] s = ans.split(" ");
                System.out.println("查询结果为：");
                System.out.println("站台名：" + s[1] + "    编号为:" + s[0]);
            } else if (choose == 2) {
                showRoutes();
            } else if (choose == 3) {
                System.out.println("请输入出发点编号");
                Scanner instart = new Scanner(System.in);
                int start = instart.nextInt();
                System.out.println("请输入终点编号");
                Scanner inend = new Scanner(System.in);
                int end = inend.nextInt();
                int temp[];
                temp = isHaveBuses(start, end);
                if (temp == null) {
                    System.out.println("路线不存在!");
                } else if (temp[1] == -1) {
                    System.out.println("公交线路为：" + temp[0] + "号线");
                } else {
                    System.out.println("公交线路为：" + temp[0] + "号线" + "转" + temp[1] + "号线");
                }
                System.out.println();
                System.out.println();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choose == 5) {
                System.out.println("即将退出...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choose == 4) {
                String masterCode = "hello";
                System.out.println("请输入管理员密码：");
                Scanner sc = new Scanner(System.in);
                String code = sc.next();
                if (code.equals(masterCode)){
                    System.out.println("----欢迎进入----");
                    System.out.println();
                    System.out.println();
                    MasterView();
                }
                else {
                    System.out.println();
                    System.out.println("密码错误，即将返回主菜单");
                    System.out.println();
                    System.out.println();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("输入错误，请重新选择");
                System.out.println();
                System.out.println();
                System.out.println();
            }
        }


    }

    //管理员交互界面
    public void MasterView() {
        clear();
        Initial();
        int choose = -1;
        while (choose != 4) {
            masterMenu();
            Scanner ch = new Scanner(System.in);
            choose = ch.nextInt();
            if (choose == 1) {
                System.out.println("1.------写入站点------");
                System.out.println("2.----修改站台名称----");
                System.out.println("3.----删除站台数据----");
                System.out.println("4.----查看站台情况----");
                System.out.println("请输入您的选择");
                Scanner ch1 = new Scanner(System.in);
                int choose1 = ch1.nextInt();
                if (choose1 == 1) {
                    System.out.println("请输入站点编号：");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入站点名称：");
                    Scanner inName = new Scanner(System.in);
                    String name = inName.next();
                    changeStation(choose1, id + " " + name, "", "");
                } else if (choose1 == 2) {
                    System.out.println("请输入要更改的站点名字：");
                    Scanner inOldName = new Scanner(System.in);
                    String oldName = inOldName.next();
                    System.out.println("请输入新的站点名字：");
                    Scanner inNewName = new Scanner(System.in);
                    String newName = inNewName.next();
                    changeStation(choose1, "", oldName, newName);
                } else if (choose1 == 3) {
                    System.out.println("请输入要删除的站点名字：");
                    Scanner inOldName = new Scanner(System.in);
                    String oldName = inOldName.next();
                    changeStation(choose1, "", oldName, "");
                } else if (choose1 == 4) {
                    showStation();
                } else {
                    System.out.println("输入错误");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                }
            } else if (choose == 2) {
                System.out.println("1.---------添加路线---------");
                System.out.println("2.------修改路线的站台------");
                System.out.println("3.---删除含有某站点的线路---");
                System.out.println("4.---------修改长度---------");
                System.out.println("5.-查询经过某站点的所有线路-");
                System.out.println("6.---------查询线路---------");
                System.out.println("7.-------查看站台情况-------");
                System.out.println("请输入您的选择");
                Scanner ch2 = new Scanner(System.in);
                int choose2 = ch2.nextInt();
                //type为操作类型，type为1为修改站点，type为2为修改长度，type为3为删除中间站，并连接前后两个站 type = 4 为添加路线
                if (choose2 == 1) {
                    System.out.println("请输入您要添加的路线的ID号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入起点位置编号：");
                    Scanner inNewPoint1 = new Scanner(System.in);
                    int newPoint1 = inNewPoint1.nextInt();
                    System.out.println("请输入终点位置编号：");
                    Scanner inNewPoint2 = new Scanner(System.in);
                    int newPoint2 = inNewPoint2.nextInt();
                    System.out.println("请输入两点的距离：");
                    Scanner inLength = new Scanner(System.in);
                    int length = inLength.nextInt();
                    changeRoutes(4, id, -1, -1, newPoint1, newPoint2, length);
                } else if (choose2 == 2) {
                    System.out.println("请输入您要修改的路线的ID号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();

                    System.out.println("请输入要修改的起点位置编号：");
                    Scanner inOldPoint1 = new Scanner(System.in);

                    int oldPoint1 = inOldPoint1.nextInt();

                    System.out.println("请输入要修改的终点位置编号：");

                    Scanner inOldPoint2 = new Scanner(System.in);

                    int oldPoint2 = inOldPoint2.nextInt();

                    System.out.println("请输入新的起点位置编号：");
                    Scanner inNewPoint1 = new Scanner(System.in);
                    int newPoint1 = inNewPoint1.nextInt();
                    System.out.println("请输入新的终点位置编号：");
                    Scanner inNewPoint2 = new Scanner(System.in);
                    int newPoint2 = inNewPoint2.nextInt();
                    changeRoutes(1, id, oldPoint1, oldPoint2, newPoint1, newPoint2, -1);
                } else if (choose2 == 3) {
                    System.out.println("请输入您要修改的路线的ID号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入要删除的位置编号：");
                    Scanner inOldPoint1 = new Scanner(System.in);
                    int oldPoint1 = inOldPoint1.nextInt();
                    changeRoutes(3, id, oldPoint1, -1, -1, -1, -1);
                } else if (choose2 == 4) {
                    System.out.println("请输入您要更改的路线的ID号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入起点位置编号：");
                    Scanner inOldPoint1 = new Scanner(System.in);
                    //可以删，idea提醒重复看着烦加的
                    System.out.print("");

                    int oldPoint1 = inOldPoint1.nextInt();
                    System.out.println("请输入终点位置编号：");
                    Scanner inOldPoint2 = new Scanner(System.in);
                    int oldPoint2 = inOldPoint2.nextInt();

                    System.out.println("请输入新的两点的距离：");
                    Scanner inLength = new Scanner(System.in);
                    int length = inLength.nextInt();
                    changeRoutes(2, id, oldPoint1, oldPoint2, -1, -1, length);
                } else if (choose2 == 5) {
                    System.out.println("请输入要查询的站点编号");
                    Scanner sc = new Scanner(System.in);
                    String site = sc.next();
                    findSiteInRouteANDPrint(site);
                } else if (choose2 == 6) {
                    System.out.println("请输入要查询的路线编号");
                    Scanner sc = new Scanner(System.in);
                    String id = sc.next();
                    findIdInRouteANDPrint(id);
                } else if (choose2 == 7) {
                    showRoutes();
                } else {
                    System.out.println("输入错误");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                }
            } else if (choose == 3) {
                //type为1为增加条目，type为2为修改站点，type为3为删除条目
                System.out.println("1.------增加条目------");
                System.out.println("2.---修改公交路线号---");
                System.out.println("3.----删除公交数据----");
                System.out.println("4.----查看公交情况----");
                System.out.println("请输入您的选择");
                Scanner ch3 = new Scanner(System.in);
                int choose3 = ch3.nextInt();
                if (choose3 == 1) {
                    System.out.println("请输入编号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入路线号");
                    Scanner inName = new Scanner(System.in);
                    String name = inName.next();
                    System.out.println("请输入行进方向");
                    Scanner inDir = new Scanner(System.in);
                    String Dir = inDir.next();
                    System.out.println("请输入起始点编号");
                    Scanner inSite1 = new Scanner(System.in);
                    int site1 = inSite1.nextInt();
                    System.out.println("请输入终点编号");
                    Scanner inSite2 = new Scanner(System.in);
                    int site2 = inSite2.nextInt();
                    changeBus(1, id, name, Dir, site1, site2);
                } else if (choose3 == 2) {
                    System.out.println("请输入编号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    System.out.println("请输入新的路线号");

                    Scanner inName = new Scanner(System.in);
                    String name = inName.next();
                    changeBus(2, id, name, null, -1, -1);

                } else if (choose3 == 3) {
                    System.out.println("请输入要删除的编号");
                    Scanner inId = new Scanner(System.in);
                    String id = inId.next();
                    changeBus(3, id, null, null, -1, -1);
                } else if (choose3 == 4) {
                    showBus();
                } else {
                }
            } else if (choose == 4) {
                System.out.println("即将退出...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("输入错误，请重新选择");
                System.out.println();
                System.out.println();
                System.out.println();
            }
        }
    }
}
