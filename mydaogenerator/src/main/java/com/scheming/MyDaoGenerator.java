package com.scheming;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema( 2, "org.scheming.greendao.bean");

        schema.setDefaultJavaPackageDao("org.scheming.greendao.dao");

        initUserBean(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java-gen");
    }

    private static void initUserBean(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addLongProperty("id").primaryKey().autoincrement();
        note.addStringProperty("content");
        note.addDateProperty("date");
    }
}
