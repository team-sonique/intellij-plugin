package sonique.intellij

import com.intellij.spellchecker.BundledDictionaryProvider

class SoniqueDictionary : BundledDictionaryProvider {
    override fun getBundledDictionaries() = arrayOf("/dictionary/sonique.dic")
}
