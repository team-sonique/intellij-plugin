package sonique.intellij.generate

import com.intellij.openapi.util.text.StringUtil.capitalizeWithJavaBeanConvention
import java.lang.String.format

internal object MethodNames {
    val withMethod: (String) -> String = { fieldName -> format("with%s", capitalizeWithJavaBeanConvention(fieldName)) }
    val fieldName: (String) -> String = { fieldName -> fieldName }
    val with: (String) -> String = { "with" }
}
