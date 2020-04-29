--Using microsoft sql server & the Chinook, Bookstore, Numbers, Cars and Lunches test databases.


--Question 1 Display the First Name, Last Name of each customer along with the First Name and Last Name of their support
--rep, sorted by customer last and first names. Give the support rep columns an appropriate alias.

select Customer.FirstName as Customer_First, Customer.LastName as Customer_Last, Employee.FirstName as Employee_First, Employee.LastName as Employee_Last
from dbo.Customer
inner join dbo.Employee on Customer.SupportRepId = Employee.EmployeeId
order by Customer_first, Customer_Last;

--Question 2 Display the track name, genre name, and mediatype name for each track in the database, sorted by track name.

select Track.Name as Track_Name, Genre.Name as Genre, MediaType.Name as MediaType from dbo.Track
inner join dbo.Genre on track.GenreId = Genre.GenreId
inner join dbo.MediaType on track.MediaTypeId = MediaType.MediaTypeId
order by Track_Name;

--Question 3 Display the name of every artist and the total number of albums each artist has available for sale. Results
--should show the highest totals first.

select Artist.Name, count(*) as Album_Count from Album
inner join dbo.Artist on Album.ArtistId = Artist.ArtistId
group by Artist.Name
order by count(*) desc;

--Question 4 Display the first name and last name of each customer along with a unique list of the types of media that
--they have purchased.

select Customer.FirstName, Customer.LastName, Track.MediaTypeId  from Customer
inner join dbo.invoice on Customer.CustomerId = invoice.CustomerId
inner join dbo.InvoiceLine on invoice.InvoiceId = InvoiceLine.InvoiceId
inner join dbo.Track on InvoiceLine.TrackId = Track.TrackId
group by Customer.FirstName, Customer.LastName, Track.MediaTypeId
order by Customer.LastName, Customer.FirstName;

--Question 5 Display the first name and last name of the single customer who has purchased the most video tracks.

select top 1 Customer.FirstName, Customer.LastName, count(*) as Purchase_Count from InvoiceLine
inner join dbo.Invoice on InvoiceLine.InvoiceId = invoice.InvoiceId
inner join dbo.Customer on Invoice.CustomerId = Customer.CustomerId
inner join dbo.Track on InvoiceLine.TrackId = Track.TrackId
where Track.MediaTypeId = 3
group by Track.MediaTypeId, Customer.FirstName, Customer.LastName
order by count(*) desc;

--Question 6 Display the name of the artist and number of orders for the single artist who has had the highest number
--orders of his/her music placed.

select top 1 Artist.Name, count(*) as Purchase_Count from Artist
inner join dbo.Album on Artist.ArtistId = Album.ArtistId
inner join dbo.PlaylistTrack on Album.AlbumId = PlaylistTrack.PlaylistId
inner join dbo.Track on PlaylistTrack.TrackId = Track.TrackId
inner join dbo.InvoiceLine on Track.TrackId = InvoiceLine.TrackId
group by Artist.Name
order by count(*) desc;

--Question 7 Display the TrackID and Track Name of any tracks that have not yet been purchased.

select Track.TrackId, Track.Name from Track
left join dbo.InvoiceLine on Track.TrackId = InvoiceLine.TrackId
where InvoiceLine.TrackId is null;

--Question 8 Using the “b_” tables, display the first and last names of all authors who currently do not have any books
--listed, sorted author last/first name.

select B_AUTHOR.Lname, B_AUTHOR.Fname from Bookstore.dbo.B_author
left join Bookstore.dbo.B_BOOKAUTHOR on B_AUTHOR.AuthorID = B_BOOKAUTHOR.AUTHORid
where B_BOOKAUTHOR.AUTHORid is null
order by B_AUTHOR.Lname, B_AUTHOR.Fname

--Question 9 Using the “b_” tables, display the Customer number, First name, and Last name of any customers who have yet
--to place an order, sorted customer last/first name.

select B_CUSTOMERS.Customer#, B_CUSTOMERS.FirstName, B_CUSTOMERS.LastName from Bookstore.dbo.B_CUSTOMERS
left join Bookstore.dbo.B_ORDERS on B_CUSTOMERS.Customer# = B_ORDERS.Customer#
where B_ORDERS.Customer# is null
order by B_CUSTOMERS.LastName, B_CUSTOMERS.FirstName;

--Question 10 Using the Cars_Car_Types, Cars_Number_Of_Doors and Cars_Colors tables, create a query that returns every
--possible combination of the values of each table.

select CAR_TYPE, COLOR, DOORS from CarsDB.dbo.CARS_CAR_TYPES
cross join CarsDB.dbo.CARS_COLORS
cross join CarsDB.dbo.CARS_NUMBER_OF_DOORS

--Question 11 List the employee ID, last name, and phone number of each employee with the name and phone number of his
--or her manager. Make sure that every employee is listed, even those that do not have a manager. Sort by the employee’s id number.

select la.EMPLOYEE_ID, la.LAST_NAME, la.PHONE_NUMBER, concat(l.FIRST_NAME, ' ', l.LAST_NAME) as Manager_Name, l.PHONE_NUMBER from LunchesDB.dbo.L_EMPLOYEES as la
right join LunchesDB.dbo.L_EMPLOYEES l on l.MANAGER_ID = la.EMPLOYEE_ID
where la.MANAGER_ID is null or la.MANAGER_ID is not null

union

select EMPLOYEE_ID, LAST_NAME, PHONE_NUMBER, NULL, NULL from LunchesDB.dbo.L_EMPLOYEES as l
where MANAGER_ID is null and EMPLOYEE_ID is not null
order by 1

--Question 12 Create one full list of first names and last names of all customers from the Chinook tables, all authors
--from the Bookstore tables, all customers from the Bookstore tables, and all employees from the Lunches tables. Sort the
--list by last name and first name in ascending order.

select FirstName COLLATE DATABASE_DEFAULT as First_Name, LastName COLLATE DATABASE_DEFAULT as Last_Name from Chinook.dbo.Customer
union
select Fname COLLATE DATABASE_DEFAULT, Lname COLLATE DATABASE_DEFAULT from Bookstore.dbo.B_AUTHOR
union
select FirstName COLLATE DATABASE_DEFAULT, LastName COLLATE DATABASE_DEFAULT from Bookstore.dbo.B_CUSTOMERS
union
select FIRST_NAME COLLATE DATABASE_DEFAULT, LAST_NAME COLLATE DATABASE_DEFAULT from LunchesDB.dbo.L_EMPLOYEES
order by 1,2;

--Question 13 Using the Numbers_Twos and Numbers_Threes tables, show the results of a query that only displays numbers
--that do not have a matching value in the other table.

select Twos from NumbersDB.dbo.Multiples_Of_Three
full join NumbersDB.dbo.Multiples_Of_Two on Multiples_Of_Two.Twos = Multiples_Of_Three.Threes
where Twos is not null;

--Question 14 Using the Numbers_Twos and Numbers_threes tables, show the results of a query that only displays numbers
--that have a matching value in the other table. Here’s the catch: You are not permitted to use a WHERE clause or JOINs for this query.

select * from NumbersDB.dbo.Multiples_Of_Three
except
select * from NumbersDB.dbo.Multiples_Of_Two

