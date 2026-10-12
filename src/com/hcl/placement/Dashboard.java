package com.hcl.placement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Dashboard {

   
    // =========================================================
    // THEME COLOURS - DARK BLACK + ROYAL ORANGE
    // =========================================================

    private static final Color BACKGROUND =
            new Color(17, 17, 17);          // #111111

    private static final Color SIDEBAR =
            new Color(12, 12, 12);          // #0C0C0C

    private static final Color CARD_BACKGROUND =
            new Color(28, 28, 28);          // #1C1C1C

    private static final Color INPUT_BACKGROUND =
            new Color(37, 37, 37);          // #252525

    private static final Color BORDER_COLOR =
            new Color(52, 52, 52);           // #343434

    private static final Color TEXT =
            new Color(249, 250, 251);       // #F9FAFB

    private static final Color SECONDARY_TEXT =
            new Color(156, 163, 175);       // #9CA3AF

    private static final Color ACCENT =
            new Color(230, 106, 0);          // #E66A00

    private static final Color TABLE_HEADER_BACKGROUND =
            new Color(40, 40, 40);           // #282828

    // Placement readiness status colours
    private static final Color STATUS_GREEN =
            new Color(34, 197, 94);          // #22C55E

    private static final Color STATUS_AMBER =
            new Color(245, 158, 11);         // #F59E0B

    private static final Color STATUS_RED =
            new Color(239, 68, 68);          // #EF4444


    private JFrame frame;
    private JPanel contentPanel;
    private JLabel pageTitle;

    private List<Student> students = new ArrayList<>();

    private final PlacementEvaluator evaluator =
            new PlacementEvaluator();
    private final AnalyticsService analyticsService =
            new AnalyticsService();
    private final RankingService rankingService =
            new RankingService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new Dashboard().createDashboard());
    }

    // =========================================================
    // CREATE DASHBOARD
    // =========================================================

    private void createDashboard() {
        loadStudents();

        frame = new JFrame("Placement Intelligence System");
        frame.setSize(1100, 700);
        frame.setMinimumSize(new Dimension(850, 550));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BACKGROUND);
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));
        sidebar.setLayout(
                new BoxLayout(sidebar, BoxLayout.Y_AXIS));

       JLabel logo = new JLabel("PLACEMENT");
logo.setForeground(ACCENT);
logo.setFont(new Font("Arial", Font.BOLD, 21));

JLabel subtitle = new JLabel("INTELLIGENCE SYSTEM");
subtitle.setForeground(SECONDARY_TEXT);
subtitle.setFont(new Font("Arial", Font.PLAIN, 10));

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(40));

        addNavigationButton(sidebar, "Dashboard",
                this::showDashboard);
        addNavigationButton(sidebar, "Students",
                this::showStudents);
        addNavigationButton(sidebar, "Placement Readiness",
                this::showPlacementReadiness);
        addNavigationButton(sidebar, "Rankings",
                this::showRankings);
        addNavigationButton(sidebar, "Analytics",
                this::showAnalytics);

        JPanel mainArea = new JPanel(
                new BorderLayout(0, 20));
        mainArea.setBackground(BACKGROUND);
        mainArea.setBorder(
                new EmptyBorder(25, 30, 25, 25));

       
JPanel header = new JPanel(new BorderLayout());
header.setBackground(BACKGROUND);
header.setOpaque(true);
header.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(
                0, 0, 1, 0, BORDER_COLOR),
        new EmptyBorder(0, 0, 12, 0)
));

        pageTitle = new JLabel("Dashboard Overview");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 26));
pageTitle.setForeground(ACCENT);
        JLabel adminLabel = new JLabel("Placement Admin");
adminLabel.setForeground(SECONDARY_TEXT);
        header.add(pageTitle, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

       contentPanel = new JPanel(new BorderLayout(0, 15));
contentPanel.setBackground(BACKGROUND);
contentPanel.setOpaque(true);

        mainArea.add(header, BorderLayout.NORTH);
        mainArea.add(contentPanel, BorderLayout.CENTER);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(mainArea, BorderLayout.CENTER);

        showDashboard();
        frame.setVisible(true);
    }

    private void loadStudents() {
        List<Student> loaded =
                CSVReader.readStudents("students.csv");

        students = loaded != null
                ? loaded
                : new ArrayList<>();
    }

    private void addNavigationButton(
            JPanel sidebar,
            String text,
            Runnable action) {

        JButton button = new JButton(text);
        
button.setFont(new Font("Arial", Font.PLAIN, 14));
button.setForeground(TEXT);
button.setBackground(SIDEBAR);
button.setHorizontalAlignment(SwingConstants.LEFT);
button.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 45));
button.setPreferredSize(new Dimension(190, 45));
button.setBorder(new EmptyBorder(10, 12, 10, 5));
button.setFocusPainted(false);
button.setOpaque(true);
button.setContentAreaFilled(true);
button.setCursor(
        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
button.setAlignmentX(Component.LEFT_ALIGNMENT);

button.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(CARD_BACKGROUND);
        button.setForeground(ACCENT);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(SIDEBAR);
        button.setForeground(TEXT);
    }
});

