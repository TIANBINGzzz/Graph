//线路编号  站点编号  站点编号  距离
//        1  1  2  650
package model;

import java.util.ArrayList;
import java.util.List;

public class routesModel {

    //线路编号
    private String routeId;
    //经过的站点
    public List<String> Site = new ArrayList<>();
    //距离
    private int distance;
    //站点名字
//    private List<String> siteName = new ArrayList<>();

    public boolean isHasPath(int start,int end){
        String s1 = String.valueOf(start);
        String s2 = String.valueOf(end);
        for (int i = 0;i<Site.size();i++){
            if (Site.indexOf(s1)<Site.indexOf(s2)&&Site.indexOf(s1)!=-1&&Site.indexOf(s2)!=-1){
                return true;
            }
        }
        return false;
    }

    public void show(){
        System.out.println();
        System.out.println();
        System.out.println("线路编号："+ routeId);
        System.out.println("站点编号路径为：");
        for (int i = 0;i<Site.size();i++){
            if (i!=Site.size()-1){
                System.out.print(Site.get(i)+"-->");
            }
            else {
                System.out.print(Site.get(i));
            }
        }
        System.out.println();
    }
    public void incDis(int num){
        distance += num;
    }
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
