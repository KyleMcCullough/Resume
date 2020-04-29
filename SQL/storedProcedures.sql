USE [Chinook]
GO

/****** Object:  Table [dbo].[RecordLogging]    Script Date: 3/10/2020 12:43:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

drop table if exists RecordLogging
CREATE TABLE [dbo].[RecordLogging](
	[LogID] [int] IDENTITY(1,1) NOT NULL,
	[TableName] [varchar](30) NULL,
	[RecordID] [int] NULL,
	[ActionType] [varchar](30) NOT NULL,
	[IsError] [bit] NOT NULL,
	[ErrorNum] [int] NULL,
	[LogDate] [datetime] NOT NULL,
 CONSTRAINT [PK_RecordLogging] PRIMARY KEY CLUSTERED 
(
	[LogID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO





create or alter proc addRecordLog

	@tableName varchar(30) = null,
	@RecordID int = null,
	@ActionType varchar(30),
	@errorNum int = null 

as

begin tran
		set XACT_ABORT ON
		print 'test'

		--If there was no error code sent, go into if statement
		if @errorNum is null
		
		begin

			-- Try to insert the values into the Recordlogging table
			begin try

			insert into dbo.RecordLogging(TableName, RecordID, [ActionType], IsError, ErrorNum, LogDate)
			values (@tableName, @RecordID, @ActionType, 0, 0, SYSDATETIME())

			end try

			-- If an error occurs, grab the error number and input it into the Recordlogging table
			begin catch 

				insert into dbo.RecordLogging(TableName, RecordID, [ActionType], IsError, ErrorNum, LogDate)
			values (@tableName, @RecordID, @ActionType, 1, ERROR_NUMBER(), SYSDATETIME())
			end catch
		end

		-- If there was an error statement sent to the proc, insert it into the table.
		else

		begin
		print 'error print'
		insert into dbo.RecordLogging(TableName, RecordID, ActionType, IsError, ErrorNum, LogDate)
		values (@tableName, @RecordID, @ActionType, 1, @errorNum, SYSDATETIME())

		end

commit tran

go

-- Testing for directly adding to the RecordLogging table
delete RecordLogging
addRecordLog 'WEWE', 10, 'HELP'
select * from RecordLogging;

go

create or alter proc uspAlbum_DeleteByID
	@RecordID int

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to delete the value from the table
	begin try
		delete from Album where AlbumId = @RecordID 
		exec addRecordLog 'Album', @RecordID, 'DELETE'
		commit tran
	end try

	-- If an error occurs jump to the catch block, get the error number and send it to te addRecordLog proc.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Album', @RecordID = @RecordID, @ActionType = 'DELETE', @errorNum = @errorID
	end catch

go

select * from Album;
select * from RecordLogging;

go

insert into dbo.Album values ('testing', 1)
select * from album where Title = 'testing'

go

-- Testing for Track procs
uspAlbum_DeleteByID 348
select * from Album where AlbumId = 348
select * from RecordLogging where TableName = 'Album'

go

create or alter proc uspAlbum_Insert
	@albumName varchar(50),
	@singerID int,
	@newAlbumID int = null output

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to insert the values into the table, then select the id and store it for output.
	-- Then send the data to the addRecordLog to log.
	begin try
		insert into dbo.Album values (@albumName, @singerID)
		select @newAlbumID = AlbumId from Album where Title = @albumName and ArtistId = @singerID
		exec addRecordLog 'Album', @newAlbumID, 'INSERT'
		commit tran
	end try

	-- If an error occurs then get the error and send it to the addRecordLog.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Album', @RecordID = @newAlbumID, @ActionType = 'INSERT', @errorNum = @errorID
	end catch

go

-- Testing for Album insert proc
select * from RecordLogging
select * from Album where Title = 'hello world' and ArtistId = 12;
uspAlbum_Insert 'hello world', 12

go

create or alter proc uspArtist_DeleteByID
	@RecordID int

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to delete the value from the table
	begin try
		delete from Artist where ArtistId = @RecordID 
		exec addRecordLog 'Artist', @RecordID, 'DELETE'
		commit tran
	end try

	-- If an error occurs jump to the catch block, get the error number and send it to te addRecordLog proc.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Artist', @RecordID = @RecordID, @ActionType = 'DELETE', @errorNum = @errorID
	end catch

go

create or alter proc uspArtist_Insert
	@artistName varchar(50) = null,
	@newArtistID int = null output

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to insert the values into the table, then select the id and store it for output.
	-- Then send the data to the addRecordLog to log.
	begin try
		insert into dbo.Artist values (@artistName)
		select @newArtistID = ArtistId from Artist where name = @artistName
		exec addRecordLog 'Artist', @newArtistID, 'INSERT'
		commit tran
	end try

	-- If an error occurs then get the error and send it to the addRecordLog.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Artist', @RecordID = @newArtistID, @ActionType = 'INSERT', @errorNum = @errorID
	end catch

go

-- Testing for Artist procs
uspArtist_DeleteByID 2
uspArtist_Insert 'Jamie'

select * from Artist where ArtistId = 2
select * from Artist where name = 'Jamie'
select * from RecordLogging

go

create or alter proc uspGenre_DeleteByID
	@RecordID int

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to delete the value from the table
	begin try
		delete from Genre where GenreId = @RecordID 
		exec addRecordLog 'Genre', @RecordID, 'DELETE'
		commit tran
	end try

	-- If an error occurs jump to the catch block, get the error number and send it to te addRecordLog proc.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Genre', @RecordID = @RecordID, @ActionType = 'DELETE', @errorNum = @errorID
	end catch

go

create or alter proc uspGenre_Insert
	@genreName varchar(50) = null,
	@newGenreID int = null output

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to insert the values into the table, then select the id and store it for output.
	-- Then send the data to the addRecordLog to log.
	begin try
		insert into dbo.Genre values (@genreName)
		select @newGenreID = GenreId from Genre where name = @genreName
		exec addRecordLog 'Genre', @newGenreID, 'INSERT'
		commit tran
	end try

	-- If an error occurs then get the error and send it to the addRecordLog.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Genre', @RecordID = @newGenreID, @ActionType = 'INSERT', @errorNum = @errorID
	end catch

go

-- Testing for Genre procs
uspGenre_DeleteByID 2
uspGenre_Insert 'testing'

select * from Genre
select * from Genre where name = 'testing'
select * from RecordLogging where TableName = 'Genre'

go

create or alter proc uspMediaType_DeleteByID
	@RecordID int

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to delete the value from the table
	begin try
		delete from MediaType where MediaTypeId = @RecordID 
		exec addRecordLog 'MediaType', @RecordID, 'DELETE'
		commit tran
	end try

	-- If an error occurs jump to the catch block, get the error number and send it to te addRecordLog proc.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'MediaType', @RecordID = @RecordID, @ActionType = 'DELETE', @errorNum = @errorID
	end catch

go

create or alter proc uspMediaType_Insert
	@Name varchar(50) = null,
	@newMediaTypeID int = null output

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to insert the values into the table, then select the id and store it for output.
	-- Then send the data to the addRecordLog to log.
	begin try
		insert into dbo.MediaType values (@Name)
		select @newMediaTypeID = MediaTypeId from MediaType where name = @Name
		exec addRecordLog 'MediaType', @newMediaTypeID, 'INSERT'
		commit tran
	end try

	-- If an error occurs then get the error and send it to the addRecordLog.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'MediaType', @RecordID = @newMediaTypeID, @ActionType = 'INSERT', @errorNum = @errorID
	end catch
	
go

-- Testing for MediaType procs
uspMediaType_DeleteByID 1
uspMediaType_Insert 'testing'

select * from MediaType
select * from MediaType where name = 'testing'
select * from RecordLogging where TableName = 'MediaType'

go

create or alter proc uspTrack_DeleteByID
	@RecordID int

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to delete the value from the table
	begin try
		delete from Track where TrackId = @RecordID 
		exec addRecordLog 'Track', @RecordID, 'DELETE'
		commit tran
	end try

	-- If an error occurs jump to the catch block, get the error number and send it to te addRecordLog proc.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Track', @RecordID = @RecordID, @ActionType = 'DELETE', @errorNum = @errorID
	end catch

go
create or alter proc uspTrack_Insert
	@Name varchar(200) = null,
	@albumID int = null,
	@mediaTypeID int,
	@GenreID int = null,
	@composer varchar(220) = null,
	@milliseconds int,
	@bytes int = null,
	@unitPrice numeric(10,2),
	@newTrackID int = null output

as

begin tran

declare @errorID int;
set @errorID = null;

	-- Try to insert the values into the table, then select the id and store it for output.
	-- Then send the data to the addRecordLog to log.
	begin try
		insert into dbo.Track(Name, AlbumID, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice) 
		values (@Name, @albumID, @mediaTypeID, @GenreID, @composer, @milliseconds, @bytes, @unitPrice)
		select @newTrackID = trackId from Track where name = @Name
		exec addRecordLog 'Track', @newTrackID, 'INSERT'
		commit tran
	end try

	-- If an error occurs then get the error and send it to the addRecordLog.
	begin catch
		set @errorID = ERROR_NUMBER()
		rollback tran
		exec addRecordLog @tableName = 'Track', @RecordID = @newTrackID, @ActionType = 'INSERT', @errorNum = @errorID
	end catch

go

-- Testing for Track procs
uspTrack_DeleteByID 1
uspTrack_Insert 'testing', 1, 1, 1, 'testing', 23455423, 122331212, 1.99

select * from Track
select * from Track where name = 'testing'
select * from RecordLogging where TableName = 'Track'
