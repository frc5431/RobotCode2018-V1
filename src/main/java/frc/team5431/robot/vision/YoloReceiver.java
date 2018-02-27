package frc.team5431.robot.vision;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frc.team5431.robot.Titan;

public class YoloReceiver extends Thread {	
	private volatile DatagramSocket server;
	private volatile byte[] buff = new byte[10000];
	private volatile List<Cube> cubes = Collections.synchronizedList(new ArrayList<Cube>());
	
	public YoloReceiver() {
		try {
			server = new DatagramSocket(5431);
		} catch (SocketException e) {
			Titan.ee("YoloError", e);
		}
	}
	
	public void run() {
		while(true) {
			//Titan.l("LOOOP");
			final DatagramPacket packet = new DatagramPacket(buff, buff.length);
			try {
				server.receive(packet);
			} catch (IOException e) {
				Titan.ee("YoloError", e);
			}
			
			synchronized(cubes) {
				cubes.clear();
				final String cubeData = new String(packet.getData(), 0, packet.getLength());
				if(cubeData.startsWith("N")) continue;
				final String subCubes[] = cubeData.split("%");
				for(final String subCube : subCubes) {
					if(subCube.length() > 0) {
						final String parts[] = subCube.split(",");
						if(parts.length == 4) {
							final int 
								x = Integer.parseInt(parts[0]),
								y = Integer.parseInt(parts[1]),
								w = Integer.parseInt(parts[2]),
								h = Integer.parseInt(parts[3]);
							cubes.add(new Cube(x, y, w, h));
						
						} else {
							Titan.e("Malformed cube data!");
						}
					}
				}
				//SmartDashboard.putString("TOOL", cubeData);
				Titan.l("Cube data: %s", cubeData);
			}
			
		}
	}
	
	public List<Cube> getCubes() {
		synchronized(cubes) {
			return cubes;
		}
	}
	
	public boolean hasCubes() {
		synchronized(cubes) {
			return cubes.size() > 0;
		}
	}

}
