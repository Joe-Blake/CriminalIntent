package database.CrimeDbSchema;

/**
 * Created by joe on 2016/9/27.
 *
 * 定义数据表元素常量
 */

public class CrimeDbSchema {

    public static final class CrimeTable {

        public static final String NAME = "crimes";     //表名

        public static final class Cols {    //字段
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }

    }
}
