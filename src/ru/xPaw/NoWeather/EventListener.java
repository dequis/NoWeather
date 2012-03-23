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
			worldLoaded( world );
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
		if( !event.isCancelled( ) )
		{
			Material mat = event.getNewState( ).getType( );
			
			if( ( mat == Material.ICE  && plugin.isNodeDisabled( "disable-ice-accumulation", event.getBlock( ).getWorld( ).getName( ) ) )
			||  ( mat == Material.SNOW && plugin.isNodeDisabled( "disable-snow-accumulation", event.getBlock( ).getWorld( ).getName( ) ) ) )
			{
				event.setCancelled( true );
			}
		}
	}
	
	@EventHandler( priority = EventPriority.MONITOR )
	public void onWorldLoad( WorldLoadEvent event )
	{
		worldLoaded( event.getWorld( ) );
	}
	
	public void worldLoaded( World world )
	{
		String worldName = world.getName( );
		
		if( !plugin.config.contains( worldName ) )
		{
			plugin.getLogger( ).info( worldName + " - no configuration, generating defaults" );
		}
		
		Boolean disWeather   = plugin.isNodeDisabled( "disable-weather", worldName );
		Boolean disThunder   = plugin.isNodeDisabled( "disable-thunder", worldName );
		Boolean disLightning = plugin.isNodeDisabled( "disable-lightning", worldName );
		Boolean disIce       = plugin.isNodeDisabled( "disable-ice-accumulation", worldName );
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
		
		plugin.setConfigNode( "disable-weather", worldName, disWeather );
		plugin.setConfigNode( "disable-thunder", worldName, disThunder );
		plugin.setConfigNode( "disable-lightning", worldName, disLightning );
		plugin.setConfigNode( "disable-ice-accumulation", worldName, disIce );
		plugin.setConfigNode( "disable-snow-accumulation", worldName, disSnow );
		plugin.saveConfig( );
	}
}
