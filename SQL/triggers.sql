USE [Chinook]
GO

/****** Object:  Table [dbo].[EmployeeCommission]    Script Date: 4/15/2020 3:02:09 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EmployeeCommission](
	[CommissionID] [int] IDENTITY(1,1) NOT NULL,
	[EmployeeID] [int] NOT NULL,
	[InvoiceID] [int] NOT NULL,
	[CommMonth] [int] NOT NULL,
	[CommYear] [int] NOT NULL,
	[CommAmount] numeric NOT NULL,
 CONSTRAINT [PK_EmployeeCommission] PRIMARY KEY CLUSTERED 
(
	[CommissionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmployeeCommission]  WITH CHECK ADD  CONSTRAINT [FK_EmployeeCommission_Employee] FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([EmployeeId])
GO

ALTER TABLE [dbo].[EmployeeCommission] CHECK CONSTRAINT [FK_EmployeeCommission_Employee]
GO

--Add new Columns to existing tables
ALTER TABLE Employee ADD CommissionAmountToDate numeric not null default 0;
ALTER TABLE Customer ADD NumBonusesToDate int not null default 0;

--Add new Chinook Bonus Track voucher with basic values
insert into Track(Name, MediaTypeId, Milliseconds, UnitPrice) values ('Chinook Bonus Track Voucher', 1, 1, 2.99);
select * from Track where Name like 'Chinook%'		-- TrackID = 3504

go

--Trigger to Enter data from transaction into EmployeeCommision table, calculating the commision aswell.
create or alter trigger trgInvoice_AddEmployeeCommission
on Invoice
for insert

as

begin
	declare @employeeID int, @total numeric, @invoiceID int, @date datetime;
	select @employeeID = c.SupportRepId from inserted 
	inner join Customer c on inserted.CustomerId = c.CustomerId

	select @total = total from inserted
	select @invoiceID = InvoiceId from inserted
	select @date = InvoiceDate from inserted
	insert into EmployeeCommission values (@employeeID, @invoiceId, month(@date), year(@date), @total * .1 )
end

--Statements to check trigger works
insert into Invoice values (1, SYSDATETIME(), null,null,null,null,null,10);
select * from EmployeeCommission;
select * from Invoice where BillingAddress is null;

go

--Deletion trigger to Delete CommisionRecored if the related Invoice is deleted
create or alter trigger trgInvoice_DeleteEmployeeCommission
on Invoice
for delete

as

begin
	declare @invoiceID int;
	select @invoiceID = invoiceID from deleted
	delete from EmployeeCommission where InvoiceID = @invoiceID
end

--Statements to check trigger
select * from Invoice where BillingAddress is null;
delete from Invoice where InvoiceId = 413
select * from EmployeeCommission;

go

--Insert trigger to add Commision amount from Employee if EmployeeCommission record is created
create or alter trigger trgEmpComm_CreditEmployeeCommToDate
on EmployeeCommission
for insert

as

begin
	declare @CommAmount numeric, @employeeID int, @employeePreviousComm numeric;
	select @CommAmount = CommAmount from inserted
	select @employeeID = EmployeeID from inserted
	select @employeePreviousComm = CommissionAmountToDate from Employee where EmployeeId = @employeeID

	update Employee set CommissionAmountToDate = @CommAmount + @employeePreviousComm where EmployeeId = @employeeID
end

go

--Statements to test trigger
insert into Invoice values (1, SYSDATETIME(), null,null,null,null,null,10);
select * from EmployeeCommission;
select * from Invoice where BillingAddress is null;
select * from Employee where EmployeeId = 3

go

--Insert trigger to retract Commision amount from Employee if EmployeeCommission record is deleted
create or alter trigger trgEmpComm_DebitEmployeeCommToDate
on EmployeeCommission
for delete

as

begin
	declare @CommAmount numeric, @employeeID int, @employeePreviousComm numeric;
	select @CommAmount = CommAmount from deleted
	select @employeeID = EmployeeID from deleted
	select @employeePreviousComm = CommissionAmountToDate from Employee where EmployeeId = @employeeID

	update Employee set CommissionAmountToDate = @employeePreviousComm - @CommAmount where EmployeeId = @employeeID
end

--Statements to check trigger
select * from Invoice where BillingAddress is null;
delete from Invoice where InvoiceId = 418
select * from EmployeeCommission;
select * from Employee where EmployeeId = 3

go

--Trigger to check if CUstomer qualifys to bonus track voucher. If they do, record that both
--In the InvoiceLine table and the Customer table.
create or alter trigger trgInvoice_AddBonusTrackVoucher
on Invoice
for insert

as

begin
	declare @total numeric, @customerID int, @invoiceID int, @bonuses int;
	select @total = Total from inserted
	select @customerID = CustomerId from inserted
	select @invoiceID = InvoiceId from inserted
	select @bonuses = NumBonusesToDate from Customer where CustomerId = @customerID

	if @total > 20
	begin
		begin try
			insert into InvoiceLine values (@invoiceID, 3504, 0, 1);
			update Customer set NumBonusesToDate = @bonuses + 1 where CustomerId = @customerID
		end try
		begin catch
			print 'Error adding Track voucher to Customer.'
		end catch
	end
end

--Statements to check if trigger is functioning properly.
insert into Invoice values (1, SYSDATETIME(), 'testing',null,null,null,null,1000);
select * from Customer where CustomerId = 1;
select * from Invoice where BillingAddress = 'testing';
select * from InvoiceLine 
inner join Invoice i on InvoiceLine.InvoiceId = i.InvoiceId
where i.Total = 1000