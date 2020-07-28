package sonique.intellij.generate.builder

import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import sonique.intellij.generate.MethodNames

class GenerateWithMethodAction : BaseGenerateAction(GenerateWithMethodHandler(MethodNames.with))
