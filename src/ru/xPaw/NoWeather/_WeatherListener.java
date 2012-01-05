package ru.xPaw.NoWeather;

import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

public class _WeatherListener extends WeatherListener
{
	private Main plugin;
	
	public _WeatherListener( Main plugin )
	{
		this.plugin = plugin;
	}
	
	public void onWeatherChange( WeatherChangeEvent event )
	{
		if( !event.isCancelled( ) && event.toWeatherState( ) && plugin.isNodeDisabled( "disable-weather", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	public void onThunderChange( ThunderChangeEvent event )
	{
		if( !event.isCancelled( ) && event.toThunderState( ) && plugin.isNodeDisabled( "disable-thunder", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
	
	public void onLightningStrike( LightningStrikeEvent event )
	{
		if( !event.isCancelled( ) && plugin.isNodeDisabled( "disable-lightning", event.getWorld( ).getName( ) ) )
		{
			event.setCancelled( true );
		}
	}
}
