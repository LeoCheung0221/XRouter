package com.tufusi.compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See @link(https://github.com/square/javapoet)
 * @Description 类类型工具类
 * 通过JavaPoet来动态生成代码
 */
public class TypeUtil {
    public static final ClassName MAPCLS = ClassName.get("java.util", "Map");
    public static final ClassName HASHMAPCLS = ClassName.get("java.util", "HashMap");
    public static final ClassName STRINGCLS = ClassName.get("java.lang", "String");
    public static final ClassName METHODTHREADCLS = ClassName.get("com.tufusi.xrouter", "ThreadFinder");
}
