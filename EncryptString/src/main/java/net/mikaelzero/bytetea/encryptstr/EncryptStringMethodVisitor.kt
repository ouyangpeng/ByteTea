package net.mikaelzero.bytetea.encryptstr

import org.apache.commons.codec.binary.Base64
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode

/**
 * @Author:         MikaelZero
 * @CreateDate:     2020/9/9 1:32 PM
 * @Description:
 */
class EncryptStringMethodVisitor(
    mv: MethodVisitor?, val methodName: String?,
    val owner: String?,
    val staticFinalStringFieldNodeList: List<FieldNode>
) : MethodVisitor(Opcodes.ASM5, mv) {

    private val className = "net/mikaelzero/bytetea/lib/encryptstr/Base64Util"
    private val encryptMethodName = "decode"


    //所有的字符串在字节码中的指令都是LDC指令，因此只需要在访问LDC指令的时候进行修改即可
    override fun visitLdcInsn(value: Any?) {
        //我们判断下是否为String类型，然后进行代码修改
        if (value is String) {
            val str = Base64.encodeBase64String(value.toByteArray())
            //这步是直接修改LDC指令下对应的value，而我们的value就是上面的字符串
            mv.visitLdcInsn(str)
            //调用编写好的Lib里的代码，也就是 Base64Util.decode
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, encryptMethodName, "(Ljava/lang/String;)Ljava/lang/String;", false)
            return
        }
        super.visitLdcInsn(value)
    }

    //上面的visitLdcInsn方法编写完成后，你会发现一个问题，就是 visitLdcInsn 访问不到 static final 的字符串，
    // 而且static final 的字符串必须设置值或者声明后在 static 块里进行初始化，
    // 所以我们需要找一个地方能够获取到 static final 的指令并且统一处理，也就是visitInsn，并且他的code为 RETURN，代码如下
    override fun visitInsn(opcode: Int) {
        //static 的函数名为 <clinit>
        if (opcode == Opcodes.RETURN && methodName == "<clinit>") {
            staticFinalStringFieldNodeList.forEach { fieldNode ->
                val str = Base64.encodeBase64String((fieldNode.value as String).toByteArray())
                mv?.visitLdcInsn(str)
                mv?.visitMethodInsn(Opcodes.INVOKESTATIC, className, encryptMethodName, "(Ljava/lang/String;)Ljava/lang/String;", false)
                mv?.visitFieldInsn(Opcodes.PUTSTATIC, owner, fieldNode.name, "Ljava/lang/String;")
            }
        }
        super.visitInsn(opcode)
    }


}