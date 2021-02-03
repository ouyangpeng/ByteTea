package net.mikaelzero.bytetea.encryptstr

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * @Author:         MikaelZero
 * @CreateDate:     2020/9/9 1:32 PM
 * @Description:    如果我们的代码本身没有static 块，也就是没有 clinit 函数，那么我们的判断就不会生效，因此如果没有 clinit函数，我们需要先创建该函数
 *
 * AddClinitClassVisitor 是优先级高的转换处理，因为需要优先创建 clinit 函数，首先在访问函数的时候判断是否有该函数，如果没有，就在访问结束时创建该函数
 */
class AddClinitClassVisitor : BaseClassVisitor() {
    private var hasClinit = false

    override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?, exceptions: Array<String?>?): MethodVisitor? {
        //在整个访问流程中，编写的代码是在init函数或者 clinit 函数进行，clinit后面再谈，所以我们需要在MethodVistor去寻找对应的字符串
        if (name == "<clinit>") {
            hasClinit = true
        }
        return super.visitMethod(access, name, desc, signature, exceptions)
    }

    override fun visitEnd() {
        if (!hasClinit) {
            val mv = super.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null)
            mv.visitCode()
            mv.visitInsn(Opcodes.RETURN)
            mv.visitMaxs(0, 0)
            mv.visitEnd()
        }
        super.visitEnd()
    }
}