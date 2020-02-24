

def enterNewTask(list):
    task = ""
    category = ""
    while True:
        task = input("Enter the new task:")

        if task.isalpha():
            break
    
    while True:
        print("The categories are:\n")
        for i in range(len(list[1])):
            print(f"{list[1][i]}")

        category = input("Enter the category:")

        if task.isalpha():
            break
    print("YYYYYYY")

def getCategory(id):
    category = ["Food", "Chores", "Exercise", "Work", "Other"]
    for i in range(5):
        if id == i:
            return category[i]

def displayList(list):
    print("------------------------------------------\nTask\t\t\t\tCategory\n")
    for i in range(len(list[0])):
        print(f"{list[0][i]}\t\t\t{getCategory(list[1][i])}")

def main():
    tasksList = [["Eat Breakfast", "Do homework", "Clean room", "Go to gym"], [0,3,1,2]]
    displayList(tasksList)

    userInput = 0
    
    print("Press 1 to enter a new task, 2 to delete a task or 3 to refresh the task list.")
    
    while True:
    
        userInput = input("Enter a value from 1-3: ")

        try: 
            int(userInput) not in range(1,3)
            break
        except:
            continue
    
    if userInput == "1":
        tasksList = enterNewTask(tasksList)

main()