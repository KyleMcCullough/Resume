use Chinook

--Add new Paid column, set to default.
alter table Invoice
add Paid bit default 0;

-- Ensures new Paid field is set to 0
update Invoice set paid = 0;


--Create new InvoicePayment table

USE [Chinook]
GO

/****** Object:  Table [dbo].[InvoicePayment]    Script Date: 2/25/2020 12:48:19 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[InvoicePayment](
	[PaymentID] [int] IDENTITY(1,1) NOT NULL,
	[InvoiceID] [int] NOT NULL,
	[CustomerID] [int] NOT NULL,
	[PaymentDate] [datetime] NOT NULL,
	[Amount] [numeric] NOT NULL,
 CONSTRAINT [PK_InvoicePayment] PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[InvoicePayment]  WITH CHECK ADD  CONSTRAINT [FK_InvoicePayment_Customer] FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customer] ([CustomerId])
GO

ALTER TABLE [dbo].[InvoicePayment] CHECK CONSTRAINT [FK_InvoicePayment_Customer]
GO

ALTER TABLE [dbo].[InvoicePayment]  WITH CHECK ADD  CONSTRAINT [FK_InvoicePayment_Invoice] FOREIGN KEY([InvoiceID])
REFERENCES [dbo].[Invoice] ([InvoiceId])
GO

ALTER TABLE [dbo].[InvoicePayment] CHECK CONSTRAINT [FK_InvoicePayment_Invoice]
GO

--uspAddNewInvoice proc

create or alter proc uspAddNewInvoice
	
	-- Declares variables and their type
	@customerID int,
	@address varchar(70),
	@city varchar(40),
	@state varchar(40),
	@country varchar(40),
	@postCode char(6),
	@price numeric,
	@amount int,
	@paid bit = 0
as
Begin tran
	
	begin try
		-- Inserts values into Invoice
		insert into Invoice(CustomerId, InvoiceDate, BillingAddress, BillingCity, BillingState, BillingCountry, BillingPostalCode, Total, Paid)
		values (@customerID, SYSDATETIME(), @address, @city, @state, @country, @postCode, @amount * @price, @paid)
	end try

	begin catch
		--Rolls back if an error occurs
		rollback tran
	end catch
commit tran

go

-- uspAddInvoicePayment
create or alter proc uspAddInvoicePayment
	
	-- Defining Variables
	@invoiceID int,
	@customerID int,
	@amount numeric
as
begin tran
	begin try

		-- Insert values into InvoicePayment
		insert into InvoicePayment(InvoiceID, CustomerID, PaymentDate, Amount) values (@invoiceID, @customerID, SYSDATETIME(), @amount)
	end try
	begin catch
		-- Rollback if an error occurs
		rollback tran
	end catch
commit tran

go

-- uspRunInvoices, moves all existing entries to the InvoicePayment table.


create or alter proc uspRunInvoices
as

--Declare variables
declare @count int;
declare @index int;
declare @CustomerID int;
declare @Total numeric;
select @count = max(InvoiceId) from Invoice;
set @index = 1

-- While index is less then the total number of records, loop.
while (@index <= @count)
	begin
	
	begin tran
		begin try

			-- Select CustomerId, Total and pass the CustomerId, Total and index value to the uspAddInvoicePayment procedure
			select @CustomerID = CustomerId from Invoice where InvoiceId = @index
			select @Total = Total from Invoice where InvoiceId = @index
			exec uspAddInvoicePayment @invoiceID = @index, @customerID = @CustomerID, @amount = @Total
			set @index += 1;
		end try

	begin catch
		rollback tran
	end catch
	commit tran
	end

go

--Procedure to check all payments and see if they have been paid
create or alter proc uspCheckPaidInvoices
as

-- Declare variables
declare @count int;
declare @index int;
declare @invoiceAmount numeric;
declare @amountDue numeric;


select @count = max(PaymentID) from InvoicePayment;
set @index = 1


while (@count >= @index)
	begin
		begin tran
			begin try
				
				select @invoiceAmount = Amount from InvoicePayment
				select @amountDue = Total from Invoice
				if @invoiceAmount >= @amountDue
				begin 
					update Invoice set Paid = 1 from Invoice where InvoiceId = @index
					set @count += 1;
				end
			end try 
		
			begin catch
				rollback tran
			end catch
		end
	commit tran
go

-- Add new invoice record
uspAddNewInvoice	@customerID = 6,
	@address = '123 Test Street',
	@city = 'Super City',
	@state = 'Super Land',
	@country = 'Super World',
	@postCode = '123456',
	@price = 5.99,
	@amount = 2

go

select * from Invoice where CustomerId = 6

go

-- Add new invoice payment record for previously created record

uspAddInvoicePayment
	@invoiceID = 3,
	@customerID = 6,
	@amount = 500

go

select * from InvoicePayment where CustomerID = 6;

go

-- Revert all paid invoices to unpaid status
update Invoice set paid = 0;
select * from Invoice;

go

-- Statement to remove all existing records from InvoicePayment

delete from InvoicePayment;
select * from InvoicePayment;

-- Statement to re-create all invoicePayment records

exec uspRunInvoices;
select * from InvoicePayment;

-- Statement to recheck paid or not
exec uspCheckPaidInvoices;
select * from Invoice where Paid = 1;