package ua.iwaithi.fablaze.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;
import ua.iwaithi.fablaze.content.entity.EntityFreya;

@SimplyRegister
public interface ModEntities
{
	@RegistryName("freya")
	EntityType<EntityFreya> FREYA = EntityType.Builder.of(EntityFreya::new, MobCategory.MISC).build("freya");
}