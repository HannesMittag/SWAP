package com.fink_edv.mavdroid;

import android.os.Looper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient extends Thread{

    //IP 127.0.0.1

    private static final String TAG = UdpClient.class.getName();

    private InetAddress address;
    private int port;
    private DatagramSocket datagramSocket;

    public UdpClient(InetAddress address, int port) {
        super();
        this.address = address;
        this.port = port;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setBroadcast(true);
            datagramSocket.setReuseAddress(true);
            //datagramSocket.connect(address, port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }

    public void sendBytes(final byte[] data) {
        Looper.prepare();
        if (datagramSocket.isClosed()) {
            try {
                datagramSocket.connect(address, port);
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);
                datagramSocket.send(datagramPacket);

                datagramSocket.close();
            }
            catch (IOException e)   {
                e.printStackTrace();
            }

        }
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!datagramSocket.isClosed()) {
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);
                        datagramSocket.send(datagramPacket);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();*/
    }
}
