package com.example.Utils;

import com.example.constant.AutoFillConstant;
import com.example.enumrtation.OperationType;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自动填充工具类：统一处理公共字段（createTime/updateTime/createUser/updateUser）的反射赋值
 */
@Slf4j
public class AutoFillUtils {

    /**
     * 私有构造，禁止实例化
     */
    private AutoFillUtils() {}

    /**
     * 为实体对象自动填充公共字段
     * @param entity 目标实体对象
     * @param operationType 操作类型（INSERT/UPDATE）
     */
    public static void fill(Object entity, OperationType operationType) {
        if (entity == null) {
            log.warn("自动填充：实体对象为空，跳过填充");
            return;
        }

        // 准备填充数据
        LocalDateTime now = LocalDateTime.now();
//        Long currentUserId = CurrentHolder.getCurrentId().longValue();

        try {
            if (operationType == OperationType.INSERT) {
                // INSERT：填充 4 个字段
                fillInsertField(entity, now/*currentUserId*/);
            } else {
                // UPDATE：填充 2 个字段
                fillUpdateField(entity, now/*currentUserId*/);
            }
        } catch (Exception e) {
            log.error("自动填充失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 填充 INSERT 操作的 4 个公共字段
     */
    private static void fillInsertField(Object entity, LocalDateTime now /*Long userId*/) throws Exception {
        // 获取 set 方法
//        Method setCreateTime = getSetMethod(entity, AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setUpdateTime = getSetMethod(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
//        Method setCreateUser = getSetMethod(entity, AutoFillConstant.SET_CREATE_USER, Long.class);
//        Method setUpdateUser = getSetMethod(entity, AutoFillConstant.SET_UPDATE_USER, Long.class);

        // 反射调用赋值
//        invokeSetMethod(setCreateTime, entity, now);
        setCreateTime.invoke(entity, now);
        invokeSetMethod(setUpdateTime, entity, now);
//        invokeSetMethod(setCreateUser, entity, userId);
//        invokeSetMethod(setUpdateUser, entity, userId);
    }

    /**
     * 填充 UPDATE 操作的 2 个公共字段
     */
    private static void fillUpdateField(Object entity, LocalDateTime now/*Long userId**/) throws Exception {
        // 获取 set 方法
        Method setUpdateTime = getSetMethod(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
//        Method setUpdateUser = getSetMethod(entity, AutoFillConstant.SET_UPDATE_USER, Long.class);

        // 反射调用赋值
        log.info("更新时间：{}", now);
        invokeSetMethod(setUpdateTime, entity, now);
//        invokeSetMethod(setUpdateUser, entity, userId);/
    }

    /**
     * 通用：获取实体类的 set 方法
     * @param entity 实体对象
     * @param methodName 方法名（如 setCreateTime）
     * @param paramType 参数类型
     * @return Method 对象
     */
    private static Method getSetMethod(Object entity, String methodName, Class<?> paramType) throws NoSuchMethodException {
        return entity.getClass().getDeclaredMethod(methodName, paramType);
    }

    /**
     * 通用：反射调用 set 方法赋值
     * @param method set 方法对象
     * @param entity 目标实体
     * @param value 要赋的值
     */
    private static void invokeSetMethod(Method method, Object entity, Object value) throws Exception {
        if (method != null) {
            method.invoke(entity, value);
        }
    }
}
