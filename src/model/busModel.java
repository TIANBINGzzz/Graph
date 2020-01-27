//编号  线路名  起点站编号  终点站编号
//        1  539上行  1  3
//        2  539下行  3  1
package model;

public class busModel {
    //编号
    private String id;
    //路线名
    private String name;
    //上行还是下行
    private String destin;
    //起点编号
    private String startId;
    //终点编号
    private String destinationId;


    public void show() {
        System.out.println("线路编号："+id + " " +"线路名："+ name + " " +"行进方向："+ destin + " " +"起始站点编号："+ startId + " "+"终点站编号：" + destinationId);
    }

    public busModel add(String id, String name, String destin, String startId, String destinationId) {
        this.id = id;
        this.name = name;
        this.destin = destin;
        this.startId = startId;
        this.destinationId = destinationId;
        return this;
    }



    public String getDestin() {
        return destin;
    }

    public void setDestin(String destin) {
        this.destin = destin;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}