button.addActionListener(e -> action.run());


        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(8));
    }

    // =========================================================
    // DASHBOARD HOME
    // =========================================================

    private void showDashboard() {
        pageTitle.setText("Dashboard Overview");
        contentPanel.removeAll();

        JPanel dashboardContent = new JPanel();
dashboardContent.setBackground(BACKGROUND);
dashboardContent.setOpaque(true);
dashboardContent.setLayout(
        new BoxLayout(dashboardContent, BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel(
                "Welcome to your Placement Dashboard!");
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcome.setForeground(TEXT);

        JLabel description = new JLabel(
                "Manage students, evaluate readiness and view performance.");
        description.setForeground(new Color(100, 110, 130));

        dashboardContent.add(welcome);
        dashboardContent.add(Box.createVerticalStrut(8));
        dashboardContent.add(description);
        dashboardContent.add(Box.createVerticalStrut(25));

        int ready = 0;
        int improvement = 0;
        int notEligible = 0;

        for (Student student : students) {
            String status = evaluator.evaluate(student);

            if ("PLACEMENT READY".equals(status)) {
                ready++;
            } else if ("NEEDS IMPROVEMENT".equals(status)) {
                improvement++;
            } else {
                notEligible++;
            }
        }

        JPanel cards = new JPanel(new GridLayout(2, 2, 15, 15));
        cards.setOpaque(false);
        cards.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 250));

cards.add(createStatCard("Total Students",
        String.valueOf(students.size()),
        ACCENT));

cards.add(createStatCard("Placement Ready",
        String.valueOf(ready),
        STATUS_GREEN));

cards.add(createStatCard("Needs Improvement",
        String.valueOf(improvement),
        STATUS_AMBER));

cards.add(createStatCard("Not Eligible",
        String.valueOf(notEligible),
        STATUS_RED));

        dashboardContent.add(cards);
        dashboardContent.add(Box.createVerticalStrut(25));

        JLabel quickTitle = new JLabel("Quick Navigation");
        quickTitle.setFont(new Font("Arial", Font.BOLD, 17));
        quickTitle.setForeground(TEXT);

        dashboardContent.add(quickTitle);
        dashboardContent.add(Box.createVerticalStrut(12));

        JPanel quickButtons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0));
        quickButtons.setOpaque(false);

        JButton studentsButton = new JButton("Manage Students");
        studentsButton.addActionListener(e -> showStudents());

        JButton readinessButton =
                new JButton("View Placement Readiness");
        readinessButton.addActionListener(
                e -> showPlacementReadiness());

        JButton rankingsButton = new JButton("View Rankings");
        rankingsButton.addActionListener(e -> showRankings());

        JButton analyticsButton = new JButton("View Analytics");
        analyticsButton.addActionListener(e -> showAnalytics());

JButton[] navigationButtons = {
        studentsButton,
        readinessButton,
        rankingsButton,
        analyticsButton
};

for (JButton button : navigationButtons) {
    button.setFont(new Font("Arial", Font.BOLD, 13));
    button.setBackground(CARD_BACKGROUND);
    button.setForeground(ACCENT);
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(10, 14, 10, 14)
    ));
    button.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
}


        quickButtons.add(studentsButton);
        quickButtons.add(readinessButton);
        quickButtons.add(rankingsButton);
        quickButtons.add(analyticsButton);

        dashboardContent.add(quickButtons);

        JScrollPane scroll = new JScrollPane(dashboardContent);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(scroll, BorderLayout.CENTER);
        refreshContent();
    }

 
private JPanel createStatCard(
        String title, String value, Color accent) {

    JPanel card = new JPanel();
    card.setBackground(CARD_BACKGROUND);

    card.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(
                                    0, 5, 0, 0, accent),
                            BorderFactory.createLineBorder(
                                    BORDER_COLOR)),
                    new EmptyBorder(18, 18, 18, 18)));

    card.setLayout(
            new BoxLayout(card, BoxLayout.Y_AXIS));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    titleLabel.setForeground(SECONDARY_TEXT);

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(new Font("Arial", Font.BOLD, 30));
    valueLabel.setForeground(TEXT);

    card.add(titleLabel);
    card.add(Box.createVerticalStrut(15));
    card.add(valueLabel);

    return card;
}


    // =========================================================
    // STUDENT MANAGEMENT
    // =========================================================

    private void showStudents() {
        pageTitle.setText("Student Management");
        contentPanel.removeAll();

        String[] columns = {
                "Student ID", "Name", "Department", "CGPA",
                "Coding", "Aptitude", "Interview", "Attendance",
                "Technical Skills", "Placement Readiness"
        };

        DefaultTableModel model = createTableModel(columns);

        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getStudentId(), s.getName(), s.getDepartment(),
                    s.getCgpa(), s.getCodingScore(),
                    s.getAptitudeScore(), s.getInterviewScore(),
                    s.getAttendance(),
                    String.join(", ", s.getTechnicalSkills()),
                    evaluator.evaluate(s)
            });
        }

