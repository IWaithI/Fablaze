package ua.iwaithi.fablaze.common.client;

import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class GeoItemArmorRenderer extends GeoArmorRenderer<GeoItemArmor> {
    public GeoItemArmorRenderer(String a, String b) {
        super(new GeoItemArmorModel(a, b));
    }
}
