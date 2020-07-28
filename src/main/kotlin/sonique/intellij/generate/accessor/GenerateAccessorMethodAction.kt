package sonique.intellij.generate.accessor

import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import sonique.intellij.generate.MethodNames

class GenerateAccessorMethodAction : BaseGenerateAction(GenerateAccessorMethodHandler(MethodNames.fieldName))
