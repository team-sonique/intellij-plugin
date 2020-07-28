package sonique.intellij.generate.builder

import com.intellij.codeInsight.generation.*
import com.intellij.ide.util.MemberChooser
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import com.intellij.psi.codeStyle.VariableKind
import com.intellij.psi.util.PsiUtil
import sonique.intellij.MyBundle

internal class GenerateWithMethodHandler(private val methodNameGenerator: (String) -> String) : GenerateSetterHandler() {

    override fun chooseMembers(classMembers: Array<out ClassMember>?, allowEmptySelection: Boolean,
                               copyJavadocCheckbox: Boolean, project: Project, editor: Editor?): Array<ClassMember>? {
        val chooser = MemberChooser<ClassMember>(classMembers, allowEmptySelection, true, project)
        chooser.title = MyBundle.message("generate.with.methods")
        chooser.setCopyJavadocVisible(copyJavadocCheckbox)
        chooser.show()

        myToCopyJavaDoc = chooser.isCopyJavadoc

        return chooser.selectedElements?.toTypedArray()
    }

    override fun generateMemberPrototypes(psiClass: PsiClass, classMember: ClassMember): Array<GenerationInfo> {
        if (classMember is PsiFieldMember) {
            val field = classMember.element

            if (!GetterSetterPrototypeProvider.isReadOnlyProperty(field)) {
                val templateMethod = generateMethodPrototype(field)
                field.containingClass?.findMethodBySignature(templateMethod, false)
                        ?: return arrayOf(PsiGenerationInfo(templateMethod))
            }
        }
        return GenerationInfo.EMPTY_ARRAY
    }

    private fun generateMethodPrototype(field: PsiField): PsiMethod {
        val method = createMethod(field)

        CodeStyleManager.getInstance(field.project).reformat(method)

        return method
    }

    private fun createMethod(field: PsiField): PsiMethod {
        val codeStyleManager = JavaCodeStyleManager.getInstance(field.project)

        val propertyName = codeStyleManager.variableNameToPropertyName(field.name, codeStyleManager.getVariableKind(field))
        val parameterName = codeStyleManager.propertyNameToVariableName(propertyName, VariableKind.PARAMETER)

        val elementFactory = JavaPsiFacade.getInstance(field.project).elementFactory
        val withMethod = elementFactory.createMethod(methodNameGenerator.invoke(propertyName), elementFactory.createType(field.containingClass!!))
        withMethod.parameterList.add(elementFactory.createParameter(parameterName, field.type))
        withMethod.body?.replace(methodBody(propertyName, parameterName, elementFactory))
        PsiUtil.setModifierProperty(withMethod, PsiModifier.PUBLIC, true)
        return withMethod
    }

    private fun methodBody(propertyName: String, parameterName: String, elementFactory: PsiElementFactory): PsiCodeBlock {
        val methodBodyBuilder: StringBuilder = StringBuilder()
                .append("{\n")
                .append("this.").append(propertyName).append("=").append(parameterName).append(";\n")
                .append("return this;\n")
                .append("}\n")

        return elementFactory.createCodeBlockFromText(methodBodyBuilder.toString(), null)
    }
}
