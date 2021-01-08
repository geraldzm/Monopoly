package Client.model.Handler;

import java.util.ArrayList;

public abstract class Handler<Type>{

    ArrayList<Type> objectsList;

    public Handler() {
        this.objectsList = new ArrayList<>();
    }

    public Handler(ArrayList<Type> objectsList) {
        this.objectsList = objectsList;
    }

    public void addObject(Type object){
        objectsList.add(object);
    }

    public  void removeObject(Type object) {
        objectsList.remove(object);
    }

    public ArrayList<Type> getList() {
        return objectsList;
    }

    public void setObjectsList(ArrayList<Type> objectsList) {
        this.objectsList = objectsList;
    }

    public <T extends Type> void addObjectsList(ArrayList<T> objectsList) {
        for (int i = 0; i < objectsList.size(); i++)
            this.objectsList.add(objectsList.get(i));
    }

}