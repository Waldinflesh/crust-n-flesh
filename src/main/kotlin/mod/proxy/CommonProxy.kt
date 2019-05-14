package mod.proxy

import net.minecraft.item.Item
import net.minecraft.util.text.translation.I18n

open class CommonProxy {

    open fun registerItemRenderer(item: Item, meta: Int) {

    }

    open fun localize(unlocalized: String, vararg args: Any): String {
        return I18n.translateToLocalFormatted(unlocalized, args)
    }

    open fun registerRenderers() {}

}