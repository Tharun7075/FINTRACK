# FINTRACK

### Identification of Risky Components
The following features, while beneficial for enhancing the application's functionality, may introduce additional complexity or require careful planning. Each component is described below along with its associated risks and suggested mitigation strategies:
•	Savings Goal Tracker: Tracking progress dynamically requires real-time updates and user feedback mechanisms. A simplified version can include static goal input and a basic progress bar to visualize savings.
•	Bank API Integration: Involves secure external API access and user consent. Each of these features has been documented with mitigation strategies and will be considered for phased implementation.
•	JWT Authentication: This method requires secure token handling and refresh logic, which can be technically demanding. Developers may opt to use Spring Security with guided tutorials or simplify the login process initially.
•	Predictive Budget Analytics: Integrating forecasting models or machine learning introduces significant complexity. A practical approach is to begin with basic alert systems and gradually incorporate predictive capabilities in later phases.
•	Recurring Expenses: Automating monthly entries involves scheduling logic and reliable background processing. As a starting point, manual flags and reminders can be used to simulate recurring behavior.
These components should be considered as potential enhancements for future development milestones. Prioritizing simplicity and core functionality during the initial release will help ensure a stable and manageable product foundation.

### Use of an Outside API
As of now, we are not using any APIs as the bank account connectivity needs to be handled with the consent of the users. If we had to, the project could integrate the Plaid API in future development phases to enable secure bank account connectivity and transaction import. 
4. Functionality We Will Not Cover
The following features are outside the scope of the current milestone and will not be implemented at this stage:
•	Search and Filter Transactions: Efficient query handling and responsive UI updates are essential for this feature. Initial implementation can leverage RecyclerView with basic filtering and search functionality.
•	Maps Integration: For visualizing spending by location or identifying nearby financial services.
•	Advanced Machine Learning Models for Budget Prediction These features are considered valuable but will be deferred to avoid overextension and ensure delivery of a stable MVP.

### Functionality We Will Cover Later
Certain components will be introduced in later phases of development, once the foundational system is stable:
•	Multi-language Support: Localization and translation file management can be resource-intensive. It is recommended to launch with English and expand language support incrementally based on user demand.
•	Media Handling: For receipt uploads or voice-based transaction entry.
•	Database Services: Migration to cloud-based storage and real-time syncing will be explored after MVP validation.

### Team Contributions to Repository
Each team member has made clearly defined contributions to the project repository, as outlined below:
•	Viswas: Developed the Login and Signup pages and UI structure.
•	Vipul: Created the Home page and implemented the navigation bar for seamless layout transitions.
•	Tharun: Designed and structured the Budget page and Records page
•	Siddharth: Built the Accounts page and Analysis page, integrating visual placeholders for future data visualization.
All contributions are reflected in the commit history of the repository, in accordance with the repo workflow guidelines.
