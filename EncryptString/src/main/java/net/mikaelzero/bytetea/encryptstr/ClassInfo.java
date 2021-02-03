package net.mikaelzero.bytetea.encryptstr;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的描述bean
 */
public class ClassInfo {
    /**
     * 表示类的修饰符：修饰符在 ASM 中是以 “ACC_” 开头的常量进行定义。可以作用到类级别上的修饰符有：
     *    ACC_PUBLIC（public）、ACC_PRIVATE（private）、
     *    ACC_PROTECTED（protected）、ACC_FINAL（final）、ACC_SUPER（extends）、
     *    ACC_INTERFACE（接口）、ACC_ABSTRACT（抽象类）、ACC_ANNOTATION（注解类型）、
     *    ACC_ENUM（枚举类型）、ACC_DEPRECATED（标记了@Deprecated注解的类）、ACC_SYNTHETIC。
     */
    protected int access;
    /**
     * 表示类的名称：通常我们的类完整类名使用 “java.lang.Runnable” 来表示，但是到了字节码中会以路径形式表示它们 “java/lang/Runnable”
     * 值得注意的是虽然是路径表示法但是不需要写明类的 “.class” 扩展名
     */
    protected String name;
    /**
     *   表示所继承的父类。由于 Java 的类是单根结构，即所有类都继承自 java.lang.Object
     *   因此可以简单的理解为任何类都会具有一个父类。虽然在编写 Java 程序时我们没有去写 extends 关键字去明确继承的父类，
     *   但是 JDK在编译时 总会为我们加上 “ extends Object”。所以倘若某一天你看到这样一份代码也不要过于紧张。
     */
    protected String superName;
    /**
     * 表示类实现的接口，在 Java 中类是可以实现多个不同的接口因此此处是一个数组
     */
    protected String[] interfaces;

    /**
     * 注解
     */
    private List<String> annotations;

    public ClassInfo(int access, String name, String superName, String[] interfaces) {
        this.access = access;
        this.name = name;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        if (annotations == null) {
            annotations = new ArrayList<>();
        }
        annotations.add(annotation);
    }

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public int getAccess() {
        return access;
    }
}
