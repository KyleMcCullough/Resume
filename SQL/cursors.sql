USE [Chinook]
GO

/****** Object:  Table [dbo].[DiscontinuedArtists]    Script Date: 4/15/2020 4:33:21 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DiscontinuedArtists](
	[DiscArtistID] [int] IDENTITY(1,1) NOT NULL,
	[OriginalArtistID] [int] NOT NULL,
	[ArtistName] [varchar](120) NULL,
	[DiscontinuedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_DiscontinuedArtists] PRIMARY KEY CLUSTERED 
(
	[DiscArtistID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

create or alter proc CheckInActiveArtists
as

begin tran

--Make it so it doesn't say how many lines were changed
SET NOCOUNT ON
drop table if exists temp

--Create a new temporary table used to make cursor much faster as it only needs
--To check this temporary table, not the entire song table for each artist.
create table temp(
	[TrackID] [int] NOT NULL,
	[TrackName] nvarchar(200) NOT NULL,
	[PlayListTitle] nvarchar(160),
	[Composers] [nvarchar](220) NULL)

--Create cursors
declare emp_checkDiscontinuedArtists Cursor for
	select ArtistId, Name from Artist

declare emp_checkSongList Cursor for
	select TrackID, TrackName, PlayListTitle, Composers from temp

print 'Report: All Artists By Album and Track

#  ID    Artist      Album						Track Name								Composer
--------------------------------------------------------------------------------------------------------------------------------------------------------'      

declare @artistID int, @artistName nvarchar(120), @TrackID int, @TrackName nvarchar(200), @PlayListTitle nvarchar(160), @TrackComposers nvarchar(220), @TotalSongCount int = 0

open emp_checkDiscontinuedArtists

fetch next from emp_checkDiscontinuedArtists into @artistID, @artistName
while @@FETCH_STATUS = 0
	begin

	--Empty the temporary table then insert all tracks with the Artists ID
	delete from temp
	insert into temp(TrackID, [TrackName], [PlayListTitle], [Composers]) select 
	Track.TrackId, Track.Name, a.Title, Track.Composer from Track
	inner join Album a on Track.AlbumId = a.AlbumId where ArtistId = @artistID;

	insert into temp(TrackID, [TrackName], [PlayListTitle], [Composers]) select 
	Track.TrackId, Track.Name, a.Title, Track.Composer from Track
	inner join Album a on Track.AlbumId = a.AlbumId where Composer is null and ArtistId = @artistID;

	--If there is atleast 1 song in the temporary table, enter here
	if (select count(*) from temp) > 0
	begin
		open emp_checkSongList
			fetch next from emp_checkSongList into @TrackId, @TrackName, @PlayListTitle, @TrackComposers
			while @@FETCH_STATUS = 0
			begin  

				--Try to print the song report
				begin try
					set @TotalSongCount = @TotalSongCount + 1
					
					--If the song has composers, print this report message. Otherwise, print the other.
					if @TrackComposers is not null
					begin
						print cast(@TotalSongCount as nvarchar) + ': ' + cast(@artistID as nvarchar) + '  -  ' + @artistName + '  -  ' +  isnull(@PlayListTitle, 'none') + '  -  ' + @TrackName + '  -  ' + @TrackComposers
						fetch next from emp_checkSongList into @TrackId, @TrackName, @PlayListTitle, @TrackComposers
					end
					else
					begin
						print cast(@TotalSongCount as nvarchar) + ': ' + cast(@artistID as nvarchar) + '  -  ' + @artistName + '  -  ' +  isnull(@PlayListTitle, 'none') + '  -  ' + @TrackName + '  -  ' + 'UNKNOWN COMPOSER'
						fetch next from emp_checkSongList into @TrackId, @TrackName, @PlayListTitle, @TrackComposers
					end
				end try

				--If an error occurs, print in error message with the trackID and trackName the cursor had an error on.
				begin catch
					print 'Error printing song report at song: ' + @TrackID + ' ' + @TrackName
					rollback
				end catch
			end
			close emp_checkSongList
			fetch next from emp_checkDiscontinuedArtists into @artistID, @artistName
	end

	--If there is not an entry in the temporary table
	else
	begin
		set @TotalSongCount = @TotalSongCount + 1

		print cast(@TotalSongCount as nvarchar) + ': ' + cast(@artistID as nvarchar) + '  -  ' + @artistName + ' NO ALBUMS OR TRACKS'
		
		--If the artist already exists in the DiscontinuedArtists table it will skip this line.
		begin try
			if not exists (select OriginalArtistID from DiscontinuedArtists where OriginalArtistID = @artistID)
			begin
				insert into DiscontinuedArtists select ArtistId, Name, sysdatetime() from Artist where ArtistId = @artistID
				delete from Artist where ArtistId = @artistID
			end
		end try
		begin catch
			print 'Error deleting or inserting arist ' + @artistID + ' ' + @artistName
			rollback
		end catch
		fetch next from emp_checkDiscontinuedArtists into @artistID, @artistName
	end

	end 

drop table temp
close emp_checkDiscontinuedArtists
deallocate emp_checkDiscontinuedArtists
deallocate emp_checkSongList
commit tran

--Deletion Statements
delete from DiscontinuedArtists

--Test 1
exec CheckInActiveArtists

select * from DiscontinuedArtists

--Change artist ID to match artist in DiscontinuedArtists table
select * from Artist where ArtistId = 183

--Test 2
insert into Artist values ('TEST'), ('TEST'), ('TEST'), ('TEST'), ('TEST')
select * from Artist where name = 'TEST'
exec CheckInActiveArtists
select * from Artist where name = 'TEST'
select * from DiscontinuedArtists where ArtistName = 'TEST'
delete from DiscontinuedArtists where ArtistName = 'TEST'