package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Food Item Class
class FoodItem {
    String name;
    int price;
    String emoji;
    String description;

    FoodItem(String name, int price, String emoji, String description) {
        this.name = name;
        this.price = price;
        this.emoji = emoji;
        this.description = description;
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

// Main Application
public class FoodOrderSystem extends JFrame {

    Color background = new Color(248, 247, 244);
    Color red = new Color(190, 65, 48);
    Color dark = new Color(40, 42, 48);
    Color white = Color.WHITE;

    ArrayList<CartItem> cart = new ArrayList<>();

    FoodItem[] menu = {
        new FoodItem("Pizza", 199, "🍕", "Cheesy Italian Pizza"),
        new FoodItem("Burger", 99, "🍔", "Classic Veg Burger"),
        new FoodItem("French Fries", 79, "🍟", "Crispy Golden Fries"),
        new FoodItem("Sandwich", 89, "🥪", "Fresh Grilled Sandwich"),
        new FoodItem("Pasta", 149, "🍝", "Creamy White Sauce Pasta"),
        new FoodItem("Milkshake", 89, "🥤", "Cold Chocolate Shake")
    };

    JTable cartTable;
    DefaultTableModel model;
    JLabel totalLabel;
    JLabel itemCount;
    JSpinner quantitySpinner;

    public FoodOrderSystem() {

        setTitle("Foodie Hub - Food Ordering System");
        setSize(1050, 720);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(background);
        setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(dark);
        header.setBorder(
            BorderFactory.createEmptyBorder(22, 30, 22, 30)
        );

        JPanel brand = new JPanel(new GridLayout(2, 1));
        brand.setOpaque(false);

        JLabel logo = new JLabel("FOODIE HUB");
        logo.setFont(new Font("Arial", Font.BOLD, 27));
        logo.setForeground(Color.WHITE);

        JLabel tagline = new JLabel(
            "Fresh food, happy moments!"
        );
        tagline.setFont(new Font("Arial", Font.PLAIN, 13));
        tagline.setForeground(new Color(210, 210, 210));

        brand.add(logo);
        brand.add(tagline);

        JLabel online = new JLabel("●  OPEN NOW  ");
        online.setFont(new Font("Arial", Font.BOLD, 13));
        online.setForeground(new Color(100, 220, 130));

        header.add(brand, BorderLayout.WEST);
        header.add(online, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // MAIN CONTENT
        JPanel content = new JPanel(new BorderLayout(20, 15));
        content.setBackground(background);
        content.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // LEFT SIDE - MENU
        JPanel menuPanel = new JPanel(new BorderLayout(10, 15));
        menuPanel.setBackground(background);

        JPanel menuHeading = new JPanel(new BorderLayout());
        menuHeading.setOpaque(false);

        JLabel menuTitle = new JLabel("Explore Our Menu");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 23));
        menuTitle.setForeground(dark);

        JLabel menuSubtitle = new JLabel(
            "Choose your favourite dishes"
        );
        menuSubtitle.setForeground(Color.GRAY);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 5));
        titleBox.setOpaque(false);
        titleBox.add(menuTitle);
        titleBox.add(menuSubtitle);

        menuHeading.add(titleBox, BorderLayout.CENTER);

        // Food cards
        JPanel foodGrid = new JPanel(new GridLayout(3, 2, 15, 15));
        foodGrid.setBackground(background);

        for (FoodItem food : menu) {
            foodGrid.add(createFoodCard(food));
        }

        menuPanel.add(menuHeading, BorderLayout.NORTH);
        menuPanel.add(foodGrid, BorderLayout.CENTER);

