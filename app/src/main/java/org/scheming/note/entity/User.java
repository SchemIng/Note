package org.scheming.note.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Scheming on 2015/7/8.
 */
public class User extends BmobObject {
    public String id;
    public String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
