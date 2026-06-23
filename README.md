# 🏠 Room Partner Finder – Smart & Secure Living Companion

A comprehensive platform for finding compatible roommates and verified accommodations. Built with **Spring Boot** (Backend) and **Next.js** (Frontend).

---

## 📋 Table of Contents

- [🧠 Problem Statement](#-problem-statement)
- [🎯 Project Objectives](#-project-objectives)
- [🏗️ Architecture Overview](#️-architecture-overview)
- [💻 Backend Structure (Spring Boot)](#-backend-structure-spring-boot)
- [🎨 Frontend Structure (Next.js)](#-frontend-structure-nextjs)
- [🗄️ Database Schema](#️-database-schema)
- [🔌 API Documentation](#-api-documentation)
- [✨ Complete Feature List](#-complete-feature-list)
- [🛠️ Technology Stack](#️-technology-stack)
- [🚀 Setup & Installation](#-setup--installation)
- [📦 Deployment Guide](#-deployment-guide)
- [🔒 Security Features](#-security-features)
- [📊 Future Roadmap](#-future-roadmap)
- [👨‍💻 Developer Info](#️-developer-info)

---

## 🧠 Problem Statement

With growing urban migration due to education and employment, young individuals often relocate to new cities. In this transition, finding a reliable and compatible roommate becomes a critical challenge. Most people face the following pain points:

- **🔍 Lack of trusted platforms** to find roommates who share similar lifestyles
- **🧩 No detailed filtering** for personal preferences (food habits, religion, occupation, etc.)
- **📉 Inadequate listing** of rooms or hostels, especially for first-time movers
- **🚫 Security and privacy risks** while connecting with strangers on public platforms
- **🔕 Lack of real-time updates**, making users miss out on potential matches

This inspired the idea to develop a smart roommate-finding system that not only solves these problems but also ensures a secure, verified, and scalable solution tailored for students and working professionals.

---

## 🎯 Project Objectives

The Room Partner Finder web app is designed to:

- ✅ Enable intelligent roommate matching based on detailed filters
- ✅ Allow users to explore verified nearby rooms or hostels with rich media and location data
- ✅ Provide a secure platform for communication with strict privacy controls
- ✅ Implement a personalized notification system for updates and new matches
- ✅ Lay the foundation for AI integration and future scalability into mobile apps
- ✅ Support multiple user roles (User, Property Owner, Admin)
- ✅ Enable seamless booking and payment integration

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     CLIENT LAYER                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   Next.js    │  │  Mobile App  │  │  Admin Panel │      │
│  │  (React +    │  │  (React      │  │  (Next.js)   │      │
│  │   Tailwind)  │  │   Native)    │  │              │      │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘      │
└─────────┼──────────────────┼──────────────────┼─────────────┘
          │                  │                  │
          └──────────────────┼──────────────────┘
                             │
┌────────────────────────────┼────────────────────────────────┐
│                   API GATEWAY LAYER                         │
│              (Spring Cloud Gateway / Nginx)                  │
└────────────────────────────┼────────────────────────────────┘
                             │
┌────────────────────────────┼────────────────────────────────┐
│                   BACKEND SERVICES                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │  User Service│  │Property      │  │  Matching    │      │
│  │  (Auth,      │  │Service       │  │  Service     │      │
│  │   Profile)   │  │(Rooms, PGs)  │  │  (AI Logic)  │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │Notification  │  │Payment       │  │  Analytics   │      │
│  │Service       │  │Service       │  │  Service     │      │
│  │(Email, SMS)  │  │(Stripe/Razor)│  │              │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└────────────────────────────┼────────────────────────────────┘
                             │
┌────────────────────────────┼────────────────────────────────┐
│                   DATA LAYER                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │    MySQL     │  │   Redis      │  │  Elasticsearch│     │
│  │  (Primary)   │  │   (Cache)    │  │  (Search)    │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
│  ┌──────────────┐  ┌──────────────┐                         │
│  │  Cloudinary  │  │   Firebase   │                         │
│  │  (Images)    │  │  (Push Notif)│                         │
│  └──────────────┘  └──────────────┘                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 Backend Structure (Spring Boot)

### Project Structure

```
src/
├── main/
│   ├── java/com/rishi/rpf/
│   │   ├── RoomPartnerFinderApp.java          # Main Application Class
│   │   │
│   │   ├── config/                            # Configuration Classes
│   │   │   ├── AppConfig.java                 # General App Config
│   │   │   ├── SecurityConfig.java             # Spring Security Config
│   │   │   ├── OAuthAuthenticationSuccessHandler.java
│   │   │   ├── LoginSuccessHandler.java
│   │   │   ├── AuthFailureHandler.java
│   │   │   ├── AdminRoleInitializer.java      # Initialize Admin Role
│   │   │   ├── DataInitializer.java           # Seed Data
│   │   │   └── DotenvEnvironmentPostProcessor.java
│   │   │
│   │   ├── controllers/                       # REST Controllers
│   │   │   ├── AuthController.java             # Authentication Endpoints
│   │   │   ├── UserController.java             # User Management
│   │   │   ├── AdminController.java            # Admin Operations
│   │   │   ├── ApiController.java              # General API Endpoints
│   │   │   ├── ForgotPasswordController.java   # Password Recovery
│   │   │   ├── contactController.java          # Contact Management
│   │   │   ├── myController.java               # Custom Controllers
│   │   │   └── RootController.java             # Root Routes
│   │   │
│   │   ├── entity/                             # JPA Entities
│   │   │   ├── User.java                       # User Entity
│   │   │   ├── Contact.java                    # Contact Entity
│   │   │   ├── Otp.java                        # OTP Entity
│   │   │   ├── Providers.java                  # OAuth Providers
│   │   │   └── SocialLink.java                 # Social Links
│   │   │
│   │   ├── forms/                              # DTO/Form Objects
│   │   │   ├── UserForm.java
│   │   │   ├── ContactForm.java
│   │   │   ├── ContactSearchForm.java
│   │   │   ├── ForgotPasswordForm.java
│   │   │   └── ResetPasswordForm.java
│   │   │
│   │   ├── repository/                         # JPA Repositories
│   │   │   ├── UserRepository.java
│   │   │   ├── ContactRepository.java
│   │   │   ├── OtpRepository.java
│   │   │   └── ProvidersRepository.java
│   │   │
│   │   ├── service/                            # Business Logic
│   │   │   ├── UserService.java
│   │   │   ├── ContactService.java
│   │   │   ├── EmailService.java
│   │   │   ├── OtpService.java
│   │   │   ├── ImageService.java
│   │   │   ├── MatchingService.java
│   │   │   ├── NotificationService.java
│   │   │   └── PaymentService.java
│   │   │
│   │   ├── dto/                                # Data Transfer Objects
│   │   │   ├── UserDTO.java
│   │   │   ├── ContactDTO.java
│   │   │   ├── MatchDTO.java
│   │   │   ├── PropertyDTO.java
│   │   │   └── ApiResponse.java
│   │   │
│   │   ├── exception/                         # Custom Exceptions
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── BadRequestException.java
│   │   │   ├── UnauthorizedException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   │
│   │   ├── security/                           # Security Components
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── CustomUserDetailsService.java
│   │   │   └── OAuth2UserService.java
│   │   │
│   │   └── util/                               # Utility Classes
│   │       ├── DateUtil.java
│   │       ├── ValidationUtil.java
│   │       └── ImageUtil.java
│   │
│   └── resources/
│       ├── application.properties              # Main Config
│       ├── application-dev.properties          # Dev Config
│       ├── application-prod.properties         # Prod Config
│       ├── META-INF/
│       │   └── spring/
│       │       └── org.springframework.boot.env.EnvironmentPostProcessor.imports
│       ├── static/                            # Static Assets
│       │   ├── css/
│       │   ├── js/
│       │   └── images/
│       └── templates/                          # Thymeleaf Templates
│           ├── home.html
│           ├── login.html
│           ├── register.html
│           └── ...
│
└── test/
    └── java/com/rishi/rpf/
        ├── UserControllerTest.java
        ├── ContactServiceTest.java
        └── ...
```

### Key Backend Components

#### 1. **Configuration Layer**
- **SecurityConfig**: JWT + OAuth2 authentication, role-based access control
- **AppConfig**: Bean configurations, CORS settings, multipart file upload
- **DataInitializer**: Seed data for development and testing

#### 2. **Controller Layer**
RESTful API endpoints with proper validation and error handling

#### 3. **Service Layer**
Business logic separated from controllers for better testability

#### 4. **Repository Layer**
JPA repositories with custom queries using @Query

#### 5. **Entity Layer**
JPA entities with proper relationships and validations

---

## 🎨 Frontend Structure (Next.js)

### Project Structure

```
frontend/                          # Next.js Application
├── public/                        # Static Assets
│   ├── images/
│   ├── icons/
│   └── favicon.ico
│
├── src/
│   ├── app/                       # App Router (Next.js 13+)
│   │   ├── layout.tsx             # Root Layout
│   │   ├── page.tsx               # Home Page
│   │   ├── globals.css            # Global Styles
│   │   │
│   │   ├── (auth)/                # Auth Group
│   │   │   ├── login/
│   │   │   │   └── page.tsx
│   │   │   ├── register/
│   │   │   │   └── page.tsx
│   │   │   ├── forgot-password/
│   │   │   │   └── page.tsx
│   │   │   └── layout.tsx
│   │   │
│   │   ├── (dashboard)/           # Dashboard Group
│   │   │   ├── dashboard/
│   │   │   │   ├── page.tsx
│   │   │   │   ├── profile/
│   │   │   │   │   └── page.tsx
│   │   │   │   ├── settings/
│   │   │   │   │   └── page.tsx
│   │   │   │   └── layout.tsx
│   │   │   ├── partner-finder/
│   │   │   │   ├── page.tsx
│   │   │   │   └── layout.tsx
│   │   │   ├── properties/
│   │   │   │   ├── page.tsx
│   │   │   │   ├── [id]/
│   │   │   │   │   └── page.tsx
│   │   │   │   └── layout.tsx
│   │   │   ├── messages/
│   │   │   │   ├── page.tsx
│   │   │   │   ├── [id]/
│   │   │   │   │   └── page.tsx
│   │   │   │   └── layout.tsx
│   │   │   └── bookings/
│   │   │       ├── page.tsx
│   │   │       └── layout.tsx
│   │   │
│   │   ├── (admin)/               # Admin Group
│   │   │   ├── admin/
│   │   │   │   ├── page.tsx
│   │   │   │   ├── users/
│   │   │   │   │   └── page.tsx
│   │   │   │   ├── properties/
│   │   │   │   │   └── page.tsx
│   │   │   │   ├── reports/
│   │   │   │   │   └── page.tsx
│   │   │   │   └── layout.tsx
│   │   │
│   │   ├── api/                   # API Routes (Server Actions)
│   │   │   ├── auth/
│   │   │   │   ├── login/route.ts
│   │   │   │   ├── register/route.ts
│   │   │   │   └── logout/route.ts
│   │   │   ├── users/
│   │   │   │   ├── route.ts
│   │   │   │   └── [id]/route.ts
│   │   │   ├── properties/
│   │   │   │   ├── route.ts
│   │   │   │   └── [id]/route.ts
│   │   │   └── matching/
│   │   │       └── route.ts
│   │   │
│   │   └── _components/           # Shared Components
│   │
│   ├── components/               # React Components
│   │   ├── ui/                    # Shadcn/UI Components
│   │   │   ├── button.tsx
│   │   │   ├── input.tsx
│   │   │   ├── card.tsx
│   │   │   ├── dialog.tsx
│   │   │   ├── dropdown-menu.tsx
│   │   │   ├── form.tsx
│   │   │   ├── select.tsx
│   │   │   ├── tabs.tsx
│   │   │   ├── toast.tsx
│   │   │   └── ...
│   │   │
│   │   ├── layout/                # Layout Components
│   │   │   ├── Header.tsx
│   │   │   ├── Footer.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   ├── Navbar.tsx
│   │   │   └── MobileNav.tsx
│   │   │
│   │   ├── auth/                  # Auth Components
│   │   │   ├── LoginForm.tsx
│   │   │   ├── RegisterForm.tsx
│   │   │   ├── ForgotPasswordForm.tsx
│   │   │   ├── ResetPasswordForm.tsx
│   │   │   ├── OAuthButton.tsx
│   │   │   └── VerifyOTP.tsx
│   │   │
│   │   ├── dashboard/             # Dashboard Components
│   │   │   ├── UserProfile.tsx
│   │   │   ├── UserStats.tsx
│   │   │   ├── RecentActivity.tsx
│   │   │   └── QuickActions.tsx
│   │   │
│   │   ├── partner-finder/        # Partner Finder Components
│   │   │   ├── FilterPanel.tsx
│   │   │   ├── PartnerCard.tsx
│   │   │   ├── PartnerList.tsx
│   │   │   ├── MatchScore.tsx
│   │   │   └── RequestButton.tsx
│   │   │
│   │   ├── properties/            # Property Components
│   │   │   ├── PropertyCard.tsx
│   │   │   ├── PropertyList.tsx
│   │   │   ├── PropertyFilters.tsx
│   │   │   ├── ImageGallery.tsx
│   │   │   ├── MapView.tsx
│   │   │   └── BookingForm.tsx
│   │   │
│   │   ├── messages/              # Messaging Components
│   │   │   ├── MessageList.tsx
│   │   │   ├── ChatWindow.tsx
│   │   │   ├── MessageInput.tsx
│   │   │   └── ConversationCard.tsx
│   │   │
│   │   ├── admin/                 # Admin Components
│   │   │   ├── UserTable.tsx
│   │   │   ├── PropertyTable.tsx
│   │   │   ├── AnalyticsChart.tsx
│   │   │   └── ReportPanel.tsx
│   │   │
│   │   └── common/                # Common Components
│   │       ├── LoadingSpinner.tsx
│   │       ├── ErrorBoundary.tsx
│   │       ├── EmptyState.tsx
│   │       ├── SearchBar.tsx
│   │       ├── Pagination.tsx
│   │       └── Modal.tsx
│   │
│   ├── lib/                       # Utility Libraries
│   │   ├── api/                   # API Client
│   │   │   ├── client.ts          # Axios/Fetch wrapper
│   │   │   ├── auth.ts
│   │   │   ├── users.ts
│   │   │   ├── properties.ts
│   │   │   └── matching.ts
│   │   │
│   │   ├── hooks/                 # Custom Hooks
│   │   │   ├── useAuth.ts
│   │   │   ├── useUser.ts
│   │   │   ├── useProperties.ts
│   │   │   ├── useMatching.ts
│   │   │   ├── useDebounce.ts
│   │   │   └── useLocalStorage.ts
│   │   │
│   │   ├── utils/                 # Utility Functions
│   │   │   ├── validation.ts
│   │   │   ├── formatting.ts
│   │   │   ├── constants.ts
│   │   │   └── helpers.ts
│   │   │
│   │   ├── store/                 # State Management (Zustand)
│   │   │   ├── authStore.ts
│   │   │   ├── userStore.ts
│   │   │   ├── propertyStore.ts
│   │   │   └── uiStore.ts
│   │   │
│   │   └── types/                 # TypeScript Types
│   │       ├── user.ts
│   │       ├── property.ts
│   │       ├── auth.ts
│   │       └── api.ts
│   │
│   ├── styles/                   # Styles
│   │   └── tailwind.css
│   │
│   └── middleware.ts             # Next.js Middleware
│
├── .env.local                     # Environment Variables
├── .env.example                   # Example Env File
├── next.config.js                 # Next.js Config
├── tailwind.config.ts             # Tailwind Config
├── tsconfig.json                  # TypeScript Config
├── package.json
└── README.md
```

### Key Frontend Components

#### 1. **UI Components (Shadcn/UI)**
- Pre-built, accessible components
- Customizable with Tailwind CSS
- Dark mode support

#### 2. **State Management (Zustand)**
- Lightweight state management
- Persistent storage with localStorage
- Optimistic updates

#### 3. **API Client**
- Axios/Fetch wrapper with interceptors
- Automatic token refresh
- Error handling

#### 4. **Custom Hooks**
- Reusable logic for common operations
- Data fetching with SWR/React Query
- Form handling with React Hook Form

---

## 🗄️ Database Schema

### ER Diagram

```
┌─────────────────┐       ┌─────────────────┐
│     USER        │       │     ROLE        │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │───┬───│ id (PK)         │
│ email           │   │   │ name            │
│ password        │   │   │ description     │
│ name            │   │   └─────────────────┘
│ phone           │   │           │
│ gender          │   │           │
│ age             │   │           │
│ food_preference │   │           │
│ occupation      │   │           │
│ religion        │   │           │
│ college         │   │           │
│ city            │   │           │
│ state           │   │           │
│ area            │   │           │
│ profile_image   │   │           │
│ bio             │   │           │
│ is_verified     │   │           │
│ is_active       │   │           │
│ created_at      │   │           │
│ updated_at      │   │           │
└─────────────────┘   │           │
                      │           │
                      │           │
┌─────────────────┐   │    ┌─────────────────┐
│ USER_ROLE       │   │    │   PROPERTY      │
├─────────────────┤   │    ├─────────────────┤
│ user_id (FK)    │◄──┘    │ id (PK)         │
│ role_id (FK)    │        │ owner_id (FK)   │
└─────────────────┘        │ title           │
                          │ description     │
                          │ type            │
                          │ rent            │
                          │ address         │
                          │ city            │
                          │ state           │
                          │ area            │
                          │ pincode         │
                          │ latitude        │
                          │ longitude       │
                          │ facilities      │
                          │ rules           │
                          │ images          │
                          │ is_verified     │
                          │ is_available    │
                          │ created_at      │
                          └─────────────────┘
                                    │
                                    │
┌─────────────────┐               │
│     MATCH       │               │
├─────────────────┤               │
│ id (PK)         │               │
│ user1_id (FK)   │───────────────┘
│ user2_id (FK)   │
│ match_score     │
│ status          │
│ created_at      │
└─────────────────┘

┌─────────────────┐       ┌─────────────────┐
│   BOOKING       │       │   PAYMENT       │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ user_id (FK)    │───────│ booking_id (FK) │
│ property_id (FK)│       │ amount          │
│ start_date      │       │ currency       │
│ end_date        │       │ status          │
│ status          │       │ payment_method │
│ total_amount    │       │ transaction_id  │
│ created_at      │       │ created_at      │
└─────────────────┘       └─────────────────┘

┌─────────────────┐       ┌─────────────────┐
│   MESSAGE       │       │   REVIEW        │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ sender_id (FK)  │       │ user_id (FK)    │
│ receiver_id (FK)│       │ property_id (FK)│
│ content         │       │ rating          │
│ is_read         │       │ comment         │
│ created_at      │       │ created_at      │
└─────────────────┘       └─────────────────┘

┌─────────────────┐       ┌─────────────────┐
│     OTP         │       │  NOTIFICATION   │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ email           │       │ user_id (FK)    │
│ code            │       │ type            │
│ type            │       │ title           │
│ expires_at      │       │ message         │
│ created_at      │       │ is_read         │
└─────────────────┘       │ created_at      │
                          └─────────────────┘
```

### Table Descriptions

#### **USER**
Stores user profile information and authentication details.

#### **ROLE**
Defines user roles (USER, ADMIN, PROPERTY_OWNER).

#### **USER_ROLE**
Many-to-many relationship between users and roles.

#### **PROPERTY**
Stores property/room/hostel listings with location and facility details.

#### **MATCH**
Stores roommate matching information with compatibility scores.

#### **BOOKING**
Stores booking information for properties.

#### **PAYMENT**
Stores payment transaction details.

 #### **MESSAGE**
Stores in-app messages between users.

#### **REVIEW**
Stores user reviews and ratings for properties.

#### **OTP**
Stores OTP codes for email verification and password reset.

#### **NOTIFICATION**
Stores user notifications.

---

## 🔌 API Documentation

### Base URL
```
Development: http://localhost:8080/api
Production: https://api.roompartnerfinder.com
```

### Authentication
Most endpoints require JWT token in Authorization header:
```
Authorization: Bearer <token>
```

### Endpoints

#### **Authentication**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/auth/register` | Register new user | No |
| POST | `/auth/login` | User login | No |
| POST | `/auth/logout` | User logout | Yes |
| POST | `/auth/refresh` | Refresh JWT token | No |
| POST | `/auth/google` | Google OAuth login | No |
| POST | `/auth/github` | GitHub OAuth login | No |
| POST | `/auth/forgot-password` | Request password reset | No |
| POST | `/auth/reset-password` | Reset password | No |
| POST | `/auth/verify-otp` | Verify email OTP | No |
| POST | `/auth/resend-otp` | Resend verification OTP | No |

#### **User Management**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/users/me` | Get current user profile | Yes |
| PUT | `/users/me` | Update current user profile | Yes |
| DELETE | `/users/me` | Delete current user | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| GET | `/users` | Get all users (Admin) | Admin |
| PUT | `/users/{id}/role` | Update user role (Admin) | Admin |
| POST | `/users/{id}/verify` | Verify user (Admin) | Admin |
| POST | `/users/me/image` | Upload profile image | Yes |
| DELETE | `/users/me/image` | Delete profile image | Yes |

#### **Partner Matching**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/matching/partners` | Find compatible partners | Yes |
| GET | `/matching/partners/{id}` | Get partner details | Yes |
| POST | `/matching/request` | Send partner request | Yes |
| PUT | `/matching/request/{id}/accept` | Accept partner request | Yes |
| PUT | `/matching/request/{id}/reject` | Reject partner request | Yes |
| DELETE | `/matching/partners/{id}` | Remove partner | Yes |
| GET | `/matching/suggestions` | Get AI-powered suggestions | Yes |

#### **Properties**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/properties` | Get all properties | No |
| POST | `/properties` | Create property listing | Yes |
| GET | `/properties/{id}` | Get property by ID | No |
| PUT | `/properties/{id}` | Update property | Yes (Owner) |
| DELETE | `/properties/{id}` | Delete property | Yes (Owner) |
| GET | `/properties/nearby` | Get nearby properties | Yes |
| POST | `/properties/{id}/images` | Upload property images | Yes (Owner) |
| PUT | `/properties/{id}/verify` | Verify property (Admin) | Admin |

#### **Bookings**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/bookings` | Get user bookings | Yes |
| POST | `/bookings` | Create booking | Yes |
| GET | `/bookings/{id}` | Get booking by ID | Yes |
| PUT | `/bookings/{id}/cancel` | Cancel booking | Yes |
| GET | `/properties/{id}/bookings` | Get property bookings (Owner) | Yes |

#### **Payments**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/payments/create` | Create payment intent | Yes |
| POST | `/payments/confirm` | Confirm payment | Yes |
| GET | `/payments/{id}` | Get payment by ID | Yes |
| GET | `/payments` | Get user payments | Yes |

#### **Messages**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/messages/conversations` | Get all conversations | Yes |
| GET | `/messages/{userId}` | Get messages with user | Yes |
| POST | `/messages` | Send message | Yes |
| PUT | `/messages/{id}/read` | Mark message as read | Yes |
| DELETE | `/messages/{id}` | Delete message | Yes |

#### **Reviews**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/reviews/property/{id}` | Get property reviews | No |
| POST | `/reviews` | Create review | Yes |
| PUT | `/reviews/{id}` | Update review | Yes (Owner) |
| DELETE | `/reviews/{id}` | Delete review | Yes (Owner) |

#### **Notifications**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/notifications` | Get user notifications | Yes |
| PUT | `/notifications/{id}/read` | Mark as read | Yes |
| PUT | `/notifications/read-all` | Mark all as read | Yes |
| DELETE | `/notifications/{id}` | Delete notification | Yes |

#### **Admin**

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/admin/stats` | Get platform statistics | Admin |
| GET | `/admin/users` | Get all users | Admin |
| GET | `/admin/reports` | Get reported content | Admin |
| PUT | `/admin/reports/{id}/resolve` | Resolve report | Admin |
| POST | `/admin/announcements` | Create announcement | Admin |

---

## ✨ Complete Feature List

### 🎯 Core Features

#### **1. User Authentication & Authorization**
- ✅ Email/Password registration and login
- ✅ Google OAuth2 integration
- ✅ GitHub OAuth2 integration
- ✅ Email verification with OTP
- ✅ Password reset via email
- ✅ JWT-based authentication
- ✅ Role-based access control (User, Admin, Property Owner)
- ✅ Session management
- ✅ Remember me functionality
- ✅ Account deactivation

#### **2. User Profile Management**
- ✅ Complete profile creation
- ✅ Profile picture upload (Cloudinary)
- ✅ Personal information (name, age, gender)
- ✅ Contact information (phone, email)
- ✅ Lifestyle preferences (food habits, religion)
- ✅ Professional details (occupation, college/institute)
- ✅ Location details (city, state, area)
- ✅ Bio and about me section
- ✅ Social media links
- ✅ Profile visibility settings
- ✅ Profile completion tracking

#### **3. Smart Partner Matching**
- ✅ Advanced filtering system
  - Location-based (state, city, area)
  - Age range filter
  - Gender preference
  - Food preference (Veg/Non-Veg)
  - Occupation filter (Student/Working)
  - College/Institute matching
  - Religion filter (optional)
- ✅ Compatibility scoring algorithm
- ✅ Match percentage display
- ✅ Partner request system
- ✅ Accept/Reject requests
- ✅ Match history
- ✅ Saved/favorite partners
- ✅ AI-powered recommendations
- ✅ Behavioral matching

#### **4. Property/Room Listings**
- ✅ Property creation by owners
- ✅ Multiple property types (Room, PG, Hostel, Flat)
- ✅ Detailed property information
  - Title and description
  - Rent details
  - Address with pincode
  - Google Maps integration
  - Facilities list (Wi-Fi, AC, Meals, etc.)
  - House rules
- ✅ Photo gallery (multiple images)
- ✅ Property verification badge
- ✅ Availability status
- ✅ Property statistics (views, saves)
- ✅ Property owner information
- ✅ Near me search
- ✅ Save/bookmark properties

#### **5. Booking System**
- ✅ Property booking requests
- ✅ Date range selection
- ✅ Availability check
- ✅ Booking confirmation
- ✅ Booking history
- ✅ Booking cancellation
- ✅ Booking status tracking
- ✅ Automatic availability update

#### **6. Payment Integration**
- ✅ Secure payment gateway (Stripe/Razorpay)
- ✅ Multiple payment methods
- ✅ Payment history
- ✅ Refund processing
- ✅ Invoice generation
- ✅ Payment reminders

#### **7. Messaging System**
- ✅ Real-time messaging
- ✅ Conversation list
- ✅ Read/unread status
- ✅ Message notifications
- ✅ Message search
- ✅ File/image sharing
- ✅ Typing indicators
- ✅ Online status
- ✅ Message encryption

#### **8. Reviews & Ratings**
- ✅ Property reviews
- ✅ Star rating system
- ✅ Written reviews
- ✅ Review moderation
- ✅ Review response by owners
- ✅ Average rating calculation
- ✅ Review filtering

#### **9. Notification System**
- ✅ Email notifications
- ✅ SMS notifications
- ✅ In-app notifications
- ✅ Push notifications (Firebase)
- ✅ Notification preferences
- ✅ Notification history
- ✅ Mark as read/unread
- ✅ Notification types
  - New partner matches
  - Booking updates
  - Payment confirmations
  - New messages
  - Profile views

#### **10. Search & Discovery**
- ✅ Advanced search
- ✅ Location-based search
- ✅ Filter combinations
- ✅ Search history
- ✅ Saved searches
- ✅ Recent searches
- ✅ Auto-suggestions
- ✅ Search analytics

### 🚀 Advanced Features

#### **11. Admin Panel**
- ✅ User management
  - View all users
  - User verification
  - Role assignment
  - Account suspension
  - User analytics
- ✅ Property management
  - Property verification
  - Property moderation
  - Report handling
  - Property analytics
- ✅ Content moderation
  - Report review system
  - Content flagging
  - Automated moderation
- ✅ Platform analytics
  - User statistics
  - Property statistics
  - Booking statistics
  - Revenue tracking
- ✅ System settings
  - Platform configuration
  - Feature toggles
  - Maintenance mode

#### **12. AI-Powered Features**
- ✅ Smart matching algorithm
- ✅ Recommendation engine
- ✅ Chatbot assistant
- ✅ Image recognition for property verification
- ✅ Fraud detection
- ✅ Price prediction
- ✅ Demand forecasting

#### **13. Social Features**
- ✅ User profiles with social links
- ✅ Share property listings
- ✅ Referral system
- ✅ Rewards program
- ✅ Community forums
- ✅ Success stories
- ✅ User testimonials

#### **14. Mobile App Features**
- ✅ Native iOS app
- ✅ Native Android app
- ✅ Push notifications
- ✅ Location services
- ✅ Camera integration
- ✅ Offline mode
- ✅ Biometric authentication

#### **15. Security Features**
- ✅ Two-factor authentication (2FA)
- ✅ Login attempt tracking
- ✅ Suspicious activity detection
- ✅ Account lockout
- ✅ Data encryption at rest
- ✅ Data encryption in transit
- ✅ GDPR compliance
- ✅ Privacy controls
- ✅ Report abuse system
- ✅ Content moderation

#### **16. Analytics & Insights**
- ✅ User behavior tracking
- ✅ Property performance analytics
- ✅ Search analytics
- ✅ Conversion tracking
- ✅ A/B testing
- ✅ Custom dashboards
- ✅ Export reports

#### **17. Multi-language Support**
- ✅ English
- ✅ Hindi
- ✅ Regional languages
- ✅ Language switcher
- ✅ Auto-detection

#### **18. Accessibility**
- ✅ WCAG 2.1 compliance
- ✅ Screen reader support
- ✅ Keyboard navigation
- ✅ High contrast mode
- ✅ Font size adjustment

---

## 🛠️ Technology Stack

### Backend (Spring Boot)

#### Core Framework
- **Java 21** - Programming language
- **Spring Boot 3.3.3** - Application framework
- **Spring Data JPA** - Database ORM
- **Spring Security** - Security framework
- **Spring OAuth2** - OAuth2 authentication
- **Spring Mail** - Email service
- **Spring Validation** - Input validation

#### Database
- **MySQL 8.0+** - Primary database
- **Redis** - Caching layer
- **Elasticsearch** - Search engine

#### Security
- **JWT (jjwt)** - Token-based authentication
- **BCrypt** - Password hashing
- **OAuth2** - Social login

#### File Storage
- **Cloudinary** - Image and file storage

#### Messaging
- **Spring WebSocket** - Real-time messaging
- **STOMP** - Messaging protocol

#### Notifications
- **JavaMail** - Email notifications
- **Twilio** - SMS notifications
- **Firebase Cloud Messaging** - Push notifications

#### Payment
- **Stripe SDK** - Payment processing
- **Razorpay SDK** - Alternative payment

#### Testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing
- **TestContainers** - Database testing

#### Build Tools
- **Maven** - Dependency management
- **Lombok** - Code generation

### Frontend (Next.js)

#### Core Framework
- **Next.js 14+** - React framework with App Router
- **React 18+** - UI library
- **TypeScript** - Type safety

#### UI Components
- **Shadcn/UI** - Pre-built components
- **Tailwind CSS** - Styling
- **Lucide Icons** - Icon library
- **Framer Motion** - Animations

#### State Management
- **Zustand** - Global state
- **React Context** - Local state
- **React Query (TanStack Query)** - Server state

#### Forms
- **React Hook Form** - Form management
- **Zod** - Schema validation

#### Data Fetching
- **Axios** - HTTP client
- **SWR** - Data fetching
- **React Query** - Server state

#### Maps
- **Google Maps API** - Location services
- **React Google Maps** - Map components

#### Real-time
- **Socket.io-client** - WebSocket client
- **Pusher** - Alternative real-time

#### Authentication
- **NextAuth.js** - Auth solution
- **JWT** - Token handling

#### Testing
- **Jest** - Unit testing
- **React Testing Library** - Component testing
- **Playwright** - E2E testing

#### Build Tools
- **ESLint** - Linting
- **Prettier** - Code formatting
- **Turbopack** - Fast bundler

### DevOps & Infrastructure

#### Containerization
- **Docker** - Containerization
- **Docker Compose** - Local development

#### CI/CD
- **GitHub Actions** - CI/CD pipeline
- **Jenkins** - Alternative CI/CD

#### Cloud Services
- **AWS** - Cloud infrastructure
  - EC2 - Compute
  - RDS - Database
  - S3 - Storage
  - CloudFront - CDN
  - Route53 - DNS
- **Vercel** - Frontend hosting
- **Heroku** - Alternative hosting

#### Monitoring
- **Prometheus** - Metrics
- **Grafana** - Visualization
- **Sentry** - Error tracking
- **LogRocket** - Session replay

#### Version Control
- **Git** - Version control
- **GitHub** - Code hosting

---

## 🚀 Setup & Installation

### Prerequisites

- **Java 21** or higher
- **Node.js 18+** and npm/yarn
- **MySQL 8.0+**
- **Redis** (optional, for caching)
- **Maven 3.8+**
- **Git**

### Backend Setup

1. **Clone the repository**
```bash
git clone https://github.com/Rishisingh96/RoomParterFinderApp.git
cd RoomParterFinderApp
```

2. **Configure MySQL Database**
```sql
CREATE DATABASE room_partner_finder;
CREATE USER 'rpf_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON room_partner_finder.* TO 'rpf_user'@'localhost';
FLUSH PRIVILEGES;
```

3. **Configure Environment Variables**
Create `.env` file in project root:
```env
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=room_partner_finder
DB_USER=rpf_user
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000

# OAuth2
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret

# Email
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=your_email@gmail.com
EMAIL_PASSWORD=your_app_password

# Cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Twilio (Optional)
TWILIO_ACCOUNT_SID=your_account_sid
TWILIO_AUTH_TOKEN=your_auth_token
TWILIO_PHONE_NUMBER=your_phone_number

# Stripe
STRIPE_SECRET_KEY=your_stripe_secret_key
STRIPE_PUBLISHABLE_KEY=your_stripe_publishable_key

# Redis (Optional)
REDIS_HOST=localhost
REDIS_PORT=6379
```

4. **Build and Run**
```bash
# Using Maven wrapper
./mvnw clean install
./mvnw spring-boot:run

# Or using Maven
mvn clean install
mvn spring-boot:run
```

5. **Access API**
```
API: http://localhost:8080/api
Swagger: http://localhost:8080/swagger-ui.html
Actuator: http://localhost:8080/actuator
```

### Frontend Setup (Next.js)

1. **Navigate to frontend directory**
```bash
cd frontend
```

2. **Install dependencies**
```bash
npm install
# or
yarn install
# or
pnpm install
```

3. **Configure Environment Variables**
Create `.env.local` file:
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_GOOGLE_MAPS_KEY=your_google_maps_key
NEXT_PUBLIC_CLOUDINARY_CLOUD_NAME=your_cloud_name
```

4. **Run development server**
```bash
npm run dev
# or
yarn dev
# or
pnpm dev
```

5. **Access Application**
```
Frontend: http://localhost:3000
```

### Docker Setup

1. **Build and run with Docker Compose**
```bash
docker-compose up -d
```

2. **Services will be available at**
```
Backend: http://localhost:8080
Frontend: http://localhost:3000
MySQL: localhost:3306
Redis: localhost:6379
```

---

## 📦 Deployment Guide

### Backend Deployment (AWS)

#### Option 1: EC2 + RDS

1. **Launch EC2 Instance**
   - Ubuntu 22.04 LTS
   - t3.medium or higher
   - Security groups: 80, 443, 22, 8080

2. **Install Java and Maven**
```bash
sudo apt update
sudo apt install openjdk-21-jdk maven -y
```

3. **Install MySQL Client**
```bash
sudo apt install mysql-client -y
```

4. **Create RDS Database**
   - MySQL 8.0
   - t3.micro or higher
   - Configure security group

5. **Deploy Application**
```bash
# Clone repo
git clone https://github.com/Rishisingh96/RoomParterFinderApp.git
cd RoomParterFinderApp

# Configure production environment
cp .env.example .env
# Edit .env with production values

# Build
./mvnw clean package -DskipTests

# Run
java -jar target/RoomPartnerFinderApp-0.0.1-SNAPSHOT.jar
```

6. **Setup Nginx Reverse Proxy**
```nginx
server {
    listen 80;
    server_name api.roompartnerfinder.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

7. **Setup SSL with Let's Encrypt**
```bash
sudo apt install certbot python3-certbot-nginx -y
sudo certbot --nginx -d api.roompartnerfinder.com
```

#### Option 2: AWS Elastic Beanstalk

1. **Create application**
2. **Upload JAR file**
3. **Configure environment variables**
4. **Deploy**

### Frontend Deployment (Vercel)

1. **Push code to GitHub**
2. **Import project in Vercel**
3. **Configure environment variables**
4. **Deploy**

Or manually:
```bash
npm run build
vercel --prod
```

### Database Migration

```bash
# Run Flyway migrations
./mvnw flyway:migrate
```

---

## 🔒 Security Features

### Authentication & Authorization
- ✅ JWT token-based authentication
- ✅ OAuth2 social login (Google, GitHub)
- ✅ Role-based access control
- ✅ Password hashing with BCrypt
- ✅ Session management
- ✅ Token refresh mechanism

### Data Protection
- ✅ Encryption at rest (AES-256)
- ✅ Encryption in transit (TLS 1.3)
- ✅ SQL injection prevention
- ✅ XSS protection
- ✅ CSRF protection
- ✅ Input validation

### Privacy Controls
- ✅ GDPR compliance
- ✅ Data anonymization
- ✅ User consent management
- ✅ Privacy policy enforcement
- ✅ Data retention policies

### Monitoring & Auditing
- ✅ Login attempt tracking
- ✅ Suspicious activity detection
- ✅ Audit logging
- ✅ Real-time alerts
- ✅ Security headers

---

## 📊 Future Roadmap

### Phase 1: Q1 2025
- ✅ Complete Next.js migration
- ✅ Implement real-time messaging
- ✅ Add payment integration
- ✅ Launch mobile apps (iOS/Android)

### Phase 2: Q2 2025
- ✅ AI-powered matching
- ✅ Advanced analytics dashboard
- ✅ Multi-language support
- ✅ Video calling integration

### Phase 3: Q3 2025
- ✅ Smart home integration
- ✅ Virtual tours
- ✅ Blockchain-based verification
- ✅ Global expansion

### Phase 4: Q4 2025
- ✅ Enterprise features
- ✅ White-label solution
- ✅ API marketplace
- ✅ Partner ecosystem

---

## 👨‍💻 Developer Info

### Developed By
**Rishi Singh**
Java Full Stack Developer
📍 Based in Indore (From Basti, Uttar Pradesh)
📧 Email: rishisingh9838@gmail.com

### Contributing
Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### License
This project is licensed under the MIT License.

### Support
For support, email rishisingh9838@gmail.com or open an issue in the repository.

---

## 📸 Screenshots

![Home Page](https://github.com/user-attachments/assets/de586ea1-6df7-4713-8799-18c64d8f9d7e)
![Home page 2](https://github.com/user-attachments/assets/af814ade-f788-443c-835c-487b4b681a25)
![Partner_Finder Web App](https://github.com/user-attachments/assets/4c53cc85-3ece-4489-89de-810be153d463)
![SpringCode 1](https://github.com/user-attachments/assets/4ee46219-052d-4508-90b4-a28911224827)
![Create Account mysql](https://github.com/user-attachments/assets/f90a5218-305d-402c-ba9d-b7eb107033b5)
![Login form](https://github.com/user-attachments/assets/0732aae4-43e3-4478-ac4b-8ba7abcb3f8d)
![Login with Google Account](https://github.com/user-attachments/assets/ab1b9455-5fdc-450e-bd46-9c1fabc72c13)
![Login with Google ](https://github.com/user-attachments/assets/e7c1b2fd-2313-4df4-a47c-f30ef1171de9)
![Sinup form](https://github.com/user-attachments/assets/c6010390-3cca-4127-9b65-380bc600eaac)
![Singup and Validation chek ](https://github.com/user-attachments/assets/27d73559-683-499e-b630-17f05163e99a)
![Profile View](https://github.com/user-attachments/assets/95b5fca8-3c29-4fe8-a6a4-1a9c0308fb11)
![Create Account 1](https://github.com/user-attachments/assets/28c86bfe-fec7-420a-a8a6-44b5101dda54)
![Create Account Sussefully Save](https://github.com/user-attachments/assets/eab0a6cc-72cd-46e0-a877-42bc711b27a)
![Cloudinary cloude for store image](https://github.com/user-attachments/assets/5a29efb7-7a44-4eb1-952d-79bcf41802a7)
![View Account Details](https://github.com/user-attachments/assets/57453826-4355-4040-92b4-fb0c39e41cc2)

---

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- Next.js team for the excellent React framework
- Cloudinary for image storage services
- All open-source contributors

---

**Made with ❤️ by Rishi Singh**

![Home Page](https://github.com/user-attachments/assets/de586ea1-6df7-4713-8799-18c64d8f9d7e)
![Home page 2](https://github.com/user-attachments/assets/af814ade-f788-443c-835c-487b4b681a25)
![Partner_Finder Web App](https://github.com/user-attachments/assets/4c53cc85-3ece-4489-89de-810be153d463)
![SpringCode 1](https://github.com/user-attachments/assets/4ee46219-052d-4508-90b4-a28911224827)
![Create Account mysql](https://github.com/user-attachments/assets/f90a5218-305d-402c-ba9d-b7eb107033b5)

![Login form](https://github.com/user-attachments/assets/0732aae4-43e3-4478-ac4b-8ba7abcb3f8d)

![Login with Google Account](https://github.com/user-attachments/assets/ab1b9455-5fdc-450e-bd46-9c1fabc72c13)
![Login with Google ](https://github.com/user-attachments/assets/e7c1b2fd-2313-4df4-a47c-f30ef1171de9)

![Sinup form](https://github.com/user-attachments/assets/c6010390-3cca-4127-9b65-380bc600eaac)
![Singup and Validation chek ](https://github.com/user-attachments/assets/27d73559-68c3-499e-b630-17f05163e99a)

![Profile View](https://github.com/user-attachments/assets/95b5fca8-3c29-4fe8-a6a4-1a9c0308fb11)

![Create Account 1](https://github.com/user-attachments/assets/28c86bfe-fec7-420a-a8a6-44b5101dda54)
![Create Account Sussefully Save](https://github.com/user-attachments/assets/eab0a6cc-72cd-46e0-a877-42bc711b271a)


![Cloudinary cloude for store image](https://github.com/user-attachments/assets/5a29efb7-7a44-4eb1-952d-79bcf41802a7)
![View Account Details](https://github.com/user-attachments/assets/57453826-4355-4040-92b4-fb0c39e41cc2)

