package slaughter.ware.client.utils.render.font;

import lombok.experimental.UtilityClass;
import net.minecraft.util.Identifier;

@UtilityClass
public class Fonts {
    public static final String SF_PRO = "sf_pro";
    public static final String PRODUCT_SANS = "product_sans";
    public static final String OTHER = "other";

    public static final AtlasFont SF_BOLD = atlas(SF_PRO + "/sf_bold");
    public static final AtlasFont SF_SEMIBOLD = atlas(SF_PRO + "/sf_semibold");
    public static final AtlasFont SF_MEDIUM = atlas(SF_PRO + "/sf_medium");
    public static final AtlasFont SF_REGULAR = atlas(SF_PRO + "/sf_regular");
    public static final AtlasFont SF_LIGHT = atlas(SF_PRO + "/sf_light");

    public static final AtlasFont PS_BLACK = atlas(PRODUCT_SANS + "/productsans_black");
    public static final AtlasFont PS_BOLD = atlas(PRODUCT_SANS + "/productsans_bold");
    public static final AtlasFont PS_MEDIUM = atlas(PRODUCT_SANS + "/productsans_medium");
    public static final AtlasFont PS_REGULAR = atlas(PRODUCT_SANS + "/productsans_regular");
    public static final AtlasFont PS_LIGHT = atlas(PRODUCT_SANS + "/productsans_light");
    public static final AtlasFont PS_THIN = atlas(PRODUCT_SANS + "/productsans_thin");

    public static final AtlasFont ICON_V1 = atlas(OTHER + "/icon");
    public static final AtlasFont ICON_DESHUX = atlas(OTHER + "/icon_deshux");
    public static final AtlasFont ICON_ESSENS = atlas(OTHER + "/icoes");
    public static final AtlasFont ICONS = atlas(OTHER + "/icons");

    public static float getMediumThickness() {
        return 0.07f;
    }

    public static float getBoldThickness() {
        return 0.10f;
    }

    public static AtlasFont atlas(String path) {
        return new AtlasFont(
                path,
                Identifier.of("slaughterware", "fonts/" + path + ".json"),
                Identifier.of("slaughterware", "fonts/" + path + ".png")
        );
    }

    public record AtlasFont(String path, Identifier metrics, Identifier texture) {
    }
}