JTable table = createTable(model, 28);
table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setColumnWidths(table, new int[]{
                80, 120, 100, 65, 70, 80, 80, 90, 200, 180
        });

        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(model);
        table.setRowSorter(sorter);

       JTextField search = new JTextField(18);
search.setBackground(INPUT_BACKGROUND);
search.setForeground(TEXT);
search.setCaretColor(ACCENT);
search.setSelectionColor(ACCENT);
search.setSelectedTextColor(Color.WHITE);
search.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(6, 8, 6, 8)));

addSearchListener(search, sorter, 0, 1, 2);

        
JButton refresh = new JButton("Refresh");

refresh.setFont(new Font("Arial", Font.BOLD, 13));
refresh.setBackground(ACCENT);
refresh.setForeground(Color.WHITE);
refresh.setFocusPainted(false);
refresh.setOpaque(true);
refresh.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                new EmptyBorder(8, 14, 8, 14)));
refresh.setCursor(
        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

refresh.addActionListener(e -> {
    loadStudents();
    showStudents();
});

       
JButton add = new JButton("Add Student");

add.setFont(new Font("Arial", Font.BOLD, 13));
add.setBackground(ACCENT);
add.setForeground(Color.WHITE);
add.setFocusPainted(false);
add.setOpaque(true);
add.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                new EmptyBorder(8, 14, 8, 14)));
add.setCursor(
        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

add.addActionListener(e -> showAddStudentForm());



JButton delete = new JButton("Delete Selected");

delete.setFont(new Font("Arial", Font.BOLD, 13));
delete.setBackground(STATUS_RED);
delete.setForeground(Color.WHITE);
delete.setFocusPainted(false);
delete.setOpaque(true);
delete.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(STATUS_RED),
                new EmptyBorder(8, 14, 8, 14)));
