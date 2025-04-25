Room Partner Finder – Smart & Secure Living Companion

🧠 Problem Statement: Why This Project?
With growing urban migration due to education and employment, young individuals often relocate to new cities. In this transition, finding a reliable and compatible roommate becomes a critical challenge. Most people face the following pain points:
	• 🔍 Lack of trusted platforms to find roommates who share similar lifestyles.
	• 🧩 No detailed filtering for personal preferences (e.g., food habits, religion, occupation, etc.).
	• 📉 Inadequate listing of rooms or hostels, especially for first-time movers.
	• 🚫 Security and privacy risks while connecting with strangers on public platforms.
	• 🔕 Lack of real-time updates, making users miss out on potential matches.
This inspired the idea to develop a smart roommate-finding system that not only solves these problems but also ensures a secure, verified, and scalable solution tailored for students and working professionals.

🎯 Objective of the Project
The Room Partner Finder web app is designed to:
	• ✅ Enable intelligent roommate matching based on detailed filters.
	• ✅ Allow users to explore verified nearby rooms or hostels with rich media and location data.
	• ✅ Provide a secure platform for communication with strict privacy controls.
	• ✅ Implement a personalized notification system for updates and new matches.
	• ✅ Lay the foundation for AI integration and future scalability into mobile apps.

💡 Key Modules & Features Explained
🔍 1. Smart Room Partner Filter System
This is the heart of the application. Users can find ideal roommates by selecting multiple filters including:

Filter	Description
State / City / Area	Localized search to match region-wise preferences
Age & Gender	Match by demographic and gender identity (Boy/Girl)
Food Preference	Veg / Non-Veg compatibility
Occupation	Understand routine (Student / Working / Other)
College/Institute	Match with peers from the same institute
Religion (Optional)	Filter based on cultural or religious background
🔍 Backed by highly optimized SQL JOINs and WHERE clauses for multi-dimensional search.

🏠 2. Room/Hostel Suggestion Engine
When a compatible partner is found, users can also browse nearby PGs/hostels with features such as:
	• 📸 Photo gallery (images uploaded using Cloudinary CDN)
	• 💰 Rent info with sharing options
	• 📍 Google Maps integration for accurate location
	• 🛏️ Facilities available (Wi-Fi, AC, Meals, Laundry, etc.)
	• 🔖 Save/bookmark favorite rooms
	• ✅ Verification badge to ensure trustworthiness

🤝 3. Safe Communication Tools
Users can communicate only after mutual interest, ensuring privacy and safety:
	• 💬 WhatsApp Chat Button (auto-open on approval)
	• 📞 Call & Email access only after match consent
	• 🤖 Future Plan: Custom AI Chatbot / Dialogflow bot for quick support

📬 4. Real-Time Notifications
User engagement is boosted with dynamic alerts:
	• 📧 Email and SMS notifications for new partner suggestions
	• 🔕 User can toggle notification preferences
	• 🔔 Future: Push notifications for mobile (Firebase or OneSignal integration)

🔒 5. Security & Privacy First
Privacy is a top priority. Key measures include:
	• 🔐 Google/GitHub OAuth2 Login using Spring Security
	• ✅ Email verification before access (SMTP-based)
	• ⚠️ Report/flag system for suspicious profiles
	• 🔒 Contact sharing only after mutual match
	• 🛡️ Planned: JWT Authentication for API security

🧰 Technology Stack in Detail
👨‍💻 Frontend:
	• HTML5 + Tailwind CSS – Clean, mobile-first layout
	• JavaScript (Vanilla JS) – Interactive filtering and chat triggering
	• Cloudinary API – Image uploads and CDN delivery
	• FontAwesome & AOS – For icons and animations
💻 Backend:
	• Java (Spring Boot) – Microservice-based API architecture
	• Spring Security – OAuth2 login, role management, access control
	• RESTful API – Decoupled logic for future mobile app integration
