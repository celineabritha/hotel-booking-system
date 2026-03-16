# Hotel Booking Management System

## Overview

This project demonstrates the design and implementation of a **Hotel Booking Management System** using **Core Java and fundamental data structures**.
The system is built incrementally through a series of use cases to illustrate how real-world software systems evolve from simple implementations to scalable architectures.

The project focuses on **object-oriented design, centralized state management, and fair request handling**, while keeping the user interface simple and console-based.

---

## Objectives

* Demonstrate practical usage of **Core Java and Object-Oriented Programming**
* Apply **data structures such as HashMap and Queue** to real-world scenarios
* Show how software systems evolve through incremental improvements
* Separate system components such as **domain models, inventory management, and request handling**

---

## Technologies Used

* **Java**
* **Object-Oriented Programming (OOP)**
* **HashMap** – for centralized inventory management
* **Queue / LinkedList** – for fair booking request handling
* **Console-based output**

---

## System Features

### UC1 – Application Entry

* Demonstrates how a Java application begins execution.
* Displays a welcome message and application version.

### UC2 – Basic Room Types & Static Availability

* Introduces **object modeling** using abstraction and inheritance.
* Defines different room types such as:

  * Single Room
  * Double Room
  * Suite Room

### UC3 – Centralized Room Inventory Management

* Replaces scattered availability variables with a **centralized HashMap**.
* Provides a single source of truth for room availability.

### UC4 – Room Search & Availability Check

* Allows guests to view available rooms.
* Implements **read-only access to inventory**.
* Filters out room types that are not available.

### UC5 – Booking Request Queue (FIFO)

* Introduces a **Queue data structure** to handle booking requests.
* Ensures **First-Come-First-Served (FIFO)** processing.
* Maintains fairness when multiple requests arrive.

---

## Project Structure

```
UseCase1HotelBookingApp.java
UseCase2RoomInitialization.java
UseCase3InventorySetup.java
UseCase4RoomSearch.java
UseCase5BookingRequestQueue.java
```

Each file represents a separate stage of the system's evolution.

---

## How to Compile and Run

Compile a specific use case:

```
javac UseCase1HotelBookingApp.java
```

Run the program:

```
java UseCase1HotelBookingApp
```

Repeat the same process for other use cases.

Example:

```
javac UseCase5BookingRequestQueue.java
java UseCase5BookingRequestQueue
```

---

## Key Concepts Demonstrated

* Object-Oriented Programming
* Abstraction and Inheritance
* Encapsulation
* Centralized state management
* HashMap usage for fast lookup
* Queue implementation using FIFO
* Separation of concerns in system design

---

## Author

**Celine Abritha**

---

## Version

Current Version: **5.1**
