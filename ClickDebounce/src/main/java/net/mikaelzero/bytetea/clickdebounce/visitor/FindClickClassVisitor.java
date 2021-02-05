package net.mikaelzero.bytetea.clickdebounce.visitor;

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor;

import net.mikaelzero.bytetea.clickdebounce.Utils;

import org.objectweb.asm.MethodVisitor;

public class FindClickClassVisitor extends BaseClassVisitor {
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        // 处理View.onClick()事件
        if (Utils.isViewOnclickMethod(access, name, desc)) {
            mv = new ViewDebounceMethodVisitor(mv);
        }
        // 处理 AdapterView的onItemClick() 事件
        if (Utils.isAdapterViewOnItemOnclickMethod(access, name, desc)) {
            mv = new AdapterViewDebounceMethodVisitor(mv);
        }
        return mv;
    }

}
