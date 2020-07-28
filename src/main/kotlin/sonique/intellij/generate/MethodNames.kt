package sonique.intellij.generate

import com.intellij.openapi.util.text.StringUtil.capitalizeWithJavaBeanConvention

internal object MethodNames {
    val withMethod: (String) -> String = { fieldName -> java.lang.String.format("with%s", capitalizeWithJavaBeanConvention(fieldName)) }
    val fieldName: (String) -> String = { fieldName -> fieldName }
    val with: (String) -> String = { "with" }
}