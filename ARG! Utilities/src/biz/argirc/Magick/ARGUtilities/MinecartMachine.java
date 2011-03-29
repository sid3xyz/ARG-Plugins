package biz.argirc.Magick.ARGUtilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class MinecartMachine extends VehicleListener {

	private final ARGUtilities	plugin;

	public MinecartMachine(final ARGUtilities plugin) {
		this.plugin = plugin;
	}

	public void onVehicleMove(VehicleMoveEvent event) {

		if (!event.getVehicle().isEmpty()) {
			Vehicle myCart = event.getVehicle();
			Entity myPassenger = myCart.getPassenger();
			if (myPassenger instanceof Player) {

				double speed = 0.1; // whatever
				Location loc = myCart.getPassenger().getLocation();
				Vector velocity = new Vector(loc.getX(), loc.getY(), loc.getZ());
				velocity.multiply(0.1 / 100);
				myCart.setVelocity(velocity);
			}

		}

	}
}