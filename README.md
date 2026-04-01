# Hotel Booking Management System

## Overview

This project demonstrates the design and implementation of a **Hotel Booking Management System** using **Core Java and fundamental data structures**.  
The system is built incrementally through a series of use cases to illustrate how real-world software systems evolve from simple implementations to scalable and production-ready architectures.

The project focuses on **object-oriented design, centralized state management, data consistency, concurrency handling, and persistence**, while keeping the user interface simple and console-based.

---

## Objectives

* Demonstrate practical usage of **Core Java and Object-Oriented Programming**
* Apply **data structures such as HashMap, Queue, List, Set, Stack** in real-world scenarios
* Show how software systems evolve through incremental improvements
* Ensure **data consistency, validation, and fault tolerance**
* Introduce **concurrency and persistence concepts**

---

## Technologies Used

* **Java**
* **Object-Oriented Programming (OOP)**
* **HashMap** – inventory & mappings  
* **Queue / LinkedList** – FIFO booking requests  
* **Set** – uniqueness enforcement  
* **List** – history tracking  
* **Stack** – rollback operations  
* **Multithreading (Thread, synchronized)** – concurrency  
* **Serialization (ObjectOutputStream)** – persistence  
* **Console-based output**

---

## System Features

### UC1 – Application Entry
* Entry point of the system
* Displays welcome message and version

---

### UC2 – Room Initialization
* Introduces **OOP concepts**
* Models room types:
  * Single
  * Double
  * Suite

---

### UC3 – Inventory Management
* Uses **HashMap** for centralized room tracking
* Single source of truth for availability

---

### UC4 – Room Search
* Allows users to view available rooms
* Filters unavailable room types

---

### UC5 – Booking Request Queue (FIFO)
* Uses **Queue (LinkedList)**
* Ensures fair request handling (First-Come-First-Served)

---

### UC6 – Reservation Confirmation & Room Allocation
* Assigns **unique room IDs**
* Prevents **double booking using Set**
* Uses **Map<RoomType, Set<RoomID>>**
* Ensures **atomic allocation + inventory update**

---

### UC7 – Add-On Service Selection
* Supports optional services (WiFi, Food, etc.)
* Uses **Map<ReservationID, List<Service>>**
* Demonstrates **composition over inheritance**

---

### UC8 – Booking History & Reporting
* Stores confirmed bookings using **List**
* Maintains **chronological order**
* Generates summary reports

---

### UC9 – Error Handling & Validation
* Implements **custom exceptions**
* Uses **fail-fast validation**
* Prevents invalid system state

---

### UC10 – Booking Cancellation & Rollback
* Uses **Stack (LIFO)** for rollback
* Reverses allocation safely
* Restores inventory correctly

---

### UC11 – Concurrent Booking Simulation
* Simulates **multi-user environment**
* Handles **race conditions using synchronized**
* Ensures **thread-safe operations**

---

### UC12 – Data Persistence & Recovery
* Implements **serialization & deserialization**
* Saves system state to file
* Restores state after restart
* Introduces **persistence mindset**

---

## Project Structure

