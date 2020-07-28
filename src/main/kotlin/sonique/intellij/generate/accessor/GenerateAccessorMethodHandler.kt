package sonique.intellij.generate.accessor

import com.intellij.codeInsight.generation.*
import com.intellij.ide.util.MemberChooser
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import com.intellij.psi.util.PsiUtil
import sonique.intellij.MyBundle

internal class GenerateAccessorMethodHandler(private val methodNameGenerator: (String) -> String) : GenerateGetterHandler() {

    override fun chooseMembers(classMembers: Array<ClassMember>, allowEmptySelection: Boolean, copyJavadocCheckbox: Boolean, project: Project, editor: Editor?): Array<ClassMember>? {
        val chooser = MemberChooser(classMembers, allowEmptySelection, true, project)
        chooser.title = MyBundle.message("generate.accessor.methods")
        chooser.setCopyJavadocVisible(copyJavadocCheckbox)
        chooser.show()

        myToCopyJavaDoc = chooser.isCopyJavadoc

        return chooser.selectedElements?.toTypedArray()
    }

    override fun generateMemberPrototypes(psiClass: PsiClass?, classMember: ClassMember?): Array<GenerationInfo> {
        if (classMember is PsiFieldMember) {
            val field = classMember.element
            val templateMethod = generateMethodPrototype(field)
            field.containingClass?.findMethodBySignature(templateMethod, false)
                    ?: return arrayOf(PsiGenerationInfo(templateMethod))
        }
        return GenerationInfo.EMPTY_ARRAY
    }

    private fun generateMethodPrototype(field: PsiField): PsiMethod {
        val codeStyleManager = JavaCodeStyleManager.getInstance(field.project)
        val elementFactory = JavaPsiFacade.getInstance(field.project).elementFactory

        val method = createMethod(codeStyleManager, field, elementFactory)

        CodeStyleManager.getInstance(field.project).reformat(method)

        return method
    }

    private fun createMethod(codeStyleManager: JavaCodeStyleManager, field: PsiField, elementFactory: PsiElementFactory): PsiMethod {
        val propertyName = codeStyleManager.variableNameToPropertyName(field.name, codeStyleManager.getVariableKind(field))

        val accessorMethod = elementFactory.createMethod(methodNameGenerator.invoke(propertyName), field.type)
        accessorMethod.body!!.replace(methodBody(field, elementFactory))
        PsiUtil.setModifierProperty(accessorMethod, PsiModifier.PUBLIC, true)
        if (field.hasModifierProperty(PsiModifier.STATIC)) {
            PsiUtil.setModifierProperty(accessorMethod, PsiModifier.STATIC, true)
        }
        return accessorMethod
    }

    private fun methodBody(field: PsiField, elementFactory: PsiElementFactory): PsiCodeBlock {
        val methodBodyBuilder = StringBuilder()
                .append("{\n")
                .append("return ").append(field.name).append(";\n")
                .append("}\n")

        return elementFactory.createCodeBlockFromText(methodBodyBuilder.toString(), null)
    }
}