🗃️ Database Design:
	• MySQL – Normalized schema with relations for:
		○ User profiles
		○ Room/Hostel listings
		○ Filters
		○ Match history
		○ Saved/bookmarked listings
	• Indexed queries for performance optimization

🔔 Notifications System
	• SMTP – Email alerts and verification
	• Twilio – SMS updates (trial account integration)
	• WhatsApp Cloud API – Direct chat access post-match

🎨 UI/UX Design Strategy
	• 💡 Minimalist dashboard with clear CTAs
	• 🧾 Filter cards with chips, badges, and dropdowns
	• 📍 Room info with Google Map Embed
	• ⚡ AOS & Animate.css for dynamic transitions
	• 📱 Fully responsive and mobile-friendly

📊 Future Enhancements (Planned)

Feature	Purpose
⭐ Partner Rating & Reviews	Build trust & feedback loop
🤖 AI Matching Engine	Smart suggestions based on behavior
💳 Payment Gateway	Online booking and deposits
🧑‍💼 Admin Panel	Hostel property management
🎁 Referral & Rewards System	Growth marketing
📱 Android/iOS App	Broader accessibility and push notifications

🎯 Target Audience
	• College students relocating to a new city
	• Job seekers and interns looking for affordable stay
	• Professionals wanting compatible housemates
	• Hostels/PG owners looking to promote verified rooms

👨‍💻 Developed By:
Rishi Singh
Java Full Stack Developer
📍 Based in Indore (From Basti, Uttar Pradesh)
📧 Email: rishisingh9838@gmail.com

📁 Next Steps:
Would you like me to:
	1. ✅ Generate a PDF version of this full documentation?
	2. ✅ Create a professional HTML/CSS portfolio page of this document?
	3. ✅ Build a React/Bootstrap dashboard prototype for demo?
Let me know how you'd like to showcase this next, Rishi. You’ve built something seriously awesome—let’s turn it into a showstopper! 🚀

![Home Page](https://github.com/user-attachments/assets/de586ea1-6df7-4713-8799-18c64d8f9d7e)
![Home page 2](https://github.com/user-attachments/assets/af814ade-f788-443c-835c-487b4b681a25)
![Partner_Finder Web App](https://github.com/user-attachments/assets/4c53cc85-3ece-4489-89de-810be153d463)
![SpringCode 1](https://github.com/user-attachments/assets/4ee46219-052d-4508-90b4-a28911224827)
![Create Account mysql](https://github.com/user-attachments/assets/f90a5218-305d-402c-ba9d-b7eb107033b5)

![Login form](https://github.com/user-attachments/assets/0732aae4-43e3-4478-ac4b-8ba7abcb3f8d)

![Login with Google Account](https://github.com/user-attachments/assets/ab1b9455-5fdc-450e-bd46-9c1fabc72c13)
![Login with Google ](https://github.com/user-attachments/assets/e7c1b2fd-2313-4df4-a47c-f30ef1171de9)

![Registration form](https://github.com/user-attachments/assets/c4615cf6-9f4b-430d-8e4b-d86a9e56d268)
![Sinup form](https://github.com/user-attachments/assets/c6010390-3cca-4127-9b65-380bc600eaac)
![Singup and Validation chek ](https://github.com/user-attachments/assets/27d73559-68c3-499e-b630-17f05163e99a)

![Profile View](https://github.com/user-attachments/assets/95b5fca8-3c29-4fe8-a6a4-1a9c0308fb11)

![Create Account 1](https://github.com/user-attachments/assets/28c86bfe-fec7-420a-a8a6-44b5101dda54)
![Create Account Sussefully Save](https://github.com/user-attachments/assets/eab0a6cc-72cd-46e0-a877-42bc711b271a)


![Cloudinary cloude for store image](https://github.com/user-attachments/assets/5a29efb7-7a44-4eb1-952d-79bcf41802a7)
![View Account Details](https://github.com/user-attachments/assets/57453826-4355-4040-92b4-fb0c39e41cc2)