delete.setCursor(
        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

delete.addActionListener(e -> {
    int selected = table.getSelectedRow();


            if (selected == -1) {
                JOptionPane.showMessageDialog(frame,
                        "Please select a student to delete.",
                        "No Student Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int modelRow = table.convertRowIndexToModel(selected);
            int id = ((Number) model.getValueAt(modelRow, 0))
                    .intValue();

            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete student ID "
                            + id + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                boolean deleted =
                        CSVReader.deleteStudent("students.csv", id);

                if (!deleted) {
                    JOptionPane.showMessageDialog(frame,
                            "Student was not found in the CSV file.",
                            "Delete Failed",
                            JOptionPane.ERROR_MESSAGE);
                    loadStudents();
                    showStudents();
                    return;
                }

                students.removeIf(s -> s.getStudentId() == id);

                JOptionPane.showMessageDialog(frame,
                        "Student deleted successfully!");

                showStudents();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Could not delete student:\n" + ex.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

       JPanel toolbar = new JPanel(
        new FlowLayout(FlowLayout.LEFT));
toolbar.setBackground(BACKGROUND);

JLabel searchLabel =
        new JLabel("Search ID, name or department:");
searchLabel.setForeground(TEXT);

toolbar.add(searchLabel);
toolbar.add(search);
toolbar.add(refresh);
toolbar.add(add);
toolbar.add(delete);
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        contentPanel.add(panel, BorderLayout.CENTER);
        refreshContent();
    }

    // =========================================================
    // PLACEMENT READINESS
    // =========================================================


private void showPlacementReadiness() {
    pageTitle.setText("Placement Readiness");
    contentPanel.removeAll();

    int ready = 0;
    int improvement = 0;
    int notEligible = 0;

    for (Student s : students) {
        String status = evaluator.evaluate(s);

        if ("PLACEMENT READY".equals(status)) {
            ready++;
        } else if ("NEEDS IMPROVEMENT".equals(status)) {
            improvement++;
        } else {
            notEligible++;
        }
    }

    final int readyCount = ready;
    final int improvementCount = improvement;
    final int notEligibleCount = notEligible;

    // Summary cards
    JPanel summary = new JPanel(new GridLayout(1, 3, 12, 0));
    summary.setOpaque(false);

    summary.add(createStatCard(
            "Placement Ready", String.valueOf(ready),
            STATUS_GREEN));

    summary.add(createStatCard(
            "Needs Improvement", String.valueOf(improvement),
            STATUS_AMBER));

    summary.add(createStatCard(
            "Not Eligible", String.valueOf(notEligible),
            STATUS_RED));

    // Readiness distribution chart
    JPanel chartPanel = new JPanel(new BorderLayout(0, 10));
    chartPanel.setBackground(CARD_BACKGROUND);
    chartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(12, 12, 12, 12)));

    JLabel chartTitle = new JLabel("Readiness Distribution");
    chartTitle.setFont(new Font("Arial", Font.BOLD, 15));
    chartTitle.setForeground(TEXT);
    chartPanel.add(chartTitle, BorderLayout.NORTH);

    JPanel chart = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int total = readyCount
                    + improvementCount + notEligibleCount;

            int x = 10;
            int y = 12;
            int width = getWidth() - 20;
            int height = 22;

            if (width <= 0) {
                return;
            }

            g.setColor(INPUT_BACKGROUND);
            g.fillRect(x, y, width, height);

            if (total == 0) {
                g.setColor(SECONDARY_TEXT);
                g.drawString("No student data available", x, y + 45);
                return;
            }

            int readyWidth = width * readyCount / total;
            int improvementWidth =
                    width * improvementCount / total;
            int notEligibleWidth =
                    width - readyWidth - improvementWidth;

            int currentX = x;

            g.setColor(STATUS_GREEN);
            g.fillRect(currentX, y, readyWidth, height);
            currentX += readyWidth;

            g.setColor(STATUS_AMBER);
            g.fillRect(currentX, y, improvementWidth, height);
            currentX += improvementWidth;

            g.setColor(STATUS_RED);
            g.fillRect(currentX, y, notEligibleWidth, height);

            g.setColor(TEXT);
            g.drawString(
                    "Ready: " + readyCount
                    + "   Needs Improvement: " + improvementCount
                    + "   Not Eligible: " + notEligibleCount,
                    x, y + 48);
        }
    };

    chart.setBackground(CARD_BACKGROUND);
    chart.setOpaque(true);
    chart.setPreferredSize(new Dimension(400, 75));
    chartPanel.add(chart, BorderLayout.CENTER);

    // Student assessment table
    String[] columns = {
            "Student ID", "Name", "Department", "CGPA",
            "Coding", "Aptitude", "Interview", "Attendance",
            "Technical Skills", "Placement Readiness"
    };

    DefaultTableModel model = createTableModel(columns);

    for (Student s : students) {
        model.addRow(new Object[]{
                s.getStudentId(),
                s.getName(),
                s.getDepartment(),
                s.getCgpa(),
                s.getCodingScore(),
                s.getAptitudeScore(),
                s.getInterviewScore(),
                s.getAttendance(),
                String.join(", ", s.getTechnicalSkills()),
                evaluator.evaluate(s)
        });
    }

    JTable table = createTable(model, 30);
    table.setAutoResizeMode(
            JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

    setColumnWidths(table, new int[]{
            80, 120, 100, 65, 70, 80, 80, 90, 200, 180
    });

    TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(model);
    table.setRowSorter(sorter);
    applyReadinessStatusColors(table);

    // Search
    JTextField search = new JTextField(18);
    search.setBackground(INPUT_BACKGROUND);
    search.setForeground(TEXT);
    search.setCaretColor(ACCENT);
    search.setSelectionColor(ACCENT);
    search.setSelectedTextColor(Color.WHITE);
    search.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(6, 8, 6, 8)));

    addSearchListener(search, sorter, 0, 1, 2, 9);

    // Refresh
    JButton refresh = new JButton("Refresh");
    refresh.setFont(new Font("Arial", Font.BOLD, 13));
    refresh.setBackground(ACCENT);
    refresh.setForeground(Color.WHITE);
    refresh.setFocusPainted(false);
    refresh.setOpaque(true);
    refresh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT),
            new EmptyBorder(8, 14, 8, 14)));

    refresh.addActionListener(e -> {
        loadStudents();
        showPlacementReadiness();
    });

    JPanel toolbar = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5));
    toolbar.setBackground(BACKGROUND);

    JLabel searchLabel =
            new JLabel("Search students or status:");
    searchLabel.setForeground(TEXT);

    toolbar.add(searchLabel);
    toolbar.add(search);
    toolbar.add(refresh);

    // Table panel
    JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
    tablePanel.setBackground(BACKGROUND);
    tablePanel.setBorder(new EmptyBorder(5, 0, 0, 0));

    JLabel tableTitle =
            new JLabel("Student Assessment Details");
    tableTitle.setFont(new Font("Arial", Font.BOLD, 15));
    tableTitle.setForeground(ACCENT);

    JPanel tableHeader = new JPanel(new BorderLayout(0, 8));
    tableHeader.setBackground(BACKGROUND);
    tableHeader.add(tableTitle, BorderLayout.NORTH);
    tableHeader.add(toolbar, BorderLayout.CENTER);

    JScrollPane tableScroll = new JScrollPane(table);
    tableScroll.setBackground(BACKGROUND);
    tableScroll.setOpaque(true);
    tableScroll.getViewport().setBackground(INPUT_BACKGROUND);
    tableScroll.getViewport().setOpaque(true);
    tableScroll.setBorder(
            BorderFactory.createLineBorder(BORDER_COLOR));

    tablePanel.add(tableHeader, BorderLayout.NORTH);
    tablePanel.add(tableScroll, BorderLayout.CENTER);

    // Arrange dashboard sections
    JPanel topSection = new JPanel(new BorderLayout(0, 15));
    topSection.setOpaque(false);
    topSection.add(summary, BorderLayout.NORTH);
    topSection.add(chartPanel, BorderLayout.CENTER);

    JPanel dashboard = new JPanel(new BorderLayout(0, 15));
    dashboard.setBackground(BACKGROUND);
    dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));
    dashboard.add(topSection, BorderLayout.NORTH);
    dashboard.add(tablePanel, BorderLayout.CENTER);

    JScrollPane dashboardScroll = new JScrollPane(dashboard);
    dashboardScroll.setBorder(null);
    dashboardScroll.setOpaque(false);
    dashboardScroll.getViewport().setOpaque(false);
    dashboardScroll.getVerticalScrollBar().setUnitIncrement(16);
    dashboardScroll.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    contentPanel.add(dashboardScroll, BorderLayout.CENTER);
    refreshContent();
}

