// 折扣策略接口
interface DiscountStrategy {
    double calculatePrice(double originalPrice, int quantity);
}

// 不打折
class NoDiscountStrategy implements DiscountStrategy {
    public double calculatePrice(double originalPrice, int quantity) {
        return originalPrice * quantity;
    }
}

// 每台减扣固定金额
class FixedAmountDiscountStrategy implements DiscountStrategy {
    private double discountPerUnit;

    public FixedAmountDiscountStrategy(double discountPerUnit) {
        this.discountPerUnit = discountPerUnit;
    }

    public double calculatePrice(double originalPrice, int quantity) {
        double discountedPrice = originalPrice - discountPerUnit;
        if (discountedPrice < 0) discountedPrice = 0;
        return discountedPrice * quantity;
    }
}

// 按百分比打折
class PercentageDiscountStrategy implements DiscountStrategy {
    private double discountRate; // 比如 0.05 表示打 95 折

    public PercentageDiscountStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    public double calculatePrice(double originalPrice, int quantity) {
        return originalPrice * (1 - discountRate) * quantity;
    }
}

// 上下文：用于封装使用哪种折扣策略
class PrinterSaleContext {
    private DiscountStrategy discountStrategy;

    public PrinterSaleContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double getFinalPrice(double unitPrice, int quantity) {
        return discountStrategy.calculatePrice(unitPrice, quantity);
    }
}

// 打印机类（可选）
class Printer {
    private String model;
    private double price;

    public Printer(String model, double price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }
}

// 测试程序
public class PrinterSalesSystem {
    public static void main(String[] args) {
        Printer printer = new Printer("HP LaserJet 1020", 1500);

        int quantity = 3;

        // 不打折
        PrinterSaleContext context = new PrinterSaleContext(new NoDiscountStrategy());
        System.out.println("原价总价：" + context.getFinalPrice(printer.getPrice(), quantity));

        // 每台减 100 元
        context.setDiscountStrategy(new FixedAmountDiscountStrategy(100));
        System.out.println("每台减100后的总价：" + context.getFinalPrice(printer.getPrice(), quantity));

        // 打95折
        context.setDiscountStrategy(new PercentageDiscountStrategy(0.05));
        System.out.println("95折后的总价：" + context.getFinalPrice(printer.getPrice(), quantity));
    }
}
