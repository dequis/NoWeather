package ru.xPaw;

import org.bukkit.Material;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockListener;

public class NoWeatherBlockListener extends BlockListener
{
	private NoWeather plugin;

	public NoWeatherBlockListener( NoWeather plugin )
	{
		this.plugin = plugin;
	}
	
	@Override
	public void onBlockForm( BlockFormEvent event )
	{
		if( !event.isCancelled()
		&& plugin.isNodeDisabled( "disable-snow-accumulation", event.getBlock().getWorld().getName() ) )
		{
			Material mat = event.getNewState().getType();
			
			// Quite useless check right now, but let's be safe
			if( mat == Material.ICE || mat == Material.SNOW )
			{
				event.setCancelled( true );
			}
		}
	}
}