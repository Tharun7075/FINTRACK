# FinTrack – Personal Finance Tracker

FinTrack is a personal finance management application designed to help users track daily expenses, manage budgets, and monitor account balances all in one place.
The app provides clear insights into spending habits and supports smart financial decision-making through intuitive dashboards and analytics.

### Official Project URL

[GitHub Repository: ](https://github.com/Tharun7075/FINTRACK) https://github.com/Tharun7075/FINTRACK

## Identification of Risky Components
The following features, while beneficial for enhancing the application's functionality, may introduce additional complexity or require careful planning. Each component is described below along with its associated risks and suggested mitigation strategies:

- **Savings Goal Tracker**: Tracking progress dynamically requires real-time updates and user feedback mechanisms. A simplified version can include static goal input and a basic progress bar to visualize savings.
- **Bank API Integration**: Involves secure external API access and user consent. Each of these features has been documented with mitigation strategies and will be considered for phased implementation.
- **JWT Authentication**: This method requires secure token handling and refresh logic, which can be technically demanding. Developers may opt to use Spring Security with guided tutorials or simplify the login process initially.
- **Predictive Budget Analytics**: Integrating forecasting models or machine learning introduces significant complexity. A practical approach is to begin with basic alert systems and gradually incorporate predictive capabilities in later phases.
- **Recurring Expenses**: Automating monthly entries involves scheduling logic and reliable background processing. As a starting point, manual flags and reminders can be used to simulate recurring behavior.

These components should be considered as potential enhancements for future development milestones. Prioritizing simplicity and core functionality during the initial release will help ensure a stable and manageable product foundation.

---
## Use of an Outside API
As of now, we are not using any APIs as the bank account connectivity needs to be handled with the consent of the users. If we had to, the project could integrate the Plaid API in future development phases to enable secure bank account connectivity and transaction import.

---
## Functionality We Will Not Cover
The following features are outside the scope of the current milestone and will not be implemented at this stage:

- **Search and Filter Transactions**: Efficient query handling and responsive UI updates are essential for this feature. Initial implementation can leverage RecyclerView with basic filtering and search functionality.
- **Maps Integration**: For visualizing spending by location or identifying nearby financial services.
- **Advanced Machine Learning Models for Budget Prediction**: These features are considered valuable but will be deferred to avoid overextension and ensure delivery of a stable MVP.

---
## Functionality We Will Cover Later
Certain components will be introduced in later phases of development, once the foundational system is stable:

- **Multi-language Support**: Localization and translation file management can be resource-intensive. It is recommended to launch with English and expand language support incrementally based on user demand.
- **Media Handling**: For receipt uploads or voice-based transaction entry.
- **Database Services**: Migration to cloud-based storage and real-time syncing will be explored after MVP validation.

---
## Milestone 3 Enhancements
In this milestone, we focused on strengthening the **backend and database integration** while polishing the **UI/UX**:

- **Database Integration**: Connected the app to a local SQLite database for transaction storage. This ensures persistence of user data across sessions and lays the groundwork for future migration to cloud-based services.
- **Improved UI Consistency**: Enhanced color schemes, typography, and spacing for a more professional look. Navigation flows were streamlined to reduce user friction.
- **Functional Expansion**:
  - Added validation for login and signup forms.
  - Implemented basic transaction recording with database persistence.
  - Introduced placeholder analytics charts to visualize spending trends.
- **Error Handling**: Added user-friendly error messages and fallback states to improve reliability.

---
## Application Overview

Many users struggle with managing expenses, budgeting effectively, and keeping track of multiple accounts. FinTrack solves these issues by offering:
* Expense tracking with categorized transactions
* Monthly budget setting and monitoring
* Spending analysis through charts
* Account balance overviews
* Predictive insights into overspending

This ensures users maintain better control over their finances.

## Use Cases (as defined in the project proposal)
1. User Authentication (Sign Up / Login)

* Users can create an account using email and password.
* Simple and secure authentication.
* Login provides access to a personalized financial dashboard.

2. Dashboard (Home Page)

Provides a summary of:
* Total expenses
* Budget progress
* Investment/financial overview
* Includes quick navigation to Expenses, Analysis, Budgets, and Accounts.

3. Analysis Page (Expense Tracking & Visuals)

* Users can add categorized expenses.
* Filters by category, date, and amount.
* Charts (pie/bar) show spending distribution.

4. Budget Page

* Users set monthly budgets per category.
* System shows visual progress bars.
* Alerts at 80% and 100% usage.

5. Accounts Page

Displays balances of:
* Cash
* Debit Card
* Savings
* Consolidated view of financial status
* Editable account details

## Test Login Credentials

Use the following credentials to log in as a pre-registered user:
- Username: test1@fintrack.com
- Password: 123456

These credentials should be included in the app’s seeded or test data.

## Completion Status (Based on Project Proposal)
| Features                          | Status    |
|-----------------------------------|-----------|
| User Sign-Up & Login              | Completed |
| Dashboard Summary                 | Completed |
| Expense Tracking & Categorization | Completed |
| Analysis Page Charts              | Completed |
| Budget Creation & Alerts          | Completed |
| Prediction for Overspending       | Completed |
| Accounts Overview                 | Completed |
| UI/UX Enhancements                | Completed |
| Test User Credentials             | Added     |

## Team Contributions to Repository
Each team member has made clearly defined contributions to the project repository, as outlined below:

- **Viswas**: Developed the Login and Signup pages, integrated database connectivity for user authentication, and refined UI structure.
- **Vipul**: Created the Home page, improved navigation bar styling, and ensured smooth layout transitions.
- **Tharun**: Designed and structured the Budget page and Records page, added database hooks for transaction persistence.
- **Siddharth**: Built the Accounts page and Analysis page, integrating visual placeholders for future data visualization and refining chart components.

All contributions are reflected in the commit history of the repository, in accordance with the repo workflow guidelines.
