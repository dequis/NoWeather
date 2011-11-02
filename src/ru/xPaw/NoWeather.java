package ru.xPaw;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NoWeather extends JavaPlugin
{
	public final Logger log = Logger.getLogger( "Minecraft" );
	public FileConfiguration config;
	
	public void onEnable( )
	{
		config = getConfig( );
		
		final NoWeatherWeatherListener wL = new NoWeatherWeatherListener( this );
		final NoWeatherWorldListener worldL = new NoWeatherWorldListener( this );
		final NoWeatherBlockListener blockL = new NoWeatherBlockListener( this );
		
		final PluginManager pm = getServer( ).getPluginManager( );
		final PluginDescriptionFile pdfFile = this.getDescription( );
		
		List<World> worlds = getServer( ).getWorlds( );
		
		for( World world : worlds )
		{
			worldL.WorldLoaded( world );
		}
		
		pm.registerEvent( Event.Type.WORLD_LOAD, worldL, Event.Priority.Monitor, this );
		pm.registerEvent( Event.Type.WEATHER_CHANGE, wL, Event.Priority.High, this );
		pm.registerEvent( Event.Type.THUNDER_CHANGE, wL, Event.Priority.High, this );
		pm.registerEvent( Event.Type.LIGHTNING_STRIKE, wL, Event.Priority.High, this );
		pm.registerEvent( Event.Type.BLOCK_FORM, blockL, Event.Priority.High, this );
		
		log.info( pdfFile.getName( ) + " version " + pdfFile.getVersion( ) + " is enabled!" );
	}
	
	public void onDisable( )
	{
		//
	}
	
	public boolean isNodeDisabled( String name, String worldName )
	{
		return config.getBoolean( worldName + "." + name, true );
	}
	
	public void setConfigNode( String name, String worldName, Boolean value )
	{
		config.set( worldName + "." + name, value );
	}
}
