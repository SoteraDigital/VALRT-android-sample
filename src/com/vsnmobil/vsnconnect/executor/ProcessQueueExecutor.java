/**
 * @author    Rajiv Manivannan <rajiv@contus.in>
 * @copyright  Copyright (C) 2014 VSNMobil. All rights reserved.
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 */
package com.vsnmobil.vsnconnect.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
/**
 * ProcessQueueExecutor.java 
 * 
 * This class is used to execute the read,write and write descriptor request one by one
 * in 1.1 seconds delay. 
 * 
 */
public class ProcessQueueExecutor extends Thread {

	public final static int REQUEST_TYPE_READ_CHAR = 1;
	public final static int REQUEST_TYPE_WRITE_CHAR = 2;
	public final static int REQUEST_TYPE_WRITE_DESCRIPTOR = 3;

	public final static long EXECUTE_DELAY = 1100;// delay in execution

	Timer processQueueTimer=new Timer();

	private static List<ReadWriteCharacteristic> processList = new ArrayList<ReadWriteCharacteristic>();

	/**
	 * Adds the request to ProcessQueueExecutor
	 * @param readWriteCharacteristic
	 */
	public static void addProcess(ReadWriteCharacteristic readWriteCharacteristic) {
		processList.add(readWriteCharacteristic);
	}

	/**
	 * Removes the request from ProcessQueueExecutor
	 * @param readWriteCharacteristic
	 */
	public static void removeProcess(ReadWriteCharacteristic readWriteCharacteristic) {
		processList.remove(readWriteCharacteristic);
	}

	public void executeProecess() {

		if (!processList.isEmpty()) {
			ReadWriteCharacteristic readWriteCharacteristic = processList.get(0);
			int type = readWriteCharacteristic.getRequestType();
			BluetoothGatt bluetoothGatt = readWriteCharacteristic.getBluetoothGatt();
			Object parseObject = readWriteCharacteristic.getObject();

			if (type == REQUEST_TYPE_READ_CHAR) {
				BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) parseObject;
				try{
					bluetoothGatt.readCharacteristic(characteristic);
				}catch(Exception e){}
			} else if (type == REQUEST_TYPE_WRITE_CHAR) {
				BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) parseObject;
				try{
					bluetoothGatt.writeCharacteristic(characteristic);
				}catch(Exception e){}
			} else if (type == REQUEST_TYPE_WRITE_DESCRIPTOR) {
				BluetoothGattDescriptor clientConfig = (BluetoothGattDescriptor) parseObject;
				try{
					bluetoothGatt.writeDescriptor(clientConfig);
				}catch(Exception e){}
			}

			removeProcess(readWriteCharacteristic);
		}
	}

	/**
	 * Returns the number of elements in ProcessQueueExecutor
	 * @return the number of elements in ProcessQueueExecutor
	 */
	 public int getSize()
	{
		if (processList != null)
		{
			return processList.size();
		}
		return 0;
	}

	 @Override
	 public void interrupt() {
		 super.interrupt();
		 if(processQueueTimer!=null){
			 processQueueTimer.cancel();
		 }
	 }

	 @Override
	 public void run() {
		 super.run();
		 processQueueTimer.scheduleAtFixedRate(new TimerTask() {
			 @Override
			 public void run() {
				 executeProecess();
			 }
		 }, 0, EXECUTE_DELAY);
	 }
}
