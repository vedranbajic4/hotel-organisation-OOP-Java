# 🏨 Hotel Management Information System

A Java-based hotel management information system. The system supports multiple user roles, room management, reservations, pricing, and housekeeping workflows.

---

## 📋 Description

This application simulates a real-world hotel information system. It supports four types of users — Administrator, Receptionist, Maid, and Guest — each with distinct roles and permissions. The system handles the full guest lifecycle: from registration and reservation to check-in, check-out, and room cleaning assignment. Pricing is dynamic, based on date-range price lists defined by the administrator.

---

## 🗂️ Overview

### User Roles

| Role | Permissions |
|---|---|
| **Administrator** | Full CRUD access to all entities; manages employees and price lists; views income/expense reports |
| **Receptionist** | Confirms/rejects reservations; performs check-in/check-out; registers guests; adds additional services |
| **Maid** | Views rooms assigned for cleaning; marks rooms as clean (available) |
| **Guest** | Makes and cancels reservations; views reservation statuses and costs; selects additional services |

### Key Workflows

- **Reservation lifecycle:** `PENDING → CONFIRMED / REJECTED` (by receptionist) or `CANCELLED` (by guest)
- **Room lifecycle:** `FREE → OCCUPIED` (check-in) `→ CLEANING` (check-out) `→ FREE` (maid confirms)
- **Automatic assignment:** On check-out, the room is automatically assigned to the maid with the fewest rooms that day
- **Dynamic pricing:** Room cost is calculated at reservation time using the price list valid for each individual night

---



## 🚀 How to Run

### Prerequisites
- Java 17 or higher installed
- Maven or Gradle installed (depending on build tool used)

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/vedranbajic4/hotel-organisation-OOP-Java.git
   cd hotel-management-system```

2. **Build the project:**
   ```bash
    mvn clean install
    # or
    gradle build```

3. **Run the application:**
    ```bash
    mvn exec:java -Dexec.mainClass="main.Main"
    # or run the Main class directly from your IDE```

4. **Login:**

    Use predefined credentials to log in as any role. Guests are registered by receptionists (email = username, passport number = password).


## 🖥️ Usage
After launching the application, you will be presented with a login screen. Depending on your role:

 - **Administrator** - Manage employees, rooms, room types, price lists, services, and view financial reports and charts

 - **Receptionist** - Handle reservations (confirm/reject), check-in/check-out guests, filter reservations by room type, price range, or additional services

 - **Maid** - View assigned rooms and mark them as cleaned

 - **Guest** - Browse available room types by date, make/cancel reservations, add services (breakfast, lunch, dinner), view total costs

 ## 📁 Project Structure
 ```
 src/
├── main/                  # Application entry point (Main.java)
├── entity/                # Core domain model classes
│   ├── User.java          # Abstract base class for all users
│   ├── Employee.java      # Abstract employee (extends User)
│   ├── Administrator.java
│   ├── Receptionist.java
│   ├── Maid.java
│   ├── Guest.java
│   ├── Room.java          # Room with status and attributes
│   ├── RoomType.java      # Room type definition (single, double, triple...)
│   ├── Reservation.java   # Reservation with status, dates, services, price
│   ├── AdditionalService.java  # Hotel services (breakfast, lunch, dinner...)
│   └── PriceList.java     # Price list with validity date range
├── enums/                 # Enumerations for predefined value sets
│   ├── ReservationStatus.java  # PENDING, CONFIRMED, REJECTED, CANCELLED
│   ├── RoomStatus.java         # FREE, OCCUPIED, CLEANING
│   ├── Gender.java
│   ├── EducationLevel.java     # Employee education level coefficient
│   └── UserRole.java
├── managerKlase/          # Business logic / service layer
│   ├── UserManager.java
│   ├── RoomManager.java
│   ├── ReservationManager.java
│   ├── MaidManager.java
│   ├── PriceListManager.java
│   └── ReportManager.java
├── gui/                   # Java Swing GUI panels and frames
│   ├── LoginFrame.java
│   ├── AdminPanel.java
│   ├── ReceptionistPanel.java
│   ├── MaidPanel.java
│   ├── GuestPanel.java
│   └── ...                # Additional dialog/form classes
├── cliMenu/               # (Optional) Console menu interface
│   └── ...
├── customClasses/         # Custom data structures or utility classes
│   └── ...
├── filter/                # Filtering logic for reservations and rooms
│   ├── ReservationFilter.java
│   └── RoomFilter.java
├── utils/                 # Utility/helper classes
│   ├── CSVReader.java
│   ├── CSVWriter.java
│   └── DateUtils.java
└── test/                  # Unit tests for manager classes
    ├── ReservationManagerTest.java
    ├── RoomManagerTest.java
    └── ...
```

 ## 📊 Reports & Charts

The system generates the following reports (accessible to Administrator/Receptionist):

- **Income & Expenses** - For a selected date range
- **Maid workload** - Number of rooms cleaned per maid in a date range
- **Reservation statistics** - Confirmed, rejected, and cancelled reservations per period
- **Room report** - Total nights and income per room for a selected period
- **Chart: Monthly revenue** - Income for the last 12 months broken down by room type (XChart)
- **Chart: Maid workload** - Room cleaning load per maid over the last 30 days
- **Chart: Reservation statuses** - Status distribution over the last 30 days

## 🧪 Testing

Unit tests are implemented for all manager (service) classes using JUnit. GUI classes and pure entity classes are excluded from unit testing scope.

```bash
mvn test
```
---

## ⚙️ Technologies

- **Language:** Java 17
- **GUI Framework:** Java Swing
- **Data persistence:** CSV (human-readable text format)
- **Unit Testing:** JUnit (manager classes)
- **Charts:** [XChart](https://knowm.org/open-source/xchart/) (Swing-compatible charting library)
- **IDE:** IntelliJ IDEA / Eclipse (WindowBuilder supported)
- **Build:** Maven / Gradle

---


## 📌 Notes

- Self-registration is **not supported** - employees are added by the Administrator, guests are registered by the Receptionist

 - Data is persisted in **CSV files** (human-readable format)

 - Prices are locked at reservation creation time and are **not affected** by later price list changes

 - Expired pending reservations are **automatically rejected**

## License 🧾

This project is for educational purposes within the OOP1 course.

You are free to use or modify it for learning and research.


## Contact 📬

If you have any questions, suggestions, or would like to collaborate — feel free to reach out!

👤 Author: Vedran Bajić

📧 Email: bajic196@gmail.com