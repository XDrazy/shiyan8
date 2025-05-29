import java.util.ArrayList;
import java.util.List;

// 观察者接口
interface DeviceObserver {
    void onTemperatureChanged(double temperature);
}

// 被观察者
class TemperatureSensor {
    private List<DeviceObserver> observers = new ArrayList<>();
    private double threshold;

    public TemperatureSensor(double threshold) {
        this.threshold = threshold;
    }

    public void addObserver(DeviceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DeviceObserver observer) {
        observers.remove(observer);
    }

    public void detectTemperature(double currentTemp) {
        System.out.println("当前温度：" + currentTemp + "°C");
        if (currentTemp >= threshold) {
            System.out.println("温度超过阈值，触发响应设备！");
            notifyObservers(currentTemp);
        } else {
            System.out.println("温度正常。");
        }
    }

    private void notifyObservers(double temperature) {
        for (DeviceObserver observer : observers) {
            observer.onTemperatureChanged(temperature);
        }
    }
}

// 各种响应设备
class Alarm implements DeviceObserver {
    public void onTemperatureChanged(double temperature) {
        System.out.println("【报警器】温度过高！发出报警声！");
    }
}

class WarningLight implements DeviceObserver {
    public void onTemperatureChanged(double temperature) {
        System.out.println("【警示灯】开始闪烁！");
    }
}

class EscapeDoor implements DeviceObserver {
    public void onTemperatureChanged(double temperature) {
        System.out.println("【安全逃生门】自动开启！");
    }
}

class IsolationDoor implements DeviceObserver {
    public void onTemperatureChanged(double temperature) {
        System.out.println("【隔热门】自动关闭！");
    }
}

// 主程序测试
public class ServerRoomMonitor {
    public static void main(String[] args) {
        TemperatureSensor sensor = new TemperatureSensor(40.0); // 设置阈值为40°C

        // 注册观察者
        sensor.addObserver(new Alarm());
        sensor.addObserver(new WarningLight());
        sensor.addObserver(new EscapeDoor());
        sensor.addObserver(new IsolationDoor());

        // 模拟温度检测
        sensor.detectTemperature(36.5); // 正常
        System.out.println("----");
        sensor.detectTemperature(42.3); // 触发所有响应
    }
}
