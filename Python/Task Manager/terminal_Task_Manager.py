

def enterNewTask():
    pass

def getCategory(id):
    
    category = ["Food", "Chores", "Exercise", "Work", "Other"]
    for i in range(5):
        if list[1][i] == i:
            return category[i]

def displayList(list):
    for i in range(len(list[0])):


        print(f"{list[0][i]}\t\t\t{getCategory(list[1][i])}", end="")

def main():

    
    tasksList = [["Eat Breakfast", "Do homework", "Clean room", "Go to gym"], [0,3,1,2]]
    
    userInput = 0
    displayList(tasksList)

main()