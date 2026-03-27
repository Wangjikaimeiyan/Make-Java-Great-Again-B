package com.example.Utils;

public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();/*创建线程内存*/

    public static void setCurrentId(Integer employeeId) {/*设置当前员工id*/
        CURRENT_LOCAL.set(employeeId);
    }

    public static Integer getCurrentId() {/*获取当前员工id*/
        return CURRENT_LOCAL.get();
    }

    public static void remove() {/*删除当前员工id*/
        CURRENT_LOCAL.remove();
    }
}