private void applyReadinessStatusColors(JTable table) {
    int statusColumn = table.getColumnModel()
            .getColumnIndex("Placement Readiness");

    table.getColumnModel().getColumn(statusColumn)
            .setCellRenderer(new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            Component cell = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String status = value == null ? "" : value.toString();

            if (isSelected) {
                cell.setBackground(ACCENT);
                cell.setForeground(Color.WHITE);
            } else if ("PLACEMENT READY".equals(status)) {
                cell.setBackground(new Color(24, 60, 43));
                cell.setForeground(STATUS_GREEN);
            } else if ("NEEDS IMPROVEMENT".equals(status)) {
                cell.setBackground(new Color(73, 55, 25));
                cell.setForeground(STATUS_AMBER);
            } else if ("NOT ELIGIBLE".equals(status)) {
                cell.setBackground(new Color(73, 35, 38));
                cell.setForeground(STATUS_RED);
            } else {
                cell.setBackground(INPUT_BACKGROUND);
                cell.setForeground(TEXT);
            }

            setFont(new Font("Arial", Font.BOLD, 12));
            setHorizontalAlignment(SwingConstants.CENTER);

            return cell;
        }
    });
}


    

    // =========================================================
    // STUDENT RANKINGS
    // =========================================================

    private void showRankings() {
        pageTitle.setText("Student Rankings");
        contentPanel.removeAll();

        JLabel description = new JLabel(
                "Students ranked by overall performance "
                        + "(highest score first).");
description.setForeground(SECONDARY_TEXT);
        String[] columns = {
                "Rank", "Student ID", "Name", "Department",
                "CGPA", "Coding", "Aptitude", "Interview",
                "Attendance", "Overall Score"
        };

        DefaultTableModel model = createTableModel(columns);

        List<Student> ranked = new ArrayList<>(students);
        ranked.sort((a, b) -> Double.compare(
                rankingService.calculateScore(b),
                rankingService.calculateScore(a)));

        int rank = 1;

        for (Student s : ranked) {
            model.addRow(new Object[]{
                    rank++, s.getStudentId(), s.getName(),
                    s.getDepartment(), s.getCgpa(),
                    s.getCodingScore(), s.getAptitudeScore(),
                    s.getInterviewScore(), s.getAttendance(),
                    String.format(
                            "%.2f", rankingService.calculateScore(s))
            });
        }

        JTable table = createTable(model, 30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setColumnWidths(table, new int[]{
                55, 90, 140, 100, 70, 75, 85, 85, 90, 110
        });

        
        JButton refresh = new JButton("Refresh");
        refresh.setFont(new Font("Arial", Font.BOLD, 13));
        refresh.setBackground(ACCENT);
        refresh.setForeground(Color.WHITE);
        refresh.setFocusPainted(false);
        refresh.setOpaque(true);
        refresh.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT),
                        new EmptyBorder(8, 14, 8, 14)));
        refresh.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        refresh.addActionListener(e -> {
            loadStudents();
            showRankings();
        });



        JPanel toolbar = new JPanel(
                new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(BACKGROUND);

        JLabel rankingLabel = new JLabel("Ranked by overall score");
        rankingLabel.setForeground(TEXT);

        toolbar.add(rankingLabel);
        toolbar.add(refresh);

        JPanel tablePanel = new JPanel(
                new BorderLayout(0, 10));
        tablePanel.setBackground(BACKGROUND);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(INPUT_BACKGROUND);
        tableScroll.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR));

        tablePanel.add(toolbar, BorderLayout.NORTH);
        tablePanel.add(tableScroll, BorderLayout.CENTER);


        JPanel page = new JPanel(new BorderLayout(0, 15));
        page.setOpaque(false);
        page.add(description, BorderLayout.NORTH);
        page.add(tablePanel, BorderLayout.CENTER);

        contentPanel.add(page, BorderLayout.CENTER);
        refreshContent();
    }

    // =========================================================
    // PLACEMENT ANALYTICS
    // =========================================================

    private void showAnalytics() {
        pageTitle.setText("Placement Analytics");
        contentPanel.removeAll();

        if (students.isEmpty()) {
            contentPanel.add(new JLabel(
                    "No student data available. Add students first.",
                    SwingConstants.CENTER), BorderLayout.CENTER);
            refreshContent();
            return;
        }

        /*
         * Track the viewport width so the report panels and
         * tables use the available space without horizontal
         * scrolling.
         */
        JPanel analyticsPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Container parent = getParent();

                if (parent instanceof JViewport) {
                    int width = parent.getWidth();
                    if (width > 0) {
                        d.width = width;
                    }
                }

                return d;
            }
        };

        analyticsPanel.setOpaque(false);
        analyticsPanel.setBorder(new EmptyBorder(5, 5, 15, 5));
        analyticsPanel.setLayout(
                new BoxLayout(analyticsPanel, BoxLayout.Y_AXIS));

        int ready = 0;
        int improvement = 0;
        int notEligible = 0;

        for (Student s : students) {
            String status = evaluator.evaluate(s);

            if ("PLACEMENT READY".equals(status)) {
                ready++;
            } else if ("NEEDS IMPROVEMENT".equals(status)) {
                improvement++;
            } else {
                notEligible++;
            }
        }

        // Summary cards
        JPanel summary = new JPanel(new GridLayout(1, 3, 12, 0));
        summary.setOpaque(false);
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 130));

        summary.add(createStatCard("Total Students",
                String.valueOf(students.size()),
                new Color(59, 100, 220)));
        summary.add(createStatCard("Placement Ready",
                String.valueOf(ready),
                new Color(25, 145, 95)));
        summary.add(createStatCard("Need Attention",
                String.valueOf(improvement + notEligible),
                new Color(210, 140, 30)));

        analyticsPanel.add(summary);
        analyticsPanel.add(Box.createVerticalStrut(18));

        // 1. Top 5 Students
        String[] topColumns = {
                "Rank", "Student ID", "Name",
                "Department", "Overall Score"
        };

        DefaultTableModel topModel = createTableModel(topColumns);
        List<Student> topStudents =
                analyticsService.getTop5Students(students);

        int rank = 1;

        for (Student s : topStudents) {
            topModel.addRow(new Object[]{
                    rank++, s.getStudentId(), s.getName(),
                    s.getDepartment(),
                    String.format(
                            "%.2f", rankingService.calculateScore(s))
            });
        }

        JTable topTable = createAnalyticsTable(topModel);

        // 2. Best-performing department
        Map.Entry<String, Double> bestDepartment =
                analyticsService.getBestDepartment(students);

        String bestDepartmentText =
                bestDepartment == null
                        ? "No department data available"
                        : bestDepartment.getKey()
                                + " (Average score: "
                                + String.format(
                                        "%.2f",
                                        bestDepartment.getValue())
                                + ")";

       JLabel bestDepartmentLabel = new JLabel(
        "Best-Performing Department: "
                + bestDepartmentText); 
