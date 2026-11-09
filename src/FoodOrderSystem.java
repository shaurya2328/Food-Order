import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Food Item Class
class FoodItem {
    String name;
    int price;

    FoodItem(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

// Cart Item Class
class CartItem {
    FoodItem food;
    int quantity;

    CartItem(FoodItem food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    int getTotal() {
        return food.price * quantity;
    }
}

// Main Application Class
public class FoodOrderSystem extends JFrame {

    JComboBox<String> foodBox;
    JSpinner quantitySpinner;
    JTable cartTable;
    DefaultTableModel model;
    JLabel totalLabel;

    ArrayList<CartItem> cart = new ArrayList<>();

    FoodItem[] menu = {
        new FoodItem("Pizza", 199),
        new FoodItem("Burger", 99),
        new FoodItem("French Fries", 79),
        new FoodItem("Sandwich", 89),
        new FoodItem("Pasta", 149),
        new FoodItem("Milkshake", 89)
    };

    FoodOrderSystem() {

        setTitle("Food Ordering System");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        mainPanel.setBackground(new Color(245, 245, 245));

        // Heading
        JLabel heading = new JLabel(
            "FOOD ORDERING SYSTEM",
            SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(180, 60, 40));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Left Panel - Food Selection
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1, 10, 10));
        leftPanel.setBorder(
            BorderFactory.createTitledBorder("Food Menu")
        );

        leftPanel.add(new JLabel("Select Food:"));

        String[] foodNames = {
            "Pizza - Rs.199",
            "Burger - Rs.99",
            "French Fries - Rs.79",
            "Sandwich - Rs.89",
            "Pasta - Rs.149",
            "Milkshake - Rs.89"
        };

        foodBox = new JComboBox<>(foodNames);
        leftPanel.add(foodBox);

        leftPanel.add(new JLabel("Quantity:"));

        quantitySpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 50, 1)
        );

        leftPanel.add(quantitySpinner);

        JButton addButton = new JButton("Add To Cart");
        JButton removeButton = new JButton("Remove Item");
        JButton clearButton = new JButton("Clear Cart");

        leftPanel.add(addButton);
        leftPanel.add(removeButton);
        leftPanel.add(clearButton);

        // Right Panel - Cart
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        rightPanel.setBorder(
            BorderFactory.createTitledBorder("Shopping Cart")
        );

        String[] columns = {
            "Food Name", "Price", "Quantity", "Total"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        cartTable = new JTable(model);
        cartTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(cartTable);

        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(
            new GridLayout(2, 1, 10, 10)
        );

        totalLabel = new JLabel("Total Amount: Rs. 0");

        totalLabel.setFont(
            new Font("Arial", Font.BOLD, 20)
        );

        JButton orderButton = new JButton("Place Order");

        bottomPanel.add(totalLabel);
        bottomPanel.add(orderButton);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Add To Cart Action
        addButton.addActionListener(e -> addToCart());

        // Remove Item Action
        removeButton.addActionListener(e -> removeItem());

        // Clear Cart Action
        clearButton.addActionListener(e -> clearCart());

        // Place Order Action
        orderButton.addActionListener(e -> placeOrder());

        setVisible(true);
    }

    // Add Food To Cart
    void addToCart() {

        int index = foodBox.getSelectedIndex();

        int quantity = (Integer)
            quantitySpinner.getValue();

        FoodItem selectedFood = menu[index];

        // Check if food already exists
        for (CartItem item : cart) {

            if (item.food.name.equals(selectedFood.name)) {
                item.quantity += quantity;
                updateCart();
                return;
            }
        }

        cart.add(new CartItem(selectedFood, quantity));

        updateCart();
    }

    // Update Cart Table and Total
    void updateCart() {

        model.setRowCount(0);

        int total = 0;

        for (CartItem item : cart) {

            int itemTotal = item.getTotal();

            total += itemTotal;

            model.addRow(new Object[] {
                item.food.name,
                "Rs. " + item.food.price,
                item.quantity,
                "Rs. " + itemTotal
            });
        }

        totalLabel.setText(
            "Total Amount: Rs. " + total
        );
    }

    // Remove Selected Item
    void removeItem() {

        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an item to remove!"
            );
            return;
        }

        cart.remove(selectedRow);

        updateCart();
    }

    // Clear Entire Cart
    void clearCart() {

        cart.clear();

        updateCart();
    }

    // Place Order
    void placeOrder() {

        if (cart.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Your cart is empty!"
            );

            return;
        }

        int total = 0;

        StringBuilder bill = new StringBuilder();

        bill.append("       FOOD ORDER RECEIPT\n");
        bill.append("------------------------------\n");

        for (CartItem item : cart) {

            bill.append(item.food.name)
                .append(" x ")
                .append(item.quantity)
                .append(" = Rs. ")
                .append(item.getTotal())
                .append("\n");

            total += item.getTotal();
        }

        bill.append("------------------------------\n");
        bill.append("Total Amount: Rs. ").append(total);
        bill.append("\n\nThank you for ordering!");

        JOptionPane.showMessageDialog(
            this,
            bill.toString(),
            "Order Confirmed",
            JOptionPane.INFORMATION_MESSAGE
        );

        cart.clear();
        updateCart();
    }

    // Main Method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FoodOrderSystem();
        });
    }
}