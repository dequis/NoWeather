package ru.xPaw.NoWeather;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class EventListener implements Listener
{
	private Main plugin;
	
	public EventListener( Main plugin )
	{
		this.plugin = plugin;
		
		plugin.getServer( ).getPluginManager( ).registerEvents( this, plugin );
		
		List<World> worlds = plugin.getServer( ).getWorlds( );
		
		for( World world : worlds )
		{
			WorldLoaded( world );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onWeatherChange( WeatherChangeEvent event )
	{
		if( !event.isCancelled( ) && event.toWeatherState( ) && plugin.isNodeDisabled( "disable-weather", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onThunderChange( ThunderChangeEvent event )
	{
		if( !event.isCancelled( ) && event.toThunderState( ) && plugin.isNodeDisabled( "disable-thunder", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onLightningStrike( LightningStrikeEvent event )
	{
		if( !event.isCancelled( ) && plugin.isNodeDisabled( "disable-lightning", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onBlockForm( BlockFormEvent event )
	{
		if( !event.isCancelled( ) && plugin.isNodeDisabled( "disable-snow-accumulation", event.getBlock( ).getWorld( ).getName( ) ) )
		{
			Material mat = event.getNewState().getType();
			
			// Quite useless check right now, but let's be safe
			if( mat == Material.ICE || mat == Material.SNOW )
			{
				event.setCancelled( true );
			}
		}
	}
	
	@EventHandler( priority = EventPriority.MONITOR )
	public void onWorldLoad( WorldLoadEvent event )
	{
		WorldLoaded( event.getWorld( ) );
	}
	
	public void WorldLoaded( World world )
	{
		String worldName = world.getName();
		
		if( !plugin.config.contains( worldName ) )
		{
			plugin.getLogger( ).info( worldName + " - no configuration, generating defaults." );
		}
		
		Boolean disWeather   = plugin.isNodeDisabled( "disable-weather", worldName );
		Boolean disThunder   = plugin.isNodeDisabled( "disable-thunder", worldName );
		Boolean disLightning = plugin.isNodeDisabled( "disable-lightning", worldName );
		Boolean disSnow      = plugin.isNodeDisabled( "disable-snow-accumulation", worldName );
		
		if( disWeather && world.hasStorm( ) )
		{
			world.setStorm( false );
			plugin.getLogger( ).info( "Stopped storm in " + worldName );
		}
		
		if( disThunder && world.isThundering( ) )
		{
			world.setThundering( false );
			plugin.getLogger( ).info( "Stopped thunder in " + worldName );
		}
		
		//plugin.getLogger( ).info( worldName + " - Weather  : " + disWeather.toString() );
		//plugin.getLogger( ).info( worldName + " - Thunder  : " + disThunder.toString() );
		//plugin.getLogger( ).info( worldName + " - Lightning: " + disLightning.toString() );
		
		plugin.setConfigNode( "disable-weather", worldName, disWeather );
		plugin.setConfigNode( "disable-thunder", worldName, disThunder );
		plugin.setConfigNode( "disable-lightning", worldName, disLightning );
		plugin.setConfigNode( "disable-snow-accumulation", worldName, disSnow );
		plugin.saveConfig( );
	}
}
