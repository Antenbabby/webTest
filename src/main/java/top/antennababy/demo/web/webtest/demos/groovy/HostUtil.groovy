package top.antennababy.demo.web.webtest.demos.groovy

class HostUtil {
    static def getHost() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address?.getHostAddress();
    }

    static void main(String[] args) {
        println getHost();
    }
}
