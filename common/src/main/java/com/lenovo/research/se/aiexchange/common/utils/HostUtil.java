package com.lenovo.research.se.aiexchange.common.utils;

import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class HostUtil {

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (!needLoop(netInterface)) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }

        return "";
    }

    private static boolean needLoop(NetworkInterface netInterface) throws SocketException {
        return netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp();
    }

    public static String getLocalName() {
        String ip = getIpAddress();
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostName = inetAddress.getHostName();
            String mac = getLocalMac(inetAddress);
            return hostName + "@" + mac;
        } catch (UnknownHostException | SocketException e) {
            // IGNORE
        }
        return "";
    }

    public static String getLocalMac(InetAddress ia) throws SocketException {
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        return IntStream.range(0, mac.length).boxed()
                .map(index -> {
                    int temp = mac[index] & 0xff;
                    String str = Integer.toHexString(temp);
                    return StringUtil.leftPad(str, 2, '0');
                }).collect(Collectors.joining("-"));

    }

    public static String getLocalMac() throws SocketException {
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while(en.hasMoreElements()){
            NetworkInterface network = en.nextElement();
            List<InterfaceAddress> addresses = network.getInterfaceAddresses();

            for(InterfaceAddress address : addresses) {
                InetAddress  ip = address.getAddress();
                NetworkInterface networkByIp = NetworkInterface.getByInetAddress(ip);
                byte[] mac = networkByIp.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                return sb.toString().toUpperCase();
            }
        }
        throw new RuntimeException("Get local mac error");
    }
}
