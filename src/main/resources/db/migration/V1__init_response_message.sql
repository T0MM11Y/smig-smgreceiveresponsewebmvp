-- Create sequence for response message IDs
create sequence resp_seq start with 1 increment by 1 nocache nocycle;

-- Create main table
create table response_message (
   id         number(19) primary key,
   content    varchar2(4000) not null,
   created_at timestamp default current_timestamp not null,
   updated_at timestamp default current_timestamp not null
);

-- Create index for performance
create index idx_response_message_created_at on
   response_message (
      created_at
   );

-- Create trigger for updated_at
create or replace trigger trg_response_message_updated_at before
   update on response_message
   for each row
begin
   :new.updated_at := current_timestamp;
end;
/