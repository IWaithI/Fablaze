package ua.iwaithi.fablaze.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public ModTags() {
    }

    public static class Items {
        public Items() {
        }

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation("fablaze", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> FABLAZE_TOOLS = tag("fablaze_tools");

        public Blocks() {
        }

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation("fablaze", name));
        }
    }
}
