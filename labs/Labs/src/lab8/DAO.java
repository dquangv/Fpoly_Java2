/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quang
 */
abstract public class DAO<Entity> {

    protected List<Entity> list = new ArrayList<>();

    public void add(Entity ent) {
        list.add(ent);
    }

    public void remove(Entity ent) {
        list.remove(ent);
    }

    abstract public void update(Entity ent);

    abstract public Entity find(Serializable id);

    public List<Entity> getList() {
        return list;
    }

    public void store(String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));

            oos.writeObject(list);
            oos.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void load(String path) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));

            list = (List<Entity>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
