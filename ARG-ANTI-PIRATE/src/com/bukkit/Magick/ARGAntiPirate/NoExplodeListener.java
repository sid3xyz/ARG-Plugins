package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimedEvent;
import org.bukkit.event.entity.EntityExplodeEvent;


public class NoExplodeListener extends EntityListener
{
    public static ARGAntiPirate plugin;
    public NoExplodeListener(ARGAntiPirate instance)
    {
        plugin = instance;
    }
    
    public void onExplosionPrimed(ExplosionPrimedEvent event)
    {
    	if (event.getEntity().getClass().getName().contains("CraftTNTPrimed"))
    	{
    		
    		event.setCancelled(true);
    	}/*
    	else if ( event.getEntity().getClass().getName().contains("CraftCreeper"))
    	{
    		event.setCancelled(true);
    	}
    	else if (event.getEntity().getClass().getName().contains("CraftFireball"))
    	{
    		event.setCancelled(true);
    	}*/
    }
    
    public void onEntityExplode(EntityExplodeEvent event)
    {
    	if ( event.getEntity().getClass().getName().contains("CraftTNTPrimed"))
    	{
    		event.setCancelled(true);
    	}/*
    	else if ( event.getEntity().getClass().getName().contains("CraftCreeper"))
    	{
    		event.setCancelled(true);
    	}
    	else if ( event.getEntity().getClass().getName().contains("CraftFireball"))
    	{
    		event.setCancelled(true);
    	}*/
    }
}
