package us.uplaw.util.db;

public class SqlCommands {

	public static String documentTable(){
		String documentPsql="-- auto-generated definition\n" + "create table document\n" + "(\n"
				+ "  id        serial not null\n" + "    constraint document_pkey\n" + "    primary key,\n"
				+ "  body      json,\n" + "  timestamp varchar(99) default CURRENT_TIME\n" + ");\n" + "\n"
				+ "alter table document\n" + "  owner to postgres;\n" + "\n" + "create index ix_document_timestamp\n"
				+ "  on document (timestamp);\n" + "\n";
		return documentPsql;
	}
}
