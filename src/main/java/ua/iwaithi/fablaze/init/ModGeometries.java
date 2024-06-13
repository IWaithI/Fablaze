package ua.iwaithi.fablaze.init;

import org.zeith.hammeranims.api.geometry.IGeometryContainer;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;

@SimplyRegister
public interface ModGeometries
{
	@RegistryName("freya")
	IGeometryContainer FREYA = IGeometryContainer.create();
}