        // RIGHT SIDE - CART
        JPanel cartPanel = new JPanel(new BorderLayout(10, 12));
        cartPanel.setBackground(white);
        cartPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 225, 225)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            )
        );

        JPanel cartHeading = new JPanel(new BorderLayout());
        cartHeading.setOpaque(false);

        JLabel cartTitle = new JLabel("Your Cart");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 21));

        itemCount = new JLabel("0 items");
        itemCount.setForeground(red);

        cartHeading.add(cartTitle, BorderLayout.WEST);
        cartHeading.add(itemCount, BorderLayout.EAST);

        String[] columns = {
            "Food", "Price", "Qty", "Total"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        cartTable = new JTable(model);
        cartTable.setRowHeight(32);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 12));
        cartTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12)
        );

        JScrollPane scroll = new JScrollPane(cartTable);
        scroll.setBorder(
            BorderFactory.createLineBorder(
                new Color(230, 230, 230)
            )
        );

        // Quantity selection
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.setOpaque(false);

        quantityPanel.add(new JLabel("Quantity:"));

        quantitySpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 50, 1)
        );

        quantityPanel.add(quantitySpinner);

        // Bottom section
        JPanel bottom = new JPanel(new GridLayout(0, 1, 8, 8));
        bottom.setOpaque(false);

        JButton removeButton = createButton(
            "Remove Selected Item", dark
        );

        JButton clearButton = createButton(
            "Clear Cart", new Color(110, 110, 110)
        );

        totalLabel = new JLabel("Total: Rs. 0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalLabel.setForeground(red);

        JButton orderButton = createButton(
            "PLACE ORDER", red
        );

        bottom.add(quantityPanel);
        bottom.add(removeButton);
        bottom.add(clearButton);
        bottom.add(new JSeparator());
        bottom.add(totalLabel);
        bottom.add(orderButton);

        cartPanel.add(cartHeading, BorderLayout.NORTH);
        cartPanel.add(scroll, BorderLayout.CENTER);
        cartPanel.add(bottom, BorderLayout.SOUTH);

        // Main layout
        content.add(menuPanel, BorderLayout.CENTER);
        content.add(cartPanel, BorderLayout.EAST);

        // Fixed cart width
        cartPanel.setPreferredSize(new Dimension(365, 500));

        add(content, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
            "  FOODIE HUB  |  Thank you for visiting!"
        );

        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.GRAY);
        footer.setBorder(
            BorderFactory.createEmptyBorder(8, 20, 12, 10)
        );

        add(footer, BorderLayout.SOUTH);

        // BUTTON ACTIONS
        removeButton.addActionListener(e -> removeItem());

        clearButton.addActionListener(e -> clearCart());

        orderButton.addActionListener(e -> placeOrder());

        setVisible(true);
    }

    // Create Food Card
    JPanel createFoodCard(FoodItem food) {

        JPanel card = new JPanel(new BorderLayout(5, 8));

        card.setBackground(white);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 225, 225)
                ),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
            )
        );

        JLabel emoji = new JLabel(
            food.emoji,
            SwingConstants.CENTER
        );

        emoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel name = new JLabel(food.name);
        name.setFont(new Font("Arial", Font.BOLD, 17));

        JLabel description = new JLabel(food.description);
        description.setFont(new Font("Arial", Font.PLAIN, 11));
        description.setForeground(Color.GRAY);

        JLabel price = new JLabel("Rs. " + food.price);
        price.setFont(new Font("Arial", Font.BOLD, 16));
        price.setForeground(red);

        JButton addButton = createButton("+ Add To Cart", red);

        addButton.addActionListener(e -> {
            int quantity = (Integer) quantitySpinner.getValue();
            addToCart(food, quantity);
        });

        JPanel details = new JPanel(new GridLayout(3, 1, 2, 2));
        details.setOpaque(false);

        details.add(name);
        details.add(description);
        details.add(price);

        card.add(emoji, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }

    // Create Styled Button
    JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        button.setPreferredSize(new Dimension(150, 38));

        return button;
    }

    // Add Food To Cart
    void addToCart(FoodItem food, int quantity) {

        boolean found = false;

        for (CartItem item : cart) {

            if (item.food.name.equals(food.name)) {
                item.quantity += quantity;
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(food, quantity));
        }

        updateCart();
    }

    // Update Cart
    void updateCart() {

        model.setRowCount(0);

        int total = 0;
        int count = 0;

        for (CartItem item : cart) {

            int itemTotal = item.getTotal();

            total += itemTotal;
            count += item.quantity;

            model.addRow(new Object[] {
                item.food.name,
                "Rs. " + item.food.price,
                item.quantity,
                "Rs. " + itemTotal
            });
        }

        totalLabel.setText("Total: Rs. " + total);
        itemCount.setText(count + " items");
    }

    // Remove Item
    void removeItem() {

        int row = cartTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an item first!"
            );
            return;
        }

        cart.remove(row);
        updateCart();
    }

    // Clear Cart
    void clearCart() {

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Your cart is already empty!"
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to clear the cart?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            cart.clear();
            updateCart();
        }
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

        bill.append("       FOODIE HUB RECEIPT\n");
        bill.append("==============================\n");

        for (CartItem item : cart) {

            bill.append(item.food.name)
                .append(" x ")
                .append(item.quantity)
                .append(" = Rs. ")
                .append(item.getTotal())
                .append("\n");

            total += item.getTotal();
        }

        bill.append("==============================\n");
        bill.append("TOTAL AMOUNT: Rs. ").append(total);
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