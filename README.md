# Task Master
* This app has a home page, a add task page that allows you to add tasks, and a all task page to look at the tasks you have added.
* It also has a settings page where you can save your username, and a task detail page where you can look at your tasks in more detail.

## Daily Change Log:
### Lab 26 Beginning TaskMaster
    * Setup
        - To start, create a new directory and repo to hold this app. Name it taskmaster.
        - Within that directory, use Android Studio to set up a new app, as discussed in class.
        - Create a README file that includes, at minimum, a description of your app and a daily change log.
    * Feature Tasks
        - Homepage
            * The main page should be built out to match the wireframe.
            * In particular, it should have a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.
        - Add a Task
            * On the “Add a Task” page, allow users to type in details about a new task, specifically a title and a body.
            * When users click the “submit” button, show a “submitted!” label on the page.
        - All Task
            * The all tasks page should just be an image with a back button; it needs no functionality.
    * Documentation
        - Create a directory called screenshots in the root of your project. Take a screenshot of the homepage you’ve created.
        - Use markdown to render the screenshot in your README.
    * Testing
        - In a future lecture, we’ll talk about how to test Android UI using Espresso.
        - For now, ensure that you’re writing good unit tests for anything unit-testable in your code.
    * Screenshots
        - ![Home Page](screenshots/home-page.jpeg)
        - ![Add A Task Page](screenshots/add-task.jpeg)
        - ![All Tasks Page](screenshots/all-tasks.jpeg)

### Lab 27 Data in TaskMaster
    * Feature Tasks
        - Task Detail Page
          * Create a Task Detail page. It should have a title at the top of the page, and a Lorem Ipsum description.
        - Settings Page
          * Create a Settings page. It should allow users to enter their username and hit save.
        - Homepage
          * The main page should be modified to contain three different buttons with hardcoded task titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page.
          * The homepage should also contain a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.
    * Screenshots
        - ![Home Page](screenshots/home-page-version-2.jpeg)
        - ![Task Detail Page](screenshots/task-detail.jpeg)
        - ![Settings Page](screenshots/settings.jpeg)

### Lab 28 RecyclerView
    * Feature Tasks
        - Task Model
         * Create a Task class. A Task should have a title, a body, and a state.
         * The state should be one of “new”, “assigned”, “in progress”, or “complete”.
        - Homepage
         * Refactor your homepage to use a RecyclerView for displaying Task data. This should have hardcoded Task data for now.
         * Some steps you will likely want to take to accomplish this:
          - Create a ViewAdapter class that displays data from a list of Tasks.
          - In your MainActivity, create at least three hardcoded Task instances and use those to populate your RecyclerView/ViewAdapter.
          - Ensure that you can tap on any one of the Tasks in the RecyclerView, and it will appropriately launch the detail page with the correct Task title displayed.
    * Screenshots
            - ![Home Page](screenshots/home-page-version-3.jpeg)
            - ![Fragment Task](screenshots/fragment-task.jpeg)
            - ![Recycler View](screenshots/recycler-view.jpeg)