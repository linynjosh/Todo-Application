# My Personal Project

## Personal Planner App


This is a personal planner app that manages *all* kinds of different 
tasks by categorizing them according to their corresponding due dates and/or tags.
To help notify the user of his/her daily tasks, the app gives **daily reminders** of what 
is due at the end of the day. 
This app is perfect for:
- Scheduling homework and studies
- Organizing workout and training routines
- Outlining daily tasks
- Planning a vacation!
- Checking the weather
- Reading Top News Headlines

Basically, anybody can find this To-Do app helpful. I am interested in this project
because I have always wanted my UBC canvas calendar to have more functionalities. I like how the way 
its due dates are color coded based on the course, however, it would be nice if it can be customized to fulfill
personal accommodations. As an example, I have a habit of going to the math centre on Tuesdays, and I would like
to have that on my calendar in addition to some other tasks. 


## User Stories
 - As a user, I want to be able to add a task to my to-do list
 - As a user, I want to be able to remove a task off my to-do list
 - As a user, I want to check off tasks from my to-do list
 - As a user, I want to add different tasks based on the level of urgency
 - As a user, I want set due dates for my tasks
 - As a user, I want to see which tasks are complete while which ones are not
 - As a user, I want to have reminders showing me what is due at the end of the day
 - As a user, I want to know the weather to plan my workouts accordingly.
 - As a user, I want to read the top news headlines of the day. 
 - As a user, I want to be able to save my to-do list to file
 - As a user, I want to be able to load my to-do list from file
 
 ## Phase 4: Task 2
 User stories I have chosen to implement：
  - **As a user, I want to be able to add a task to my to-do list**
  （The classes involved with this user 
   story include the abstract Task class, including its subclasses: RegularTask, UrgentTask,
   and OngoingTask, and the TodoList class. The method for this user story is the addTodo() 
   method which adds a Task to the TodoList.）
  - **As a user, I want to be able to remove a task off my to-do list**
  （The classes involved with this user 
   story include the abstract Task class, including its subclasses: RegularTask, UrgentTask,
   and OngoingTask, and the TodoList class. The method for this user story is the removeTask() 
   method which removes a Task from the TodoList.）
  - **As a user, I want to check off tasks from my to-do list**
  （The classes involved with this user 
   story include the abstract Task class, including its subclasses: RegularTask, UrgentTask,
   and OngoingTask, and the TodoList class. The method for this user story is the completeTask() 
   method which checks off a Task from the TodoList and transfers the Task to the list of completed Tasks.）
  - **As a user, I want to add different tasks based on the level of urgency**
  （The classes involved with this user 
   story include the Urgency class, the abstract Task class and its subclasses, and the TodoList class. 
   The apparent type of Task would be Task, while the actual
   type of Task is one of: RegularTask, UrgentTask, and OngoingTask. The level of urgency will be chosen from the 
   Enum Urgency class based on the instantiated type, and the constructor that class sets the urgency accordingly.）
  - **As a user, I want set due dates for my tasks**
  (The type Task hierarchy is introduced during the implementation of this user story. The due date of a Task differs
  according to its urgency. For example, an UrgentTask would be due at the end of the day, while an OngoingTask 
  would have no due date and would be included on the TodoList as more of a mental note. Since the due dates are different 
  for each Task according to its level of urgency, there is an override setDueDate() method within each of RegularTask, 
  UrgentTask, and OngoingTask.)
  - **As a user, I want to see which tasks are complete while which ones are not**
  (In  the TodoList class there are three different lists of tasks. The first list is the 
  todo list, the second is a completed list, and the third is a list of reminders.)
  - **As a user, I want to have reminders showing me what is due at the end of the day**
  (In  the TodoList class there are three different lists of tasks. The first list is the 
  todo list, the second is a completed list, and the third is a list of reminders.)
  - **As a user, I want to know the weather to plan my workouts accordingly.**
  (The WeatherApp reads the weather and weather icon off of https://openweathermap.org as a string.)
  - **As a user, I want to be able to save my to-do list to file**
  (The JsonWriter writes the todo list as a Json file and saves it at the specified file destination)
  - **As a user, I want to be able to load my to-do list from file**
  (The JsonReader reads in the todo list from Json file from the specified source file)

 ## Phase 4: Task 3
 In summary, the classes in my code are built upon the single-responsibility principle, thus there is no need to delegate 
 functionalities into separate classes to increase cohesion. However, I would refactor in order to reduce duplicate
 codes and increase readability. I noticed there are numerous duplicate codes within my TodoTaskList, 
 RemindersTaskList, and the CompletedTaskList classes. If I had more time I would create an abstract class  
 extending JPanel, and refactor all the duplicate codes from TodoTaskList, RemindersTaskList, and 
 CompletedTaskList into that abstract class to reduce coupling. As for readability, there are methods within classes such as SetDueDate, 
 SelectTaskUrgency, WeatherApp, etc that have lengthy methods that can be refactored into smaller ones. This will 
 capture the behaviour of the methods more concisely over time. 
