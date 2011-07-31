package de.crannk.xPaw;

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
		&& event.getNewState().getType() == Material.ICE
		&& plugin.isNodeDisabled( "disable-snow-accumulation", event.getBlock().getWorld().getName() ) )
		{
			event.setCancelled( true );
		}
	}
}
