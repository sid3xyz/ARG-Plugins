package biz.argirc.Minecraft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import biz.argirc.Minecraft.MagickMod;

public class MinecartListener extends VehicleListener {

	public static final double	MAXIMUM_MOMENTUM	= 1E150D;

	public MinecartListener() {

	}

	private void setMotion(double motionX, double motionY, double motionZ, Minecart minecart) {
		Vector newVelocity = new Vector();
		newVelocity.setX(motionX);
		newVelocity.setY(motionY);
		newVelocity.setZ(motionZ);
		minecart.setVelocity(newVelocity);
	}

	public void setMotionX(double motionX, Minecart minecart) {
		setMotion(motionX, getMotionY(minecart), getMotionZ(minecart), minecart);
	}

	public void setMotionY(double motionY, Minecart minecart) {
		setMotion(getMotionX(minecart), motionY, getMotionZ(minecart), minecart);
	}

	public void setMotionZ(double motionZ, Minecart minecart) {
		setMotion(getMotionX(minecart), getMotionY(minecart), motionZ, minecart);
	}

	public double getMotionX(Minecart minecart) {
		return minecart.getVelocity().getX();
	}

	public double getMotionY(Minecart minecart) {
		return minecart.getVelocity().getY();
	}

	public double getMotionZ(Minecart minecart) {
		return minecart.getVelocity().getZ();
	}

	@Override
	public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {

	}

	@Override
	public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {

	}

	@Override
	public void onVehicleExit(VehicleExitEvent event) {
		Vehicle myV = event.getVehicle();
		if (myV instanceof Minecart) {
			Minecart minecart = (Minecart) myV;
			setMotion(0D, 0D, 0D, minecart);
			minecart.remove();
			ItemStack cart = new ItemStack(Material.MINECART, 1);

			minecart.getWorld().dropItemNaturally(minecart.getLocation(), cart);

		}
	}

	@Override
	public void onVehicleMove(VehicleMoveEvent event) {
		if (MagickMod.autocartStatus) {

			Vehicle myV = event.getVehicle();
			if (myV instanceof Minecart) {
				Minecart minecart = (Minecart) event.getVehicle();
				if (!minecart.isEmpty()) {

					Entity myPassenger = minecart.getPassenger();
					if (myPassenger instanceof Player) {
						Location myLoc = minecart.getLocation();
						myLoc.setY(myLoc.getY() - 1);
						int blockID = myLoc.getBlock().getTypeId();

						switch (blockID) {

							case 45:
								setMotion(0D, 0D, 0D, minecart);
								return;

							default:

								double xvol = minecart.getVelocity().getX();
								double yvol = minecart.getVelocity().getY();
								double zvol = minecart.getVelocity().getZ();
								int multiplier = 2;
								if (MAXIMUM_MOMENTUM / multiplier > Math.abs(xvol)) {
									setMotionX(xvol * multiplier, minecart);
								}
								if (MAXIMUM_MOMENTUM / multiplier > Math.abs(yvol)) {
									setMotionY(yvol * multiplier, minecart);
								}
								if (MAXIMUM_MOMENTUM / multiplier > Math.abs(zvol)) {
									setMotionZ(zvol * multiplier, minecart);
								}
						}
					}
				}
			}
		}
	}
}