bestDepartmentLabel.setFont(
        new Font("Arial", Font.BOLD, 14));
bestDepartmentLabel.setForeground(TEXT);
bestDepartmentLabel.setOpaque(true);
bestDepartmentLabel.setBackground(CARD_BACKGROUND);
bestDepartmentLabel.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(12, 10, 12, 10)));

        // 3. Average coding score by department
        String[] codingColumns = {
                "Department", "Average Coding Score"
        };

        DefaultTableModel codingModel =
                createTableModel(codingColumns);

        analyticsService
                .getAverageCodingScoreByDepartment(students)
                .forEach((department, average) ->
                        codingModel.addRow(new Object[]{
                                department,
                                String.format("%.2f", average)
                        }));

        JTable codingTable = createAnalyticsTable(codingModel);

        // 4. Most common technical skills
        String[] skillsColumns = {
                "Technical Skill", "Number of Students"
        };

        DefaultTableModel skillsModel =
                createTableModel(skillsColumns);

        analyticsService.getMostCommonSkills(students)
                .forEach((skill, count) ->
                        skillsModel.addRow(new Object[]{
                                skill, count
                        }));

        JTable skillsTable = createAnalyticsTable(skillsModel);

        // 5. Students with most technical skills
        String[] studentSkillsColumns = {
                "Student ID", "Name",
                "Number of Skills", "Technical Skills"
        };

        DefaultTableModel studentSkillsModel =
                createTableModel(studentSkillsColumns);

        analyticsService.getStudentsWithMostSkills(students)
                .forEach(s ->
                        studentSkillsModel.addRow(new Object[]{
                                s.getStudentId(),
                                s.getName(),
                                s.getTechnicalSkills().size(),
                                String.join(", ", s.getTechnicalSkills())
                        }));

        JTable studentSkillsTable =
                createAnalyticsTable(studentSkillsModel);

        // 6. Students requiring improvement
        String[] improvementColumns = {
                "Student ID", "Name",
                "Department", "Placement Readiness"
        };

        DefaultTableModel improvementModel =
                createTableModel(improvementColumns);

        analyticsService
                .getStudentsRequiringImprovement(students, evaluator)
                .forEach(s ->
                        improvementModel.addRow(new Object[]{
                                s.getStudentId(),
                                s.getName(),
                                s.getDepartment(),
                                evaluator.evaluate(s)
                        }));

        JTable improvementTable =
                createAnalyticsTable(improvementModel);

        // Place reports inside the same vertical layout.
        analyticsPanel.add(createReportSection(
                "1. Top 5 Students", topTable));
        analyticsPanel.add(Box.createVerticalStrut(14));

        analyticsPanel.add(createReportSection(
                "2. Best-Performing Department",
                bestDepartmentLabel));
        analyticsPanel.add(Box.createVerticalStrut(14));

        analyticsPanel.add(createReportSection(
                "3. Average Coding Score by Department",
                codingTable));
        analyticsPanel.add(Box.createVerticalStrut(14));

        analyticsPanel.add(createReportSection(
                "4. Most Common Technical Skills",
                skillsTable));
        analyticsPanel.add(Box.createVerticalStrut(14));

        analyticsPanel.add(createReportSection(
                "5. Students with Most Technical Skills",
                studentSkillsTable));
        analyticsPanel.add(Box.createVerticalStrut(14));

        analyticsPanel.add(createReportSection(
                "6. Students Requiring Improvement",
                improvementTable));

       
        JButton refresh = new JButton("Refresh Analytics");
        refresh.setFont(new Font("Arial", Font.BOLD, 13));
        refresh.setBackground(ACCENT);
        refresh.setForeground(Color.WHITE);
        refresh.setFocusPainted(false);
        refresh.setOpaque(true);
        refresh.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT),
                        new EmptyBorder(8, 14, 8, 14)));
        refresh.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        refresh.addActionListener(e -> {
            loadStudents();
            showAnalytics();
        });


        JPanel toolbar = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 5));
        toolbar.setOpaque(false);
        toolbar.add(refresh);

        JPanel page = new JPanel(new BorderLayout(0, 10));
        page.setOpaque(false);
        page.add(toolbar, BorderLayout.NORTH);

        JScrollPane analyticsScroll =
                new JScrollPane(analyticsPanel);

       analyticsScroll.setOpaque(false);
