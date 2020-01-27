package model;

public class stationModel {
    //编号
    private String id;
    //名字
    private String name;


    public stationModel add(String id,String name){
        this.id = id;
        this.name = name;
        return this;
    }

    public void show(){
        System.out.println("站点编号："+id+"         站点名："+name);
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
}
