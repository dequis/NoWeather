package ru.xPaw.NoWeather;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public FileConfiguration config;
	
	@Override
	public void onEnable( )
	{
		config = getConfig( );
		
		new EventListener( this );
		
		// Metrics
		try
		{
			new MetricsLite( this ).start( );
		}
		catch( IOException e )
		{
			getLogger( ).warning( "[Metrics] " + e.getMessage( ) );
		}
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
