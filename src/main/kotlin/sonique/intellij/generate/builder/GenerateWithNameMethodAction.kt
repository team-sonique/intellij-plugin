package sonique.intellij.generate.builder

import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import sonique.intellij.generate.MethodNames

class GenerateWithNameMethodAction : BaseGenerateAction(GenerateWithMethodHandler(MethodNames.withMethod))
