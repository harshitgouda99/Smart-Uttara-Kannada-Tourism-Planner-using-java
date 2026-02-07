# Smart-Uttara-Kannada-Tourism-Planner-using-java
A Java-based, menu-driven console application that intelligently suggests tourist places in Uttara Kannada using DSA concepts, OOP principles, and CSV-based data handling.

📌 Project Overview
--------------------------
The Smart Uttara Kannada Tourism Planner helps users explore tourist destinations based on:

Season

Category (Beach, Temple, Waterfall, Adventure, Heritage)

Budget

Crowd preference

Popularity

The system dynamically loads tourism data from a CSV file, making it easy to update destinations without modifying the source code.

✨ Features
--------------------------------------
🔹 Core Features

Menu-driven console interface

Dynamic data loading from CSV file

Ranked place recommendations

Realistic tourism planner output format

🔹 DSA & Logic Features

Sort places by popularity

Plan trip within budget (Greedy Algorithm)

Top-K popular places

Group places by Taluk (HashMap)

Search place by name (Linear Search)

Binary search by entry fee

Search places by multiple filters

Season

Category

Budget

Crowd level

🧠 Java Concepts Used
----------------------------------------
Object-Oriented Programming (OOP)

Collections Framework (ArrayList, HashMap)

Sorting & Comparators

Greedy Algorithm

Binary Search

File Handling (BufferedReader, FileReader)

Menu-driven programming

Clean modular methods

📂 Project Structure
---------------------------------
SmartTourismPlanner/
│
├── SmartTourismPlanner.java
├── uttara_kannada_tourism_data.csv
└── README.md

📊 CSV File Structure
-------------------------------------
The application reads data from:

uttara_kannada_tourism_data.csv

CSV Columns:
Place,
Taluk,
Category,
Best_Season,
Avg_Visitors_Per_Month,
Crowd_Level,
Entry_Fee,
Distance_From_Gokarna_KM,
Popularity_Score,
Eco_Sensitivity