analyticsScroll.getViewport().setOpaque(false);
analyticsScroll.getVerticalScrollBar().setBackground(BACKGROUND);
        analyticsScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        analyticsScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        analyticsScroll.getVerticalScrollBar().setUnitIncrement(18);

        page.add(analyticsScroll, BorderLayout.CENTER);
        contentPanel.add(page, BorderLayout.CENTER);

        refreshContent();
    }

    // Create tables that resize to fit the Analytics viewport.
    private JTable createAnalyticsTable(DefaultTableModel model) {
        JTable table = createTable(model, 28);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(false);
        return table;
    }


private JPanel createReportSection(
        String title,
        JComponent component) {

    JPanel section = new JPanel(new BorderLayout(0, 8));
    section.setBackground(CARD_BACKGROUND);

    section.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    new EmptyBorder(12, 12, 12, 12)));

    JLabel heading = new JLabel(title);
    heading.setFont(new Font("Arial", Font.BOLD, 16));
    heading.setForeground(ACCENT);

    section.add(heading, BorderLayout.NORTH);
    section.add(component, BorderLayout.CENTER);
    section.setAlignmentX(Component.LEFT_ALIGNMENT);
    section.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

    return section;
}


    // =========================================================
    // TABLE HELPERS
    // =========================================================

    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

   
private JTable createTable(
        DefaultTableModel model,
        int rowHeight) {

    JTable table = new JTable(model);
    table.setRowHeight(rowHeight);
    table.setFillsViewportHeight(true);
    table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);

    table.setShowGrid(true);
    table.setGridColor(BORDER_COLOR);

   table.setBackground(INPUT_BACKGROUND);
