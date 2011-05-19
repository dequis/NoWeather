package de.crannk.xPaw;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SnowFormEvent;

public class NoWeatherBlockListener extends BlockListener
{
	private NoWeather plugin;

	public NoWeatherBlockListener( NoWeather plugin )
	{
		this.plugin = plugin;
	}
	
	@Override
	public void onSnowForm( SnowFormEvent event )
	{
		if( !event.isCancelled() && plugin.isNodeDisabled( "disable-snow-accumulation", event.getBlock().getWorld().getName() ) )
		{
			event.setCancelled( true );
		}
	}
}
