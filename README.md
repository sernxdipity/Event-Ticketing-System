# 🎟️ Event Ticketing and Reservation System

A Java-based command-line application developed to manage events, ticket reservations and simulated payments using **Object-Oriented Programming (OOP)** principles.

The system supports two types of events: **Concerts** and **Conferences**, providing separate interfaces for administrators and attendees.

## ✨ Features

### Administrator
- Secure administrator login
- Add, view, search and update events
- Manage venues and seating capacities
- Configure Standard and VIP ticket pricing
- Manage event schedules and ticket allocations
- View upcoming events within the next 7 days

### Attendee
- Browse available concerts and conferences
- View Standard and VIP seat availability
- Make ticket reservations
- Receive a 10% early-bird discount when booking at least 14 days before an event
- Choose between simulated card and online banking payments
- Receive digital tickets after successful bookings
- View reservation history

## 🛠️ Technologies Used

- **Language:** Java
- **Interface:** Command-Line Interface (CLI)
- **Development Environment:** Visual Studio Code
- **Version Control:** Git & GitHub
- **Design:** UML Diagrams

## 🧩 OOP Principles

The application demonstrates four fundamental OOP principles:

- **Encapsulation:** Private attributes and controlled access through methods.
- **Inheritance:** ConcertEvent and ConferenceEvent inherit from Event.
- **Abstraction:** The abstract Event class defines common event functionality.
- **Polymorphism:** Different event types implement their own event-specific behaviours.

## ▶️ How to Run

1. Clone or download this repository.
2. Open the project folder in VS Code or a terminal.
3. Ensure the Java Development Kit (JDK) is installed.
4. Compile the Java source files using `javac *.java`.
5. Run the application using `java Main`.
6. Select Administrator or Attendee from the Main Menu.

## 🎨 Terminal Interface

The system includes a baby-blue and white, music-inspired terminal interface with decorative illustrations for concerts and conferences.

## 📌 Notes

- This project was developed for academic purposes.
- Payments are simulated; no real transactions occur.
- Data is stored in memory and is not retained after the application closes.
- Administrator authentication is intended for demonstration, not production security.

## 📚 Project Documentation

The accompanying project report includes UML Use Case, Activity, Sequence and Class Diagrams, OOP explanations, and implementation screenshots.

## 📄 License

Developed as an academic project for CSC 1223 — Object-Oriented Programming.