table.setOpaque(true);
table.setForeground(TEXT);

    // Selected row colours
    table.setSelectionBackground(ACCENT);
    table.setSelectionForeground(Color.WHITE);

    // Table header styling
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setBackground(
            TABLE_HEADER_BACKGROUND);
    table.getTableHeader().setForeground(TEXT);
    table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12));
    table.getTableHeader().setOpaque(true);
    table.getTableHeader().setDefaultRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        label.setBackground(TABLE_HEADER_BACKGROUND);
        label.setForeground(TEXT);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(4, 4, 4, 4)));

        return label;
    }
});
    table.getTableHeader().setBorder(
        BorderFactory.createLineBorder(BORDER_COLOR));

    return table;
}


    private void setColumnWidths(JTable table, int[] widths) {
        for (int i = 0;
             i < widths.length
                     && i < table.getColumnModel().getColumnCount();
             i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }
    }

    private void addSearchListener(
            JTextField searchField,
            TableRowSorter<DefaultTableModel> sorter,
            Integer... columns) {

        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

                    private void filter() {
                        String text =
                                searchField.getText().trim();

                        if (text.isEmpty()) {
                            sorter.setRowFilter(null);
                            return;
                        }

                        int[] indexes = new int[columns.length];

                        for (int i = 0; i < columns.length; i++) {
                            indexes[i] = columns[i];
                        }

                        sorter.setRowFilter(
                                RowFilter.regexFilter(
                                        "(?i)" + Pattern.quote(text),
                                        indexes));
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        filter();
                    }
                });
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    private void showAddStudentForm() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField cgpaField = new JTextField();
        JTextField codingField = new JTextField();
        JTextField aptitudeField = new JTextField();
        JTextField interviewField = new JTextField();
        JTextField attendanceField = new JTextField();
        JTextField skillsField = new JTextField();
        
        JTextField[] fields = {
                idField, nameField, departmentField,
                cgpaField, codingField, aptitudeField,
                interviewField, attendanceField, skillsField
        };

        for (JTextField field : fields) {
            field.setBackground(INPUT_BACKGROUND);
            field.setForeground(TEXT);
            field.setCaretColor(ACCENT);
            field.setSelectionColor(ACCENT);
            field.setSelectedTextColor(Color.WHITE);
            field.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            new EmptyBorder(6, 8, 6, 8)));
        }


        
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(CARD_BACKGROUND);
        form.setBorder(new EmptyBorder(12, 12, 12, 12));


       
        JLabel idLabel = new JLabel("Student ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel departmentLabel = new JLabel("Department:");
        JLabel cgpaLabel = new JLabel("CGPA (0-10):");
        JLabel codingLabel = new JLabel("Coding Score (0-100):");
        JLabel aptitudeLabel = new JLabel("Aptitude Score (0-100):");
        JLabel interviewLabel = new JLabel("Interview Score (0-100):");
        JLabel attendanceLabel = new JLabel("Attendance (0-100):");
        JLabel skillsLabel = new JLabel(
                "Technical Skills (comma-separated):");

        JLabel[] labels = {
                idLabel, nameLabel, departmentLabel,
                cgpaLabel, codingLabel, aptitudeLabel,
                interviewLabel, attendanceLabel, skillsLabel
        };

        for (JLabel label : labels) {
            label.setForeground(TEXT);
            label.setFont(new Font("Arial", Font.PLAIN, 13));
        }

        form.add(idLabel);
        form.add(idField);
        form.add(nameLabel);
        form.add(nameField);
        form.add(departmentLabel);
        form.add(departmentField);
        form.add(cgpaLabel);
        form.add(cgpaField);
        form.add(codingLabel);
        form.add(codingField);
        form.add(aptitudeLabel);
        form.add(aptitudeField);
        form.add(interviewLabel);
        form.add(interviewField);
        form.add(attendanceLabel);
        form.add(attendanceField);
        form.add(skillsLabel);
        form.add(skillsField);



        UIManager.put("OptionPane.background", CARD_BACKGROUND);
        UIManager.put("Panel.background", CARD_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("Button.background", ACCENT);
        UIManager.put("Button.foreground", Color.WHITE);

        int result = JOptionPane.showConfirmDialog(
                frame, form, "Add New Student",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }


        try {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            String cgpaText = cgpaField.getText().trim();
            String codingText = codingField.getText().trim();
            String aptitudeText = aptitudeField.getText().trim();
            String interviewText = interviewField.getText().trim();
            String attendanceText = attendanceField.getText().trim();
            String skillsText = skillsField.getText().trim();

            if (idText.isEmpty() || name.isEmpty()
                    || department.isEmpty() || cgpaText.isEmpty()
                    || codingText.isEmpty() || aptitudeText.isEmpty()
                    || interviewText.isEmpty()
                    || attendanceText.isEmpty()
                    || skillsText.isEmpty()) {

                JOptionPane.showMessageDialog(frame,
                        "Please fill in all fields.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);

            if (id <= 0) {
                throw new IllegalArgumentException(
                        "Student ID must be positive.");
            }

            if (students.stream().anyMatch(
                    s -> s.getStudentId() == id)) {
                JOptionPane.showMessageDialog(frame,
                        "A student with this ID already exists.",
                        "Duplicate ID",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double cgpa = Double.parseDouble(cgpaText);
            int coding = Integer.parseInt(codingText);
            int aptitude = Integer.parseInt(aptitudeText);
            int interview = Integer.parseInt(interviewText);
            double attendance = Double.parseDouble(attendanceText);

            if (cgpa < 0 || cgpa > 10
                    || coding < 0 || coding > 100
                    || aptitude < 0 || aptitude > 100
                    || interview < 0 || interview > 100
                    || attendance < 0 || attendance > 100) {

                JOptionPane.showMessageDialog(frame,
                        "CGPA must be 0-10. Scores and attendance "
                                + "must be 0-100.",
                        "Invalid Values",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> skills = new ArrayList<>();

            for (String skill : skillsText.split(",")) {
                if (!skill.trim().isEmpty()) {
                    skills.add(skill.trim());
                }
            }

            if (skills.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Enter at least one technical skill.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(
                    id, name, department, cgpa,
                    coding, aptitude, interview,
                    attendance, skills);

            CSVReader.appendStudent("students.csv", student);
            students.add(student);

            JOptionPane.showMessageDialog(frame,
                    "Student saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            showStudents();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Enter valid numeric values for ID, CGPA and scores.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame,
                    ex.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Could not save student to CSV:\n"
                            + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshContent() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}