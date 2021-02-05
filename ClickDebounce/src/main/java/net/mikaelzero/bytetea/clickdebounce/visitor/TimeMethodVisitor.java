package net.mikaelzero.bytetea.clickdebounce.visitor;


import net.mikaelzero.bytetea.clickdebounce.ClickDebounceExtension;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TimeMethodVisitor extends MethodVisitor {
    private ClickDebounceExtension clickDebounceExtension;

    public TimeMethodVisitor(MethodVisitor mv, ClickDebounceExtension clickDebounceExtension) {
        super(Opcodes.ASM5, mv);
        this.clickDebounceExtension = clickDebounceExtension;
    }


    @Override
    public void visitLdcInsn(Object value) {
        // 判断是否是Long型
        if (value instanceof Long) {
            // 如果  extension中读取的重复点击有效的间隔时间  为空   则设置默认为300
            if (clickDebounceExtension.getTime() == 0L) {
                super.visitLdcInsn(300L);
            } else {
                // 否则 设置为 gradle插件中配置的数值
                super.visitLdcInsn(clickDebounceExtension.getTime());
            }
        } else {
            super.visitLdcInsn(value);
        }
    }
}
